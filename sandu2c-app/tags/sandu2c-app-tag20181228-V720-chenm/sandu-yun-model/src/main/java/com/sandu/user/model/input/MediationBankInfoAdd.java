package com.sandu.user.model.input;

import com.sandu.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MediationBankInfoAdd implements Serializable{
    private static final long serialVersionUID = 750313590741884063L;



    /**
     * 银行卡号
     */
    @NotBlank()
    @Bank()
    @Name("银行卡号")
    private String bankNumber;

    /**
     * 银行名称
     */
    @NotBlank()
    @Chinese()
    @Name("银行名称")
    private String bankName;

    /**
     * 银行支行名称
     */
    @NotBlank()
    @Chinese()
    @Name("银行支行名称")
    private String bankNameInfo;

    /**
     * 银行预留手机号
     */
    @NotBlank()
    @Phone()
    @Name("银行预留手机号")
    private String preMobile;

    /**
     * 身份证号码
     */
    @IDCard()
    @Name("身份证号码")
    private String cardNumber;

    /**
     * 身份证姓名
     */
    @Chinese()
    @Name("身份证姓名")
    private String cardName;


}