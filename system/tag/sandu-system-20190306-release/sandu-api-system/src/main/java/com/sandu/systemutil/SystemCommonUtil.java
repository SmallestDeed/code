package com.sandu.systemutil;

import com.nork.common.model.LoginUser;
import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.constance.EntityClassTypeConstant;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.model.SysUserRole;
import com.sandu.api.user.model.SysUserRoleGroup;
import com.sandu.api.user.model.UserJurisdiction;
import com.sandu.pay.order.model.PayAccount;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * 系统通用工具类
 * Created by xiaoxc on 2018/4/23 0023.
 */
public class SystemCommonUtil {

    /**
     * 获取session缓存用户
     * @param request
     * @return
     */
    public static LoginUser getLoginUserFormSession(HttpServletRequest request){

        LoginUser loginUser = new LoginUser();
        if (request.getSession() == null
                || request.getSession().getAttribute("loginUser") == null) {
            loginUser.setLoginName("nologin");
        } else {
            loginUser = (LoginUser)request.getSession().getAttribute("loginUser");
        }
        return loginUser;
    }

    /**
     * 自动存储系统字段
     */
    public static void sysSave(Object obj, com.sandu.api.user.model.LoginUser loginUser, String classType) {

        if (StringUtils.isEmpty(classType)) {
            return;
        }

        if (obj != null) {
            if (EntityClassTypeConstant.ENTITY_CLASS_TYPE_SYSUSER.equals(classType)) {
                SysUser model = (SysUser) obj;
                if(model.getId() == null){
                    model.setGmtCreate(new Date());
                    model.setCreator(loginUser.getLoginName());
                    model.setIsDeleted(0);
                    if(model.getSysCode()==null || "".equals(model.getSysCode())){
                        model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                    }
                }
                model.setGmtModified(new Date());
                model.setModifier(loginUser.getLoginName());

            } else if (EntityClassTypeConstant.ENTITY_CLASS_TYPE_PAYACCOUNT.equals(classType)) {
                PayAccount model = (PayAccount) obj;
                if(model.getId() == null){
                    model.setGmtCreate(new Date());
                    model.setCreator(loginUser.getLoginName());
                    model.setIsDeleted(0);
                }
                model.setGmtModified(new Date());
                model.setModifier(loginUser.getLoginName());

            } else if (EntityClassTypeConstant.ENTITY_CLASS_TYPE_SYSUSERROLEGROUP.equals(classType)) {
                SysUserRoleGroup model = (SysUserRoleGroup) obj;
                if(model.getId() == null){
                    model.setGmtCreate(new Date());
                    model.setCreator(loginUser.getLoginName());
                    model.setIsDeleted(0);
                }
                model.setGmtModified(new Date());
                model.setModifier(loginUser.getLoginName());

            } else if (EntityClassTypeConstant.ENTITY_CLASS_TYPE_SYSUSERROLE.equals(classType)) {
                SysUserRole model = (SysUserRole) obj;
                if(model.getId() == null){
                    model.setGmtCreate(new Date());
                    model.setCreator(loginUser.getLoginName());
                    model.setIsDeleted(0);
                    if(model.getSysCode()==null || "".equals(model.getSysCode())){
                        model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                    }
                }
                model.setGmtModified(new Date());
                model.setModifier(loginUser.getLoginName());

            } else if (EntityClassTypeConstant.ENTITY_CLASS_TYPE_USERJURISDICTION.equals(classType)) {
                UserJurisdiction model = (UserJurisdiction) obj;
                if(model.getId() == null){
                    model.setGmtCreate(new Date());
                    model.setCreator(loginUser.getLoginName());
                    model.setIsDeleted(0);
                    if(model.getSysCode()==null || "".equals(model.getSysCode())){
                        model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                    }
                }
                model.setGmtModified(new Date());
                model.setModifier(loginUser.getLoginName());

            } else if (EntityClassTypeConstant.ENTITY_CLASS_TYPE_BASECOMPANY.equals(classType)) {
                BaseCompany model = (BaseCompany) obj;
                if(model.getId() == null){
                    model.setGmtCreate(new Date());
                    model.setCreator(loginUser.getLoginName());
                    model.setIsDeleted(0);
                    if(model.getSysCode()==null || "".equals(model.getSysCode())){
                        model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                    }
                }
                model.setGmtModified(new Date());
                model.setModifier(loginUser.getLoginName());

            } else {

            }
        }
    }


    /**
     * md5加密(copy)
     *
     * @param plainText
     * @return
     * @author huangsongbo
     */
    public static String md5(String plainText) {
        String str = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
//			//System.out.println("result: " + buf.toString());// 32位的加密
//			//System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密
            str = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getMd5Password(String password) {
        return md5(md5("WeB" + password));
    }
}
