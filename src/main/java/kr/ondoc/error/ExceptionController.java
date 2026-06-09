package kr.ondoc.error;


import java.util.Map;
import java.util.HashMap;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class ExceptionController implements ErrorController {
	
	@RequestMapping(value = "/error")
    public ResponseEntity<Object> handleNoHandlerFoundException(HttpServletResponse response, HttpServletRequest request) {
        int status = response.getStatus();
		
        //System.out.println(status);  //오류 상태
        
        Map<String, Object> result = new HashMap<>();
        result.put("status", status);
        String message = "ERROR";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        switch (status) {
	        case 500: message = "INTERNAL_SERVER_ERROR"; httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; break;
	        case 401: message = "UNAUTHORIZED"; httpStatus = HttpStatus.UNAUTHORIZED; break;
	        case 404: message = "NOT_FOUND"; httpStatus = HttpStatus.NOT_FOUND; break;
	        case 204: message = "NO_RESULT"; httpStatus = HttpStatus.NO_CONTENT; break;
	        
	        case 1001: message = "NO MATCHING DATA"; httpStatus = HttpStatus.NO_CONTENT; break;
	        case 1002: message = "NO MATCHING PHONENUMBER"; httpStatus = HttpStatus.NO_CONTENT; break;
	        case 1003: message = "RECEIPT FAIL"; httpStatus = HttpStatus.NO_CONTENT; break;
        }
        result.put("message", message);
        
        return new ResponseEntity<>(result, httpStatus);
    }
}