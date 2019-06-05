package com.excilys.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

	 
    @GetMapping(value = "errors")
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
         
        ModelAndView errorPage = new ModelAndView("errorPage");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);
 
        switch (httpErrorCode) {
            case 400:
                errorMsg = "error.400";
                break;
            case 401:
                errorMsg = "error.401";
                break;
            case 404:
                errorMsg = "error.404";
                break;
            case 500:
                errorMsg = "error.500";
                break;
            default : 
            	errorMsg = "error.default";
            	break;
        }
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }
     
    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
          .getAttribute("javax.servlet.error.status_code");
    }
	
}
