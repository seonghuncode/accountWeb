//join.html에서 회원가입에 대한 기능을 서버로 전송하고 결과에 대한 로직을 수행하는 패이지

//<!-- JQuery를 통한 ajax방식-->
//<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
// <script type="text/javascript">

//이메일 중복 확인 버튼을 클릭했을 경우-----------------------------------------------------------------------------------------------------------------------------------
$("#emailDuplication").click(function () {

    var email = {
        email: $("#email").val(),
        userId: "중복확인"  //중복확인 버튼의 경우

        /*
        email중복 확인에 email, userId에 "중복 확인"을 넣어 보내는 이유
        controller에서는 dto에 선언된 변수명으로만 받을 수 있기 때문에 현재 사용하지 않는 userId에 중복확인에 대한 요청이라는 것을 알려주어
        controller에서 이메일 중복에 해당하는 유효성 검사만 실행 하고 그에 대한 결과만 return해 주도록 예외처리를 하기 위해 같이 보내준다.
        */
    };

    function validation(res) {

        if (res.email) {   //이메일 형식 체크
            $('#validEmail').html(res.email);
        } else if (res.name) {  //이메일 중복 여부 체크
            if (res.name === "해당 이메일은 사용 가능 합니다.") {  //사용 가능할 경우 폰튼 색을 파란색으로 지정
                const changeCss = document.getElementById("validEmail");
                changeCss.style.color = 'blue';
                $('#validEmail').html(res.name);
            } else { //중복의 경우 빨간색으로 다시 변경
                const changeCss = document.getElementById("validEmail");
                changeCss.style.color = 'red';
                $('#validEmail').html(res.name);
            }
        } else { //이메일 중복 여부 체크
            $('#validEmail').html("");
        }

    }

    if ($("#email").val() === "") {
        $('#validEmail').html("중복 확인을 위해 이메일을 입력해 주세요.");
    } else {

        $.ajax({
            url: "/usr/joinFn",
            data: JSON.stringify(email),  //data: info, JSON.stringify(info)
            method: "post",
            dataType: "json",   //dataType : "html",
            contentType: "application/json; charset=utf-8",
            success: function (res) {
                // alert("success");
                // console.log("controller에서 받은 데이터 ==>  ")
                // console.log(res);
                validation(res);
            },
            error: function () {
                console.log("요청 또는 응답에 있어 문제가 발생했습니다.");
                alert("error")
            }
        });
    }
})
//--------------------------------------------------------------------------------------------------------------------------------------------



//아이디 중복 확인 버튼을 클릭했을 경우 실행될 로직-------------------------------------------------------------------------------------------
$("#userIdDuplication").click(function () { //--> 중복확인 버튼을 클릭했을 경우 실행

    var userId = {
        userId: $("#userId").val(),
        email: "중복확인"  //controller에 중복확인 버튼 로직을 실행시킬 것을 알려주기 위해서
    };

    function validation(res) {

       if(res.userId){
           if(res.userId === "해당 아이디는 사용 가능 합니다"){
               const changeCss = document.getElementById("validUserId");
               changeCss.style.color = 'blue';
               $('#validUserId').html(res.userId);
           }else{
               const changeCss = document.getElementById("validUserId");
               changeCss.style.color = 'red';
               $('#validUserId').html(res.userId);
           }
       }

    }

    if ($("#userId").val() === "") {
        $('#validUserId').html("중복 확인을 위해 아이디를 입력해 주세요.");
    } else {

        $.ajax({
            url: "/usr/joinFn",
            data: JSON.stringify(userId),  //data: info, JSON.stringify(info)
            method: "post",
            dataType: "json",   //dataType : "html",
            contentType: "application/json; charset=utf-8",
            success: function (res) {
                // alert("success");
                // console.log("controller에서 받은 데이터 ==>  ")
                console.log(res);
                validation(res);
            },
            error: function () {
                console.log("요청 또는 응답에 있어 문제가 발생했습니다.");
                alert("error")
            }
        });
    }
})
//--------------------------------------------------------------------------------------------------------------------------------------------






//회원가입 버튼을 클릭했을때 로직-------------------------------------------------------------------------------------------------------------
$("#try-join").click(function () {
    //alert("click");
    //let data = getData();

    //유효성 검사를 서버에서 받아와 존재하는 오류에 대한 값 처기 로직
    function validation(res) {
        //해당 key값이 존재 한다면 오류 처리 로직

        if (res.name) {   // msg = res["name"]을 경고 메세지로 사용할 에정
            // console.log("res.name : " + res.name);
            // console.log(res["name"])
            $('#validName').html(res.name);
        } else {
            $('#validName').html("");
        }

        if (res.email) {
            $('#validEmail').html(res.email);
        } else {
            $('#validEmail').html("");
        }

        if (res.userId) {
            $('#validUserId').html(res.userId);
        } else {
            $('#validUserId').html("");
        }

        if (res.password) {
            $('#validPassword1').html(res.password);
        } else {
            $('#validPassword1').html("");
        }

        if (res.checkPassword) {
            $('#validPassword2').html(res.checkPassword);
        } else {
            $('#validPassword2').html("");
        }

    }

    //사용자가 입력한 value값들을 객체에 모아두기
    let info = {
        name: $("#name").val(),
        email: $("#email").val(),
        userId: $("#userId").val(),
        password: $("#userPw1").val(),
        checkPassword: $("#userPw2").val(),
        view_yn: $('input:radio[name=select]:checked').val()
    };

    //controller로 value값 json으로 전송
    $.ajax({
        url: "/usr/joinFn",
        data: JSON.stringify(info),  //data: info, JSON.stringify(info)
        method: "post",
        dataType: "json",   //dataType : "html",
        contentType: "application/json; charset=utf-8",
        success: function (res) {
            alert("success");
            console.log("controller에서 받은 데이터 ==>  ")
            console.log(res);


            if (res['success'] === 200) {
                alert('회원가입이 완료 되었습니다. \n 로그인 페이지로 이동 합니다.');
                location.href = '/usr/loginForm';
            } else {
                validation(res);
            }


        },
        error: function () {
            alert("error")
        }

    });
});
//--------------------------------------------------------------------------------------------------------------------------------------------
