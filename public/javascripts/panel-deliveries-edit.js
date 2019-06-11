jQuery(document).ready(function () {


    // getRegions();
    // getVehicles();
    getCouriers();
    getSingleDelivery();
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

var getCouriers = function () {
    $.ajax({
        url: 'http://localhost:9000/panel/employees/get',
        processData: false,
        type: 'GET',
        beforeSend: function (jqXHR, settings) {
            jqXHR.setRequestHeader("Content-Type", "application/json");
        },
        success: function (data, textStatus, jqXHR) {
            setCouriers(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
        },
        complete: function (jqXHR, textStatus) {
        }
    });
};

var setCouriers = function (employeesTableJSON) {
    var employees = JSON.parse(employeesTableJSON);

    $.each(employees, function (key, value) {
        $('#courier').append($('<option>', {
            value: value["employeeID"],
            text: value["employeeFirstName"] + " " + value["employeeLastName"]
        }))
    })


};


var getSingleDelivery = function () {
    var deliveryGetUrl = 'http://localhost:9000/panel/packages/get?id=' + getUrlParameter('id');
    $.ajax({
        url: deliveryGetUrl,
        processData: false,
        type: 'GET',
        beforeSend: function (jqXHR, settings) {
            jqXHR.setRequestHeader("Content-Type", "application/json");
        },
        success: function (data, textStatus, jqXHR) {
            displaySingleDeliveryForm(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
        },
        complete: function (jqXHR, textStatus) {
        }
    });
};

var displaySingleDeliveryForm = function (deliveryTableJSON) {
    var deliveryTable = JSON.parse(deliveryTableJSON);

    $('#courier').val(deliveryTable["employeeID"]);
    $('#status').val(deliveryTable["deliveryStatus"]);

};
