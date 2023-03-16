package dto;

public class PageMaker {
    //페이징 관련 버튼들을 만드는 기능을 하는 클래스(계산)


    private Criteria cri;
    private int totalCount;  //총 게시글 수
    private int startPage;
    private int endPage;
    private boolean prev;
    private boolean next;
    private int displayPageNum = 5; //화면 하단에 보여지는 페이지 버튼의 수

    public Criteria getCri() {
        return cri;
    }
    public void setCri(Criteria cri) {
        this.cri = cri;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        calcData();   //총 게시글 수를 세팅할때 calData()메서드를 호출 하여 페이징 관련 버튼 계산산
   }

    private void calcData() {  //페이징의 버튼들을 생성하는 계산식(끝 페이지 번호, 시작 페이지 번호, 이전, 다음 버튼을 구한다.)

        //끝 페이지 번호 = (현재 페이지 번호 / 화면에 보여질 페이지 번호의 갯수) * 화면에 보여질 페이지 번호의 갯수
        //math.ceil ==> 소수점 이하를 올림
        //ex. (3 / 10) * 10
        endPage = (int) (Math.ceil(cri.getPage() / (double) displayPageNum) * displayPageNum);

        //시작 페이지 번호 = (끝 페이지 번호 - 화면에 보여질 페이지 번호의 갯수) + 1
        //ex. (40-10)+1 = 21
        startPage = (endPage - displayPageNum) + 1;
        if(startPage <= 0) startPage = 1; //마지막 페이지 번호가 화면에 보여질 페이징 갯수보다 작으면 문제 -> 시작 페이지 번호가 음수가 된다.
        //ex. 끝 페이지(3), 보여줄 페이지 갯수(5) -> 시작페이지 = -1 따라서 시작페이지가 0보다 작으면 시작페이지는 1로 만든다.

        //마지막 페이지 번호 = 총 게시글 수 / 한 페이지당 보여줄 게시글 갯수
        int tempEndPage = (int) (Math.ceil(totalCount / (double) cri.getPerPageNum()));
        if (endPage > tempEndPage) { //끝페이지 번호 보다 작을 경우 마지막 페이지 번호를 끝 페이지 번호로 저장
            endPage = tempEndPage;
        }

        prev = startPage == 1 ? false : true; //이전 버튼 생성 여부 = 시작 페이지 번호 == 1 ? false : true  (이전 버튼 생성 여부, 시작페이지가 1이 아니면 생긴다.)
        next = endPage * cri.getPerPageNum() < totalCount ? true : false; //다음 버튼 생성 여부 = 끝 페이지 번호 * 한 페이지당 보여줄 게시글의 갯수 < 총 개시글의 수?true:false
        //ex. 끝페이지(7) * 페이지당 개시글 수(10) < 총 게시글 수(65) --> 다음 버튼 생성 여부 false
    }

    public int getStartPage() {
        return startPage;
    }
    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }
    public int getEndPage() {
        return endPage;
    }
    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }
    public boolean isPrev() {
        return prev;
    }
    public void setPrev(boolean prev) {
        this.prev = prev;
    }
    public boolean isNext() {
        return next;
    }
    public void setNext(boolean next) {
        this.next = next;
    }
    public int getDisplayPageNum() {
        return displayPageNum;
    }
    public void setDisplayPageNum(int displayPageNum) {
        this.displayPageNum = displayPageNum;
    }

}
