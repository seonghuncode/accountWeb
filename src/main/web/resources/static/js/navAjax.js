// 내비게이션바 에서 각 버튼을 클릭했을 경우를 처리하는  ajax

$("#try_logout").click(function () {

    $.ajax({
        url: "/usr/doLogout",
        dataType: "json",   //dataType : "html", "json", "text"
        success: function (res) {
            // alert("success");
            // console.log("응답");
            //console.log(res);


            if (res['status'] === '이미 로그아웃 되어 있습니다.') {
                swal('실패', res.status, 'warning')
                    .then(function () {
                        //location.href = '/';  //메인화면 디자인 해서 주소 바꾸기
                    })
            } else {
                swal('성공', res.status, 'success')
                    .then(function () {
                        location.href = '/';  //메인화면 디자인 해서 주소 바꾸기
                    })
            }

        },
        error: function () {
            alert("error")
        }
    });

})