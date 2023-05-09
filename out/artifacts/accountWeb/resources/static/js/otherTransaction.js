$("#show_more").click(function () {  //7일치 더 보기를 클릭할 경우의 로직
    
    // console.log(dateCnt);
    function showMore(changeDateCnt) {

        $('#topTable > div').empty();

        //사용자가 클라이언트 에서 7일치 더보기 버튼을 클릭할 경우 서버에서 최대 보여줄 수 있는 값을 받아 온 후에 다시
        //기존에 html의 테이블을 지우고 태그 속성을 변경해 주어야 하는데 타임리프 문법을 그대로 사용하면 렌더링이 되지 않아
        //타임리프에서 자바스크립트로 필요한 데이터를 넘겨주어
        //자바스크립트에서 해당 데이터를 통해 같은 구조를 만들어 타임리프에 추가해주는 코드


        var theadHtml = '';
        var week = new Array('일', '월', '화', '수', '목', '금', '토');

        //table전체 부분을 만드는 부분
        $.each(getDailyTotalData, function (index, transactionDate) {

            //바디 태그 부분만 담당 해주는 코드
            var tbodyHtml = '';
            $.each(transactionHistory, function (index, history) {
                // console.log(index);
                // console.log(history.transaction_date);
                // console.log(history.memo);
                // console.log(history.price.toLocaleString());
                if (history.transaction_date === transactionDate.transaction_date) {
                    tbodyHtml += '<tr>';
                    tbodyHtml += '<th scope="row">' + (index + 1) + '</th>';
                    tbodyHtml += '<td>' + history.name + '</td>';
                    tbodyHtml += '<td>' + history.memo + '</td>';
                    tbodyHtml += '<td>' + history.price.toLocaleString() + '</td>';
                    tbodyHtml += '</tr>';
                }
            });


            // console.log(new Date(transactionDate.transaction_date).getDay());
            var today = new Date(transactionDate.transaction_date).getDay();

            if ((index + 1) <= changeDateCnt) {
                // console.log("만들어지는 테이블 갯수")
                theadHtml += '<table class="table table-hover" style="text-align: center; font-size: small">';
                theadHtml += '<thead>';
                theadHtml += '<tr style="background-color: #FDF5E6">';
                theadHtml += '<th colspan="2" style="text-align: left; padding-left: 15px">' + transactionDate.transaction_date + week[today] + '</th>';
                theadHtml += '<th colspan="2" style="text-align: right; padding-right: 15px">' + '합계(수입 :' + '<span style="color: red">' + transactionDate.dayCntIncome + '</span>' + '지출 : ' + '<span style="color: blue">' + transactionDate.dayCntExpend + '</span>' + ')';
                theadHtml += '</th>';
                theadHtml += '</tr>';
                theadHtml += '<tr style="background-color: #FDF5E6">';
                theadHtml += '<th scope="col">' + '번호' + '</th>';
                theadHtml += '<th scope="col">' + '분류명' + '</th>';
                theadHtml += '<th scope="col">' + '매모' + '</th>';
                theadHtml += '<th scope="col">' + '거래' + '</th>';
                theadHtml += '</tr>';
                theadHtml += '</thead>';
                theadHtml += '<tbody>' + tbodyHtml;

                theadHtml += '</tbody>';
                theadHtml += '</table>';
            }
        });

        $('#topTable > div').append(theadHtml);
        // console.log(transactionHistory);

    }


    $.ajax({
        url: "/transaction/showTransaction/dateCnt",
        data: {"dateCnt": dateCnt}, //data: info, JSON.stringify(info)
        type: "get",
        dataType: "json",   //dataType : "html", "json", "text"
        contentType: "application/json; charset=utf-8",
        success: function (res) {
            // console.log(res);

            let changeDate = res['changeDateCnt'];
            showMore(changeDate);
            // console.log(changeDate);

            if (res['lastPage'] === 1) {
                swal('※알림', "마지막 지출 내역 입니다.", 'info')
                    .then(function () {
                        // location.href = '/usr/loginForm';
                    })
            }

        },
        error: function () {
            alert("error")
        }
    });


});


//맨위로 이동하는 버튼을 클릭하면 페이지 상단으로 이동시키는 로직
$("#moveTopBtn").click(function () {

    // console.log("상단 이동")
    window.scrollTo({top: 0, left: 0, behavior: 'smooth'});

})


//특정 회원의 지출내역 화면에서 radio button에 대한 검색 조건에 따라 화면 설정 하는 javascript부분
//step1 : 우선 radio button에 따라 검색 조건에 따라 디자인만 변경해주는 부분
$(".search_select").click(function () {

    //현재 어떤 radio button이 선택 되어 있는지 받아온다.
    var checkVal = $('input[name=search]:checked').val();
    // console.log(checkVal);

    if (checkVal === "이번 달") {
        // console.log("이번 달");
        $('#whichRadio > th').remove();
        $('#whichRadio > td').remove();
        $('#whichRadio').append(
            '<th style="background-color: #dcdcdc">당월</th>' +
            '<td>' +
            year +
            '년' +
            month +
            '월' +
            '</td>'
        );
    } else if (checkVal === "월별검색") { //타임리프 에서 받아야 할 데이터 : 연도, 월, 분류명
        // console.log("월별검색");
        // $('#whichRadio > th,td').empty();
        $('#whichRadio > th').remove();
        $('#whichRadio > td').remove();
        $('#whichRadio').append(
            '<th style="background-color: #dcdcdc">월 선택</th>' +
            '<td>' +
            '<input type="month" id="selectMonth"/>' +
            '</td>'
        );
    } else if (checkVal === "기간별검색") {
        // console.log("기간별검색");
        $('#whichRadio > th').remove();
        $('#whichRadio > td').remove();
        $('#whichRadio').append(
            '<th style="background-color: #dcdcdc; vertical-align: middle">기간 선택</th>' +
            '<td>' +
            '시작일 : <input type="date" name="startDate" id="startDate">' +
            '</br>' +
            '종료일 : <input type="date" name="endDate" id="endDate">' +
            '</td>'
        );
    }

})

//특정 회원의 지출내역 화면에서 radio button에 대한 검색 조건에 따라 화면 설정 하는 javascript부분
//step2 : step1에서 바뀐 디자인에 사용자가 데이터를 입력 or 선택 하고 검색 버튼을 클릭하면 지출 내역 데이터를 바꾸어 주는 부분
//발생 가능 상황 : 월 선택, 검색어 / 기간 데이터, 검색어 / 이번달, 검색어에 대한 결과
$('#searchBtn').click(function () { // ==> 각 검색 조건에 있어 어떠한 라디오 버튼에 대한 검색 인지 다르게 동작하는 로직

    var checkVal = $('input[name=search]:checked').val();
    // console.log("click button!!");
    let userId1 = userId; //타임리프에서 받아온 userId값을 저장하는 변수 (저장하고 사용하지 않으면 페이지 이동 or 뒤로가기시 null값이 들어가 오류 발생)
    //controller로 데이터를 보냈을때 해당 데이터가 radio button이 월별검색으로 선택된 데이터라는 것을 알려주기 위해 넘기는 변수
    let typeRadio;

    if (checkVal === "이번 달") {
        // console.log();
        location.href = '/transaction/showTransaction?userId=' + userId1; //이번달을 선택한고 검색 버튼을 클릭하면 해당 회원의 아이디값을 해당 url로 넘겨 준다.
    } else if (checkVal === "월별검색") {
        // console.log($('#selectMonth').val());
        let date = $('#selectMonth').val(); //사용자가 radio button중 월별검색 에서 선택한 날짜
        let year = date.split('-')[0]; //사용자가 선택한 연도
        let month = date.split('-')[1]; //사용자가 선택한 월
        // console.log("year : " + year + " month : " + month);
        let sortName = $('#sortName').val(); //사용자가 입력한 분류명
        //controller로 데이터를 보냈을때 해당 데이터가 radio button이 월별검색으로 선택된 데이터라는 것을 알려주기 위해 넘기는 변수
        typeRadio = "searchMonth";

        // 1. controller로 어떤 데이터인지 알려주기 위한 변수 보내주기 2.특정 월을 선택하지 않으면 alert경고 보내기기  3. 검색가 입력되면 mabatis에 조건문 달아주기
        // console.log("분류명 : " + sortName);
        // console.log("date : " + date);
        // alert('stop');

        if (date.trim().length === 0) {
            swal('알림!', "검색을 위해 월을 선택해 주세요.", 'warning');
        } else {
            // console.log(userId1);
            location.href = '/transaction/showTransaction/whichSelect?userId=' + userId1 + '&selectYear=' + year + '&selectMonth=' + month + '&typeRadio=' + typeRadio + '&sortName=' + sortName;
        }
    } else if (checkVal === "기간별검색") {
        typeRadio = "searchPeriod";
        let sortName = $('#sortName').val();
        let startDate = $('#startDate').val();
        let endDate = $('#endDate').val();
        // console.log(startDate, endDate);

        if(startDate.trim().length === 0 || endDate.trim().length === 0){
            swal('알림!', "검색을 위해 시작일과 종료일을 선택해 주세요.", 'warning');
            // console.log("에러1");
        }else if(startDate > endDate){
            swal('알림!', "종료일이 시작일 보다 빠릅니다.", 'warning');
            // console.log("에러2");
        }else{
            location.href = '/transaction/showTransaction/whichSelect?userId=' + userId1 +  '&typeRadio=' + typeRadio + '&sortName=' + sortName + '&startDate=' + startDate + '&endDate=' + endDate;
        }

    }


})


//페이지에 따라 radio button 체크 박스 설정하기(페이지가 리로딩 되어도 해당 페이지에 맞게 사용자가 입력한 검색 조건이 유지되도록 하는 로직)
const target1 = document.getElementById('firstSearch');
const target2 = document.getElementById('secondSearch');
const target3 = document.getElementById('thirdSearch');


//현재 페이지를 (ex)locahost:8085다음 ?전까지 불러온다
const nowPage = window.location.pathname;
// console.log("현재 페이지 : " + nowPage);
const typeRadio1 = beforeTypeRadio; //월별검색, 기간별검색의 경우 controller에서 하나의 url로 데이터를 받아  처리하기 때문에 두 기능에 대해 구분할 수 있는 변수가 필요

if (nowPage === "/transaction/showTransaction") {
    target1.checked = false;
    target2.checked = false;
    target3.checked = true;
    $('#whichRadio > th').remove();
    $('#whichRadio > td').remove();
    $('#whichRadio').append(
        '<th style="background-color: #dcdcdc">당월</th>' +
        '<td>' +
        year +
        '년' +
        month +
        '월' +
        '</td>'
    );
} else if (nowPage === "/transaction/showTransaction/whichSelect" && typeRadio1 === "searchMonth") {
    // console.log(typeRadio1);
    target1.checked = true;
    target2.checked = false;
    target3.checked = false;

    $('#whichRadio > th').remove();
    $('#whichRadio > td').remove();
    $('#whichRadio').append(
        '<th style="background-color: #dcdcdc">월 선택</th>' +
        '<td>' +
        '<input name="inputValue" type="month" id="selectMonth" value="" />' +
        '</td>'
    );
    $('input[name=inputValue]').attr('value', selectYear + "-" + selectMonth); //valuer값에 타임리프에서 보내준 사용자가 선택한 년,월의 값을 넣는다.

    //사용자가 분류명을 했을경우 입력한 분류명을 검색창에 유지시키기 위한 로직
    // console.log("값 : " + sortName);
    let sort = sortName;
    //설명 : 사용자가 데이터를 입력하고 컨틀롤러로 사용자가 입력한 분류명을 전송 -> controller에서 클라이언트로 해당 분류명을 함께 전송
    //-> 만약 분류명이 있다면 검색 input에 해당 분류명을 placeholder에 두고 없다면 분류명 입력을 placeholder 문구로 사용
    if (!sort) {
        $('#whichSortName > th').remove();
        $('#whichSortName > td').remove();
        $('#whichSortName').append(
            '<th style="background-color: #dcdcdc">조건 추가(선택사항)</th>' +
            '<td>' +
            '<input class="form-control form-control-sm" type="text" placeholder="분류명 입력" aria-label="default input example" id="sortName">' +
            '</td>'
        );
    } else if (sort) {
        $('#whichSortName > th').remove();
        $('#whichSortName > td').remove();
        $('#whichSortName').append(
            '<th style="background-color: #dcdcdc">조건 추가(선택사항)</th>' +
            '<td>' +
            '<input name="inputValue2" class="form-control form-control-sm" type="text" placeholder="" aria-label="default input example" id="sortName">' +
            '</td>'
        );
        $('input[name=inputValue2]').attr('placeholder', "분류명을 입력");
        $('input[name=inputValue2]').attr('value', sort);
    }


} else if (nowPage === "/transaction/showTransaction/whichSelect" && typeRadio1 === "searchPeriod") {
    target1.checked = false;
    target2.checked = true;
    target3.checked = false;
    $('#whichRadio > th').remove();
    $('#whichRadio > td').remove();
    $('#whichRadio').append(
        '<th style="background-color: #dcdcdc; vertical-align: middle">기간 선택</th>' +
        '<td>' +
        '시작일 : <input name="periodInputValue1" type="date" name="startDate" id="startDate" value="">' +
        '</br>' +
        '종료일 : <input name="periodInputValue2" type="date" name="endDate" id="endDate" value="">' +
        '</td>'
    );
    $('input[name=periodInputValue1]').attr('value', startDate);
    $('input[name=periodInputValue2]').attr('value', endDate);

    let sort = sortName;
    if (!sort) {
        $('#whichSortName > th').remove();
        $('#whichSortName > td').remove();
        $('#whichSortName').append(
            '<th style="background-color: #dcdcdc">조건 추가(선택사항)</th>' +
            '<td>' +
            '<input class="form-control form-control-sm" type="text" placeholder="분류명 입력" aria-label="default input example" id="sortName">' +
            '</td>'
        );
    } else if (sort) {
        $('#whichSortName > th').remove();
        $('#whichSortName > td').remove();
        $('#whichSortName').append(
            '<th style="background-color: #dcdcdc">조건 추가(선택사항)</th>' +
            '<td>' +
            '<input name="inputValue2" class="form-control form-control-sm" type="text" placeholder="" aria-label="default input example" id="sortName">' +
            '</td>'
        );
        $('input[name=inputValue2]').attr('placeholder', "분류명을 입력");
        $('input[name=inputValue2]').attr('value', sort);
    }

}
