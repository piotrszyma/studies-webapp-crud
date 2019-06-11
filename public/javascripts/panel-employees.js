jQuery(document).ready(function () {


    var getEmployeeTable = function () {
        $.ajax({
            url: 'http://localhost:9000/panel/employees/get',
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
                "<tr><td>" + value["employeeFirstName"] + "</td>" +
                "<td>" + value["employeeLastName"] + "</td>" +
                "<td>" + value["employeeSSN"] + "</td>" +
                "<td>" + value["employeePhone"] + "</td>" +
                "<td>" + value["employeeAddress"] + "</td>" +
                "<td>" + value["employeeBranch"] + "</td>" +
                "<td>" + value["employeePackages"] + "</td>" +
                "<td>" + "<a href='employees/edit?id=" + value["employeeID"] + "'> <div class='form-button-small'>edit</div> </a>" +
                " <a href='employees/delete?id=" + value["employeeID"] + "'> <div class='form-button-small'>fire</div></a>" + "</td></tr>"
            );

        })
    }

    getEmployeeTable();


    //edit...


});
