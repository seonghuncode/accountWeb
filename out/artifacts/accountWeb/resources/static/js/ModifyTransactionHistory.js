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
            // console.log(data);

            var option = '';
            $.each(data, function (index, history) {
                option += `<option value="${index}">` + history.name + '</option>';
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


//----------------------------------------------------------------------------------------------------------------------------------------------------------

doSortListProcess(); //초기 하나의 필드가 동작하기 위해 함수를 실행 시킨다.

//필드 에서 사용자가 날짜를 선택하면 분류명 리스트를 보여주고 경고메세지를 없애주는 로직---------------------------------------------------------------------------
function doSortListProcess() {


    var uniqueNum = $(`#uniqueNum1`).text();
    //예산액 추가 부분에서 필드 번호가 존재할 경우에만 실행 되도록 조건문 추가 (존재하지 않을 경우 동작X)-------------------------------------------------------------

    // console.log(uniqueNum);

    //거래 내역 추가 에서 계속 특정 필드의 날짜가 변경 되는지 감지 한다.-----------------------------------------------------------------
    $(document).ready(function () {
        $(`#selAddTransactionDate1`).change(function () {
            var selDate1 = $(`#selAddTransactionDate1`).val();
            console.log("값 변화");
            console.log(selDate1);
            // console.log(i + " : " + `${i}`)


            //만약 특정 필드의 날짜 값이 변경 되었을 경우 날짜가 선택되었다면 분류명을 보기 위해 날짜를 선택하라는 문구를 없앤다.
            if (selDate1 != "") {
                console.log("날짜 입력 경고 메세지 없애기 " + selDate1);
                // console.log(`sortListValid${i}`)
                $(`#sortListValid1`).text("");
            } else if (selDate1 == "") {
                $(`#sortListValid1`).text("※분류명을 선택하기 위해서는 날짜를 먼저 선택해 주세요.");
                $(`#sortListValid1`).css("color", "red");
            }
        });
    });


    // 사용자가 예산액 추가 에서 날짜를 선택할 경우 분류명 리스트를 불러오는 부분 (사용자가 특정 필드의 분류명 select태그를 선택했을때 동작하는 태그)------------------
    // $(`#sortList${i}`).click(function () {
    var preSelDate = "1"; //이전에 선택한 날짜를 저장할 변수
    $(`#sortList1`).click(function () {


        //예산액을 추가하는 분류명 필드는 추가, 삭제가 가능하기 때문에 정확히 어떤 필드의 날짜인지를 구별하는 것이 중요!!
        var selDate = $(`#selAddTransactionDate1`).val();
        var loginId = userId;
        var year;
        var month;
        //year2, month2의 변수명은 거래내역 추가 기능에서 필요하지 않지만 기존에 만들었던 분류명 불러오기 함수를 사용하기 위해서는 빈값으로 변수명을 넘겨 주어야 사용이 가능 하다.
        var year2 = '';
        var month2 = '';
        // console.log(`sortList${i}`)
        // console.log("선택한 날짜 : " + selDate);


        if (selDate == null) {
            // console.log("undefined입니다!!");
        } else if (selDate != '' && selDate != preSelDate) {  //사용자가 날짜를 입력 했을 경우 에만 실행되도록 하는 조건문
            //selDate != preSelDate를 비교하지 않을 경우 특정 필드 에서 분류명 리스트를 분러오지만 선택할 수 없는 이슈 발생
            //(위의 조건을 추가 하지 않을 경우 분류명 리스트를 선택하면 날짜가 선택되어 있기 때문에 계속해서 리스트를 호출하기 때문에 선택한 값이 계속 초기화 되기 때문에 조건 추가)

            // console.log("날짜가 선택 됨");
            // console.log(selDate);
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
            getSortList(data1, 1);
            preSelDate = selDate;
        } else if (selDate != '' && selDate == preSelDate) {
            //필드 추가를 2개 이상 하고 -> 같은 날짜를 연달아 선택 할경우
            //selDate != '' && selDate != preSelDate 위의 else if에 해당 하지 않게 되어 리스트를 분러오지 못 하는 이슈 발생
            //이때 이전 선택 날짜를 1로 바꾸어 위의 조건문을 만족할 수 있도록 수정해 주기 위해 임의로 1로 바꾸어 준다.
            preSelDate = 1;
        }

    });


}


//==============================위의 코드 까지는 분류명 추가(addTransactionHistory)에서 사용하던 코드를 약간만 변형하여 그대로 사용 하는 코드=======================






//거래내역 페이지 에서 수정 버튼을 클릭했을 경우 유효성 검증을 진해하는 부분--------------------
function checkValid() {
    var selDate = $('#selAddTransactionDate1').val();
    var selSortName = $('#sortList1').find(":selected").text();
    var memo = $('#memo1').val();
    var price = $('#price1').val();
    var type = $('#type1').find(":selected").text();


    // console.log(selDate);
    // console.log(selSortName);
    // console.log(memo);
    // console.log(price);
    // console.log(type);

    var result = "success";

    if (selDate == "") {
        $(`#selAddTransactionValid1`).text("※거래 내역 수정을 위해서 날짜는 필수로 선택 해야 합니다.");
        $(`#selAddTransactionValid1`).css("color", "red");
        $(`#selAddTransactionValid1`).css("font-size", "small");
        result =  "fail";
    }else if (selDate != "") {
        $(`#selAddTransactionValid1`).text("");
    }
    if (selSortName == "") {
        $(`#sortListValid1`).text("※거래 내역 수정을 위해서 분류명은 필수로 선택 해야 합니다.");
        $(`#sortListValid1`).css("color", "red");
        $(`#sortListValid1`).css("font-size", "small");
        result =  "fail";
    }else if (selSortName != "") {
        $(`#sortListValid1`).text("");
    }

    if (price.trim() == "") {
        $(`#priceValid1`).text("※거래 내역 수정을 위해서 금액은 필수로 입력 해야 합니다.");
        $(`#priceValid1`).css("color", "red");
        $(`#priceValid1`).css("font-size", "small");
        result =  "fail";
    }else if(price.trim() > 999999999){
        $(`#priceValid1`).text("※금액은 최대999,999,999원 까지 입력가능 합니다.");
        $(`#priceValid1`).css("color", "red");
        $(`#priceValid1`).css("font-size", "small");
        result =  "fail";
    }else if(!$.isNumeric(price.trim())){
        $(`#priceValid1`).text("※금액은 숫자만 입력 가능 합니다.");
        $(`#priceValid1`).css("color", "red");
        $(`#priceValid1`).css("font-size", "small");
        result =  "fail";
    }else if(price.trim() != "" && price.trim() <= 999999999 && $.isNumeric(price.trim())){
        $(`#priceValid1`).text("");
    }

    if(memo.length >= 10){
        $(`#memoValid1`).text("※메모는 10글자 이내로 입력 가능 합니다.");
        $(`#memoValid1`).css("color", "red");
        $(`#memoValid1`).css("font-size", "small");
        result =  "fail";
    }else if(memo.length <10){
        $(`#memoValid1`).text("");
    }

    return result;

}


//거래내역 페이지 에서 수정 아이콘을 클릭할 경우 수정을 진행하는 로직---------------------------------------------------
$('#doModifyTransactionData').click(function () {

    var result = checkValid();
    var index2 = index;
    var transactionHistoryId2 = transactionHistoryId;
    // console.log("result : " + result)
    // console.log(transactionHistoryId2)

    if(result == "success"){ //유효성 검증에 성공 했다면 거래내역 수정을 진행하도록 controller로 데이터 전송

        let data = {
            "selDate": $('#selAddTransactionDate1').val(),
            "selSortName": $('#sortList1').find(":selected").text(),
            "memo": $('#memo1').val(),
            "price": $('#price1').val(),
            "type": $('#type1').find(":selected").text(),
            "userId" : userId,
            "sortNamePK" : "null",
            "year" :  "",
            "month" : "",
            "userIdPK" : "",
            "index" : index2,
            "transactionHistoryId" : transactionHistoryId2
        };

        // console.log(data);

        $.ajax({
            url: "/transaction/doModifyTransactionField",
            data: data, //data: info, JSON.stringify(info)
            type: "get",
            dataType: "json",   //dataType : "html", "json", "text"
            contentType: "application/json; charset=utf-8",
            success: function (res) {
                // console.log(res);
                if(res.result == "success"){
                    var result = confirm('정말 해당 필드의 거래내역을 수정 하시겠습니까?');
                    if(result){
                        alert("해당 필드의 거래내역이 정상적으로 수정 되었습니다.")
                    }
                }else if(res.result == "fail"){
                    alert("해당 필드의 거래내역을 수정하는데 요청이 실패 되었습니다. 관리자 에게 문의 하세요.")
                }
            },
            error: function () {
                alert("error")
            }
        });

    }

})






