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


//네비게이션바 에서 사용자가 거래내역을 클릭할 경우 현재 로그인 되어있는 세션 아이디를 구해서 거래내역 페이지로 넘기기
$("#moveShowTransaction").click(function () {

    $.ajax({
        url: "/transaction/getNowSessionValue", // 스프링 컨트롤러에 매핑된 경로
        type: "get",
        dataType: "json",
        contentType: "application/json; charset=UTF-8",
        success: function(data) {
            console.log("세션값 가지고 오기 성공");
            console.log(data);
            console.log(data['sessionValue']);
            location.href="/transaction/showTransaction?userId=" + data['sessionValue'];
        },
        error: function() {
          console.log("(ajax요청 실패)현재 로그인 되어있는 회원의 세션값을 가지고 오는 요청을 실패 했습니다.");
        }
    });

})