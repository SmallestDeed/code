package com.sandu.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Validator
{
  EAMIL("^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"), 
  MOBILE("^((17[0-9])|(2[0-9][0-9])|(13[0-9])|(15[012356789])|(18[0-9])|(14[57]))[0-9]{8}$");

  String regex;

  Validator(String regex) { this.regex = regex; }


  public boolean validate(String email)
  {
    if (StringUtils.isEmpty(email)) {
        return false;
    }
    Pattern regexPattern = Pattern.compile(this.regex);
    Matcher matcher = regexPattern.matcher(email);
    return matcher.matches();
  }

}