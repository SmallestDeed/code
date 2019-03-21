package com.sandu.panorama.service.exception;

/**
 * 自定义720制作异常类
 * Created by chenm on 2018/8/3.
 */
public class PanorameException extends  RuntimeException{

    private static final long serialVersionUID = -5885207149083968212L;

    public PanorameException(String message){
        super(message);
    }
}
