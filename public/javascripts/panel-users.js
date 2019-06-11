jQuery(document).ready(function () {


    var getUsersTable = function () {
        $.ajax({
            url: 'http://localhost:9000/panel/users/get',
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
                "<tr><td>" + value["userID"] + "</td>" +
                "<td>" + value["userLogin"] + "</td>" +
                "<td>" + value["userType"] + "</td>" +
                "<td>" + value["userFirstName"] + "</td>" +
                "<td>" + value["userLastName"] + "</td>" +
                "<td>" + "<a href='users/edit?id=" + value["userID"] + "'><div class='form-button-small'>edit</div></a>" +
                "<a href='users/delete?id=" + value["userID"] + "'><div class='form-button-small'>delete</div></a>" + "</td></tr>"
            );

        })
    }

    getUsersTable();


    //edit...


});
