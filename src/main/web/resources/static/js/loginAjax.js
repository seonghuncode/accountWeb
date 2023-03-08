$("#try-login").click(function () {
    //alert("click");

    function validation(res) {

        //현재 프로젝트 구현(로그인 -> 메인화면)이기 때문에 로그인이 되어있는 상황에서 로그인을 할 상황은 없지만 검사를 위해 만듬
        if(res.checkPassword){  //만약 이미 로그인이 되어 있는 상태라면 어떤 값을 입력하든 이미 로그인이 되어 있다는 경고 alert를 내보낸다.
            swal('오류', res.checkPassword, 'warning')
                .then(function () {
                    location.href = '/';  //이미 로그인 되어있을 경우 alert ()
                })
        }
        else if(res.email && res.userId && res.password){  //--> 아이디, 비밀번호 모두 미입력한 경우
            $('#validNotCorrect').html(""); //아이디, 비밀번호중 하나라도 미입력한 경우 비교 에러는 보여주지 않는다.

            const inputLineColor1 = document.getElementById("loginId");  //id input red
            inputLineColor1.style.outline = "1px solid red";
            $('#validNoId').html(res.userId);
            
            console.log("1");//프론트 에서 어떤로직에 걸렸는지 확인하기 위함
            
            const inputLineColor2 = document.getElementById("loginPw");  //pw input red
            inputLineColor2.style.outline = "1px solid red";
            $('#validNoPw').html(res.password);
        }
        else if (res.email && res.userId) {  //--> 아이디만 입력하지 않은 경우
            $('#validNotCorrect').html("");

            const inputLineColor2 = document.getElementById("loginPw");  //비밀번호 input다시 black으로
            inputLineColor2.style.outline = "1px solid black";
            $('#validNoPw').html("");
            
            console.log("2");
            
            const inputLineColor = document.getElementById("loginId");  //아이디 input은 red로
            inputLineColor.style.outline = "1px solid red";
            $('#validNoId').html(res.userId);
        } else if (res.email && res.password) {  //--> 비밀번호만 입력 하지 않은 경우
            $('#validNotCorrect').html("");

            const inputLineColor1 = document.getElementById("loginId");   //id input black
            inputLineColor1.style.outline = "1px solid black";
            $('#validNoPw').html(res.password);
            $('#validNoId').html("");
            
            console.log("3");
            
            const inputLineColor = document.getElementById("loginPw");  //pw input red
            inputLineColor.style.outline = "1px solid red";
            $('#validNoId').html(res.userId);
        } else if (res.userId && !res.email) { //-->응답에 userId가 존재할 경우 (email의 경우 데이터 미입력할 경우 값을 담아오는 변수로 사용) [존재하지 않는 아이디를 입력한 경우]
            const inputLineColor2 = document.getElementById("loginPw");  //비밀번호 input다시 black으로
            inputLineColor2.style.outline = "1px solid black";
            $('#validNoPw').html("");

            $('#validNoId').html("");
            
            console.log("4");
            
            const inputLineColor = document.getElementById("loginId");
            inputLineColor.style.outline = "1px solid black";
            $('#validNotCorrect').html(res.userId);
        }  else if (res.password && !res.email) { //--> 오류에 password의 메세지 값이 존재 할 경우(입력한 아이디는 존재하지만 일치 해당 아이디와 일치 하지 않을 경우우)
            $('#validNoId').html(""); //아이디는 입력한 경우 이기 때문에 이전에 오류가 났을 경우을 위해

            const inputLineColor1 = document.getElementById("loginId");  //id input red
            inputLineColor1.style.outline = "1px solid black";
            $('#validNoPw').html("");

            const inputLineColor = document.getElementById("loginPw");
            inputLineColor.style.outline = "1px solid black";
            
            console.log("5");
            
            $('#validNotCorrect').html(res.password);
        } else if (!res.password && !res.email && !res.userId && !res.checkPassword) {  //--> 어떤 에러 메세지도 없을 경우
            const inputLineColor1 = document.getElementById("loginId");   //id input black
            inputLineColor1.style.outline = "1px solid black";
            $('#loginId').html(res.userId);
            
            console.log("6");
            
            const inputLineColor2 = document.getElementById("loginPw");  //비밀번호 input다시 black으로
            inputLineColor2.style.outline = "1px solid black";
            $('#loginPw').html(res.userId);

            $('#validNotCorrect').html("");
        }


    }

    //실패하고 있는 과정에서 성공했을경우 로그인 화면에 남아있는 에러 문구들을 모두 지워주는 함수
    function successForm(){
        const inputLineColor1 = document.getElementById("loginId");   //id input black
        inputLineColor1.style.outline = "1px solid black";

        const inputLineColor2 = document.getElementById("loginPw");  //비밀번호 input다시 black으로
        inputLineColor2.style.outline = "1px solid black";

        $('#validNoId').html("");
        $('#validNoPw').html("");
        $('#validNotCorrect').html("");
    }


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
            // alert("success");
            // console.log("응답");
            // console.log(res);


            if (res['success'] === 200) {
                swal('로그인 성공!', res.name + "님 로그인 되었습니다.", 'success')
                    .then(function () {
                        successForm(); //기존에 에러 메세지가 있다면 모두 지워준다.
                        // console.log(res);
                        // console.log("성공");
                        //location.href = '/';  //메인화면 디자인 해서 주소 바꾸기
                    })
            } else {
                validation(res);
            }

        },
        error: function () {
            alert("error")
        }
    });
});
