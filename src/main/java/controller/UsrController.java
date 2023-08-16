package controller;

import dto.Criteria;
import dto.PageMaker;
import dto.UsrDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.TransactionService;
import service.UsrService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j //log사용(로그를 남기는 것)
@Controller
public class UsrController {

    @Autowired
    private UsrService usrService;

    @Autowired
    private TransactionService transactionService;


    //메인 으로 들어오면 로그인 화면으로 보내도록 지정
    @RequestMapping(value = "/")
    public String Main() {
        return "redirect:/usr/loginForm";
    }


    //로그인 폼 화면으로 이동하는 경로
    @RequestMapping(value = "/usr/loginForm")
    public String showLogin() {
        return "thymeleaf/usr/login";
    }

    //로그인 폼에서 사용자가 입력한 value값에 대해 성공, 실패 로직을 처리하는 controller
    @RequestMapping(value = "/usr/loginFn", produces = "application/json; charset=utf8", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> doLogin(@RequestBody UsrDto usrDto, BindingResult bindingResult, HttpSession httpSession) {
        //로그인 에서 @Validated를 사용하지 않는 이유는 회원가입에 맞게 dto에 메세지를 설정해주었기 때문애 serviceImpl에서 예외처리를 만들어 사용한다.
//        System.out.println("로그인 화면에서 넘겨 받은 값");
//        System.out.println("아이디 : " + usrDto.getUserId()+ "비밀번호" + usrDto.getPassword());
//
//        System.out.println("==============");
//        System.out.println("로그인 로직 return값");
//        System.out.println(usrService.doCheckLogin(usrDto, bindingResult));
//        System.out.println("==============");



        return usrService.doCheckLogin(usrDto, bindingResult, httpSession);


//        return usrService.doCheckLogin(usrDto, bindingResult, httpSession);
    }


    //회원가입 폼으로 화면으로 이동 시키는 controller
    @RequestMapping("/usr/joinForm")
    public String showJoin() {
        return "thymeleaf/usr/join";
    }

    //회원가입 폼에서 받은 value값들에 대해 성공, 실패를 처리하는 로직의 controller
    @RequestMapping(value = "/usr/joinFn", method = {RequestMethod.POST}, produces = "application/json; charset=utf8")
    @ResponseBody
    public Map<String, Object> doJoin(@Validated @RequestBody UsrDto usrDto, BindingResult bindingResult
    ) throws Exception {

        //회원가입 폼에서 받아온 value값들을 확인용
        System.out.println("html에서 받아온 데이터");
        System.out.println(
                "유저 이름 : " + usrDto.getName() +
                        "유저 이메일 : " + usrDto.getEmail() +
                        "유저 아이디 : " + usrDto.getUserId() +
                        "비밀번호 : " + usrDto.getPassword() +
                        "비밀번호  확인용" + usrDto.getCheckPassword() +
                        "view_yn : " + usrDto.getView_yn()
        );

        //중복확인 버튼을 클릭할 경우 email만 받아오면 되기 때문에 이메일 중복확인 아라는 것을 알려주기 위해 사용하지 않는 userId에 중복확인을 넣어 보내준다.
        //ajax에서 데이터가 넘어 왔을대 해당 데이터가 어떤 버튼을 클릭하고 넘어온 데이터인지 구분하여 기능 수행
        if (usrDto.getUserId().equals("중복확인")) { //이메일 중복확인 버튼을 클릭했을때 실행될 로직
            String existEmail = usrService.getCheckExistEmail(usrDto.getEmail());
            return usrService.doCheckEmail(usrDto, bindingResult, existEmail);
        } else if (usrDto.getEmail().equals("중복확인")) {  //아이디 중복확인 버튼을 클릭했을 경우 실행될 로직
            return usrService.doCheckUserId(usrDto, bindingResult);
        } else {//가입 하기 버튼을 클릭했을때 실행될 로직(비밀번호:일치여부, 특수문자 조합 및 길이 확인, 이메일 중복, 아이디 중복, 나머지 DTO에서 @Valid설정한 유효성 검사) / 성공 상황
            return usrService.doCheckJoin(usrDto, bindingResult);
        }
    }


    //내비게이션 바에서 -> 로그아웃 버튼 클릭 -> 유효성 검사 -> 결과 리턴 -> 프론트단에서 처리
    @RequestMapping(value = "/usr/doLogout", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> doLogout(HttpSession httpSession) {  //현재 return값은 없지만 추후 이미 로그아웃 되어 있는 상태등 오류 메세지를 return하도록 수정 필요

//        boolean isLoginedId = false;
//        if(httpSession.getAttribute("loginedUserId") == null){
//            isLoginedId = true;
//        }
//        //로그아웃이 되어 있다면 실질적으로 로그아웃 버튼을 클릭할 상황은 없지만 일단 만들어 놓음
//        if(isLoginedId){
//            System.out.println(httpSession.getAttribute("loginedUserId"));
//            System.out.println("이미 로그아웃 되어 있습니다.");
//        }else{
//            System.out.println(httpSession.getAttribute("loginedUserId"));
//            httpSession.removeAttribute("loginedUserId");
//            System.out.println("로그아웃 되었습니다.");
//        }
        
        //로그아웃을 실행하면 저장된 Spring Security권한이 삭제 된다
        SecurityContextHolder.clearContext();

        return usrService.doLogout(httpSession);

    }


    //로그인 폼에서 로그인을 성공하면 해당 회원 정보를 메인화면으로 넘겨 메인화면으로 요청하는 로직
    //현재 로그인 되어있는 세션의 아이디로 회원정보를 찾을 수 있기 때문에 따로 넘겨줄 매개변수는 없다.
    @RequestMapping("/usr/main")
    public ModelAndView doMain(Model model, Criteria cri, HttpSession httpSession) throws Exception {
        //현재 전체 회원을 보내주는 것이 아니라 페이징 기능을 사용하기 때문에 페이지에 해당하는 화원만 프론트로 보내주면 되기 때문에 전체 회원을 불러오는 기능은 사용X
//        List<UsrDto> users = usrService.getAllUserFromDB();
//        model.addAttribute("users", users); //메인화면 애서 회원 이름, 지출내역 공개 여부에 대한 정보를 보여주어야 하기대문에 객체를 넘겨준다.
//        return "thymeleaf/content/main";

        ModelAndView mav = new ModelAndView("thymeleaf/content/main");

        PageMaker pageMaker = new PageMaker();  //페이징 기능을 위해 선언(페이지 번호 관련)
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(usrService.countUsrListTotal());  //DB에 있는 총 회원수를 넣어 준다.

        List<Map<String, Object>> list = usrService.selectBoardList(cri);  //전체 회원 정보를 DB에서 불러온다.
        mav.addObject("list", list);
        mav.addObject("pageMaker", pageMaker);
        //현재 페이지 버튼의 색상을 다르게 하기 위해 현재 페이지를 클라이언트로 넘겨준다
        int nowPage = cri.getPage();
        mav.addObject("nowPage", nowPage);

//        System.out.println("list입니다.");
//        System.out.println(list);

        //회원수에 대한 정보를 넘기기 위한 부분(화면 하단 전체회원,공개회원,비공개 회원을 보여주기 위한 용도)
        int allUser = usrService.getAllUserCnt();
        mav.addObject("allUser", allUser);  // DB에 존재하는 전체 회원의 수
        int userYesCnt = usrService.countUsrListTotal();
        mav.addObject("userYesCnt", userYesCnt); //view_yn=yes인 회원의 수
        int userNoCnt = usrService.getNoUserCnt();
        mav.addObject("userNoCnt", userNoCnt);

        //메인 페이지 에서 네비게이션 바 우측 상단에 로그인 되어있는 회원의 아이디를 보여주기 위해 사용
        String userId = (String) httpSession.getAttribute("loginedUserId");
        model.addAttribute("userId", userId);

//        System.out.println(mav);
        return mav;
    }


    //메인 페이지 에서 검색 기능을 수행하는 controller -> 비동기 통신 필요 -> ajax처리
    //동작 과정 : 클라이언트 검색어 입력 -> 버튼 클릭 -> js ajax를 통해 controller로 검색어 전달 -> controller에서 해당 검색어에 대한 정보만 return -> ajax에서 success or error처리
    @GetMapping("/usr/doSearch")
    @ResponseBody
    public Map doSearch(@RequestParam(value = "search") String search, Criteria cri) throws Exception {
        //System.out.println(search);  //클라이언트에서 받는 것 까지는 성공 > 다음으로 json형식으로 return해주면 된다.

        PageMaker pageMaker = new PageMaker();  //페이징 기능을 위해 선언(페이지 번호 관련)
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(usrService.countSearchUsrListTotal(search));  //검색어에 해당하는 DB회원수 메서드 만들어서 수정
        //System.out.println(usrService.countSearchUsrListTotal(search));
        int nowPage = cri.getPage();

        //사용자가 입력한 검색어에 해당한는 값들만 DB에서 찾아와 List에 담아준다.
        //mybatis에서 두개의 파라미터를 넘길 경우 map에 넣어 보내야 한다??
        cri.setSearch(search);
        List<Map<String, Object>> searchUsers = usrService.getUsersFromSearch(cri);

        //Map은 선언 시 <key, value>로 값을 넣는다. (key로 식별하고 value에 사용할 값을 넣는 방식), key=value형식으로 데이터 저장
        //클라이언트로 전송해야할 데이터가 1.검색어에대한 회원 정보 2. 페이징 관련 3. 현재 페이지 정보를 보내야 하기때문에 key, value를 통해 나누어 보내준다.
        Map result = new HashMap<String, Object>();
        result.put("userInfo", searchUsers); //검색어에 해당하는 회원 정보를 넘겨준다.
        result.put("pageMaker", pageMaker); //페이징 관련 해서 ajax로 데이터 넘겨 주기
        result.put("nowPage", nowPage);  //페이징 관련 해서 ajax로 데이터 넘겨 주기

        return result;
    }





    /**
     * 네비게이션바 에서 회원정보 버튼을 클릭했을 경우 세션에 저장된 아이디 값을 받아 회원정보 페이지로 이동하는 controller
     * @param model
     * @param userId : 현재 세션에 저장된 회원의 아이디
     * @return
     */
    @RequestMapping("usr/myInfo")
    public String myInfo(Model model, String userId) {

//        System.out.println("userId : " + userId);
        model.addAttribute("userId", userId);
        int primaryId = transactionService.getPrimaryId(userId); //파라미터로 받은 사용자 아이디를 통해 해당 회원의 PK값을 구한다.

        Map<String, Object> userInfo = transactionService.getUserInfo(primaryId); //위에서 구한 회원PK값을 통해 해당 회원의 데이터를 받아온다.
//        System.out.println("=====사용자 정보=====");
//        System.out.println(userInfo);
        model.addAttribute("userInfo", userInfo);

        return "thymeleaf/content/myInfoPage";
    }



    //사용자가 마이페이지 에서 정보수정 버튼을 클릭해서 나오는 모달창에 입력한 비밀번호가 현재 로그인한 회원의 아이디와 일치하는지 확인하는 로직
    @RequestMapping(value = "/usr/checkPW", produces = "application/json; charset=utf8", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> checkPW(@RequestBody  Map<String, Object> data) {

//        System.out.println("Controller에서의 data : " + data);
        Map<String, Object> result = usrService.checkPW(data);

        return result;
    }


    /**
     *
     * @param userId : 현재 로그인 되어 있는 사용자 아이디
     * @param name : 사용자 닉네임
     * @param email : 사용지
     * @param view_YN : 거래내역 공개 여부
     * @param createDate : 사용자 회원가입 날짜
     * @param userIdPK : 사용자 PK
     */
    @RequestMapping("usr/myInfoModify")
    public String myInfoModify(String userId, String name, String email, String view_YN, String createDate, int userIdPK, Model model){
//        System.out.println("userId : " + userId);
//        System.out.println("name : " + name);
//        System.out.println("email : " + email);
//        System.out.println("view_YN : " + view_YN);
//        System.out.println("createDate : " + createDate);
//        System.out.println("userIdPK : " + userIdPK);

        model.addAttribute("userId", userId);
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("view_YN", view_YN);
        model.addAttribute("createDate", createDate);
        model.addAttribute("userIdPK", userIdPK);


        return "thymeleaf/content/myInfoModifyPage";

    }


    /**회원정보 수정 페이지 에서 사용자가 입력한 이메일이 자신 이메일을 제외하고 중복되는 이메일이 있는지 확인하는 로직
     * 
     * @param data
     * @return
     */
    @RequestMapping(value = "/usr/checkEmailForModifyMyInfo", produces = "application/json; charset=utf8", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> checkEmailForModifyMyInfo(@RequestBody  Map<String, Object> data) {

//        System.out.println("Controller에서의 data : " + data);
        Map<String, Object> result = usrService.checkEmailForModifyMyInfo(data);
        
        return result;
    }



    //회원 정보 수정 에서 사용자가 입력한 아이디가 현재 자신의 아이디 외에 중복되는 아이디가 있는지 확인하는 로직
    @RequestMapping(value = "/usr/checkUserIdForModifyMyInfo", produces = "application/json; charset=utf8", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> checkUserIdForModifyMyInfo(@RequestBody  Map<String, Object> data) {

//        System.out.println("Controller에서의 data : " + data);
        Map<String, Object> result = usrService.checkUserIdForModifyMyInfo(data);

        return result;
    }


    //회원 정보 수정 에서 사용자가 입력한 모든 데이터가 유효성 검증을 통과해서 넘어오는 데이터로 실제 데이터베이스에 회원정보를 수정하는 로직이다.
    @RequestMapping(value = "/usr/doModifyUserInfo", produces = "application/json; charset=utf8", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> doModifyUserInfo(@RequestBody  Map<String, Object> data) {
//        System.out.println("최종적으로 수정할 데이터 : " + data);
        Map<String, Object> result = usrService.doModifyUserInfo(data);
        return result;
    }


    //로그인 페이지 에서 사용자 아이디를 찾는 페이지로 return하는 로직
    @RequestMapping(value = "/usr/findUserId", produces = "application/json; charset=utf8", method = {RequestMethod.GET})
    public String findUserId() {
        return "thymeleaf/usr/findUserId";
    }

    @RequestMapping("/usr/findUserIdProcess")
    @ResponseBody
    public Map<String,Object> findUserIdProcess(Model model, @RequestParam Map<String, Object> data) {

//        System.out.println(data);
        //사용자가 아이디 찾기 페이지 에서 보낸 usreName, userEmail와 일치하는 회원의 아이디를 찾는 로직
        Map<String, Object> result = usrService.findUserIdProcess(data);
//        System.out.println(result);

        return result;

    }


    //사용자가 로그인/아이디 찾기 페이지 에서 비밀번호 찾기 버튼을 클릭할 경우 비밀번호 찾기 페이지로 이동하는 로직
    @RequestMapping("usr/findUserPw")
    public String findUserPw(){
        return "thymeleaf/usr/findUserPw";
    }


    /**
     * 사용자가 비밀번호 찾기 에서 입력한 이름, 아이디, 이메일 값이 유효성 검증을 통과하면 해당 데이터와 일치하는 회원의 PK값을 찾는 로직
     * @param data
     * @return
     */
    @RequestMapping(value = "/usr/findUserPWProcess", produces = "application/json; charset=utf8", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> findUserPWProcess(@RequestBody  Map<String, Object> data)  {
        System.out.println(data);

        //사용자가 입력한 이름, 아이디, 이메일과 일치하는 회원의 PK값을 가지고 오는 로직
        Map<String, Object> result =  usrService.getUserPkByFindPw(data);
//        System.out.println("회원의 PK : " + result);

        return result;
    }



    @RequestMapping("usr/myInfoDelete")
    @ResponseBody
    public Map<String, Object> myInfoDelete(@RequestBody  Map<String, Object> data, HttpSession httpSession){
        //삭제 로직 진행 -> 성공시 알림 메세지 + 성공시 로그인 화면으로 이동

//        System.out.println("userId : " + data.get("userId"));
//        System.out.println("name : " + data.get("name"));
//        System.out.println("userIdPK : " + data.get("userIdPK"));
        
        //현재 로그인 되어 있는 회원을 탈퇴시키는 로직
        Map<String, Object> result = usrService.doInfoDelete(data, httpSession);
//        System.out.println(result);

        return result;
    }



}
