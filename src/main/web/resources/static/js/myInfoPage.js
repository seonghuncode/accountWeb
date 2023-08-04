//사용자가 정보 수정 버튼을 클릭했을 경우 로직
$("#modifyMyInfo").click(function () {
    openPasswordModal(); //모달 띄우는 함수
})


//모달 관련 함수---------------------------------------------------------------------------------------
//prompt대신 모달을 만들어서 사용하는 이유
//1. prompt로 비밀번호를 입력 받을 경우 사용자가 입력하는 비밀번호가 input창에 나오는 보안적 단점
//2. 동일하게 입력해도 한글로 입력할때와 비밀번호로 입력할때를 다르게 인식한다.

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
    if(password == null){
        //취소를 클릭했을 경우
    }else if(password.trim() == ""){
        $(`#modifyInfoValid`).text("※비밀번호가 입력되지 않았습니다.");
        $(`#modifyInfoValid`).css("color", "red");
        $(`#modifyInfoValid`).css("text-align", "center");
    }else if(password.trim() != ""){
        $(`#modifyInfoValid`).text("");
        var result = checkPWProcess(password);
        // console.log(">>>>>> : " + result);
        if(result == "success"){ //사용자가 입력한 비밀번호가 현재 로그인한 아이디와 일치하는 비밀번호일 경우
            location.href="/usr/myInfoModify?userId=" + userId  + "&name=" + name + "&email=" + email + "&view_YN=" + viewYN + "&createDate=" + createDate + "&userIdPK=" + userIdPK;
            closePasswordModal(); // 모달 닫기
        }else if(result == "fail"){ //사용자가 입력한 비밀번호가 현재 로그인한 아이디와 일치하지 않을 경우
            $(`#modifyInfoValid`).text("※비밀번호가 현재 로그인한 회원의 아이디와 일치 하지 않습니다.");
            $(`#modifyInfoValid`).css("color", "red");
            $(`#modifyInfoValid`).css("text-align", "center");
        }

    }


}
//모달 관련 함수---------------------------------------------------------------------------------------




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