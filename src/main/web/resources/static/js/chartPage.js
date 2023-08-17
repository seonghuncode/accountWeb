//차트를 보여주는 로직------------------------------------------------------------------------------------------------
if (totalPrice != null) { //값이 있을 경우 에만 실행, 값이 없을 경우 실행 되지X (구글 차트 사용)

    
// Load the Visualization API and the corechart package.
    google.charts.load('current', {'packages': ['corechart']});

// Set a callback to run when the Google Visualization API is loaded.
    google.charts.setOnLoadCallback(drawChart);

// Callback that creates and populates a data table,
// instantiates the pie chart, passes in the data and
// draws it.
    function drawChart() {

        var data1 = result;
        var labels2 = data1.map(item => item.name);
        var values3 = data1.map(item => parseInt(item.sum)); // parseFloat를 사용하여 소수점 값 처리
        // console.log(data1);
        // console.log(labels2);
        // console.log(values3);

        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Topping');
        data.addColumn('number', 'Slices');
        data1.forEach(function(item) {
            var formattedValue = item.sum.toLocaleString('en-US'); // 3자리마다 쉼표를 추가하여 서식화
            // data.addRows([[`${item.name} (${formattedValue})`, item.sum]]);
            data.addRows([[item.name , item.sum]]);
        });

        // Set chart options
        var options = {
            'title': '',
            'width': 400,
            'height': 300,
            // pieSliceText: 'value-and-percentage' // 이름과 퍼센트 모두 표시
        };

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, options);

        var options2 = {
            title: '지출관리웹',
            vAxis: {
                title: '분류명',
                titleTextStyle: {
                    fontSize: 15, // 제목 텍스트 크기 조절
                    bold: true, // 제목 텍스트 굵게 설정
                    italic: false, // 기울임 없이 설정
                    rotation: 0 // 글자 회전 각도 설정 (0도: 가로, 90도: 세로)
                }
            },
            hAxis: {
                title: '금액',
                titleTextStyle: {
                    fontSize: 15,
                    bold: true,
                    italic: false,
                    rotation: 0
                }
            },
            legend: { position: 'none' }, // 범례를 표시하지 않음
        };

        var barChart = new google.visualization.BarChart(document.getElementById('barChart_div'));
        barChart.draw(data, options2);
    }




} else {
// //값이 없을때의 차트 디자인-------------------------------------------------------------------------------------------
    
// Load the Visualization API and the corechart package.
    google.charts.load('current', {'packages': ['corechart']});

// Set a callback to run when the Google Visualization API is loaded.
    google.charts.setOnLoadCallback(drawChart);

// Callback that creates and populates a data table,
// instantiates the pie chart, passes in the data and
// draws it.
    function drawChart() {


        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Topping');
        data.addColumn('number', 'Slices');
        data.addRows([
            [`X`, 0.0]
        ])


        // Set chart options
        var options = {
            'title': '거래 내역이 없어 차트를 그릴 수 없습니다.',
            'width': 400,
            'height': 300
        };

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('chart_div2'));
        chart.draw(data, options);
    }

    
}


//검색 버튼을 클릭 했을 경우 사용자가 선택한 조건을 가지고 통계 페이지로 재요청을 하는 로직
$(document).on('click', '#doSearchChart', function () {

    var date = $('#selectMonth').val();
    var type = $('input[name=flexRadioDefault]:checked').val();
    var year = date.substr(2, 2);
    var month = date.substr(5, 2);
    // console.log(userId);
    // console.log(date);
    // console.log(type);
    // console.log(year);
    // console.log(month);

    if (date == "") {
        $(`#searchValid`).text("※특정월을 검색을 위해서는 날짜를 선택해야 합니다.");
        $(`#searchValid`).css("color", "red");
        $(`#searchValid`).css("font-size", "small");
    } else {
        $(`#searchValid`).text("");
        doSuccess();
    }

    //회원이 선택한 검색 조건 정보를 통계 페이지로 보내 해당 조건에 대한 통계 정보를 보여주도록 하는 통신
    function doSuccess() {

        $.ajax({
            url: "/transaction/getNowSessionValue", // 스프링 컨트롤러에 매핑된 경로
            type: "get",
            dataType: "json",
            contentType: "application/json; charset=UTF-8",
            success: function (data) {
                // console.log("세션값 가지고 오기 성공");
                // console.log(data);
                // console.log(data['sessionValue']);
                var userId = data['sessionValue'];
                location.href = "/transaction/moveChartPage?userId=" + userId + "&year=" + year + "&month=" + month + "&type=" + type;
            },
            error: function () {
                console.log("(ajax요청 실패)현재 로그인 되어있는 회원의 세션값을 가지고 오는 요청을 실패 했습니다.");
            }
        });

    }


})


//사용자가 기간 검색 radio버튼을 클릭했을 경우 디자인 변경----------------------------------------------------------------------------
$('#searchPeriod').click(function () {

    $('#searchPart > *').remove();

    $('#searchPart').append(
        '<span style="margin-right: 7px;">특정 기간 검색</span>' +
        '<input type="month" id="startDate" style="margin-right: 10px;">' +
        `<span style="margin-left: 3px; margin-right: 3px;">~</span> ` +
        `<input type="month" id="endDate" style="margin-right: 10px;">` +
        `<div class="form-check" style="display: inline-flex; margin-right: 10px;">` +
        `<input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1" value="지출" checked>` +
        `<label class="form-check-label" for="flexRadioDefault1">지출 통계</label>` +
        `</div>` +
        `<div class="form-check" style="display: inline-flex">` +
        `<input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" value="수입">` +
        `<label class="form-check-label" for="flexRadioDefault2">수입 통계</label>` +
        `</div>` +
        `<div style="display: inline">` +
        `<button type="button" class="btn btn-outline-secondary" style="float: right; margin-right: 7px;" id="doSearchChartByPeriod">검색` +
        `</button>` +
        `</div>` +
        `<hr class="frame" style="margin-top: 20px; display: flex; width: 100%">`
    );


})


//사용자가 기간 검색을 선택하고 검색 버튼을 클릭할 경우 실행 되는 로직
// 현재 클릭하는 것이 동적으로 추가되는 요소를 클릭한 경우 이기 때문에
//초기 html에 없는 요쇼에 대해 이벤트 핸들러를 바인딩 하기 때문에 사용 불가
//==>동적으로 추가되는 요소에 대한 이벤트 핸들러를 바인딩 하려면 아래와 같은 코드 사용
$(document).on('click', '#doSearchChartByPeriod', function () {

    var startDate = $('#startDate').val();
    var endDate = $('#endDate').val();
    var type = $('input[name=flexRadioDefault]:checked').val();
    var startYear = startDate.substr(2, 2);
    var startMonth = startDate.substr(5, 2);
    var endYear = endDate.substr(2, 2);
    var endMonth = endDate.substr(5, 2);

    // console.log("startDate : " + startDate);
    // console.log("endDate : " + endDate);
    // console.log("type : " + type);
    // console.log("startYear : " + startYear);
    // console.log("startMonth : " + startMonth);
    // console.log("endYear : " + endYear);
    // console.log("endtMonth : " + endMonth);

    //검색 유효성 검사 로직-------------------------------------------------------------------
    if (startDate == "") {
        $(`#searchValid`).text("※기간 검색을 위해서는 시작 날짜를 선택해야 합니다.");
        $(`#searchValid`).css("color", "red");
        $(`#searchValid`).css("font-size", "small");
    } else if (endDate == "") {
        $(`#searchValid`).text("※기간 검색을 위해서는 마지막 날짜를 선택해야 합니다.");
        $(`#searchValid`).css("color", "red");
        $(`#searchValid`).css("font-size", "small");
    } else if (startYear > endYear) {
        $(`#searchValid`).text("※기간 검색을 위해서는 마지막 날짜가 더 커야 합니다.");
        $(`#searchValid`).css("color", "red");
        $(`#searchValid`).css("font-size", "small");
    } else if (startYear == endYear && startMonth >= endMonth) {
        $(`#searchValid`).text("※기간 검색을 위해서는 마지막 날짜가 더 커야 합니다.");
        $(`#searchValid`).css("color", "red");
        $(`#searchValid`).css("font-size", "small");
    } else {
        $(`#searchValid`).text("");
        doSuccess()
    }


    //사용자가 유효성에 맞게 검색 조건을 선택해서 검색 버튼을 클릭한다면 해당 기간의 데이터를 보여주는 통신
    function doSuccess() {
        $.ajax({
            url: "/transaction/getNowSessionValue", // 스프링 컨트롤러에 매핑된 경로
            type: "get",
            dataType: "json",
            contentType: "application/json; charset=UTF-8",
            success: function (data) {
                // console.log("세션값 가지고 오기 성공");
                // console.log(data);
                // console.log(data['sessionValue']);
                var userId = data['sessionValue'];
                location.href = "/transaction/moveChartPageByPeriod?userId=" + userId + "&startYear=" + startYear + "&startMonth=" + startMonth + "&endYear=" + endYear + "&endMonth=" + endMonth + "&type=" + type + "&searchMethod=기간검색" + "&startDate=" + startDate + "&endDate=" + endDate;
            },
            error: function () {
                console.log("(ajax요청 실패)현재 로그인 되어있는 회원의 세션값을 가지고 오는 요청을 실패 했습니다.");
            }
        });
    }


})


//사용자가 통계 페이지 에서 특정 월 검색 라디오 버튼을 클릭하면 특정월을 검색하도록 검색 조건 ui를 변경한다.
$('#searchMonth').click(function () {

    $('#searchPart > *').remove();

    $('#searchPart').append(
        `<span style="margin-right: 7px;">검색</span>` +
        ` <input type="month" id="selectMonth" style="margin-right: 10px;">` +
        `<div class="form-check" style="display: inline-flex; margin-right: 10px;">` +
        `<input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1" value="지출" checked>` +
        `<label class="form-check-label" for="flexRadioDefault1">지출 통계</label>` +
        `</div>` +
        `<div class="form-check" style="display: inline-flex">` +
        `<input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" value="수입">` +
        `<label class="form-check-label" for="flexRadioDefault2">수입 통계</label>` +
        `</div>` +
        `<div style="display: inline">` +
        `<button type="button" class="btn btn-outline-secondary" style="float: right; margin-right: 7px;" id="doSearchChart">검색</button>` +
        `</div>` +
        `<hr class="frame" style="margin-top: 20px; display: flex; width: 100%">`
    );


})






//100단위씩 ","로 숫자를 구분해 주는 함수
function addComma(value){
    return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}



//통계 페이지 에서 분류명을 기준으로 테이블 통계를 보여줄때 해당 버튼을 클릭하면 오름 분류명을 금액 기준으로 오름차순으로 표현 해주는 기능
$('#sortDesc').click(function () {
    // console.log("result : " + result);
    // console.log("userId : " + userId);
    // console.log("year : " + year);
    // console.log("month : " + month);
    // console.log("type : " + type);

    //오름 차순 정렬 진행
    result.sort(function(a, b) {
        return a.sum - b.sum;
    });
    // console.log("정렬후");
    // result.forEach (function (key, index) {
    //     console.log('elements', index, key);
    //     console.log(key.name);
    //     console.log(key.sum);
    //     console.log(key.type);
    // });

    var list = '';
    $.each(result, function (index, history) {
        list += `<tr id = "newList">`
        list += `<th scope="row">` + history.name + `</th>`
        list += `<td>` + addComma(history.sum) + `원</td>`
        list += `</tr>`
    });
    

    $(`tbody > #sortList`).remove();
    $(`tbody > #newList`).remove(); //기존에 정렬한 것을 지워주고 다시 만든다
    $('tbody').prepend(list);


})




//통계 페이지 에서 분류명을 기준으로 테이블 통계를 보여줄때 해당 버튼을 클릭하면  분류명을 금액 기준으로 내림차순으로 표현 해주는 기능
$('#sortAsc').click(function () {

    //내림 차순 정렬 진행
    result.sort(function(a, b) {
        return b.sum - a.sum;
    });

    var list = '';
    $.each(result, function (index, history) {
        list += `<tr id = "newList">`
        list += `<th scope="row">` + history.name + `</th>`
        list += `<td>` + addComma(history.sum) + `원</td>`
        list += `</tr>`
    });

    $(`tbody > #sortList`).remove();
    $(`tbody > #newList`).remove(); //기존에 정렬한 것을 지워주고 다시 만든다
    $('tbody').prepend(list);
    
})



//통계 페이지 에서 사용자가 선택하는 차트 종류에 따라 파이차트 또는 막대 차트로 보여준다.
$('#pieChart').click(function () {
    $('#barChart_div').hide(); // 태그 숨기기
    $('#chart_div').show(); // 태그 보이게 하기
})

$('#barChart').click(function () {
    $('#chart_div').hide(); // 태그 숨기기
    $('#barChart_div').show(); // 태그 보이게 하기
})
