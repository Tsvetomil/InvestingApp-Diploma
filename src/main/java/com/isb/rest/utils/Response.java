package com.isb.rest.utils;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Response {

    private int status;
    private String msg;
    private LocalDateTime localDateTime = LocalDateTime.now();
    private Object entity;

    public Response(int status){
        this.status = status;
    }

    public Response(int status, String msg){
        this.status = status;
        this.msg = msg;
    }
    public Response(int status, Object entity){
        this.status = status;
        this.entity = entity;
    }
}
