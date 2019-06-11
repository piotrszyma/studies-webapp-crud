jQuery(document).ready(function () {


    getRegions();
    getVehicles();
    getSingleEmployee();
    //edit...

});

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

var getRegions = function () {
    $.ajax({
        url: 'http://localhost:9000/panel/branches',
        processData: false,
        type: 'GET',
        beforeSend: function (jqXHR, settings) {
            jqXHR.setRequestHeader("Content-Type", "application/json");
        },
        success: function (data, textStatus, jqXHR) {
            setRegions(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
        },
        complete: function (jqXHR, textStatus) {
        }
    });
};

var setRegions = function (regionsTableJSON) {
    var regions = JSON.parse(regionsTableJSON);
    $.each(regions, function (key, value) {
        $('#employeeBranch').append($('<option>', {
            value: value["branchID"],
            text: value["branchName"]
        }))


    })

};
var getVehicles = function () {
    $.ajax({
        url: 'http://localhost:9000/panel/vehicles/get',
        processData: false,
        type: 'GET',
        beforeSend: function (jqXHR, settings) {
            jqXHR.setRequestHeader("Content-Type", "application/json");
        },
        success: function (data, textStatus, jqXHR) {
            setVehicles(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
        },
        complete: function (jqXHR, textStatus) {
        }
    });
};

var setVehicles = function (VehiclesTableJSON) {
    var Vehicles = JSON.parse(VehiclesTableJSON);
    $.each(Vehicles, function (key, value) {
        $('#employeeVehicle').append($('<option>', {
            value: value["vehicleID"],
            text: value["vehicleName"]
        }))


    })

    $('#employeeVehicle').append($('<option>', {
        value: "-1",
        text: "NO VEHICLE"
    }))

};


var getSingleEmployee = function () {
    var employeeGetUrl = 'http://localhost:9000/panel/employees/get?id=' + getUrlParameter('id');
    $.ajax({
        url: employeeGetUrl,
        processData: false,
        type: 'GET',
        beforeSend: function (jqXHR, settings) {
            jqXHR.setRequestHeader("Content-Type", "application/json");
        },
        success: function (data, textStatus, jqXHR) {
            displaySingleEmployeeForm(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
        },
        complete: function (jqXHR, textStatus) {
        }
    });
};

var displaySingleEmployeeForm = function (employeesTableJSON) {
    var employeesTable = JSON.parse(employeesTableJSON);
    $('#employeeFirstName').val(employeesTable["employeeFirstName"]);
    $('#employeeLastName').val(employeesTable["employeeLastName"]);
    $('#employeeSSN').val(employeesTable["employeeSSN"]);
    $('#employeePhone').val(employeesTable["employeePhone"]);
    $('#employeeAddress').val(employeesTable["employeeAddress"]);
    $('#employeeBranch').val(employeesTable["employeeBranchID"]);
    if (employeesTable["employeeVehicleID"] === undefined) {
        $('#employeeVehicle').val("-1");
    } else {
        $('#employeeVehicle').val(employeesTable["employeeVehicleID"]);
    }


};
