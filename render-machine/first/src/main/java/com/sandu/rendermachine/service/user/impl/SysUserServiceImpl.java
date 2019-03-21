package com.sandu.rendermachine.service.user.impl;

import com.sandu.rendermachine.common.properties.AesProperties;
import com.sandu.rendermachine.common.util.AESUtil2;
import com.sandu.rendermachine.common.util.Utils;
import com.sandu.rendermachine.dao.render.MobileRenderRecordMapper;
import com.sandu.rendermachine.dao.user.SysUserMapper;
import com.sandu.rendermachine.model.render.WhiteDeviceList;
import com.sandu.rendermachine.model.user.SysUser;
import com.sandu.rendermachine.service.user.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 4:46 2018/4/19 0019
 * @Modified By:
 */
@Slf4j
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    // 密匙
    private static String key= Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_ENCRYPT_KEY_FILEKEY, "41e5c74dd46e4ddcb942dc8ce6224a2e").trim();

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private MobileRenderRecordMapper mobileRenderRecordMapper;

    @Override
    public SysUser get(Integer id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean checkWhiteList(String terminalImei) {
        List<WhiteDeviceList> list = mobileRenderRecordMapper.checkWhiteList();
        for (WhiteDeviceList whiteDeviceList : list) {
            if (terminalImei.equals(whiteDeviceList.getDeviceKey())) {
                return true;
            }else {
                continue;
            }
        }
        return false;
    }

    /**
     * 用token对加密文件的key进行加密
     */
    @Override
    public SysUser EencryptKey(SysUser user) {
        String token = "";
        if(org.apache.commons.lang3.StringUtils.isNotBlank(user.getToken())){
            //截取token的前八位
            if(user.getToken().length() < 8){
                //字符长度不够8位则在后面补0
                token = user.getToken()+String.format("%1$0"+(8-user.getToken().length())+"d",0);
            }else{
                token = user.getToken().substring(0,8);
            }
            String encrypt = AESUtil2.encryptFile(key, token);
            user.setCryptKey(encrypt);
            return user;
        }else{
            log.info("用户id="+user.getId()+"返回token信息为null！");
        }
        return user;
    }

}
