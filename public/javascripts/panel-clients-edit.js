jQuery(document).ready(function () {


    getRegions();
    getSingleClient();
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
        url: 'http://localhost:9000/panel/regions',
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
        $('#clientRegionID').append($('<option>', {
            value: value["regionID"],
            text: value["regionName"]
        }))


    })

};


var getSingleClient = function () {
    var clientGetUrl = 'http://localhost:9000/panel/clients/get?id=' + getUrlParameter('id');
    $.ajax({
        url: clientGetUrl,
        processData: false,
        type: 'GET',
        beforeSend: function (jqXHR, settings) {
            jqXHR.setRequestHeader("Content-Type", "application/json");
        },
        success: function (data, textStatus, jqXHR) {
            displaySingleClientForm(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
        },
        complete: function (jqXHR, textStatus) {
        }
    });
};

var displaySingleClientForm = function (clientsTableJSON) {
    var clientsTable = JSON.parse(clientsTableJSON);
    $('#clientFirstName').val(clientsTable["clientFirstName"]);
    $('#clientLastName').val(clientsTable["clientLastName"]);
    $('#clientPhone').val(clientsTable["clientPhone"]);
    $('#clientAddress').val(clientsTable["clientAddress"]);
    $('#clientRegionID').val(clientsTable["clientRegionID"]);

};
