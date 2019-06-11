jQuery(document).ready(function () {


    var getDeliveriesTable = function () {
        $.ajax({
            url: 'http://localhost:9000/panel/packages/get',
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
            var packageStatus;


            // var changeStatusPending = "<a href='" + "packages/modify?id=" + value["deliveryID"] + "&courier=" + value["employeeID"] + "&status=pending'>PENDING</a>";
            // var changeStatusDelivery = "<a href='" + "packages/modify?id=" + value["deliveryID"] + "&courier=" + value["employeeID"] + "&status=transit'>TRANSIT</a>";
            // var changeStatusTransit = "<a href='" + "packages/modify?id=" + value["deliveryID"] + "&courier=" + value["employeeID"] + "&status=delivery'>DELIVERIED</a>";


            // if (value["deliveryStatus"] === "transit") {
            //
            //     packageStatus = changeStatusDelivery + " " + changeStatusPending + "<div><b>TRANSIT</b></div>";
            //
            // } else if (value["deliveryStatus"] === "pending") {
            //
            //     packageStatus = changeStatusDelivery + " " + changeStatusTransit + "<div><b>PENDING</b></div>";
            //
            //
            // } else {
            //     packageStatus = changeStatusDelivery + " " + changeStatusPending + "<div><b>DELIVERIED</b></div>";
            //
            // }

            $("table").append(
                "<tr><td>" + value["senderFirstName"] + "</td>" +
                "<td>" + value["senderLastName"] + "</td>" +
                "<td>" + value["senderAddress"] + "</td>" +
                "<td>" + value["receiverFirstName"] + "</td>" +
                "<td>" + value["receiverLastName"] + "</td>" +
                "<td>" + value["receiverAddress"] + "</td>" +
                "<td>" + value["employeeFirstName"] + "</td>" +
                "<td>" + value["employeeLastName"] + "</td>" +
                "<td>" + value["deliveryStatus"] + "</td>" +
                "<td>" + "<a href='packages/delete?id=" + value["deliveryID"] + "'><div class='form-button-small'>delete</div></a>" +
                " <a href='packages/edit?id=" + value["deliveryID"] + "'><div class='form-button-small'>edit</div></a>" + "</td></tr>"
            );

        })
    }

    getDeliveriesTable();


    //edit...


});
