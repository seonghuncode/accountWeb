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

            $('#info > tr').empty();  //기존 전체 회원정보를 지원다.(처음 메인화면으로 넘어 왔을때 보여주는 리스트)
            $('#search*').empty(); //검색기능으로 불러왔던 데이터를 지우고 아래서 새로운 검색어에 대한 리스트를 보여준다.
            data.forEach(function(item){
                str='<tr id="search">'
                str += "<th >"+item.id+"</th>";
                str+="<td>"+item.name+"</td>";
                str+="<td>"+item.view_yn+"</td>";
                str+="</tr>"
                // $('#main_table').append(str);
                // $('#main_table').append(str);
                $('#thead').append(str);
            })

            //controller에서 List로 return했기 때문에 반복문을 통해 값을 하나씩 꺼내야 한다.(console.log확인용)
            // $(data).each(function(){
            //     console.log(this.id + " " + this.name + " " + this.view_yn);
            // });



            // alert('success');

        }, error: function () {
            alert("error");
        }
    })

})