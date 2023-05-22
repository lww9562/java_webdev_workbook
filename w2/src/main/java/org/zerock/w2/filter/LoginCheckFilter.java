package org.zerock.w2.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.zerock.w2.dto.MemberDTO;
import org.zerock.w2.service.MemberService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter(urlPatterns = {"/todo/*"})
@Log4j2
public class LoginCheckFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.info("Login Check filter.......");

		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;

		HttpSession session = req.getSession();

		if(session.getAttribute("loginInfo") == null) {
			resp.sendRedirect("/login");

			return;
		}

		//session에 loginInfo 값이 없다면, 쿠키를 체크
		Cookie cookie = findCookie(req.getCookies(), "remember-me");

		//세션에도 없고, 쿠키도 없다면 로그인으로 이동
		if(cookie == null) {
			resp.sendRedirect("/login");
			return;
		}

		//쿠키가 존재하는 상황이라면
		log.info("cookie 존재!");
		//uuid 값 가져오기
		String uuid = cookie.getValue();

		try {
			//DB에서 uuid 조회
			MemberDTO memberDTO = MemberService.INSTANCE.getByUUID(uuid);

			log.info("사용자 정보 : " + memberDTO);
			if(memberDTO == null) {
				throw new Exception("Cookie value is not valid");
			}

			//회원정보를 세션에 추가
			session.setAttribute("loginInfo", memberDTO);
			chain.doFilter(request, response);
		} catch(Exception e){
			e.printStackTrace();
			resp.sendRedirect("/login");
		}
	}

	private Cookie findCookie(Cookie[] cookies, String name) {
		if(cookies == null || cookies.length == 0) {
			return null;
		}

		Optional<Cookie> result = Arrays.stream(cookies).filter(c -> c.getName().equals(name))
				.findFirst();

		return result.isPresent() ? result.get() : null;
	}
}
