package com.sandu.user.model.view;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MediationBankInfoListVo implements Serializable{
    private static final long serialVersionUID = 750313590741884063L;
    /**
     * 银行卡号
     */
    private String bankNumber;

    /**
     * 银行名称
     */
    private String bankName;


    private Integer id;


}