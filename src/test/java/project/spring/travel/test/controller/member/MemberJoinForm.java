package project.spring.travel.test.controller.member;
//package project.spring.travel.controller.member;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.logging.log4j.LogManager;
//
//import project.spring.helper.BaseController;
//import project.spring.helper.RegexHelper;
//import project.spring.helper.WebHelper;
//
///**
// * @fileName    : MemberJoinForm.java
// * @author      : 홍준성
// * @description : 회원가입 3단계 정보입력 View를 위한 컨트롤러
// * @lastDate    : 2019. 3. 28.
// */
//@WebServlet("/member/member_join_form.do")
//public class MemberJoinForm extends BaseController {
//    private static final long serialVersionUID = 9066037458464754338L;
//    
//    /**
//     * ===== 로그인 중에는 접근할 수 없는 페이지 =====
//     * - 로그인 페이지는 로그인 상태에서는 접근할 수 없도록 처리해야 하기 때문에
//     *     세션이 존재한다면 (null이 아니라면) 접근할 수 없도록 차단한다.
//     */
//    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
//    // -> import project.spring.helper.WebHelper
//    WebHelper web;
//    // -> import study.jsp.helper.RegexHelper
//    RegexHelper regex;
//
//    @Override
//    public String doRun(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        /** (2) 사용하고자 하는 Helper + Service 객체 생성 */
//        logger = LogManager.getFormatterLogger(request.getRequestURI());
//        web = WebHelper.getInstance(request, response);
//        regex = RegexHelper.getInstance();
//        
//        /** ===== 악의적인 접근에 대한 방어 코드 */
//        String referer = request.getHeader("referer");
//        if(referer == null) {
//            // 정상적인 접근 방법이 아닐 경우
//            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//            web.redirect(web.getRootPath() + "/error/error_page.do", null);
//            return null;
//        } else {
//            if(regex.isIndexCheck(referer, "member_join_agreement_ok.do")) {
//                // 거쳐야할 절차를 건너뛴 경우
//                logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//                web.redirect(web.getRootPath() + "/error/error_page.do", null);
//                return null;
//            }
//        }
//        
//        /** (3) 로그인 여부 검사 */
//        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
//        if(web.getSession("loginInfo") != null) {
//            web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
//            return null;
//        }
//        
//        /** ===== (4) 파라미터 받기 ===== */
//        // 입력된 이메일 인증 폼의 값을 받는다.
//        String cert_ok = (String) web.getSession("cert_ok");
//        String userName = (String) web.getSession("userName");
//        String gender = (String) web.getSession("gender");
//        String year = (String) web.getSession("year");
//        String month = (String) web.getSession("month");
//        String day = (String) web.getSession("day");
//        String email = (String) web.getSession("email");
//        String certNum = (String) web.getSession("certNum");
//        // member_join_agreement에서 보낸 파라미터를 추가한다.
//        String serviceCheck = (String) web.getSession("serviceCheck");
//        String infoCollectionCheck = (String) web.getSession("infoCollectionCheck");
//        String consignmentCheck = (String) web.getSession("consignmentCheck");
//        String marketingCheck = (String) web.getSession("marketingCheck");
//        String allCheck = (String) web.getSession("allCheck");
//        
//        // 모든 세션 삭제
//        web.removeSession("cert_ok");
//        web.removeSession("userName");
//        web.removeSession("gender");
//        web.removeSession("year");
//        web.removeSession("month");
//        web.removeSession("day");
//        web.removeSession("email");
//        web.removeSession("certNum");
//        web.removeSession("serviceCheck");
//        web.removeSession("infoCollectionCheck");
//        web.removeSession("consignmentCheck");
//        web.removeSession("marketingCheck");
//        web.removeSession("allCheck");
//        
//        /** 우선 로그에 기록 */
//        // member_join에서 보낸 파라미터를 기록한다.
//        logger.debug("cert_ok = " + cert_ok);
//        logger.debug("userName = " + userName);
//        logger.debug("gender = " + gender);
//        logger.debug("year = " + year);
//        logger.debug("month = " + month);
//        logger.debug("day = " + day);
//        logger.debug("email = " + email);
//        logger.debug("certNum = " + certNum);
//        // member_join_agreement에서 보낸 파라미터를 기록한다.
//        logger.debug("serviceCheck = " + serviceCheck);
//        logger.debug("infoCollectionCheck = " + infoCollectionCheck);
//        logger.debug("consignmentCheck = " + consignmentCheck);
//        logger.debug("marketingCheck = " + marketingCheck);
//        logger.debug("allCheck = " + allCheck);
//        
//        /** ===== (5) 입력값의 유효성 검사 ===== */
//        // member_join에서 보낸 파라미터를 검사한다.
//        // 이메일 인증 검사
//        if(!cert_ok.equals("true")) {
//            web.redirect(null, "이메일 인증에 성공하지 못하였습니다.");
//            return null;
//        }
//        
//        // 이름 검사
//        if(!regex.isValue(userName)) {
//            web.redirect(null, "이름을 입력하세요.");
//            return null;
//        }
//        if(!regex.isKor(userName)) {
//            web.redirect(null, "이름은 한글만 가능합니다.");
//            return null;
//        }
//        if(!regex.isMinLength(userName, 2)) {
//            web.redirect(null, "이름은 최소 2자 이상 입력해야 합니다.");
//            return null;
//        }
//        if(!regex.isMaxLength(userName, 20)) {
//            web.redirect(null, "이름은 최대 20자 까지만 입력 가능합니다.");
//            return null;
//        }
//        
//        // 성별 검사
//        if(!regex.isValue(gender)) {
//            web.redirect(null, "성별을 선택해 주세요.");
//            return null;
//        }
//        if(!gender.equals("M") && !gender.equals("F")) {
//            web.redirect(null, "성별이 잘못되었습니다.");
//            return null;
//        }
//        
//        // 생년월일 검사
//        if(!regex.isValue(year)) {
//            web.redirect(null, "생년월일을 선택해 주세요.");
//            return null;
//        }
//        if(!regex.isValue(month)) {
//            web.redirect(null, "생년월일을 선택해 주세요.");
//            return null;
//        }
//        if(!regex.isValue(day)) {
//            web.redirect(null, "생년월일을 선택해 주세요.");
//            return null;
//        }
//        
//        // 이메일 검사
//        if(!regex.isValue(email)) {
//            web.redirect(null, "이메일을 입력해 주세요.");
//            return null;
//        }
//        if(!regex.isEmail(email)) {
//            web.redirect(null, "이메일 주소의 형식이 잘못되었습니다.");
//            return null;
//        }
//        
//        // 이메일 인증번호 검사
//        if(!regex.isValue(certNum)) {
//            web.redirect(null, "이메일 인증번호를 입력해 주세요.");
//            return null;
//        }
//        
//        // member_join_agreement에서 보낸 파라미터를 검사한다.
//        // 서비스 이용약관 동의 검사
//        if(!regex.isValue(serviceCheck)) {
//            if(!serviceCheck.equals("on")) {
//                web.redirect(null, "서비스 이용약관에 동의하셔야 합니다.");
//                return null;
//            }
//        }
//        // 서비스 이용약관 동의 검사
//        if(!regex.isValue(infoCollectionCheck)) {
//            if(!infoCollectionCheck.equals("on")) {
//                web.redirect(null, "개인정보 수집 및 이용에 동의하셔야 합니다.");
//                return null;
//            }
//        }
//        // 서비스 이용약관 동의 검사
//        if(!regex.isValue(consignmentCheck)) {
//            if(!consignmentCheck.equals("on")) {
//                web.redirect(null, "개인정보 처리 업무 위탁에 동의하셔야 합니다.");
//                return null;
//            }
//        }
//        // 마케팅 목적 이용에 관한 검사
//        if(marketingCheck != null) {
//            if(!marketingCheck.equals("on")) {
//                web.redirect(null, "마케팅 목적 이용에 관한 동의가 잘못되었습니다.");
//                return null;
//            }
//        }
//        
//        /** ===== MemberJoinFormOk 컨트롤러에서 사용하기 위한 세션 저장 ===== */
//        web.setSession("cert_ok", cert_ok);
//        web.setSession("userName", userName);
//        web.setSession("gender", gender);
//        web.setSession("year", year);
//        web.setSession("month", month);
//        web.setSession("day", day);
//        web.setSession("email", email);
//        web.setSession("certNum", certNum);
//        web.setSession("serviceCheck", serviceCheck);
//        web.setSession("infoCollectionCheck", infoCollectionCheck);
//        web.setSession("consignmentCheck", consignmentCheck);
//        web.setSession("marketingCheck", marketingCheck);
//        web.setSession("allCheck", allCheck);
//        
//        request.setAttribute("userName", userName);
//        request.setAttribute("gender", gender);
//        request.setAttribute("year", year);
//        request.setAttribute("month", month);
//        request.setAttribute("day", day);
//        request.setAttribute("email", email);
//        
//        return "member/member_join_form";
//    }
//
//}
