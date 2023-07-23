//분류명을 가지고 오는 함수---------------------------------------------------------------------------------------------
function getSortList(data1, i) {
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


//분류명 관리를 진행하는 함수(추가/수정/삭제)----------------------------------------------------------------------------
$('#manageSortName').click(function () {

    // var _width = '650';
    // var _height = '380';
    var _width = '850';
    var _height = '480';

    // 팝업을 가운데 위치시키기 위해 아래와 같이 값 구하기
    var _left = Math.ceil((window.screen.width - _width) / 2);
    var _top = Math.ceil((window.screen.height - _height) / 2);

    //거래 내역 페이지 에서 받아오는 데이터
    var loginId = userId; //현재 로그인한 사용자 아이디
    var pageYear; //이번달에서 사용자가 검색한 연도(당연도)
    var pageMonth; //이번달 에서 사용자가 검색한 월(당월)

    //현재 필요 없지만 기존의 분류명 관리 함수를 사용하기 위해서 빈 변수라도 넘겨주어야 한다.
    var year1;
    var month1;
    //만약 기간별 검색일 경우 종료 연도, 월을 담아주는 변수
    var year2;
    var month2;

    //사용자가 원하는 분류명 관리하고 싶은 연도와 월을 선택과 동시에 유효성 검사를 하는 로직
    var selDate = prompt("관리할 분류명의 연도와 월을 입력하세요(Ex. 2023-06)");
    if (selDate == null) {
    } else if (selDate.trim() == "") {
        alert("관리하고 싶은 분류명 날짜를 입력 해주세요.")
    } else if (!$.isNumeric(selDate.substr(0, 4)) || !$.isNumeric(selDate.substr(5, 2)) || selDate.substr(4, 1) != "-" || selDate.trim().length > 7) {
        // console.log(selDate.substr(0, 4));
        // console.log(selDate.substr(5, 2))
        // console.log(selDate.substr(4, 1))
        alert("잘못된 형식 입니다.")
    } else {
        pageYear = selDate.substr(2, 2);
        pageMonth = selDate.substr(5, 2);
        alert(pageYear + "년 " + pageMonth + "월에 대한 분류명 관리 페이지를 생성합니다.");

        //사용자가 거래내역 페이지 에서 어떠한 검색 조건으로 검색을 하느냐에 따라 다른 값을 받기 때문에 선별
        if (pageYear && pageMonth) {
            year1 = pageYear;
            month1 = pageMonth;
        }
        window.open('/transaction/sortManage?loginId=' + loginId + '&year=' + year1 + '&month=' + month1 + '&year2=' + year2 + '&month2=' + month2, 'popup-test', 'width=' + _width + ', height=' + _height + ', left=' + _left + ', top=' + _top);
    }
})

//내역 필드 열을 추가 해주는 함수---------------------------------------------------------------------------------------
$('#addTransactionField').click(function () {

    var option = '';
    let num;

    for (let i = 1; i <= 10; i++) {
        var uniqueNum = $(`#uniqueNum${i}`).text();
        //예산액 추가 부분에 존재하는 필드를 카운트 하여 숫자를 지정해 주기 위한 반복문
        if (uniqueNum == i) {
            num = i;
        }
    }

    //존재하는 필드 갯수가 10개가 되면 추가가 불가능 하도록 유효성 검사
    if(num <= 9){
        option += `<tr id="uniqueTr${num+1}">` +
            `<th scope="row"><input class="form-check-input" type="checkbox" value="" id='checkBox${num+1}'></th>` +
            `<td id="uniqueNum${num+1}">${num+1}</td>` +
            ` <td><input type="date" id="selAddTransactionDate${num+1}"></td>` +
            '<td>' +
            `<select class="form-select form-select-sm" aria-label=".form-select-sm example" id="sortList${num+1}"><option selected style="text-align: center">---선택---</option></select>` +
            `<span style="color: red; font-size: small" id="sortListValid${num+1}">※분류명을 선택하기 위해서는 날짜를 먼저 선택해 주세요.</span>` +
            '</td>' +
            '<td>' +
            '<input class="form-control form-control-sm" type="text" placeholder="메모입력(10글자 이내)" aria-label=".form-control-sm example">' +
            '</td>' +
            '<td>' +
            '<input class="form-control form-control-sm" type="text" placeholder="최대 10억 미만 으로 입력 가능(숫자만 입력 가능)" style="width: 350px" aria-label=".form-control-sm example">' +
            '</td>' +
            '<td>' +
            '<select class="form-select form-select-sm" aria-label=".form-select-sm example">' +
            '<option selected>수입</option>' +
            '<option value="1">지출</option>' +
            ' </select>' +
            ' </td>' +
            '</tr>';


        // //받아온 현재 분류명 리스트를 반영해 주어야 된다.
        // $(`#sortList${i} > *`).remove();
        $(`#uniqueTr${num}`).after(
            option
        );

        doSortListProcess(); //필드가 추가 될때마다 해당 함수를 실행시켜야 추가된 필드도 해당 기능이 적용 된다.
    }else if(num >= 10){
        alert("거래 내역 추가는 한번에 최대 10개 까지의 내역만 추가 가능 합니다.");
    }

   

})


//----------------------------------------------------------------------------------------------------------------------------------------------------------

doSortListProcess(); //초기 하나의 필드가 동작하기 위해 함수를 실행 시킨다.

//필드 에서 사용자가 날짜를 선택하면 분류명 리스트를 보여주고 경고메세지를 없애주는 로직---------------------------------------------------------------------------
function doSortListProcess() {


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
                        "year2": year2,
                        "month2": month2
                    };
                    //console.log(data1);
                    getSortList(data1, i);
                }


            });


        }
    }
}