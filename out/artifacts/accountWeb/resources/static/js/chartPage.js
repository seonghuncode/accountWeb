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
                    return `${value.toLocaleString('ko-KR')} (${percent}%)`;
                }
            }
        }
    }
});