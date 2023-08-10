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
                // console.log(history.id);
                // console.log(history);
                var memo = history.memo;
                var encodingMemo = encodeURIComponent(memo); //메모에 특수문자가 들어갈 경우 오류 발생 -> 특수문자가 들어갈 경우를 대비해 인코딩 하여 해결
                var sortName = history.name;
                var encodingSortName = encodeURIComponent(sortName);



                if (history.transaction_date === transactionDate.transaction_date && nowLoginUserId != userId) { //자신의 거래내역 페이지가 아닐 경우
                    tbodyHtml += '<tr>';
                    tbodyHtml += '<th scope="row">' + (index + 1) + '</th>';
                    tbodyHtml += '<td>' + history.name + '</td>';
                    tbodyHtml += '<td>' + history.memo + '</td>';
                    tbodyHtml += '<td>' + history.price.toLocaleString() + '</td>';
                    tbodyHtml += '</tr>';
                }else if(history.transaction_date === transactionDate.transaction_date && nowLoginUserId == userId){ //자신의 거래내역 페이지 일경우
                    tbodyHtml += '<tr>';
                    tbodyHtml += '<th scope="row">' + (index + 1) + '</th>';
                    tbodyHtml += '<td>' + history.name + '</td>';
                    tbodyHtml += '<td>' + history.memo + '</td>';
                    if(history.type == "수입"){
                        tbodyHtml += '<td style="color: red">' + history.price.toLocaleString() + '</td>';
                    }else if(history.type == "지출"){
                        tbodyHtml += '<td style="color: blue;">' + history.price.toLocaleString() + '</td>';
                    }


                    tbodyHtml += '<td>';
                    // tbodyHtml += '<button type="button" class="btn btn-outline-secondary" style="border: none; padding: 0px" id="modifyTransactionField" th:onclick="|location.href=\'@{/transaction/modifyTransactionField(transactionHistoryId = ${history.id} ,type=${history.type}, userId=${nowLoginUserId}, transactionDate=${history.transaction_date}, index=${index.count}, sortName=${history.name}, memo=${history.memo}, price=${history.price})}\'|">';
                    // tbodyHtml += '<a type="button" class="btn btn-outline-secondary" style="border: none; padding: 0px" id="modifyTransactionField" href='+"/transaction/modifyTransactionField?transactionHistoryId=" + history.id + '&type=' + history.type + '&userId=' + nowLoginUserId + '&transactionDate=' + history.transaction_date + '&index=' + (index+1) + '&sortName=' + history.name + '&memo=' + `${history.memo}` + '&price=' + history.price + '>';
                    tbodyHtml += '<a type="button" class="btn btn-outline-secondary" style="border: none; padding: 0px" id="modifyTransactionField" href='+"/transaction/modifyTransactionField?transactionHistoryId="  + history.id + '&type=' + history.type + '&userId=' + nowLoginUserId + '&transactionDate=' + history.transaction_date + '&index=' + (index+1) + '&sortName=' + encodingSortName + '&memo=' + encodingMemo + '&price=' + history.price + '>';
                    tbodyHtml += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pen" viewBox="0 0 16 16" >';
                    tbodyHtml += '<path d="m13.498.795.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0 1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 .131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001zm-.644.766a.5.5 0 0 0-.707 0L1.95 11.756l-.764 3.057 3.057-.764L14.44 3.854a.5.5 0 0 0 0-.708l-1.585-1.585z"></path>';
                    tbodyHtml += '</svg>';
                    tbodyHtml += '<span class="visually-hidden">Button</span>';
                    tbodyHtml += '</a>';
                    tbodyHtml += '</td>';

                    tbodyHtml += '<td>';
                    // tbodyHtml += '<button type="button" class="btn btn-outline-secondary" style="border: none; padding: 0px" th:onclick="deleteTransactionField([[${history.id}]], [[${history.type}]], [[${nowLoginUserId}]], [[${history.transaction_date}]], [[${index.count}]], [[${history.name}]], [[${history.memo}]], [[${history.price}]])">';
                    tbodyHtml += '<button type="button" class="btn btn-outline-secondary" style="border: none; padding: 0px" onclick="deleteTransactionField(' + history.id + ', \'' + history.type + '\', \'' + nowLoginUserId + '\', \'' + history.transaction_date + '\', \'' + (index+1) + '\', \'' + encodingSortName + '\', \'' + encodingMemo + '\', \'' + history.price  + '\')">'
                    tbodyHtml += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">';
                    tbodyHtml += '<path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"></path>';
                    tbodyHtml += '</svg>';
                    tbodyHtml += '<span class="visually-hidden">Button</span>';
                    tbodyHtml += '</button>';
                    tbodyHtml += '</td>';
                    tbodyHtml += '</td>';

                    tbodyHtml += '</tr>';
                }
            });


            // console.log(new Date(transactionDate.transaction_date).getDay());
            var today = new Date(transactionDate.transaction_date).getDay();

            if ((index + 1) <= changeDateCnt && nowLoginUserId != userId) { //현재 로그인한 회원이 아닌 다른 회원의 거래내역일 경우
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
                theadHtml += '<th scope="col">' + '메모' + '</th>';
                theadHtml += '<th scope="col">' + '거래' + '</th>';
                theadHtml += '</tr>';
                theadHtml += '</thead>';
                theadHtml += '<tbody>' + tbodyHtml;

                theadHtml += '</tbody>';
                theadHtml += '</table>';
            }
            else if((index + 1) <= changeDateCnt && nowLoginUserId == userId){ //자시느이 거래내역일 경우
                theadHtml += '<table class="table table-hover" style="text-align: center; font-size: small">';
                theadHtml += '<thead>';
                theadHtml += '<tr style="background-color: #FDF5E6">';
                theadHtml += '<th colspan="2" style="text-align: left; padding-left: 15px">' + transactionDate.transaction_date + week[today] + '</th>';
                theadHtml += '<th colspan="4" style="text-align: right; padding-right: 15px">' + '합계(수입 :' + '<span style="color: red">' + transactionDate.dayCntIncome + '</span>' + '지출 : ' + '<span style="color: blue">' + transactionDate.dayCntExpend + '</span>' + ')';
                theadHtml += '</th>';
                theadHtml += '</tr>';
                theadHtml += '<tr style="background-color: #FDF5E6">';
                theadHtml += '<th scope="col">' + '번호' + '</th>';
                theadHtml += '<th scope="col">' + '분류명' + '</th>';
                theadHtml += '<th scope="col">' + '메모' + '</th>';
                theadHtml += '<th scope="col">' + '거래' + '</th>';
                theadHtml += '<th scope="col">' + '수정' + '</th>';
                theadHtml += '<th scope="col">' + '삭제' + '</th>';
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
        let sortName = $('#sortName').val();
        // console.log(year, month);
        if (sortName.trim().length === 0) {
            // console.log(sortName.trim().length); //사용자가 분류명을 입력하지 않은 경우
            location.href = '/transaction/showTransaction?userId=' + userId1;
        } else if (sortName.trim().length != 0) { //사용자가 분류명을 입력한 경우
            // console.log(sortName.trim().length);
            location.href = '/transaction/showTransaction?userId=' + userId1 + "&sortName=" + sortName;
        }

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

        if (startDate.trim().length === 0 || endDate.trim().length === 0) {
            swal('알림!', "검색을 위해 시작일과 종료일을 선택해 주세요.", 'warning');
            // console.log("에러1");
        } else if (startDate > endDate) {
            swal('알림!', "종료일이 시작일 보다 빠릅니다.", 'warning');
            // console.log("에러2");
        } else {
            location.href = '/transaction/showTransaction/whichSelect?userId=' + userId1 + '&typeRadio=' + typeRadio + '&sortName=' + sortName + '&startDate=' + startDate + '&endDate=' + endDate;
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

    //해당 url에서 페이지가 리로딩 되어 나타날때 이전에 사용자가 분류명을 입력했다면 해당 분류명을 input 태그에 유지 아닐경우 빈칸으로 유지
    let sort = sortName;
    // console.log(sort);

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


//현재 로그인 되어있는 회원의 거래내역 페이지 에서 특정 필드의 거래내역 삭제 아이콘을 클릭할 경우 해당 필드의 데이터를 함수로 보내주어 삭제를 처리하는 로직
function deleteTransactionField(transactionId, type, nowLoginUserId, transactionDate, count, transactionName, transactionMemo, transactionPrice) {
    // console.log(transactionId);
    // console.log(type);
    // console.log(nowLoginUserId);
    // console.log(transactionDate);
    // console.log(count);
    // console.log(transactionName);
    // console.log(transactionMemo);
    // console.log(transactionPrice);


    var data = {
        "transactionId": transactionId, //거래내역 PK
        "type": type, //지출 or 수입
        "nowLoginUserId": nowLoginUserId, //현재 로그인 되어있는 회원의 아이디
        "transactionDate": transactionDate, //거래 날짜
        "count": count, //거래내역 페이지 에서 사용자가 선택한 필드의 index
        "transactionName": transactionName, //거래내역 분류명
        "sortNamePK": "null", //
        "year": "",
        "month": "",
        "userIdPK": "",
        "transactionMemo": transactionMemo, //거래내역 메모
        "transactionPrice": transactionPrice //거래 가격
    };


    var result = confirm('정말 해당 필드의 거래내역을 삭제 하시겠습니까?');
    if (result) {
        deleteProcess();
    }


    function deleteProcess() {
        $.ajax({
            url: "/transaction/deleteTransactionField", // 클라이언트가 HTTP 요청을 보낼 서버의 URL 주소
            data: data,                // HTTP 요청과 함께 서버로 보낼 데이터
            method: "GET",                           // HTTP 요청 방식(GET, POST)
            dataType: "json"                         // 서버에서 보내줄 데이터의 타입
        })
            // HTTP 요청이 성공하면 요청한 데이터가 done() 메소드로 전달됨.
            .done(function (json) {
                // console.log("성공");
                alert("해당 필드의 거래내역이 정상적으로 삭제 되었습니다.");
                location.reload(); //삭제된 값이 반영되도록 페이지 리로딩
            })
            // HTTP 요청이 실패하면 오류와 상태에 관한 정보가 fail() 메소드로 전달됨.
            .fail(function (xhr, status, errorThrown) {
            })
            // HTTP 요청이 성공하거나 실패하는 것에 상관없이 언제나 always() 메소드가 실행됨.
            .always(function (xhr, status) {
            });
    }


}

