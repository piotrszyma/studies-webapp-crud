jQuery(document).ready(function () {


    var getStatsTable = function () {
        $.ajax({
            url: 'http://localhost:9000/panel/stats/get',
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
                "<tr><td>" + value["branchID"] + "</td>" +
                "<td>" + value["branchName"] + "</td>" +
                "<td>" + value["branchAddress"] + "</td>" +
                "<td>" + value["branchRegion"] + "</td>" +
                "<td>" + value["branchNumOfPackages"] + "</td>" +
                "<td><a href='" + "/panel/stats/edit?id=" + value["branchID"] + "'><div class='form-button-small'>edit</div></a> <a href='" + "/panel/stats/delete?id=" + value["branchID"] + "'><div class='form-button-small'>delete</div></a></td></tr>"
            );
        })
    }

    getStatsTable();


    //edit...


});
