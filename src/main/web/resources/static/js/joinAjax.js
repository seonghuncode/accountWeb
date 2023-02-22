//join.html에서 회원가입에 대한 기능을 서버로 전송하고 결과에 대한 로직을 수행하는 패이지

//<!-- JQuery를 통한 ajax방식-->
//<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
// <script type="text/javascript">

    $("#try-join").click(function() {
        //alert("click");
        //let data = getData();

        let info = {
            name : $("#name").val(),
            email : $("#email").val(),
            userId : $("#userId").val(),
            userPw1 : $("#userPw1").val(),
            userPw2 : $("#userPw2").val(),
            view_yn : $('input:radio[name=select]:checked').val()
        };


        $.ajax({
            url : "/usr/joinFn",
            data : JSON.stringify(info),  //data: info, JSON.stringify(info)
            method : "post",
            dataType : "json",   //dataType : "html",
            contentType: "application/json; charset=utf-8",
            success : function(res) {
                alert("success");
                console.log("controller에서 받은 데이터 ==>  ")
                console.log(res);

                // if (response['saveResult'] === 200) {
                //     alert('정상적으로 등록되었습니다.');
                //     location.href = '/users/list';
                // }



            },
            error : function() {
                alert("error")
            }
        });
    });



//    </script>