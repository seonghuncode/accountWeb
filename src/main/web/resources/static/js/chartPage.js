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
            titleFontSize: 30,
            bodyFontSize: 20,
        callbacks: {
                title: function(tooltipItem, data) {
                    var dataset = data.datasets[0];
                    var value = dataset.data[tooltipItem[0].index];
                    var percent = ((value / totalValue) * 100).toFixed(2);

                    return data.labels[tooltipItem[0].index];
                },
                label: function(tooltipItem, data) {
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
$('#doSearchChart').click(function () {
    var date = $('#selectMonth').val();
    var type = $('input[name=flexRadioDefault]:checked').val();
    var year = date.substr(2,2);
    var month = date.substr(5,2);
    // console.log(userId);
    // console.log(date);
    // console.log(type);
    // console.log(year);
    // console.log(month);

    //회원이 선택한 검색 조건 정보를 통계 페이지로 보내 해당 조건에 대한 통계 정보를 보여주도록 하는 통신
    $.ajax({
        url: "/transaction/getNowSessionValue", // 스프링 컨트롤러에 매핑된 경로
        type: "get",
        dataType: "json",
        contentType: "application/json; charset=UTF-8",
        success: function(data) {
            // console.log("세션값 가지고 오기 성공");
            // console.log(data);
            // console.log(data['sessionValue']);
            var userId = data['sessionValue'];
            location.href="/transaction/moveChartPage?userId=" + userId + "&year=" + year + "&month=" + month + "&type=" + type;
        },
        error: function() {
            console.log("(ajax요청 실패)현재 로그인 되어있는 회원의 세션값을 가지고 오는 요청을 실패 했습니다.");
        }
    });




})


