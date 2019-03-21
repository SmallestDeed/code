package com.sandu.validate.service.parser;


import com.sandu.annotation.Bank;
import com.sandu.annotation.Phone;
import com.sandu.common.util.RegValidator;
import com.sandu.validate.service.IAnnotationParser;
import com.sandu.validate.vo.ValidateResult;
import lombok.experimental.var;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: weis
 * Date: 2018/8/20
 * Time: 15:26
 * Description: 是否是合法的手机号
 *
 */
public class BankParser implements IAnnotationParser {

    @Override
    public ValidateResult validate(Field f, Object value) {
        ValidateResult result = new ValidateResult();
        if(f.isAnnotationPresent(Bank.class)){
            if(value != null && !value.equals("")){

                String s = value.toString();
                String strBin = "10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";
                if(!RegValidator.checkBankCard(s)){
                    result.setFieldName(f.getName());
                    result.setMessage(value+"不合法");
                }else if (s == "") {
                    result.setFieldName(f.getName());
                    result.setMessage("请填写银行卡号");
                }else if (s.length() < 16 || s.length() > 19) {
                    result.setFieldName(f.getName());
                    result.setMessage("银行卡号长度必须在16到19之间");
                }else if (!RegValidator.isAllNumeric(s)) {
                    result.setFieldName(f.getName());
                    result.setMessage("银行卡号必须全为数字");
                }else if (strBin.indexOf(s.substring(0, 2)) == -1) {
                    result.setFieldName(f.getName());
                    result.setMessage("银行卡号开头6位不符合规范");
                }

            }
        }
        return result;
    }

}


/*
    public static boolean CheckBankNo(String bankNo) {
        String bankNo = bankno.trim();
        if (bankNo == "") {
            layer.msg("请填写银行卡号");
            return false;
        }
        if (bankno.length < 16 || bankno.length > 19) {
            layer.msg("银行卡号长度必须在16到19之间");
            return false;
        }
        var num = /^\d * $ /;//全数字
        if (!num.exec(bankno)) {
            layer.msg("银行卡号必须全为数字");
            return false;
        }
        //开头6位
        String strBin = "10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";
        if (strBin.indexOf(bankno.substring(0, 2)) == -1) {
            layer.msg("银行卡号开头6位不符合规范");
            return false;
        }
        //Luhn校验
        if (!this.luhnCheck(bankno)) {
            return false;
        }
        return true;
    }*/
