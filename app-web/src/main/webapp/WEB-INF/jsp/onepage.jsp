<html>
<head>
    <title>Logistic Center</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <meta charset="UTF-8">
    <style type="text/css">
        .block1 {
            width: 270px;
            padding: 10px;
            float: left;
        }
        .block2 {
            padding-left: 50px;
            float: left;
            position: relative;
        }
        .block3 {
            width: 270px;
            padding: 10px;
            float: left;
        }
        .block4 {
            padding-left: 50px;
            float: left;
            position: relative;
        }
        .error {
            color: red;
        }
    </style>
</head>
<body>

<fieldset>
    <legend>Driver manager</legend>
    <div class="block1" id="block1">
        <form id="driver_form" onsubmit="return false" >
            <input type="text" name="driverId" placeholder="driverId" id="driverId" disabled/><br/>
            <input type="text" name="name" placeholder="name" id="name"/><br/>
            <input type="text" name="age" placeholder="age" id="age"/><br/>
            <br/>
            <button type="button" id="add_driver">Add</button>
            <button type="button" id="update_driver" disabled >Update</button><br/>
            <p class="error" id="driver_error"></p>
        </form>
    </div>
    <div class="block2" id="block2"/>
</fieldset>


<fieldset>
    <legend>Trip manager</legend>
    <div class="block3" id="block3">
        <form id="trip_form" onsubmit="return false">
            <input type="text" name="tripId" placeholder="tripId" id="tripId" disabled/><br/>
            <input type="text" name="driverName" placeholder="driverName" id="driverName"/><br/>
            <input type="text" name="car" placeholder="car" id="car"/><br/>
            <input type="text" name="fuelRate100" placeholder="fuelRate100" id="fuelRate100"/><br/>
            <input type="text" name="startPoint" placeholder="startPoint" id="startPoint"/><br/>
            <input type="text" name="endPoint" placeholder="endPoint" id="endPoint"/><br/>
            <input type="text" name="distance" placeholder="distance" id="distance"/><br/>
            <input type="date" name="startDate" value="2016-08-23" id="startDate"/><br/>
            <input type="date" name="endDate" value="2016-08-24" id="endDate"/><br/>
            <input type="text" name="sumFuel" placeholder="sumFuel" id="sumFuel" disabled/><br/>

            <button type="button" id="add_trip">Add</button>
            <button type="button" id="update_trip" disabled>Update</button><br/>
            <p class="error" id="trip_error"></p>

            <p id="fuel_driver_text" style="width: 200px">Show the amount of fuel consumed by this driver</p>
            <select class="select" id="select" style="width: 150px"></select>
            <button type="button" id="fuel_by_driver">Show</button>

            <p id="fuel_dates_text" style="width: 200px">Show the amount of fuel consumed between this dates</p>
            <p>From: </p><input type="date" id="from"/><br/>
            <p>To: </p><input type="date" id="to"/><br/>
            <button type="button" id="fuel_by_dates">Show</button>
        </form>
    </div>
    <div class="block4" id="block4"/>
</fieldset>


<script>

    $(document).ready(function(){
        displayDrivers();
        displayTrips();
    });

    <%-- For Drivers --%>

    var displayDrivers = function() {
        $.ajax({
            method          : "GET",
            url             : 'http://localhost:9090/drivers/all/',
            contentType     : "application/json; charset=utf-8",
            processData     : false,
            dataType        : 'json',
            complete        : displayAllDrivers
        })
    };

    var displayAllDrivers = function (data) {
        $("#block2").empty();
        $("#select").empty();
        var table = document.createElement('table');
        table.setAttribute("border", "2");
        $(table).append('<tr><td width="50">ID</td><td width="150">Name</td><td width="50">Age</td></tr>')
        block2.appendChild(table);
        $.each(data.responseJSON, function (index, value) {
            $(table).append('<tr><td>' + value.id + '</td><td>' + value.name + '</td><td>' + value.age + '</td><td>'+
                    '<button type="button" class="update_button" data-id="'+value.id+'">Update</button></td><td>' +
                    '<button type="button" class="remove_button" data-id="'+value.id+'">Delete</button></td></tr>');
            $(".select").append('<option id="'+ value.name + '">'+  value.name +'</option>');
        });
        $("div#block2 .remove_button").click(removeDriver);
        $("div#block2 .update_button").click(updateDriver);
        displayTrips();
    };

    var removeDriver = function(){
        var id = $(this).data('id');
        $.ajax({
            method          : "DELETE",
            url             : "http://localhost:9090/drivers/remove/"+ id,
            contentType     : "application/json; charset=utf-8",
            processData     : false,
            dataType        : 'json',
            complete        : function() {
                displayDrivers()
            }
        })
    };

    var updateDriver = function(){
        var id = $(this).data('id');
        $.ajax({
            method          : "GET",
            url             : "http://localhost:9090/drivers/id/"+ id,
            contentType     : "application/json; charset=utf-8",
            processData     : false,
            dataType        : 'json',
            complete        : getDriverWithId
        })
    };

    var getDriverWithId = function(data){
        $("#driverId").val(data.responseJSON.id);
        $("#name").val(data.responseJSON.name);
        $("#age").val(data.responseJSON.age);
        $("#update_driver").removeAttr("disabled");
        $("#add_driver").attr('disabled', 'disabled');
        $("#name").attr('disabled', 'disabled');
    };

    $("#update_driver").click(function(){
        $("#driver_error").text("");
        var data = JSON.stringify({
            "id"    : $("#driverId").val(),
            "name"  : $("#name").val(),
            "age"   : parseInt($("#age").val())
        });
        $.ajax({
            method          : "PUT",
            url             : 'http://localhost:9090/drivers',
            contentType     : "application/json; charset=utf-8",
            processData     : false,
            dataType        : 'json',
            data            : data,
            error        : function(data){
                displayDriverErrors(data);
            },
            success         : function(){
                displayDrivers();
            }
        });
        $("#driverId").val('');
        $("#name").val('');
        $("#age").val('');
        $("#update_driver").attr('disabled', 'disabled');
        $("#name").removeAttr('disabled');
        $("#add_driver").removeAttr('disabled');
    });

    $("#add_driver").click(function () {
        $("#driver_error").text("");
        var data = JSON.stringify({
            "id"    : null,
            "name"  : $("#name").val(),
            "age"   : parseInt($("#age").val())
        });
        $.ajax({
            method          : "POST",
            url             : 'http://localhost:9090/drivers',
            contentType     : "application/json; charset=utf-8",
            processData     : false,
            dataType        : 'json',
            data            : data,
            error           : function(data) {
                displayDriverErrors(data);
            },
            success         : function() {
                displayDrivers()
            }
        });
        $("#driverId").val('');
        $("#name").val('');
        $("#age").val('');
    });

    var displayDriverErrors = function(data) {
        $.each(data.responseJSON, function (index, value) {
            $("#driver_error").append(value.defaultMessage);
        });
    };

    <%-- For Trips --%>

    var displayTrips = function() {
        $.ajax({
            method: "GET",
            url: 'http://localhost:9090/trips/all/',
            contentType: "application/json; charset=utf-8",
            processData: false,
            dataType: 'json',
            complete: displayAllTrips
        })
        $("#tripId").val('');
        $("#driverName").val('');
        $("#car").val('');
        $("#fuelRate100").val('');
        $("#startPoint").val('');
        $("#endPoint").val('');
        $("#distance").val('');
        $("#startDate").val('2016-08-23');
        $("#endDate").val('2016-08-24');
        $("#sumFuel").val('');
        $("#trip_error").text("");
    };

    var displayAllTrips = function (data) {
        $("#block4").empty();
        var table = document.createElement('table');
        table.setAttribute("border", "2");
        $(table).append('<tr><td width="50">ID</td><td width="150">Driver\'s name</td><td width="100">Car</td>' +
                '<td width="100">Fuel rate</td><td width="100">Departure</td><td width="100">Destination</td>' +
                '<td width="100">Distance</td><td width="100">Departure date</td><td width="110">Destination date</td>' +
                '<td width="100">Summary fuel</td></tr>');
        block4.appendChild(table);
        $.each(data.responseJSON, function (index, value) {
            $(table).append('<tr><td>' + value.id + '</td><td>' + value.driverName + '</td><td>' + value.car + '</td>' +
                    '<td>' + value.fuelRate100 + '</td><td>' + value.startPoint + '</td><td>' + value.endPoint + '</td>' +
                    '<td>' + value.distance + '</td><td>' + value.startDate + '</td><td>' + value.endDate + '</td>' +
                    '<td>' + value.sumFuel + '</td><td>' +
                    '<button type="button" class="update_button" data-id="' + value.id + '">Update</button></td><td>' +
                    '<button type="button" class="remove_button" data-id="' + value.id + '">Delete</button></td></tr>');
        });
        $("div#block4 .remove_button").click(removeTrip);
        $("div#block4 .update_button").click(updateTrip);
    };

    var removeTrip = function() {
        var id = $(this).data('id');
        $.ajax({
            method: "DELETE",
            url: "http://localhost:9090/trips/remove/" + id,
            contentType: "application/json; charset=utf-8",
            processData: false,
            dataType: 'json',
            complete: function () {
                displayTrips()
            }
        })
    };

    var updateTrip = function() {
        var id = $(this).data('id');
        $.ajax({
            method: "GET",
            url: "http://localhost:9090/trips/id/" + id,
            contentType: "application/json; charset=utf-8",
            processData: false,
            dataType: 'json',
            complete: getTripWithId
        })
    };

    var getTripWithId = function(data) {
        $("#tripId").val(data.responseJSON.id);
        $("#driverName").val(data.responseJSON.driverName);
        $("#car").val(data.responseJSON.car);
        $("#fuelRate100").val(data.responseJSON.fuelRate100);
        $("#startPoint").val(data.responseJSON.startPoint);
        $("#endPoint").val(data.responseJSON.endPoint);
        $("#distance").val(data.responseJSON.distance);
        $("#startDate").val(data.responseJSON.startDate);
        $("#endDate").val(data.responseJSON.endDate);
        $("#sumFuel").val(data.responseJSON.sumFuel);
        $("#update_trip").removeAttr("disabled");
        $("#add_trip").attr('disabled', 'disabled');
    };

    $("#update_trip").click(function() {
        $("#trip_error").text("");
        var data = JSON.stringify({
            "id": $("#tripId").val(),
            "driverName": $("#driverName").val(),
            "car": $("#car").val(),
            "fuelRate100": parseFloat($("#fuelRate100").val()),
            "startPoint": $("#startPoint").val(),
            "endPoint": $("#endPoint").val(),
            "distance": $("#distance").val(),
            "startDate": $("#startDate").val(),
            "endDate": $("#endDate").val(),
            "sumFuel": parseFloat($("#distance").val()) / 100 * parseFloat($("#fuelRate100").val())
        });
        $.ajax({
            method: "PUT",
            url: 'http://localhost:9090/trips',
            contentType: "application/json; charset=utf-8",
            processData: false,
            dataType: 'json',
            data: data,
            error: function (data) {
                displayTripErrors(data);
            },
            success: function () {
                displayTrips();
                $("#update_trip").attr('disabled', 'disabled');
                $("#add_trip").removeAttr('disabled');
            }
        });
    });

    $("#add_trip").click(function () {
        $("#trip_error").text("");
        var data = JSON.stringify({
            "id": null,
            "driverName": $("#driverName").val(),
            "car": $("#car").val(),
            "fuelRate100": parseFloat($("#fuelRate100").val()),
            "startPoint": $("#startPoint").val(),
            "endPoint": $("#endPoint").val(),
            "distance": $("#distance").val(),
            "startDate": $("#startDate").val(),
            "endDate": $("#endDate").val(),
            "sumFuel": parseFloat($("#distance").val()) / 100 * parseFloat($("#fuelRate100").val())
        });
        $.ajax({
            method: "POST",
            url: 'http://localhost:9090/trips',
            contentType: "application/json; charset=utf-8",
            processData: false,
            dataType: 'json',
            data: data,
            error: function (data) {
                displayTripErrors(data);
            },
            success: function () {
                displayTrips();
            }
        });
    });

    var displayTripErrors = function(data) {
        $.each(data.responseJSON, function (index, value) {
            console.log(value.defaultMessage);
            $("#trip_error").append(value.defaultMessage);
        });
    };

    $("#fuel_by_driver").click(function() {
        var name = $("#select :selected").val();
        $.ajax({
            method: "GET",
            url: "http://localhost:9090/trips/sumFuel/driver/" + name,
            contentType: "application/json; charset=utf-8",
            processData: false,
            dataType: 'json',
            complete: function (data) {
                alert("Total fuel consumption by " + data.responseJSON.driver + " was " + data.responseJSON.sum + " liters");
            }
        })
    });

    $("#fuel_by_dates").click(function() {
        var from = $("#from").val();
        var to = $("#to").val();
        $.ajax({
            method: "GET",
            url: "http://localhost:9090/trips/sumFuel/date/" + from + "/" + to,
            contentType: "application/json; charset=utf-8",
            processData: false,
            dataType: 'json',
            complete: function (data) {
                alert("Total fuel consumption from " + data.responseJSON.startDate + " to " + data.responseJSON.endDate +
                        " was " + data.responseJSON.sum + " liters");
            }
        })
    });

</script>

</body>
</html>
