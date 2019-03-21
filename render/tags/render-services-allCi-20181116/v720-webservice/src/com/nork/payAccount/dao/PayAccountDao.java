package com.nork.payAccount.dao;

import com.nork.payAccount.model.PayAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PayAccountDao {

    PayAccount selectPayAccountByUserId(@Param("userId") Long userId,@Param("platformBussinessType") String platformBussinessType);

    void updateBalanceAmount(@Param("balanceAmount") Double planPrice, @Param("userId") Integer userId,@Param("platformBussinessType") String platformBussinessType);
}
