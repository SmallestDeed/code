package com.nork.common.util;

import java.util.ResourceBundle;

/**
 * Created by Administrator on 2017/5/2 0002.
 */
public class VersionUtils {

    private final static ResourceBundle app = ResourceBundle.getBundle("app");

    public static final String VERSION_FIVE_ONE = "5.1";
    public static final String VERSION_FIVE_SIX = "5.6";

    //是否是5.6版本
    public static boolean isFiveDotSix() {
    	String versionStr = null;
    	try{
    		 versionStr = app.getString("app.sysModel.version");
    	}catch (Exception e) {
    		versionStr = "5.1";
		}
        if( StringUtils.isNotEmpty(versionStr) && VERSION_FIVE_SIX.equals(versionStr) ){
            return true;
        }
        return false;
    }

}
