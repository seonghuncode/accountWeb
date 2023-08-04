var finalValidResult = "success"; //최종적으로 해당 변수 값이 fail로 바뀌지 않는다면 회원 정보를 수정하는 통신이 가능하도록 확인하는 변수

//사용자가 회원 정보 수정 버튼을 클릭할때마다 그 당시 사용자가 입력한 값을 변수에 저장한다
//사용자가 유효성 통과후 비밀번호 입력확인 후 DB에 쿼리 업데이트를 할때 데이터를 보내주기 위해 전역변수로 사용해야 한다.
var newName;
var newEmail;
var newUserId;
var newPassword;
var newCheckPassword;
var newView_YN;
var regDate;


//회원 정보 수정 버튼을 클릭했을 경우 실행되는 로직---------------------------------------------------------------------
$("#modifyMyInfo2").click(function () {

    finalValidResult = "success"; //클릭하면 fail로 되어있어도 success로 바꾸어 주어야 된다.

    newName = $('#exampleFormControlInput1').val();
    newEmail = $('#exampleFormControlInput2').val();
    newUserId = $('#exampleFormControlInput3').val();
    newPassword = $('#exampleFormControlInput4').val();
    newCheckPassword = $('#exampleFormControlInput5').val();
    newView_YN = $('input[name=select]:checked').val();
    regDate = $('#exampleFormControlInput7').val();

    // console.log("newName : " + newName);
    // console.log("newUserId : " + newUserId);
    // console.log("newEmail : " + newEmail);
    // console.log("newPassword : " + newPassword);
    // console.log("newCheckPassword : " + newCheckPassword);
    // console.log("newView_YN : " + newView_YN);
    // console.log("regDate : " + regDate);


    validNewData(newName, newUserId, newEmail, newPassword, newCheckPassword, newView_YN, regDate);


    // console.log("유효성 검증 성공 여부 : " + finalValidResult);
    if (finalValidResult == "success") { //모든 유효성을 만족했다면 비밀번호 확인 진행후 성공시 실제 데이터베이스에 사용자가 입력한 데이터를 업데이트 하는 통신 진행
        openPasswordModal(); //모달 띄우는 함수
    }


})


//사용자가 입력한 값에 대해 유효성 검사를 하는 로직---------------------------------------------------------------------
function validNewData(newName, newUserId, newEmail, newPassword, newCheckPassword, newView_YN, regDate) {

    //이름에 대한 유효성 검사 로직------------------------------------------------------------------
    if (newName == "") {
        $(`#nameValid`).text("※회원 정보를 수정 하기 위해서는 이름을 반드시 입력해야 합니다.");
        $(`#nameValid`).css("color", "red");
        $(`#nameValid`).css("font-size", "small");
        finalValidResult = "fail";
    } else if (newName.length > 10) {
        $(`#nameValid2`).css("color", "red");
        finalValidResult = "fail";
    } else if (newName.trim != "" && newName.length <= 10) {
        $(`#nameValid`).text("");
        $(`#nameValid2`).css("color", "black");
    }


    //이메일에 대한 유효성 검사 로직-------------------------------------------------------------------------------
    var regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/; //이메일 형식을 확인하는 정규식

    if (newEmail == "") {
        $(`#emailValid`).text("※회원 정보를 수정 하기 위해서는 이메일은 반드시 입력해야 합니다.");
        $(`#emailValid`).css("color", "red");
        $(`#emailValid`).css("font-size", "small");
        finalValidResult = "fail";
    } else if (!regEmail.test(newEmail)) {
        $(`#emailValid`).text("※이메일 형식이 아닙니다.");
        $(`#emailValid`).css("color", "red");
        $(`#emailValid`).css("font-size", "small");
        finalValidResult = "fail";
    } else if (newEmail != "") {
        emailDuplicationProcess(newEmail);
    }

    //아이디에 대한 유효성 검사-------------------------------------------------------------------------------------
    if (newUserId == "") {
        $(`#userIdValid`).text("※회원정보 수정을 위해서는 아이디는 반드시 입력해야 합니다.");
        $(`#userIdValid`).css("color", "red");
        $(`#userIdValid`).css("font-size", "small");
        finalValidResult = "fail";
    } else if (newUserId.length < 6 || newUserId.length > 10) {
        $(`#userIdValid2`).css("color", "red");
        $(`#userIdValid`).text("");
        finalValidResult = "fail";
    } else if (newUserId != "" && newUserId.length >= 6 && newUserId.length <= 10) {
        userDuplicationProcess(newUserId);
        $(`#userIdValid2`).css("color", "black");
    }


    //비밀번호에 대한 유효성 검사-----------------------------------------------------------------------------------
    let regPass = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;

    if (newPassword != "" || newCheckPassword != "") {


        if (newPassword != newCheckPassword) {
            $(`#passwordValid1`).text("※입력한 비밀번호가 서로 일치 하지 않습니다.");
            $(`#passwordValid1`).css("color", "red");
            $(`#passwordValid1`).css("font-size", "small");
            $(`#passwordValid2`).text("※입력한 비밀번호가 서로 일치 하지 않습니다.");
            $(`#passwordValid2`).css("color", "red");
            $(`#passwordValid2`).css("font-size", "small");
            finalValidResult = "fail";
        } else if (!(regPass.test(newPassword.trim())) || !(regPass.test(newCheckPassword.trim()))) { //숫자, 문자, 특수문자 포함 8자~15 자리
            // console.log(newPassword)
            $(`#passwordValid1`).text("");
            $(`#passwordValid2`).text("");
            $(`#passwordValid11`).css("color", "red");
            $(`#passwordValid22`).css("color", "red");
            finalValidResult = "fail";
        } else {
            $(`#passwordValid1`).text("");
            $(`#passwordValid2`).text("");
            $(`#passwordValid11`).css("color", "black");
            $(`#passwordValid22`).css("color", "black");
        }

    }


}


//사용자가 입력한 이메일이 중복되는 이메일인지 중복 검사를 하는 통신----------------------------------------------------
function emailDuplicationProcess(newEmail) {
    var data = {
        userId: userId,
        userIdPK: userIdPK,
        email: newEmail
    };

    // console.log("emailDuplicationProcess : " + data);

    $.ajax({  //이메일 중복 여부를 확인하는 통신
        url: "/usr/checkEmailForModifyMyInfo",
        data: JSON.stringify(data),  //data: info, JSON.stringify(info)
        async: false,
        method: "post",
        dataType: "json",   //dataType : "html",
        contentType: "application/json; charset=utf-8",
        success: function (res) {
            // console.log(res);
            if (res.result == 'success') {
                $(`#emailValid`).text("※사용 가능한 이메일 입니다.");
                $(`#emailValid`).css("color", "blue");
                $(`#emailValid`).css("font-size", "small");
            } else if (res.result == 'fail') {
                $(`#emailValid`).text(newEmail + "은 이미 사용중인 이메일 입니다.");
                $(`#emailValid`).css("color", "red");
                $(`#emailValid`).css("font-size", "small");
                finalValidResult = "fail";
            }
        },
        error: function () {
            console.log("요청 또는 응답에 있어 문제가 발생했습니다.");
            alert("error")
        }
    });
}


//사용자가 입력한 아이디가 중복되는 아이디인지 중복 검사를 하는 통신----------------------------------------------------
function userDuplicationProcess(newUserId) {
    var data = {
        userId: newUserId,
        userIdPK: userIdPK,
    };

    // console.log("userDuplicationProcess : " + data);

    $.ajax({  //이메일 중복 여부를 확인하는 통신
        url: "/usr/checkUserIdForModifyMyInfo",
        data: JSON.stringify(data),  //data: info, JSON.stringify(info)
        async: false,
        method: "post",
        dataType: "json",   //dataType : "html",
        contentType: "application/json; charset=utf-8",
        success: function (res) {
            // console.log(res);
            if (res.result == 'success') {
                $(`#userIdValid`).text("※사용 가능한 아이디 입니다.");
                $(`#userIdValid`).css("color", "blue");
                $(`#userIdValid`).css("font-size", "small");
            } else if (res.result == 'fail') {
                $(`#userIdValid`).text(newUserId + "은 이미 사용중인 아이디 입니다.");
                $(`#userIdValid`).css("color", "red");
                $(`#userIdValid`).css("font-size", "small");
                finalValidResult = "fail";
            }
        },
        error: function () {
            console.log("요청 또는 응답에 있어 문제가 발생했습니다.");
            alert("error")
        }
    });
}


//사용자가 이메일에 대한 중복확인 버튼을 클릭할 경우 실행되는 로직------------------------------------------------------
$("#emailIdDuplication").click(function () {
    var regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/; //이메일 형식을 확인하는 정규식
    var newEmail = $('#exampleFormControlInput2').val();

    if (!regEmail.test(newEmail)) {
        $(`#emailValid`).text("※이메일 형식이 아닙니다.");
        $(`#emailValid`).css("color", "red");
        $(`#emailValid`).css("font-size", "small");
        finalValidResult = "fail";
    } else {
        emailDuplicationProcess(newEmail);
    }
})

//사용자가 아이디에 대한 중복확인 버튼을 클릭할 경우 실행되는 로직------------------------------------------------------
$("#userIdDuplication").click(function () {
    var newUserId = $('#exampleFormControlInput3').val();
    if (newUserId.length < 6 || newUserId.length > 10) {
        $(`#userIdValid2`).css("color", "red");
    } else if (newUserId != "" && newUserId.length >= 6 && newUserId.length <= 10) {
        userDuplicationProcess(newUserId);
        $(`#userIdValid2`).css("color", "black");
    }
})


//모달 관련 로직------------------------------------------------------------------------------------------------

// 비밀번호 입력 모달 열기
function openPasswordModal() {
    document.getElementById('passwordModal').style.display = 'block';
}

// 비밀번호 입력 모달 닫기
function closePasswordModal() {
    document.getElementById('passwordModal').style.display = 'none';
}

// 비밀번호 확인 버튼 클릭 시 처리
function submitPassword() {

    const password = document.getElementById('inputPassword').value;
    // console.log('입력한 비밀번호:', password);

    // 여기서부터는 입력한 비밀번호를 원하는 동작으로 처리하면 됩니다.
    if (password == null) {
        //취소를 클릭했을 경우
    } else if (password.trim() == "") {
        $(`#modifyInfoValid`).text("※비밀번호가 입력되지 않았습니다.");
        $(`#modifyInfoValid`).css("color", "red");
        $(`#modifyInfoValid`).css("text-align", "center");
    } else if (password.trim() != "") {
        $(`#modifyInfoValid`).text("");
        var result = checkPWProcess(password);
        // console.log(">>>>>> : " + result);
        if (result == "success") { //사용자가 입력한 비밀번호가 현재 로그인한 아이디와 일치하는 비밀번호일 경우
            $(`#modifyInfoValid`).text("");
            doUpdateUserInfo(newName, newEmail, newUserId, newPassword, newView_YN, regDate);
            closePasswordModal(); // 모달 닫기
        } else if (result == "fail") { //사용자가 입력한 비밀번호가 현재 로그인한 아이디와 일치하지 않을 경우
            $(`#modifyInfoValid`).text("※비밀번호가 현재 로그인한 회원의 아이디와 일치 하지 않습니다.");
            $(`#modifyInfoValid`).css("color", "red");
            $(`#modifyInfoValid`).css("text-align", "center");
        }

    }
}



//사용자가 모달에 입력한 비밀번호를 controller로 보내서 해당 비밀번호가 아이디와 일치하는지 확인한는 비동기 통신
function checkPWProcess(password){

    let data = {
        "userId": userId,
        "password": password
    };

    // console.log(data);
    var result = "문제 발생(checkPWProcess)부분!!";

    $.ajax({
        url: "/usr/checkPW",
        data: JSON.stringify(data), //data: info, JSON.stringify(info)
        type: "post",
        dataType: "json",   //dataType : "html", "json", "text"
        async: false, //동기 처리
        contentType: "application/json; charset=utf-8",
        success: function (res) {
            // console.log(res);
            result = res.result;
        },
        error: function () {
            alert("error")
        }
    });
    return result;
}

//모달 관련 로직------------------------------------------------------------------------------------------------


//사용자가 입력한 데이터가 유효성 검사를 통과하고 확인차 입력한 비밀번호까지 일치 했을 경우 최종적으로 업데이트를 진행하도록 통신하는 함수------------
function doUpdateUserInfo(newName, newEmail, newUserId, newPassword, newView_YN, regDate) {

    var data = {
        newName: newName,
        newEmail: newEmail,
        newUserId: newUserId,
        newPassword: newPassword,
        newView_YN: newView_YN,
        regDate: regDate,
        userIdPK: userIdPK,
    };

    // console.log("최종 수정할 데이터 : " + data);

    $.ajax({  //이메일 중복 여부를 확인하는 통신
        url: "/usr/doModifyUserInfo",
        data: JSON.stringify(data),  //data: info, JSON.stringify(info)
        async: false,
        method: "post",
        dataType: "json",   //dataType : "html",
        contentType: "application/json; charset=utf-8",
        success: function (res) {
            // console.log(res);
            if (res.result == 'success') {
                alert("회원 정보가 정상적으로 수정 되었습니다. \n※수정된 정보로 다시 로그인 해주세요.");
                doLogout();
            } else if (res.result == 'fail') {
                alert("회원 정보를 수정하는데 문제가 발생했습니다. 관리자에게 문의 하세요.");
            }
        },
        error: function () {
            console.log("요청 또는 응답에 있어 문제가 발생했습니다.");
            alert("error")
        }
    });

}


//정상적으로 회원 정보가 수정되고 로그아웃 시키는 통신
function doLogout(){
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
                        location.href = '/';  //메인화면 디자인 해서 주소 바꾸기
            }

        },
        error: function () {
            alert("error")
        }
    });
}