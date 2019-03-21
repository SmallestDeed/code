package com.sandu.service.banner.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.banner.input.MiniProIndexDialogAdd;
import com.sandu.api.banner.input.MiniProIndexDialogUpdate;
import com.sandu.api.banner.model.BaseBannerAd;
import com.sandu.api.banner.model.MiniProIndexDialog;
import com.sandu.api.banner.service.MiniProIndexDialogService;
import com.sandu.api.common.exception.SystemException;
import com.sandu.commons.LoginUser;
import com.sandu.commons.Utils;
import com.sandu.service.banner.dao.MiniProIndexDialogMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service("miniProIndexDialogService")
@Slf4j
public class MiniProIndexDialogServiceImpl implements MiniProIndexDialogService {

    @Autowired
    private MiniProIndexDialogMapper miniProIndexDialogMapper;

    @Override
    public int addSXWIndexDialog(MiniProIndexDialogAdd miniProIndexDialogAdd, LoginUser loginUser) {
        MiniProIndexDialog miniProIndexDialog =transferBean(miniProIndexDialogAdd,loginUser);
        return miniProIndexDialogMapper.insert(miniProIndexDialog);
    }

    @Override
    public PageInfo<MiniProIndexDialog> getDialogList(String dialogCode, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<MiniProIndexDialog> lists =  miniProIndexDialogMapper.selectMiniProIndexDialogList(dialogCode,page,limit);
        DeCoderUrl(lists);
        return new PageInfo<>(lists);
    }

    @Override
    public MiniProIndexDialog getEnableDialog(String dialogCode) {
        MiniProIndexDialog dialog = miniProIndexDialogMapper.getEnableDialog(dialogCode);
        if (Objects.nonNull(dialog)) DeCoderUrl(Arrays.asList(dialog));
        return dialog;
    }

    @Override
    public int updateDialog(MiniProIndexDialogUpdate update,LoginUser loginUser) {
        MiniProIndexDialog log = new MiniProIndexDialog();
        BeanUtils.copyProperties(update,log);
        log.setUrl(encodeUrl(log.getUrl()));
        saveSystemInfo(log,loginUser);
        log.setIsEnable(null);
        return miniProIndexDialogMapper.update(log);
    }

    @Override
    public int changeBannerIsEnable(Integer dialogId, LoginUser loginUser) {
        //获取弹窗信息
        MiniProIndexDialog miniProIndexDialog = miniProIndexDialogMapper.get(dialogId);
        MiniProIndexDialog update = new MiniProIndexDialog();
        update.setId(Long.valueOf(dialogId + ""));
        if (Objects.equals(0,miniProIndexDialog.getIsEnable())){
            //开启弹窗
            int count = miniProIndexDialogMapper.countIsEnable();
            if (count > 0){
               //将开启的弹窗关闭
               miniProIndexDialogMapper.updateAlreadyIsEnable(dialogId);
            }
            update.setIsEnable(1);
            return miniProIndexDialogMapper.update(update);
        }else{
            update.setIsEnable(0);
            return miniProIndexDialogMapper.update(update);
        }
    }

    @Override
    public int deletedDialog(Integer dialogId, LoginUser loginUser) {
        MiniProIndexDialog log = new MiniProIndexDialog();
        log.setIsDeleted(1);
        log.setId(Long.valueOf(dialogId + ""));
        saveSystemInfo(log,loginUser);
        return miniProIndexDialogMapper.update(log);
    }

    private void DeCoderUrl(List<MiniProIndexDialog> lists) {
        //解码带有url参数的路径
        lists.stream().forEach(dialog ->{
            String skipPath = dialog.getUrl();
            if (StringUtils.isEmpty(skipPath))
                return ;

            int index = skipPath.indexOf("?url=");
            if (index >= 0) {
                StringBuilder sb = new StringBuilder();
                String suffix = skipPath.substring(index + "?url=".length(), skipPath.length());
                String prefix = skipPath.substring(0, index + "?url=".length());
                sb.append(prefix);
                try {
                    String encodeUrl = URLDecoder.decode(suffix, "UTF-8");
                    sb.append(encodeUrl);
                    dialog.setUrl(sb.toString());
                } catch (Exception e) {
                    log.error("encode url 失败");
                }

            }
        });
    }

    private MiniProIndexDialog transferBean(MiniProIndexDialogAdd miniProIndexDialogAdd, LoginUser loginUser) {
        MiniProIndexDialog log = MiniProIndexDialog.builder().build();
        BeanUtils.copyProperties(miniProIndexDialogAdd,log);
        saveSystemInfo(log,loginUser);
        //对URL进行编码
        log.setUrl(encodeUrl(log.getUrl()));
        return log;
    }

    public  String encodeUrl(String skipPath) {

        if (StringUtils.isEmpty(skipPath))
            return skipPath;

        int index = skipPath.indexOf("?url=");
        if (index >= 0) {
            StringBuilder sb = new StringBuilder();
            String suffix = skipPath.substring(index + "?url=".length(), skipPath.length());
            String prefix = skipPath.substring(0, index + "?url=".length());
            sb.append(prefix);
            try {
                String encodeUrl = URLEncoder.encode(suffix, "UTF-8");
                sb.append(encodeUrl);
            } catch (Exception e) {
                log.error("encode url 失败");
            }
            return sb.toString();
        }
        return skipPath;
    }

    /**
     * 自动存储系统字段
     * @param miniProIndexDialog MiniProIndexDialog实体
     * @param loginUser 当前登录用户
     */
    public void saveSystemInfo(MiniProIndexDialog miniProIndexDialog, LoginUser loginUser) {
        if(miniProIndexDialog != null){
            //新增
            if(miniProIndexDialog.getId() == null){
                miniProIndexDialog.setGmtCreate(new Date());
                if (null!=loginUser.getLoginName()){
                    miniProIndexDialog.setCreator(loginUser.getLoginName());
                }
                miniProIndexDialog.setIsDeleted(0);
                if(miniProIndexDialog.getSysCode()==null || "".equals(miniProIndexDialog.getSysCode())){
                    miniProIndexDialog.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                }
            }
            //修改
            miniProIndexDialog.setGmtModified(new Date());
            if (null!=loginUser.getLoginName()){
                miniProIndexDialog.setModifier(loginUser.getLoginName());
            }
            miniProIndexDialog.setIsEnable(0);
            miniProIndexDialog.setDialogCode("sxw");
        }
    }
}
