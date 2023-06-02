// 사용자가 보고 있는 지출 내역 페이지가 현재 로그인한 회원의 지출내역 페이지 일때 분류명 관리 버튼을 클릭할 경우 팝업 형식으로 관리 페이지 띄우기
$('#openPopUp').click(function () {

    // var _width = '650';
    // var _height = '380';
    var _width = '850';
    var _height = '480';

    // 팝업을 가운데 위치시키기 위해 아래와 같이 값 구하기
    var _left = Math.ceil((window.screen.width - _width) / 2);
    var _top = Math.ceil((window.screen.height - _height) / 2);

    //거래 내역 페이지 에서 받아오는 데이터
    var loginId = userId; //현재 로그인한 사용자 아이디
    var pageYear = year; //이번달에서 사용자가 검색한 연도(당연도)
    var pageMonth = month; //이번달 에서 사용자가 검색한 월(당월)
    var selectYear1 = selectYear; //월별 검색 에서 사용자가 선택한 특정 연도
    var selectMonth1 = selectMonth; //월별 검색 에서 사용자가 선택한 월
    var startDate1 = startDate; //기간별 검색에서 사용자가 선택한 시작 날짜
    var endDate1 = endDate; //기간별 검색에서 사용자가 선택한 마지만 날자

    // console.log(startDate1Year)

    var year1;
    var month1;
    //만약 기간별 검색일 경우 종료 연도, 월을 담아주는 변수
    var year2;
    var month2;


    //사용자가 거래내역 페이지 에서 어떠한 검색 조건으로 검색을 하느냐에 따라 다른 값을 받기 때문에 선별
    if (pageYear && pageMonth) {
        year1 = pageYear;
        month1 = pageMonth;
    } else if (selectYear1 && selectMonth1) {
        year1 = selectYear1;
        month1 = selectMonth1;
    } else if (startDate1 && endDate1) {
        year1 = startDate1.substr(2, 2);
        month1 = startDate1.substr(5, 2);

        year2 = endDate1.substr(2, 2);
        month2 = endDate1.substr(5, 2);

    }

    window.open('/transaction/sortManage?loginId=' + loginId + '&year=' + year1 + '&month=' + month1 + '&year2=' + year2 + '&month2=' + month2, 'popup-test', 'width=' + _width + ', height=' + _height + ', left=' + _left + ', top=' + _top);

})


//분류명 관리 에서 사용자가 분류명 추가를 진행하고 완료 버튼을 클릭했을 경우 로직
// $('#sortComplete').click(function () { //아래 방식으로 변경한 이유 : 하단에서 제이쿼리로 id값을 변경했을 경우 해당 id값으로 이벤트 핸들러에 반영하기 위함
$(document).on('click', '#sortComplete', function() {


    //버튼을 클릭하면 무조건 실행되는 부분------------------------------------------------------------------------------------------------------
    //현재 로그인 되어있는 회원의 PK값
    var loginId = primaryId;

    var sortDateWhichSelect = $('input[name=selAllOrMonth]:checked').val(); //선택된 라디오 버튼의 값
    var sortSelectValue; //선택된 라디오 버튼에 따라 해당 값을 담을 변수
    if (sortDateWhichSelect == "특정월") {
        sortSelectValue = $("#monthLabel1").val();
    } else {
        sortSelectValue = "항상";
    }
    //console.log(sortSelectValue);
    // console.log($("#addSortLabel2").val())
    // console.log($('.addSortLabel2').val())

    let sortData = {
        "addSort": $(".addSortLabel2").val().replace(/^\s+|\s+$/gm, ''), //사용자가 입력한 추가할 분류명(앞뒤 공백 제거)
        "sortDate": sortSelectValue,  //선택된 라디오 버튼에 따라 해당 값 저장
        "loginIdByPK": loginId,
        "result": false          //결과값으로 result는 false로 보내서 문제 없이 진행이 되었다면 true로 반환 받는다.
    };

    // console.log(sortData)

    //상요자가 분류명을 입력 하지 않고 완료 버튼을 클릭한 경우 or 10글자 이상 작성한 경우
    var sortName = $(".addSortLabel2").val().replace(/^\s+|\s+$/gm, '');
    const sortCheck = document.getElementById('sortCheck');
    if (sortName == "") {
        sortCheck.style.color = "red";  //분류명이 빈값이라면 조건의 글자를 빨간색
    } else {
        sortCheck.style.color = "black"; //사용자가 입력을 했다면 다시 조건 글시를 검정색 으로
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    //분류명 추가 로직이 실패했을 경우 실행 되는 함수------------------------------------------------------------------------------------------
    function failToAdd(data) {
        // console.log("failToAdd : " + data.result);
        var sortValid = document.getElementById('sortValid'); //이미 존재 하는 분류명일 경우
        var sortName = $(".addSortLabel2").val().replace(/^\s+|\s+$/gm, ''); //사용자가 입력한 분류명
        var addSortSuccess = document.getElementById('addSortSuccess'); //사용자가 성공했을 경우 성공메세지를 보여줄 위치의 태그

        if (data.result == "false") { //이미 존재하는 분류명이라면
            sortValid.style.color = "red";

            //실패 할 경우 성공했을때 나타난 성공 메세지 없애기
            $('#addSortSuccess').text('');
            addSortSuccess.style.color = "black";
        } else if (data.result == "true") {
            sortValid.style.color = "black";

            //만약 분류명 추가가 성공적으로 완료 됬을 경우 사용자 에게 성공 알리기
            $('#addSortSuccess').text('분류명 ' + sortName + ' 이(가) 성공적으로 추가 되었습니다.');
            addSortSuccess.style.color = "blue";
        }


    }

    $.ajax({
        url: "/transaction/sortAddProcess",
        data: sortData,  //JSON.stringify(search)
        type: "get",
        dataType: "json",
        contentType: "application/json; charset=UTF-8",
        success: function (data) {

            // console.log(data);
            failToAdd(data); //분류명 추가를 실패했을 경우 사용자 에세 색상으로 오류 알리는 함수 + 성공시 알리기


        }, error: function () {
            alert("error");
        }
    })


})



//분류명 관리에 대한 기능--------------------------------------------------------------------------------

//만약 사용자가 분류명 관리 페이지 에서 추가 버튼을 클릭할 경우 UI변경
$('#sortAdd').click(function () {

    //추가 버튼을 클릭하면 상단에 추가 페이지 라는 것을 알려준다
    const element = document.getElementById('sortFnName');
    element.innerHTML = '<h6 style="margin-bottom: 13px" id="sortFnName">기능 : 분류명 추가</h6>'

    //console.log(element.textContent);

    //현재 페이지가 분류명 추가라면 해당 완료 버튼에 대한 id값이 분류명 추가 하는 ajax랑 연결된 id값을 주어야 된다.
    if(element.textContent == '기능 : 분류명 추가'){

        //자바스크립트로 특정 태그를 수정 하는 방법
        // const element2 = document.getElementById('sortWhichSelect');
        // element2.innerHTML  =
        //     '<button type="button" class="btn btn-outline-primary" style="width: 70%; display: inline;" id="sortComplete">완 료(추가)</button>' +
        //     '<a type="button" class="btn btn-outline-secondary" style="width: 28%; float: right; display: inline" onclick="javascript:window.close();" >닫 기</a>'

        $('#sortWhichSelect > button').text('완 료(추가)');
        //제이쿼리로 특정 태그의 속성을 변경하는 방법
        $('#sortWhichSelect > button').attr('id', 'sortComplete');
    }

})


//만약 사용자가 분류명 관리 페이지 에서 수정 버튼을 클릭할 경우 UI변경
$('#sortModify').click(function () {

    //상단 현재 페이지가 수정 페이지라는 것을 나타낸다다
    const element = document.getElementById('sortFnName');
    element.innerHTML = '<h6 style="margin-bottom: 13px" id="sortFnName">기능 : 분류명 수정</h6>'

    if(element.textContent == '기능 : 분류명 수정'){
        // const element2 = document.getElementById('sortWhichSelect');
        // element2.innerHTML  =
        //     '<button type="button" class="btn btn-outline-primary" style="width: 70%; display: inline" id="sortCompleteModify">완 료(수정)</button>\n' +
        //     '<a type="button" class="btn btn-outline-secondary" style="width: 28%; float: right; display: inline" onclick="javascript:window.close();" >닫 기</a>'

        $('#sortWhichSelect > button').text('완 료(수정)');
        $('#sortWhichSelect > button').attr('id', 'sortCompleteModify');
    }


})

//만약 사용자가 분류명 관리 페이지 에서 수정 버튼을 클릭할 경우 UI변경
$('#sortDelete').click(function () {

    //상단 현재 페이지가 수정 페이지라는 것을 나타낸다다
    const element = document.getElementById('sortFnName');
    element.innerHTML = '<h6 style="margin-bottom: 13px" id="sortFnName">기능 : 분류명 삭제</h6>'


    if(element.textContent == '기능 : 분류명 삭제'){
        // const element2 = document.getElementById('sortWhichSelect');
        // element2.innerHTML  =
        //     '<button type="button" class="btn btn-outline-primary" style="width: 70%; display: inline" id="sortCompleteDelete">완 료(삭제)</button>\n' +
        //     '<a type="button" class="btn btn-outline-secondary" style="width: 28%; float: right; display: inline" onclick="javascript:window.close();" >닫 기</a>'


        $('#sortWhichSelect > button').text('완 료(삭제)');
        
        $('#sortWhichSelect > button').attr('id', 'sortCompleteDelete');

    }

})



