package com.sandu.service.user.impl.manage;

import com.sandu.api.system.model.SysDictionary;
import com.sandu.api.system.service.SysResLevelCfgService;
import com.sandu.api.user.model.SysUserEquipment;
import com.sandu.api.user.service.manage.SysUserEquipmentService;
import com.sandu.common.exception.BizException;
import com.sandu.common.exception.ExceptionCodes;
import com.sandu.config.ResourceConfig;
import com.sandu.service.user.dao.SysUserEquipmentDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("sysUserEquipmentService")
@Transactional
public class SysUserEquipmentServiceImpl implements SysUserEquipmentService {

    @Autowired
    private SysUserEquipmentDao sysUserEquipmentDao;

    @Autowired
    private SysResLevelCfgService sysResLevelCfgService;

    @Autowired
    public void setSysUserEquipmentMapper(SysUserEquipmentDao sysUserEquipmentMapper) {
        this.sysUserEquipmentDao = sysUserEquipmentMapper;
    }

    @Override
    public int add(SysUserEquipment sysUserEquipment) {
        return sysUserEquipmentDao.insertSelective(sysUserEquipment);
    }

    @Override
    public int getCount(SysUserEquipment sysUserEquipment) {
        return sysUserEquipmentDao.selectCount(sysUserEquipment);
    }

    @Override
    public List<SysUserEquipment> getList(SysUserEquipment sysUserEquipment) {
        return sysUserEquipmentDao.selectList(sysUserEquipment);
    }

    /**
     * 通过用户id,和设备类型  删除设备号
     *
     * @param userId
     * @param equipmentType
     */
    @Override
    public void deleteByUserId(Long userId, String equipmentType) {
        sysUserEquipmentDao.deleteByUserId(userId, equipmentType);
    }

    /**
     * 检测设备号
     *
     * @param userId          用户id
     * @param deviceRestrict  设备限制值
     * @param userImei        PCI网卡Mac地址(PCI设备号)
     * @param usbTerminalImei USB网卡Mac地址(USB设备号)
     * @param equipmentType   登录设备类型  移动端 、pc端？
     * @return
     */
    @Override
    public void equipmentCheck(Long userId, Integer deviceRestrict, String userImei, String usbTerminalImei, String equipmentType) {

        //用户登录设备校验开关
        int checkSwitch = ResourceConfig.PC_DEVICE_USER_CHECK_CONFIG;

        //关闭校验直接跳过后续校验方法----全局跳过或单用户跳过
        if (SysUserEquipment.CLOSE_DEVICE_USER_CHECK == checkSwitch || deviceRestrict == SysUserEquipment.CANCEL_NETWORD_CARD_DEVICE_RESTRICT) {
            return;
        }

        //禁用设备不允许登录
        if (deviceRestrict == SysUserEquipment.NOT_ALLOW_ALL_NETWORD_CARD) {
            throw new BizException(ExceptionCodes.CODE_10010002, "此用户已被限制登录，请联系客服!");
        }


        if (StringUtils.isEmpty(userImei) && StringUtils.isEmpty(usbTerminalImei)) {
            throw new BizException(ExceptionCodes.CODE_10010003, "userImei和usbTerminalImei不能同时为空!");
        }

        //可用设备数
        Integer equipmentNum = 0;
        //获取限制设备数量
        List<SysDictionary> equipmentNumList = sysResLevelCfgService.getEquipmentNum(userId, equipmentType);
        if (equipmentNumList == null || equipmentNumList.size() <= 0) {
            throw new BizException(ExceptionCodes.CODE_10010004, "数据初始化有问题,请联系客服!");
        }
        if (equipmentNumList.size() != 1) {
            throw new BizException(ExceptionCodes.CODE_10010005, "设备登录异常,请联系客服!");
        }
        equipmentNum = equipmentNumList.get(0).getValue();
        if (equipmentNum == null || equipmentNum.intValue() <= 0) {
            throw new BizException(ExceptionCodes.CODE_10010006, "无设备可登录,请联系客服!");
        }

        //用户设备信息
        SysUserEquipment userEquipment = new SysUserEquipment();
        userEquipment.setUserId(userId);
        userEquipment.setEquipmentType(equipmentType);
        userEquipment.setIsDeleted(0);

        //获取已登记设备数
        int beenUsed = this.getCount(userEquipment);
        if (beenUsed > equipmentNum) {    //已使用设备 大于 设备上限 说明用户被降级,清理掉该用户所有设备号,让其重新绑定
            this.deleteByUserId(userId, equipmentType);
        }

        //获取已登记设备列表
        List<SysUserEquipment> equipmentList = this.getList(userEquipment);
        if (equipmentList == null || equipmentList.size() <= 0) {  //为null说明为首次登录
            //检查用户网卡设备限制--超出限制不允许注册设备
            switch (null == deviceRestrict ? 0 : deviceRestrict) {
                case SysUserEquipment.ALLOW_ALL_NETWORD_CARD:
                    //允许所有设备
                    if (!StringUtils.isEmpty(userImei) || !StringUtils.isEmpty(usbTerminalImei)) {
                        userEquipment.setUserImei(userImei);
                        userEquipment.setUsbTerminalImei(usbTerminalImei);
                        userEquipment.setGmtCreate(new Date());
                        //新增设备
                        this.add(userEquipment);
                        return;
                    }
                    break;
                case SysUserEquipment.ONLY_ALLOW_PCI_NETWORD_CARD:
                    //检查PCI网卡
                    if (!StringUtils.isEmpty(userImei)) {
                        userEquipment.setUserImei(userImei);
                        userEquipment.setUsbTerminalImei(usbTerminalImei);
                        userEquipment.setGmtCreate(new Date());
                        //新增设备
                        this.add(userEquipment);
                        return;
                    }
                    break;
                case SysUserEquipment.ONLY_ALLOW_USB_NETWORD_CARD:
                    //检查USB网卡
                    if (!StringUtils.isEmpty(usbTerminalImei)) {
                        userEquipment.setUserImei(userImei);
                        userEquipment.setUsbTerminalImei(usbTerminalImei);
                        userEquipment.setGmtCreate(new Date());
                        //新增设备
                        this.add(userEquipment);
                        return;
                    }
                    break;
                case SysUserEquipment.NOT_ALLOW_ALL_NETWORD_CARD:
                    //不允许登录
                    throw new BizException(ExceptionCodes.CODE_10010002, "此用户已被限制登录，请联系客服!");

                default:
                    //不允许登录
                    throw new BizException(ExceptionCodes.CODE_10010007, "设备限制类型异常，请联系客服!");
            }
            //设备限制类型与设备类型不匹配
            throw new BizException(ExceptionCodes.CODE_10010008, "设备类型不匹配，请重新登录!");
        }

        //对比设备，检查是否是已登记设备校验
        for (SysUserEquipment sysUserEquipment : equipmentList) { //非首次登录，判断设备号是否存在
            //检查用户网卡设备限制
            switch (null == deviceRestrict ? 0 : deviceRestrict) {
                case SysUserEquipment.ALLOW_ALL_NETWORD_CARD:
                    //检查PCI网卡
                    if (!StringUtils.isEmpty(userImei)) {
                        if (userImei.equals(sysUserEquipment.getUserImei())) {
                            return;
                        }
                    } else if (!StringUtils.isEmpty(usbTerminalImei)) {
                        //检查USB网卡
                        if (usbTerminalImei.equals(sysUserEquipment.getUsbTerminalImei())) {
                            return;
                        }
                    }
                    break;
                case SysUserEquipment.ONLY_ALLOW_PCI_NETWORD_CARD:
                    //检查PCI网卡
                    if (!StringUtils.isEmpty(userImei)) {
                        if (userImei.equals(sysUserEquipment.getUserImei())) {
                            return;
                        }
                    }
                    break;
                case SysUserEquipment.ONLY_ALLOW_USB_NETWORD_CARD:
                    //检查USB网卡
                    if (!StringUtils.isEmpty(usbTerminalImei)) {
                        if (usbTerminalImei.equals(sysUserEquipment.getUsbTerminalImei())) {
                            return;
                        }
                    }
                    break;
                case SysUserEquipment.NOT_ALLOW_ALL_NETWORD_CARD:
                    throw new BizException(ExceptionCodes.CODE_10010002, "此用户已被限制登录，请联系客服!");
                default:
                    throw new BizException(ExceptionCodes.CODE_10010009, "此用户已被限制登录，请联系客服!");
            }
        }

        //检查剩余位
        if (equipmentNum.intValue() > equipmentList.size()) { // 没有从数据库中得到设备号，判断设备限制是否有剩余位置
            //设备数量限制有剩余设备位
            userEquipment.setUserImei(userImei);
            userEquipment.setUsbTerminalImei(usbTerminalImei);
            userEquipment.setGmtCreate(new Date());
            //登记设备
            this.add(userEquipment);
            return;
        } else {
            //设备数量已超出系统预设值
            throw new BizException(ExceptionCodes.CODE_10010010, "该帐号已绑定其他设备,只能使用初始设备登录!");
        }
    }
}
