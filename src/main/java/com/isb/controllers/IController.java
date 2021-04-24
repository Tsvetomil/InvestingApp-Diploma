package com.isb.controllers;

import com.isb.exception.UnauthorisedException;
import com.isb.exception.UserException;
import com.isb.rest.utils.Response;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface IController {
    Logger logger = Logger.getLogger(IController.class.getName());


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserException.class})
    default Response handleUserException(UserException e){
        logger.error(e);
        return new Response(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthorisedException.class})
    default Response handleUnauthorizedException(UnauthorisedException e){
        logger.error(e);
        return new Response(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

}
