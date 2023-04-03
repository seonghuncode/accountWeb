package controller;

import dto.Criteria;
import dto.PageMaker;
import dto.UsrDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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

        return usrService.doLogout(httpSession);

    }


    //현재 로그인 되어있는 세션의 아이디로 회원정보를 찾을 수 있기 때문에 따로 넘겨줄 매개변수는 없다.
    @RequestMapping("/usr/main")
    public ModelAndView doMain(Model model, Criteria cri) throws Exception {
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




}
