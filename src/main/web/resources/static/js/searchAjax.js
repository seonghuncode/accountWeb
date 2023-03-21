$("#doSearch").click(function () {

    let search = {
        search: $("#searchText").val(),   //input태그의 사용자가 입력한 값을 담는 변수
    };



    console.log(search);

    //ajax로 데이터를 보낼때 data : 위에서 만들 객체인 search를 넣어주면 controller로 정상적으로 가지 못 함
    //data : {} 객체로 감싸서 search : search.search로 해야 controller로 정상적으로 간다
    //==>후자의 방법으로 해야 controller에서 json형태로 잘 받는다 ????

    $.ajax({
        url: "/usr/doSearch",
        data: {search : search.search},  //JSON.stringify(search)
        type: "get",
        dataType: "json",
        contentType: "application/json; charset=UTF-8",
        success: function (data) {

            //이부분에 usr/doSearch에서 받은 해당 검색어에 대한 데이터들만 테이블에 넣어 주는 작업을 해야 한다.  --> 함수로 빼서 실행
            // for (let i = 0; i < data.length; i++) {
            //     var tag = "<tr>" +
            //         "<td>" + data[i].id + "</td>" +
            //         "<td>" + data[i].name + "</td>" +
            //         "<td>" + <i className="bi bi-box-arrow-in-right"></i> + "</td>" +
            //         "</tr>"
            //
            //     $("#main_table").append(tag);
            // }

            //controller에서 List로 return했기 때문에 반복문을 통해 값을 하나씩 꺼내야 한다.
            $(data).each(function(){
                console.log(this.id + " " + this.name + " " + this.view_yn);
            });

            alert('success');

        }, error: function () {
            alert("error");
        }
    })

})