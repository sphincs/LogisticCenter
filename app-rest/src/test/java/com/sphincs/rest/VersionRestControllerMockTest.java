package com.sphincs.rest;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import javax.annotation.Resource;

@ContextConfiguration(locations = {"classpath*:/spring-rest-mock-test.xml"})
public class VersionRestControllerMockTest extends AbstractTestNGSpringContextTests{

    private MockMvc mockMvc;

    @Resource
    private VersionRestController versionRestController;

    @BeforeClass
    public void setUp() {
        this.mockMvc = standaloneSetup(versionRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void getRestApiVersionTest() throws Exception {
        this.mockMvc.perform(
                get("/version", "Empty")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("\"1.0\""));
    }
}
