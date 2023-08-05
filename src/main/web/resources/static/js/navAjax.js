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
            // console.log("세션값 가지고 오기 성공");
            // console.log(data);
            // console.log(data['sessionValue']);
            location.href="/transaction/showTransaction?userId=" + data['sessionValue'];
        },
        error: function() {
          console.log("(ajax요청 실패)현재 로그인 되어있는 회원의 세션값을 가지고 오는 요청을 실패 했습니다.");
        }
    });

})



//네비게이션바에서 회원정보 버튼을 클릭할 경우 회원 정보 페이지로 이동하는 로직
$("#myInfo").click(function () {

    //회원정보 페이지로 이동하기 위해서는 현재 로그인 되어 있는 회원의 아이디를 알아야 하기 때문에 세션에 저장된 값을 가지고오는 비동기 통신
    $.ajax({
        url: "/transaction/getNowSessionValue", // 스프링 컨트롤러에 매핑된 경로
        type: "get",
        dataType: "json",
        contentType: "application/json; charset=UTF-8",
        success: function(data) {
            // console.log("세션값 가지고 오기 성공");
            // console.log(data);
            // console.log(data['sessionValue']);
            location.href="/usr/myInfo?userId=" + data['sessionValue'];
        },
        error: function() {
            console.log("(ajax요청 실패)현재 로그인 되어있는 회원의 세션값을 가지고 오는 요청을 실패 했습니다.");
        }
    });

})




//네비게이션바에서  통계 버튼을 클릭할 경우 통계 페이지로 이동하는 로직
$("#moveChartPage").click(function () {

    //통계 페이지의 경우 조건이 날짜 검색, 지출 or 수입에 따라 화면을 보여주기 때문에
    //필요한 파라미터터는 현재 로그인 되어 있는 회원 아이디, 날짜, 지출 or 수입에 대한 데이터가 필요하다.
    //네비게이션바에서 통계를 클릭할 경우 현제 당월 날짜와 지출을 기준으로 데이터를 보여준다.

    var now = new Date(); //현재 시간
    // console.log(now);
    var nowYear = now.getFullYear().toString();
    var nowMonth = now.getMonth()+1;

    if(nowMonth < 10){
        nowMonth = "0"+nowMonth;
    }
    nowYear = nowYear.substr(2,2)
    // console.log(nowYear);
    // console.log(nowMonth);

    //회원정보 페이지로 이동하기 위해서는 현재 로그인 되어 있는 회원의 아이디를 알아야 하기 때문에 세션에 저장된 값을 가지고오는 비동기 통신
    $.ajax({
        url: "/transaction/getNowSessionValue", // 스프링 컨트롤러에 매핑된 경로
        type: "get",
        dataType: "json",
        contentType: "application/json; charset=UTF-8",
        success: function(data) {
            // console.log("세션값 가지고 오기 성공");
            // console.log(data);
            // console.log(data['sessionValue']);
            var userId = data['sessionValue'];
            location.href="/transaction/moveChartPage?userId=" + userId + "&year=" + nowYear + "&month=" + nowMonth;
        },
        error: function() {
            console.log("(ajax요청 실패)현재 로그인 되어있는 회원의 세션값을 가지고 오는 요청을 실패 했습니다.");
        }
    });

})



