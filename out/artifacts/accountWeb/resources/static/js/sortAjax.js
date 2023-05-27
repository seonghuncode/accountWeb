// 사용자가 보고 있는 지출 내역 페이지가 현재 로그인한 회원의 지출내역 페이지 일때 분류명 관리 버튼을 클릭할 경우 팝업 형식으로 관리 페이지 띄우기
$('#openPopUp').click(function (){

    // var _width = '650';
    // var _height = '380';
    var _width = '850';
    var _height = '480';

    // 팝업을 가운데 위치시키기 위해 아래와 같이 값 구하기
    var _left = Math.ceil(( window.screen.width - _width )/2);
    var _top = Math.ceil(( window.screen.height - _height )/2);

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
    if(pageYear && pageMonth){
        year1 = pageYear;
        month1 = pageMonth;
    }else if(selectYear1 && selectMonth1){
        year1 = selectYear1;
        month1 = selectMonth1;
    }else if(startDate1 &&  endDate1){
        year1 =  startDate1.substr(2,2);
        month1 = startDate1.substr(5,2);

        year2 = endDate1.substr(2,2);
        month2 = endDate1.substr(5,2);

    }

    window.open('/transaction/sortManage?loginId=' + loginId + '&year=' + year1 + '&month=' + month1 + '&year2=' + year2 + '&month2=' + month2, 'popup-test', 'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top );

})


//분류명 관리 에서 사용자가 분류명 추가를 진행하고 완료 버튼을 클릭했을 경우 로직
$('#sortComplete').click(function (){


    var sortDateWhichSelect = $('input[name=selAllOrMonth]:checked').val(); //선택된 라디오 버튼의 값
    var sortSelectValue; //선택된 라디오 버튼에 따라 해당 값을 담을 변수
    if(sortDateWhichSelect == "특정월"){
        sortSelectValue = $("#monthLabel1").val();
    }else{
        sortSelectValue = "항상";
    }
    //console.log(sortSelectValue);
    // console.log($("#addSortLabel2").val())
    // console.log($('.addSortLabel2').val())

    let sortData = {
        "addSort": $(".addSortLabel2").val(), //사용자가 입력한 추가할 분류명
        "sortDate": sortSelectValue  //선택된 라디오 버튼에 따라 해당 값 저장
    };

    $.ajax({
        url: "/transaction/sortAddProcess",
        data: sortData,  //JSON.stringify(search)
        type: "get",
        dataType: "json",
        contentType: "application/json; charset=UTF-8",
        success: function (data) {


            showSearchData(data);  //검색어에 대한 회원 정보 테이블

            showPaging(data, search); //검색어에 대한 페이징 기능
            // console.log("페이징 버튼을 클릭 후 받아온 데이터")
            // console.log(data); //controller로 부터 받아온 데이터 확인

        }, error: function () {
            alert("error");
        }
    })



})