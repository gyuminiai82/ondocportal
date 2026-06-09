package kr.ondoc.aspect;

import java.net.InetAddress;
import java.util.Enumeration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import kr.ondoc.error.UnauthorizedException;

@Aspect
@Component
public class SecurityAspect {
	
	private final Logger logger = LogManager.getLogger(SecurityAspect.class);

	//헤더 token 체크후 일치하지 않으면 인증에러 발생
		@Before("execution(* kr.ondoc.controller.*.*.*(..))")
		public void headerCheck(JoinPoint joinPoint) throws Exception {
			Object[] args = joinPoint.getArgs();
			
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = attr.getRequest();
			
			String uri = request.getRequestURI();
			
			logger.info("===============================================================1"+request.getRequestURI()+"=START="+System.currentTimeMillis());
//			Enumeration params = request.getParameterNames();
//			while(params.hasMoreElements()) {
//			  String name = (String) params.nextElement();
//			  if(!name.equals("token")) logger.info(name + " : " + request.getParameter(name) + "     "); 
//			}
			logger.info("===============================================================1"+request.getRequestURI()+"=END="+System.currentTimeMillis());

			
			if(uri.equals("/healthCheck")) return;
			//온닥CRM 첫화면 접근하기 위해서 필요
			if(uri.equals("/ondoccrm")) return;
			
			if(uri.equals("/penchartChartTree") || uri.equals("/penchartAddTree") || uri.equals("/healthCheck") || uri.equals("/crmInstall")) return;
			if(uri.equals("/crmGoogleCalendarMigration")) return;

			
			//if(InetAddress.getLocalHost().getHostAddress().equals("210.220.255.17")) return;
			
			if(request.getHeader("token") == null) throw new UnauthorizedException("NO_TOKEN");
			if(!request.getHeader("token").equals("xxxxx")) throw new UnauthorizedException("NO_TOKEN");
		}
	
	//레거시 token 체크후 일치하지 않으면 인증에러 발생 
	@Before("execution(* kr.ondoc.controller.legacy.*.*.*(..))")
	public void tokenCheck(JoinPoint joinPoint) throws Exception {
		Object[] args = joinPoint.getArgs();
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		
		logger.info("==============================================================="+request.getRequestURI()+"=START="+System.currentTimeMillis());
//		Enumeration params = request.getParameterNames();
//		while(params.hasMoreElements()) {
//		  String name = (String) params.nextElement();
//		  if(!name.equals("token")) logger.info(name + " : " + request.getParameter(name) + "     "); 
//		}
		logger.info("==============================================================="+request.getRequestURI()+"=END="+System.currentTimeMillis());
		String uri = request.getRequestURI();
		
		//if(uri.equals("/crmInstall")) return;
		
		//if(InetAddress.getLocalHost().getHostAddress().equals("210.220.255.17")) return;
		
		if(request.getParameter("token") == null) throw new UnauthorizedException("NO_TOKEN");
		if(!request.getParameter("token").equals("xxxxx")) throw new UnauthorizedException("NO_TOKEN");
	}	
}
