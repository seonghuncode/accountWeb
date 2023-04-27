$("#show_more").click(function () {


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
        $.each(getDailyTotalData, function(index, transactionDate) {

            //바디 태그 부분만 담당 해주는 코드
            var tbodyHtml = '';
            $.each(transactionHistory, function(index, history) {
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

            if((index+1) <= changeDateCnt){
                console.log("만들어지는 테이블 갯수")
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
            console.log(res);

            let changeDate = res['changeDateCnt'];
            showMore(changeDate);
            console.log(changeDate);

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