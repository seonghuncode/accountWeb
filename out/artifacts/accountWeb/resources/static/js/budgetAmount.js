// 사용자가 자신의 거래내역 페이지 에서 예산액 수정을 클릭할 경우 propt창을 통해 예산액 수정 진행
$('#budgetAmount').click(function () {

    //거래 내역 페이지 에서 받아오는 데이터
    var loginId = userId; //현재 로그인한 사용자 아이디
    var pageYear = year; //이번달에서 사용자가 검색한 연도(당연도)
    var pageMonth = month; //이번달 에서 사용자가 검색한 월(당월)
    var selectYear1 = selectYear; //월별 검색 에서 사용자가 선택한 특정 연도
    var selectMonth1 = selectMonth; //월별 검색 에서 사용자가 선택한 월
    var startDate1 = startDate; //기간별 검색에서 사용자가 선택한 시작 날짜
    var endDate1 = endDate; //기간별 검색에서 사용자가 선택한 마지만 날자

    // console.log(startDate1Year)

    //특정 날짜에 대한 예산액을 설정하기 위해 현재 페이지 에서 설정 되어 있는 날짜를 저장하는 변수
    var year1;
    var month1;
    //만약 기간별 검색일 경우 종료 연도, 월을 담아주는 변수
    var year2;
    var month2;

    let budget; //사용자로 부터 입력 받은 예산액을 저장하는 변수

    //사용자가 거래내역 페이지 에서 어떠한 검색 조건으로 검색을 하느냐에 따라 다른 값을 받기 때문에 선별
    if (pageYear && pageMonth) { //사용자가 당월에 대한 검색 조건을 선택했을 경우
        year1 = pageYear;
        month1 = pageMonth;
        budget = prompt(year1 + "년 " + month1 + '월에 대해 설정하고 싶은 예산액을 입력해 주세요. \n (숫자만 입력 가능 합니다!)');
    } else if (selectYear1 && selectMonth1) { //사용자가 특정원에 대한 검색 조건을 선택 했을 경우
        year1 = selectYear1;
        month1 = selectMonth1;
        budget = prompt(year1 + "년 " + month1 + '월에 대해 설정하고 싶은 예산액을 입력해 주세요. \n (숫자만 입력 가능 합니다!)');
    } else if (startDate1 && endDate1) { //사용자가 기간별에 대한 검색 조건을 선택 했을 경우
        year1 = startDate1.substr(2, 2);
        month1 = startDate1.substr(5, 2);

        year2 = endDate1.substr(2, 2);
        month2 = endDate1.substr(5, 2);
        budget = prompt(year1 + "년 " + month1 + '월에 대해 설정하고 싶은 예산액을 입력해 주세요. \n (숫자만 입력 가능 합니다!)');
    }
    
    const budgetValue = Number(budget);
    //console.log("입력받은 예산액" + budgetValue);

    let data1 = { // 현재 분류명을 불러 오기 위해 필요한 데이터
        "loginId": loginId,
        "year": year1,
        "month": month1,
        "budgetValue": budgetValue
    };


    if(budget != null){  // 사용자가 취소 버튼을 누르지 않았을 경우
        if(budgetValue == ""){
            alert("예산액을 입력해 주세요!");
        }else if(!budgetValue) {
            alert("예산액은 숫자만 입력 가능 합니다.");
        }else if(budgetValue > 999999999){
            alert("예산액은 숫자만 999,999,999원 까지만 가능 합니다.");
        }else{
            budgetProcess();
            alert(budgetValue +'원 으로 예산액이 설정 되었습니다.');
            location.reload();
        }
    }

    // console.log(loginId);
    // console.log(year1);
    // console.log(month1);
    // console.log(budgetValue);

    //예산액을 데이터베이스에 반영하는 함수
    function budgetProcess(){
        $.ajax({
            url: "/transaction/budgetAmount",
            data: data1,  //JSON.stringify(search)
            type: "get",
            dataType: "json",
            contentType: "application/json; charset=UTF-8",
            success: function (data) {
                //console.log(data);
                // if(data.result == 'success'){
                //     console.log("예산액 추가 성공");
                // }else{
                //     console.log("예산액 추가 실패");
                // }
            }, error: function () {
                alert("error");
            }
        })
    }



})