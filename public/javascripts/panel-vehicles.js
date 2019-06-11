jQuery(document).ready(function () {


    var getVehiclesTable = function () {
        $.ajax({
            url: 'http://localhost:9000/panel/vehicles/get',
            processData: false,
            type: 'GET',
            beforeSend: function (jqXHR, settings) {
                jqXHR.setRequestHeader("Content-Type", "application/json");
            },
            success: function (data, textStatus, jqXHR) {
                displayTable(data);
            },
            error: function (jqXHR, textStatus, errorThrown) {
            },
            complete: function (jqXHR, textStatus) {
            }
        });
    };
    var displayTable = function (vehiclesTableJSON) {
        var vehiclesTable = JSON.parse(vehiclesTableJSON);

        $.each(vehiclesTable, function (key, value) {
            $("table").append(
                "<tr><td>" + value["vehicleName"] + "</td>" +
                "<td>" + value["vehicleInsurance"] + "</td>" +
                "<td>" + value["vehicleTechnicalCheck"] + "</td>" +
                "<td>" + value["vehicleFuelType"] + "</td>" +
                "<td>" + value["vehicleBranch"] + "</td>" +
                "<td>" + value["vehicleRegion"] + "</td>" +
                "<td>" + "<div class='form-button-small'><a href='vehicles/delete?id=" + value["vehicleID"] + "'>delete</a></div>" +
                " <div class='form-button-small'><a href='vehicles/edit?id=" + value["vehicleID"] + "'>edit</a></div>" + "</td></tr>"
            );

        })
    }

    getVehiclesTable();


    //edit...


});
