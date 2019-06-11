jQuery(document).ready(function () {

    var elements = jQuery(
        "#senderFirstName, #senderLastName, #senderPhone, #senderRegion, #senderAddress, #senderCountry, #senderEmail, " +
        "#receiverFirstName, #receiverLastName, #receiverPhone, #receiverRegion, #receiverAddress, #receiverCountry, #receiverEmail, " +
        "#packageWeight, #packageInsurance, #packageValue");


    elements.bind('change', function () {

        if (this.value === "") {
            jQuery(this).prev().html("Required!").show();
        }
        else {
            jQuery(this).prev().hide();
        }
    });

    jQuery("#senderEmail, #receiverEmail").bind('change', function () {
        var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;

        if (regex.test(this.value)) {
            jQuery(this).prev().hide();
        } else if (this.value === "") {
            jQuery(this).prev().html("Required!").show();
        }
        else {
            jQuery(this).prev().html("Invalid email").show();
        }
    });


    jQuery('#packageWeight, #packageValue').bind('change', function () {
        var regex = /^\d*\.?\d+$/;

        if (regex.test(this.value)) {
            jQuery(this).prev().hide();
        } else if (this.value === "") {
            jQuery(this).prev().html("Required!").show();
        }
        else {
            jQuery(this).prev().html("Invalid value").show();
        }
    });

    jQuery("#senderFirstName, #senderLastName, #receiverFirstName, #receiverLastName").bind('change', function () {
        var regex = /^[a-zA-Z]+$/;

        if (regex.test(this.value)) {
            jQuery(this).prev().hide();
        } else if (this.value === "") {
            jQuery(this).prev().html("Required!").show();
        }
        else {
            jQuery(this).prev().html("Invalid value").show();
        }
    });

    jQuery('#submit').click(function (e) {

        var insurance = jQuery('input[name=insuranceType]:checked').val();
        jQuery("#packageInsurance").val(insurance);

        elements.trigger('change');
        if (isNotInputValid()) {
            e.preventDefault();
        }
    });

    function isNotInputValid() {

        var i = 0;
        elements.each(function () {
            if (jQuery(this).prev().is(':visible')) {
                console.log(jQuery(this).prev());
                i++;
            }
        });
        return i > 0;
    }


    jQuery('#receiverRegionSelect').on('change', function () {
        jQuery('#receiverRegion').val(jQuery(this).val());
    })

    jQuery('#senderRegionSelect').on('change', function () {
        jQuery('#senderRegion').val(jQuery(this).val());
    })

    getRegions();

});

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
        $('#receiverRegionSelect').append($('<option>', {
            value: value["regionID"],
            text: value["regionName"]
        }))

        $('#senderRegionSelect').append($('<option>', {
            value: value["regionID"],
            text: value["regionName"]
        }))
    })


    jQuery('#receiverRegionSelect').trigger('change');
    jQuery('#senderRegionSelect').trigger('change');

};
