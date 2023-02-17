
$("#loginBtn").on("click", function () {
    var formData = $("form").serialize();

    $.ajax({
        url: "/employeeRegistExecute",
        async: false,
        type: "post",
        data: JSON.stringify(formData),
        //contentType: "application/json; charset=UTF-8",
        contentType: 'application/json',
        //enctype: 'multipart/form-data',
        //processData: false,
        //contentType: false,
        //cache: false,
        success: function (result) {
            console.log(result);


        },
        error: function (a, b, c, d) {
            console.log("errorA:" + a);
            console.log("errorB:" + b);
            console.log("errorC:" + c);
            console.log("errorD:" + d);
        }
    });
});