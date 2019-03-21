package com.nork.common.exception;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/18.
 */
public class GeneratePanoramaException extends Exception implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public GeneratePanoramaException(boolean flag, String message){
        this.message = message;
        this.flag = flag;
    }
    public GeneratePanoramaException(String msg){
        super(msg);
    }

}
