jQuery(document).ready(function () {


    var getClientsTable = function () {
        $.ajax({
            url: 'http://localhost:9000/panel/clients/get',
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
                "<tr><td>" + value["clientID"] + "</td>" +
                "<td>" + value["clientFirstName"] + "</td>" +
                "<td>" + value["clientLastName"] + "</td>" +
                "<td>" + value["clientAddress"] + "</td>" +
                "<td>" + value["clientPhone"] + "</td>" +
                "<td>" + value["clientRegion"] + "</td>" +
                "<td>" + "<div class='form-button-small'><a href='" + "/panel/clients/delete?id=" + value["clientID"] + "'>delete </a></div>" +
                "<div class='form-button-small'><a href='" + "/panel/clients/edit?id=" + value["clientID"] + "'>edit</a></div></td></tr>"
            );

        })
    }

    getClientsTable();


    //edit...


});
