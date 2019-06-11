jQuery(document).ready(function () {


    getBranches();
    getSingleVehicle();


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

var getBranches = function () {
    $.ajax({
        url: 'http://localhost:9000/panel/branches',
        processData: false,
        type: 'GET',
        beforeSend: function (jqXHR, settings) {
            jqXHR.setRequestHeader("Content-Type", "application/json");
        },
        success: function (data, textStatus, jqXHR) {
            setBranches(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
        },
        complete: function (jqXHR, textStatus) {
        }
    });
};

var setBranches = function (vehiclesTableJSON) {
    branches = JSON.parse(vehiclesTableJSON);
    $.each(branches, function (key, value) {
        $('#vehicleBranchID').append($('<option>', {
            value: value["branchID"],
            text: value["branchName"]
        }))


    })

};


var getSingleVehicle = function () {
    var vehicleGetUrl = 'http://localhost:9000/panel/vehicles/get?id=' + getUrlParameter('id');
    $.ajax({
        url: vehicleGetUrl,
        processData: false,
        type: 'GET',
        beforeSend: function (jqXHR, settings) {
            jqXHR.setRequestHeader("Content-Type", "application/json");
        },
        success: function (data, textStatus, jqXHR) {
            displaySingleVehicleForm(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
        },
        complete: function (jqXHR, textStatus) {
        }
    });
};

var displaySingleVehicleForm = function (vehiclesTableJSON) {
    var vehiclesTable = JSON.parse(vehiclesTableJSON);
    $('#vehicleName').val(vehiclesTable["vehicleName"]);
    $('#vehicleInsurance').val(vehiclesTable["vehicleInsurance"]);
    $('#vehicleTechnicalCheck').val(vehiclesTable["vehicleTechnicalCheck"]);
    $('#vehicleBranchID').val(vehiclesTable["vehicleBranchID"]);
    $('#vehicleFuelType').val(vehiclesTable["vehicleFuelType"]);

};
