//차트를 보여주는 로직------------------------------------------------------------------------------------------------
var context = document
    .getElementById('myChart')
    .getContext('2d');

var data = result;
var labels = data.map(item => item.name);
var values = data.map(item => parseInt(item.sum)); // parseFloat를 사용하여 소수점 값 처리

// 랜덤 RGB 값 생성 함수(각 영역별로 색상을 다르게 하기 위함)
function getRandomRGB() {
    var r = Math.floor(Math.random() * 256);
    var g = Math.floor(Math.random() * 256);
    var b = Math.floor(Math.random() * 256);
    return `rgba(${r}, ${g}, ${b},`;
}

var backgroundColors = values.map(() => getRandomRGB() + ' 0.2)'); // 랜덤한 색상 + 투명도
// var borderColors = values.map(() => getRandomRGB() + ' 1)'); // 랜덤한 색상 + 투명도
var borderColors = backgroundColors; // 랜덤한 색상 + 투명도

var totalValue = values.reduce((acc, curr) => acc + curr, 0); // 전체 값을 계산

var myChart = new Chart(context, {
    type: 'pie',
    data: {
        labels: labels,
        datasets: [
            {
                label: 'test1',
                fill: false,
                data: values,
                backgroundColor: backgroundColors,
                borderColor: borderColors,
                borderWidth: 1
            }
        ]
    },
    //원래 data에 들어가는 values가 정수가 들어가기 때문에 화면에서 마우스를 가져다 데면 숫자만 나오는데
    //+이 부분을 100단위로 나누어 ,로 구분하도록 하는 옵션
    //+전체를 100%로 했을 경우 각 파트별로 마우스를 가겨다데면 차지하는 비중을 퍼센트로 보여주는 옵션 추가
    //+마우스를 가져다 데면 해당 영역의 이름(label)을 같이 보여주는 옵션
    options: {
        tooltips: {
            titleFontSize: 25,
            bodyFontSize: 15,
            callbacks: {
                title: function (tooltipItem, data) {
                    var dataset = data.datasets[0];
                    var value = dataset.data[tooltipItem[0].index];
                    var percent = ((value / totalValue) * 100).toFixed(2);

                    return data.labels[tooltipItem[0].index];
                },
                label: function (tooltipItem, data) {
                    var dataset = data.datasets[0];
                    var value = dataset.data[tooltipItem.index];
                    var percent = ((value / totalValue) * 100).toFixed(2);
                    return `${value.toLocaleString('ko-KR') + '원'} (${percent}%)`;
                }
            }
        }
    }
});


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



