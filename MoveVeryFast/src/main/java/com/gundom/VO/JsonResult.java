package com.gundom.VO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class JsonResult implements Serializable {

    private static final long serialVersionUID = -852024038217431339L;

    private int state = 1;

    private String message="ok";

    private Object data;


    public JsonResult(Object data){
        this.data=data;
    }

    public JsonResult(Throwable throwable){
        this.state=0;
        this.message=throwable.getMessage();
    }

}