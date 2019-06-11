jQuery(document).ready(function () {


    getCouriers();
    getSingleUser();
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


var getSingleUser = function () {
    var userGetUrl = 'http://localhost:9000/panel/users/get?id=' + getUrlParameter('id');
    $.ajax({
        url: userGetUrl,
        processData: false,
        type: 'GET',
        beforeSend: function (jqXHR, settings) {
            jqXHR.setRequestHeader("Content-Type", "application/json");
        },
        success: function (data, textStatus, jqXHR) {
            displaySingleUserForm(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
        },
        complete: function (jqXHR, textStatus) {
        }
    });
};

var displaySingleUserForm = function (usersTableJSON) {
    var usersTable = JSON.parse(usersTableJSON);
    console.log(usersTable);
    $('#userEmployeeID').val(usersTable["userID"]);
    $('#userLogin').val(usersTable["userLogin"]);
    $('#userType').val(usersTable["userType"]);

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
        $('#userEmployeeID').append($('<option>', {
            value: value["employeeID"],
            text: value["employeeFirstName"] + " " + value["employeeLastName"]
        }))
    })


};