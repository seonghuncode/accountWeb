//분류명을 가지고 오는 함수
function getSortList(data1, i){
    $.ajax({
        url: "/transaction/getNowSortList",
        data: data1,  //JSON.stringify(search)
        type: "get",
        dataType: "json",
        contentType: "application/json; charset=UTF-8",
        success: function (data) {

            //1. 분류명을 받아오면 기존의 select태그의 분류명 리스트가 존재하던 말던 삭제하고 갱신을 해주어야 한다.
            console.log(data);

            var option = '';
            $.each(data, function (index, history) {
                option += '<option value="1">' + history.name + '</option>';
            });

            //받아온 현재 분류명 리스트를 반영해 주어야 된다.
            $(`#sortList${i} > *`).remove();
            $(`#sortList${i}`).append(
                option
            );

        }, error: function () {
            alert("error");
        }
    })
}

//----------------------------------------------------------------------------------------------------------------------------------------------------------


//예산액 추가는 한번에 최대 10개의 예산액만 추가 가능하도록 한다.---------------------------------------------------------------------------
for (let i = 1; i <= 10; i++) {

    var uniqueNum = $(`#uniqueNum${i}`).text();
    //예산액 추가 부분에서 필드 번호가 존재할 경우에만 실행 되도록 조건문 추가 (존재하지 않을 경우 동작X)-------------------------------------------------------------
    if (uniqueNum == i) {
        //console.log(uniqueNum);

        //거래 내역 추가 에서 계속 특정 필드의 날짜가 변경 되는지 감지 한다.-----------------------------------------------------------------
        $(document).ready(function () {
            $(`#selAddTransactionDate${i}`).change(function () {
                var selDate1 = $(`#selAddTransactionDate${i}`).val();
                //console.log("값 변화");
                //console.log(selDate1);

                //만약 특정 필드의 날짜 값이 변경 되었을 경우 날짜가 선택되었다면 분류명을 보기 위해 날짜를 선택하라는 문구를 없앤다.
                if (selDate1 != null) {
                    $(`#sortListValid${i}`).text("");
                } else {
                    $(`#sortListValid${i}`).text("※분류명을 선택하기 위해서는 날짜를 먼저 선택해 주세요.");
                    $(`#sortListValid${i}`).css("color", "red");
                }
            });
        });


        // 사용자가 예산액 추가 에서 날짜를 선택할 경우 분류명 리스트를 불러오는 부분 (사용자가 특정 필드의 분류명 select태그를 선택했을때 동작하는 태그)------------------
        $(`#sortList${i}`).click(function () {

            //예산액을 추가하는 분류명 필드는 추가, 삭제가 가능하기 때문에 정확히 어떤 필드의 날짜인지를 구별하는 것이 중요!!
            var selDate = $(`#selAddTransactionDate${i}`).val();
            var loginId = userId;
            var year;
            var month;
            //year2, month2의 변수명은 거래내역 추가 기능에서 필요하지 않지만 기존에 만들었던 분류명 불러오기 함수를 사용하기 위해서는 빈값으로 변수명을 넘겨 주어야 사용이 가능 하다.
            var year2 = '';
            var month2 = '';
            //console.log(selDate);

            if (selDate != '') {  //사용자가 날짜를 입력 했을 경우 에만 실행되도록 하는 조건문
                //console.log("날짜가 선택 됨");
                //console.log(selDate);
                year = selDate.substr(2, 2);
                month = selDate.substr(5, 2);
                let data1 = { // 현재 분류명을 불러 오기 위해 필요한 데이터
                    "loginId": loginId,
                    "year": year,
                    "month": month,
                    "year2" : year2,
                    "month2" : month2
                };
                console.log(data1);
                 getSortList(data1, i);
            }




        });


    }
}