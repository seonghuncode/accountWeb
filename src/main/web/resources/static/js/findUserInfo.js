//아이디 찾기 관련-------------------------------------------------------------------------------------------------------

//사용자가 아이디 찾기 페이지 에서 아이디 찾기 버튼을 클릭했을 경우 실행 되는 로직
$(`#try-findUserId`).click(function () {
    var userName = $('#findName').val();
    var userEmail = $('#findEmail').val();
    // console.log(userName);
    // console.log(userEmail);


    var result = validData(userName, userEmail); //사용자가 입력한 데이터 유효성 검사
    // console.log(result);
    if(result == "success"){ //유효성 검사를 성공 했다면
        findUserIdProcess(userName, userEmail);
    }

})


//사용자가 입력한 데이터가 조건을 충족하는지 검사 하는 로직
function validData(userName, userEmail){

    var result = "success";
    var regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/; //이메일 형식을 확인하는 정규식

    if(userName == ""){
        $(`#validNoName`).text("※아이디를 입력해 주세요.");
        $(`#validNoName`).css("color", "red");
        $(`#validNoName`).css("font-size", "small");
        result = "fail"
    }else if(userName != ""){
        $(`#validNoName`).text("");
    }

    if(userEmail == ""){
        $(`#validNoEmail`).text("※이메일을 입력해 주세요.");
        $(`#validNoEmail`).css("color", "red");
        $(`#validNoEmail`).css("font-size", "small");
        result = "fail"
    } else if (!regEmail.test(userEmail)) {
        $(`#validNoEmail`).text("※이메일을 형식이 아닙니다.");
        $(`#validNoEmail`).css("color", "red");
        $(`#validNoEmail`).css("font-size", "small");
        result = "fail"
    }else if(userEmail != "" && regEmail.test(userEmail)){
        $(`#validNoEmail`).text("");
    }

    return result;

}


//유효성 검사에서 성공할 경우 사용자가 입력한 데이터와 일치하는 아이디를 찾는 통신 로직
function findUserIdProcess(userName, userEmail){


    var data = {
        userName: userName,
        userEmail: userEmail
    };

    $.ajax({  //이메일 중복 여부를 확인하는 통신
        url: "/usr/findUserIdProcess",
        data: data,  //data: info, JSON.stringify(info)
        async: false,
        method: "get",
        dataType: "json",   //dataType : "html",
        contentType: "application/json; charset=utf-8",
        success: function (res) {

            // console.log("========");
            // console.log(res);
            // console.log(res.userId);

            if(res.userId == "fail"){
                $(`#validNotCorrect`).text("※아이디와 이메일이 일치 하지 않습니다.");
                $(`#validNotCorrect`).css("color", "red");
                $(`#validNotCorrect`).css("font-size", "small");
            }else{
                $(`#validNotCorrect`).text("");
                //swal("아이디 찾기 성공",userName + "님의 아이디는 '" + res.userId + "'입니다.", "success");
                swal({
                    title:"아이디 찾기 성공",
                    text:userName + "님의 아이디는 '" + res.userId + "'입니다. \n※로그인 페이지로 이동합니다.",
                    icon:"success",
                    buttons: "확인"
                }).then(()=>{
                    location.href = '/usr/loginForm';
                })
            }

        },
        error: function () {
            console.log("요청 또는 응답에 있어 문제가 발생했습니다.");
            alert("error")
        }
    });
}




//비밀번호 찾기 관련-------------------------------------------------------------------------------------------------------

//사용자가 비밀번호 찾기 페이지 에서 비밀번호 찾기 버튼을 클릭했을 경우 실행 되는 로직
$(`#try-findUserPw`).click(function () {
    var userName = $('#findName').val();
    var userId = $('#loginId').val();
    var userEmail = $('#findEmail').val();
    console.log(userName);
    console.log(userId);
    console.log(userEmail);

    var result = validFindPassword(userName, userId, userEmail);
    console.log(result);
    
    if(result == "success"){  //비밀번호 찾기 에서 사용자가 입력한 데이터들이 유효성 검사를 통과한 경우 실행되는 로직
        
    }
    
})


//사용자가 비밀번호 찾기 에서 입력한 데이터에 대해서 유효성 검사 하는 로직
function validFindPassword(userName, userId, userEmail){

    var result = "success";

    if(userName == ""){
        $(`#validNoName`).text("※이름을 입력해 주세요.");
        $(`#validNoName`).css("color", "red");
        $(`#validNoName`).css("font-size", "small");
        result = "fail";
    }else if(userName != ""){
        $(`#validNoName`).text("");
    }

    if(userId == ""){
        $(`#validNoId`).text("※아이디를 입력해 주세요.");
        $(`#validNoId`).css("color", "red");
        $(`#validNoId`).css("font-size", "small");
        result = "fail";
    }else if(userId != ""){
        $(`#validNoId`).text("");
    }

    var regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/; //이메일 형식을 확인하는 정규식
    if(userEmail == ""){
        $(`#validNoEmail`).text("※이메일을 입력해 주세요.");
        $(`#validNoEmail`).css("color", "red");
        $(`#validNoEmail`).css("font-size", "small");
        result = "fail";
    }else if(!regEmail.test(userEmail)){
        $(`#validNoEmail`).text("※이메일 형식이 아닙니다.");
        $(`#validNoEmail`).css("color", "red");
        $(`#validNoEmail`).css("font-size", "small");
        result = "fail";
    }else if(userEmail != "" && regEmail.test(userEmail)){
        $(`#validNoEmail`).text("");
    }

    return result;
    
}