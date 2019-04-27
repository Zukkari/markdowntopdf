package com.github.zukkari.pdffrontend.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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


        int status = e instanceof HttpServerErrorException ? ((HttpServerErrorException) e).getRawStatusCode() : -1;
        ModelAndView mav = new ModelAndView();

        if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            // Service is starting up return loading page
            mav.setViewName("starting-up");
            mav.addObject("lblink", "/status");
        } else {
            if (status != -1) {
                mav.addObject("statusCode", status);
            }

            mav.setViewName("error-view");
        }

        return mav;
    }

}
