package dto;

public class Criteria {
    //게시글 조회 쿼리에 전달될 파라미터를 담게될 클래스

    private int page;  //현재 페이지 번호
    private int perPageNum; //한 페이지당 보여줄 게시물의 갯수

    public int getPageStart() {  //특정 페이지의 게시글 시작 번호, 게시글 시작 행 번호
        //현재 페이지 게시글 시작 번호 = (현재 페이지 번호 - 1) * 페이지당 보여줄 게시글 갯수
        //(3-1)* 5=10 ==> 3페이지 에서 시작할 게시글 시작 행 번호를 의미
        return (this.page-1)*perPageNum;
    }

    public Criteria() {
        this.page = 1;
        this.perPageNum = 10;
    }

    public int getPage() {  //현재 페이지 번호
        return page;
    }
    public void setPage(int page) {  //페이지가 음수 값이 되지 않아야 한다. 음수가 되면 1페이지를 나타낸다.
        if(page <= 0) {
            this.page = 1;
        } else {
            this.page = page;
        }
    }
    public int getPerPageNum() { //한 페이지당 보여줄 게시글 갯수
        return perPageNum;
    }
    public void setPerPageNum(int pageCount) {  //페이지당 보여줄 게시글 수가 변하지 앟게 설정
        int cnt = this.perPageNum;
        if(pageCount != cnt) {
            this.perPageNum = cnt;
        } else {
            this.perPageNum = pageCount;
        }
    }

}
