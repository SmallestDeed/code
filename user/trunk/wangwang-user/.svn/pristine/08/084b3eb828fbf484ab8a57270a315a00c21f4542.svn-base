package com.sandu.service.account.impl;

import com.sandu.api.account.model.MgrRechargeLog;
import com.sandu.api.account.model.PayAccount;
import com.sandu.api.account.service.PayAccountService;
import com.sandu.service.account.dao.PayAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @author WangHaiLin
 * @date 2018/6/12  18:24
 */
@Service("payAccountService")
public class PayAccountServiceImpl implements PayAccountService{

    @Autowired
    private PayAccountDao payAccountDao;

    @Override
    public Long insertPayAccount(PayAccount payAccount) {
        int result = payAccountDao.addPayAccount(payAccount);
        if(result>0){
            return payAccount.getId();
        }
        return 0L;
    }

    @Override
    public PayAccount getPayAccountByUserId(Long userId) {
        return payAccountDao.selectByUserId(userId);
    }

    @Override
    public int updateUserAmount(PayAccount p) {
        return payAccountDao.update(p);
    }

    @Override
    public PayAccount getInfoByUserIdAndPlatformBussinessType(Integer userId, String paltformBussinssType) {
        return payAccountDao.getInfoByUserIdAndPlatformBussinessType(userId,paltformBussinssType);
    }

    @Override
    public List<MgrRechargeLog> getUserRechargeRecord(Long userId, Integer start, Integer limit) {
        return payAccountDao.getUserRechargeRecord(userId,start,limit);
    }

    @Override
    public int countUserRechargeRecord(Long userId) {
        return payAccountDao.countUserRechargeRecord(userId);
    }

    @Override
    public void handlerUserDubiInfo(Long userId, Double dubi) {
       dubi = dubi * 10;
       int row = 0;
       row =  payAccountDao.updateUserBalanceAmount(userId,dubi);

       if (row < 1){
           //用户没有开通账号
           PayAccount payAccount = this.buildPayAccount(userId, dubi);
           row = payAccountDao.addPayAccount(payAccount);
       }
    }

    private PayAccount buildPayAccount(Long userId, Double dubi) {
        PayAccount payAccount = new PayAccount();
        Date date = new Date();
        payAccount.setUserId(userId);
        payAccount.setCreator("system");
        payAccount.setGmtCreate(date);
        payAccount.setModifier("system");
        payAccount.setGmtModified(date);
        payAccount.setIsDeleted(0);
        payAccount.setBalanceAmount(dubi);
        payAccount.setConsumeAmount(0.0);
        payAccount.setPlatformBusinessType( "2b");
        return payAccount;
    }


}
