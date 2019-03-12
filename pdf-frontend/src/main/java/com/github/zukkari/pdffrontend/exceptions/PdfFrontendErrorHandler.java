package com.github.zukkari.pdffrontend.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class PdfFrontendErrorHandler {
    public static final Logger log = LoggerFactory.getLogger(PdfFrontendErrorHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView serverErrorHandler(Exception e) {
        log.error("Handling error", e);

        ModelAndView mav = new ModelAndView();
        if (e instanceof HttpServerErrorException) {
            mav.addObject("statusCode", ((HttpServerErrorException) e).getRawStatusCode());
        }

        mav.setViewName("error-view");

        return mav;
    }

}
