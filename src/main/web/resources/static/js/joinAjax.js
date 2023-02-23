//join.html에서 회원가입에 대한 기능을 서버로 전송하고 결과에 대한 로직을 수행하는 패이지

//<!-- JQuery를 통한 ajax방식-->
//<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
// <script type="text/javascript">

//이메일 중복 확인 버튼을 클릭했을 경우
$("#emailDuplication").click(function (){

    var email = {
        email: $("#email").val(),
        userId: "중복확인"  //중복확인 버튼의 경우
    };

    if($("#email").val() === ""){
       $('#validEmail').html("중복 확인을 위해 이메일을 입력해 주세요.");
    }else{

        $.ajax({
            url: "/usr/joinFn",
            data: JSON.stringify(email),  //data: info, JSON.stringify(info)
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
                    console.log(res)
                }


            },
            error: function () {
                alert("error")
            }

        });

    }


})



//회원가입 버튼을 클릭했을때 로직
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

        if(res.email){
            $('#validEmail').html(res.email);
        }else{
            $('#validEmail').html("");
        }

        if(res.userId){
            $('#validUserId').html(res.userId);
        }else{
            $('#validUserId').html("");
        }

        if(res.password){
            $('#validPassword1').html(res.password);
        }else{
            $('#validPassword1').html("");
        }

        if(res.checkPassword){
            $('#validPassword2').html(res.checkPassword);
        }else{
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

