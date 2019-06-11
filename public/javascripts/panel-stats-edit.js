jQuery(document).ready(function () {

    getRegions();
    getSingleBranch();


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
        $('#branchRegion').append($('<option>', {
            value: value["regionID"],
            text: value["regionName"]
        }))


    })

};


var getSingleBranch = function () {
    var vehicleGetUrl = 'http://localhost:9000/panel/stats/get?id=' + getUrlParameter('id');
    $.ajax({
        url: vehicleGetUrl,
        processData: false,
        type: 'GET',
        beforeSend: function (jqXHR, settings) {
            jqXHR.setRequestHeader("Content-Type", "application/json");
        },
        success: function (data, textStatus, jqXHR) {
            displaySingleBranchForm(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
        },
        complete: function (jqXHR, textStatus) {
        }
    });
};

var displaySingleBranchForm = function (vehiclesTableJSON) {
    var branchesTable = JSON.parse(vehiclesTableJSON);
    console.log(branchesTable);
    $('#branchName').val(branchesTable["branchName"]);
    $('#branchAddress').val(branchesTable["branchAddress"]);
    $('#branchRegion').val(branchesTable["branchRegionID"]);

};
