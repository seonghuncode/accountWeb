$("#try-login").click(function () {
    //alert("click");

    let info = {
        "userId": $("#loginId").val(),
        "password": $("#loginPw").val()
    };


    console.log(info);
    $.ajax({
        url: "/usr/loginFn",
        data: JSON.stringify(info), //data: info, JSON.stringify(info)
        type: "post",
        dataType: "json",   //dataType : "html", "json", "text"
        contentType: "application/json; charset=utf-8",
        success: function (res) {
            alert("success");
            console.log("응답");
            console.log(res);

        },
        error: function () {
            alert("error")
        }
    });
});
