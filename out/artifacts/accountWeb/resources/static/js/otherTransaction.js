$("#show_more").click(function (){



    // console.log(dateCnt);
    function showMore(changeDateCnt){

        $('#topTable > table').empty();

        $('#topTable > table').append(
            ' <table class="table table-hover" style="text-align: center; font-size: small"\n' +
            '               th:each="transactionDate, index : ${getDailyTotalData}">'+
            '<show-cnt id="more7days" th:if="${index.count}<=changeDateCnt">' +
            '<thead>' +
            ' <tr style="background-color: #FDF5E6">' +
            ' <th colspan="2" style="text-align: left; padding-left: 15px">' +
            '[[${transactionDate.transaction_date}]]' +
            '([[${#calendars.dayOfWeekNameShort(transactionDate.transaction_date)}]])' +
            '</th>' +
            '<th colspan="2" style="text-align: right; padding-right: 15px">합계(수입 :' +
            '<span style="color: red">[[${transactionDate.dayCntIncome}]]' +
            '</span> 지출 :' +
            ' <span style="color: blue">[[${transactionDate.dayCntExpend}]]' +
            '</span> )' +
            '</th>' +
            '</tr>' +
            ' <tr style="background-color: #FDF5E6">' +
            ' <th scope="col">번호' +
            '</th>' +
            ' <th scope="col">분류명' +
            '</th>' +
            '<th scope="col">매모' +
            '</th>' +
            ' <th scope="col">거래' +
            '</th>' +
            ' </tr>' +
            '</thead>' +
            ' <tbody>' +
            ' <tr th:each="history, index : ${transactionHistory}">' +
            '<div th:if="${#strings.equals(history.transaction_date, transactionDate.transaction_date)}">' +
            '<th scope="row">[[${index.count}]]' +
            '</th>' +
            ' <td>[[${history.name}]]' +
            '</td>' +
            '<td>[[${history.memo}]]' +
            '</td>' +
            '<td th:text="${#numbers.formatInteger(history.price, 0, \'COMMA\')}">' +
            '</td>' +
            '</div>' +
            ' </tr>' +
            '</tbody>' +
            ' </show-cnt>' +
            '</table>')

        // var thymeleafStr =
        //     '<table class="table table-hover" style="text-align: center; font-size: small"\n' +
        //     '               th:each="transactionDate, index : ${getDailyTotalData}">' +
        //     '<show-cnt id="more7days" th:if="${index.count} &lt;= ${dateCnt}">' +
        //     '<thead>' +
        //     ' <tr style="background-color: #FDF5E6">' +
        //     ' <th colspan="2" style="text-align: left; padding-left: 15px">' +
        //     '[[${transactionDate.transaction_date}]]' +
        //     '([[${#calendars.dayOfWeekNameShort(transactionDate.transaction_date)}]])' +
        //     '</th>' +
        //     '<th colspan="2" style="text-align: right; padding-right: 15px">합계(수입 :' +
        //     '<span style="color: red">[[${transactionDate.dayCntIncome}]]' +
        //     '</span> 지출 :' +
        //     ' <span style="color: blue">[[${transactionDate.dayCntExpend}]]' +
        //     '</span> )' +
        //     '</th>' +
        //     '</tr>' +
        //     ' <tr style="background-color: #FDF5E6">' +
        //     ' <th scope="col">번호' +
        //     '</th>' +
        //     ' <th scope="col">분류명' +
        //     '</th>' +
        //     '<th scope="col">매모' +
        //     '</th>' +
        //     ' <th scope="col">거래' +
        //     '</th>' +
        //     ' </tr>' +
        //     '</thead>' +
        //     ' <tbody>' +
        //     ' <tr th:each="history, index : ${transactionHistory}">' +
        //     '<div th:if="${#strings.equals(history.transaction_date, transactionDate.transaction_date)}">' +
        //     '<th scope="row">[[${index.count}]]' +
        //     '</th>' +
        //     ' <td>[[${history.name}]]' +
        //     '</td>' +
        //     '<td>[[${history.memo}]]' +
        //     '</td>' +
        //     '<td th:text="${#numbers.formatInteger(history.price, 0, \'COMMA\')}">' +
        //     '</td>' +
        //     '</div>' +
        //     ' </tr>' +
        //     '</tbody>' +
        //     ' </show-cnt>'+
        //     '</table>';
        //
        // $('#topTable > table').append(thymeleafStr);

    }


    $.ajax({
        url: "/transaction/showTransaction/dateCnt",
        data: {"dateCnt" : dateCnt}, //data: info, JSON.stringify(info)
        type: "get",
        dataType: "json",   //dataType : "html", "json", "text"
        contentType: "application/json; charset=utf-8",
        success: function (res) {
            console.log(res);

            let changeDate = res['changeDateCnt'];
            showMore(changeDate);
            console.log(changeDate);

            if (res['lastPage'] === 1) {
                swal('※알림',"마지막 지출 내역 입니다.",'info')
                    .then(function(){
                        // location.href = '/usr/loginForm';
                    })
            }

        },
        error: function () {
            alert("error")
        }
    });


});