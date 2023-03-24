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
        data: {search: search.search},  //JSON.stringify(search)
        type: "get",
        dataType: "json",
        contentType: "application/json; charset=UTF-8",
        success: function (data) {

            //현재 DB문제로 데이터 불러오는 문제!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //<a href="/usr/showTransaction?userId=" + item.user_id + ">

            //controller로 부터 받아온 검색어에 대한 회원 정보를 화면 테이블을 수정 해주는 코드
            $('#info > tr').empty();  //기존 전체 회원정보를 지원다.(처음 메인화면으로 넘어 왔을때 보여주는 리스트)
            $('#search*').empty(); //검색기능으로 불러왔던 데이터를 지우고 아래서 새로운 검색어에 대한 리스트를 보여준다.
            data.userInfo.forEach(function (item) {
                str = '<tr id="search">'
                str += "<th >" + item.id + "</th>";
                str += "<td>" + item.name + "</td>";
                str += "<td>" + ' <a href="/usr/showTransaction?userId=' + item.user_id + '"> ' + " <i class=\"bi bi-box-arrow-in-right\"></i>" + "</a>" + "</td>";
                str += "</tr>"
                $('#thead').append(str);
            })
          


            // 페이징 관련해서 controller에서 받아온 데이터로 수정 해주는 작업
            //html : 기존에 있던 태그를 지우고 새로 추가 append : 기존의 태그를 남겨 두고 다음에 추가
            $('#pagingFn > ul').empty();  //기존 페이징 버튼들 모두 제거
            //$('#newPaging*').empty();

            if(data.pageMaker.prev){
                $('#pagingFn > ul').append(
                    ' <li class="page-item">' +
                    '<a class="page-link" href="/usr/main?page=' + data.pageMaker.startPage + '-1"  aria-label="Previous"> ' +
                    ' <span aria-hidden="true">&laquo;</span>' +
                    ' </a>' +
                    '  </li>' +
                    '   </div>' )
            }

            for(var pageNum = data.pageMaker.startPage; pageNum <= data.pageMaker.endPage; pageNum++){

                if(data.nowPage == pageNum){
                    $('#pagingFn > ul').append(
                        ' <li class="page-item active" aria-current="page">' +
                        // <!-- 만약 현재 페이지 == 버튼 숫자가 동일하다면 색상 지정 -->
                        '  <a class="page-link" href="/usr/main?page=' + pageNum + '">' +
                        '<i class="fa">' + pageNum + '</i>' + '</a>' +
                        ' </li>'
                    )
                }else{
                    $('#pagingFn > ul').append(
                        '<li class="page-item">' +   //여기까지 수정
                        // <!-- 현재 페이지 == 버튼번호가 같은 경우가 아니라면 색을 채우지 말고 보여줘라 -->
                        ' <a class="page-link" href="/usr/main?page='+ pageNum + '">'+'<i class="fa">'+ pageNum +'</i>' + '</a>' +
                        '  </li>'
                    )

                }

            }


            if(data.pageMaker.next && data.pageMaker.endPage > 0){
                $('#pagingFn > ul').append(
                    ' <li class="page-item">' +
                    '  <a class="page-link" href="/usr/main?page=' + data.pageMaker.endPage+1 +'" aria-label="Next">' +
                        '  <span aria-hidden="true">&raquo;</span>' +
                        '  </a>' +
                        ' </li>'
                )
            }













            console.log(data); //controller로 부터 받아온 데이터 확인
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