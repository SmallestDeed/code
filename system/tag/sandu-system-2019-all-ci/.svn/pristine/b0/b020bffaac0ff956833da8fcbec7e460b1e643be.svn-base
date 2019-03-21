package com.sandu.service.pic.impl;

import com.sandu.api.company.common.FileModel;
import com.sandu.api.company.common.FileUtils;
import com.sandu.api.pic.model.ResPic;
import com.sandu.api.pic.model.po.ResPicBusinessUpdate;
import com.sandu.api.pic.model.po.ResPicPO;
import com.sandu.api.pic.service.ResPicService;
import com.sandu.commons.LoginUser;
import com.sandu.commons.Utils;
import com.sandu.service.pic.dao.ResPicMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author chenqiang
 * @Description 资源图片 基础 业务层
 * @Date 2018/6/1 0001 10:30
 * @Modified By
 */
@Slf4j
@Service("resPicService")
public class ResPicServiceImpl implements ResPicService{

    @Autowired
    private ResPicMapper resPicMapper;

    /**
     * 根据主键id 物理删除资源图片信息
     * @author chenqiang
     * @param id 主键id
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int deleteByPrimaryKey(Long id){
        return resPicMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据主键id 逻辑删除资源图片信息
     * @author chenqiang
     * @param id 主键id
     * @param loginName 删除人登录名
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int deleteLogicByPrimaryKey(Long id, String loginName){
        return resPicMapper.deleteLogicByPrimaryKey(id,loginName);
    }

    /**
     * 根据资源图片基础实体类 选择性 新增数据
     * @author chenqiang
     * @param record 资源图片基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int insertSelective(ResPic record){
        int count =  resPicMapper.insertSelective(record);
        if(count > 0)
            return record.getId().intValue();
        else
            return 0;

    }

    /**
     * 根据主键id 查询 资源图片基础信息
     * @author chenqiang
     * @param id 资源图片主键id
     * @return 资源图片基础实体类
     * @date 2018/5/31 0031 18:21
     */
    public ResPic selectByPrimaryKey(Long id){
        return resPicMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据主键 选择性 修改数据
     * @author chenqiang
     * @param record 资源图片基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int updateByPrimaryKeySelective(ResPic record){
        return resPicMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 删除图片信息/支持批量删除
     * @author chenqiang
     * @param ids 主键ids
     * @return 是否成功Boolean
     */
    public boolean deletePic(String ids,LoginUser loginUser){
        if (StringUtils.isNotEmpty(ids) ) {

            String[] strs = ids.split(",");

            for (String c : strs) {
                Integer id = new Integer(c);

                /** 获取当前id 图片数据 */
                ResPic rp = this.selectByPrimaryKey(new Long(id));
                if (rp != null) {

                    /** 查询该数据是否有共享一个文件 ，如果有只删除数据库数据，不删除物理文件 **/
//                    int picPathCount = resPicMapper.picPathCount(rp.getPicPath());
//                    if (picPathCount > 1) {
//
//                        resPicMapper.deleteLogicByPrimaryKey(new Long(id), loginUser.getLoginName());
//
//                    } else {

                    try {

                        /** 删除该图片的缩略图(数据库,物理文件) */
                        this.deleteSmallPic(rp, loginUser);

                        /** 删除物理文件、数据库信息 */
                        FileUtils.deleteAllFile(rp.getPicPath());

                    } catch (Exception e) {
                        log.error("删除图片文件出错 ", e);
                        return false;
                    }

                    resPicMapper.deleteLogicByPrimaryKey(new Long(id), loginUser.getLoginName());
                }
            }
        }

        return true;
    }

    /**
     * 查询该数据是否有共享一个文件
     * @author chenqiang
     * @param picPath 路径
     * @return 新增数据主键id
     */
    public int picPathCount(String picPath){
        return resPicMapper.picPathCount(picPath);
    }

    public void deleteSmallPic(ResPic resPic,LoginUser loginUser) {
        if(StringUtils.isNotBlank(resPic.getSmallPicInfo())){
            Map<String,String> map = readFileDesc(resPic.getSmallPicInfo());
            Set<String> set = map.keySet();
            if (set.size() > 0) {
                for (String s : set) {
                    Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(map.get(s));
                    if(mer.find()){
                        /*删除缩略图物理文件*/
                        ResPic resPicSmall = resPicMapper.selectByPrimaryKey(new Long(Integer.parseInt(map.get(s))));
                        if(resPicSmall!=null){
                            if(StringUtils.isNotBlank(resPicSmall.getPicPath())){
                                FileUtils.deleteAllFile(resPicSmall.getPicPath());
                            }
                        }
                      /*删除数据库数据*/
                        resPicMapper.deleteLogicByPrimaryKey(new Long(Integer.parseInt(map.get(s))),loginUser.getLoginName());
                    }
                }
            }
        }
    }


    private Map<String, String> readFileDesc(String fileDesc) {
        Map<String, String> map = new HashMap<String, String>();
        String[] strs = fileDesc.split(";");
        for (String str : strs) {
            if (str.split(":").length == 2) {
                map.put(str.split(":")[0].trim(), str.split(":")[1].trim());
            }
        }
        return map;
    }

    /**
     * 将图片上传信息保存到resPic
     * @param map 上传服务器文件的详细信息
     * @param businessId 该资源所关联的表id
     * @param loginUser 登录名
     * @return
     */
    public Integer saveUploadImgPic(Map map,Integer businessId,LoginUser loginUser,String mes){
        Integer id = null;
        if( map != null && map.size() > 0 ) {
            ResPic resPic = new ResPic();

            //设置数据
            String dbFilePath = map.get(FileUtils.DB_FILE_PATH).toString();
            String fileName = map.get(FileModel.FILE_NAME).toString();
            resPic.setGmtCreate(new Date());
            resPic.setGmtModified(new Date());
            resPic.setIsDeleted(0);
            resPic.setPicName(fileName);//文件名
            resPic.setPicSize(Integer.parseInt(map.get(FileModel.FILE_SIZE).toString()));
            resPic.setPicSuffix(map.get(FileModel.FILE_SUFFIX).toString());
            resPic.setBusinessId(businessId);//该资源所关联的表id
            resPic.setPicName(fileName);//原文件名
            resPic.setPicFileName(fileName);
            resPic.setPicPath(dbFilePath);
            resPic.setFileKey(map.get(FileModel.FILE_KEY).toString());
            resPic.setPicLevel("0");
            resPic.setPicHigh(map.get(FileModel.PIC_WEIGHT).toString());
            resPic.setPicWeight(map.get(FileModel.PIC_HEIGHT).toString());
            resPic.setPicFormat(map.get(FileModel.FORMAT).toString());
            resPic.setPicType(mes);

            this.saveSystemInfo(resPic,loginUser);

            //保存
            id = this.insertSelective(resPic);
        }

        return id;
    }

    /**
     * 自动存储系统字段
     * @param pic 图片
     * @param loginUser 当前登录用户
     */
    public void saveSystemInfo(ResPic pic, LoginUser loginUser) {
        if(pic != null){
            //新增
            if(pic.getId() == null){
                pic.setGmtCreate(new Date());
                pic.setCreator(loginUser.getLoginName());
                pic.setIsDeleted(0);
                if(pic.getSysCode()==null || "".equals(pic.getSysCode())){
                    pic.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                    pic.setPicCode(pic.getPicCode());
                }
            }
            //修改
            pic.setGmtModified(new Date());
            pic.setModifier(loginUser.getLoginName());
        }
    }

    @Override
    public int batchUpdateBusinessId(ResPicBusinessUpdate businessUpdate) {
        return resPicMapper.batchUpdateBusinessId(businessUpdate);
    }

    @Override
    public List<ResPicPO> findByIds(List<Integer> idList) {
        return resPicMapper.findByIds(idList);
    }

    @Override
    public List<com.sandu.system.model.ResPic> getResPicByIds(List<Integer> ids) {
        if (ids == null || ids.size() == 0) {
            return Collections.emptyList();
        }

        return resPicMapper.getResPicByIds(ids);
    }

    @Override
    public com.sandu.system.model.ResPic get(Integer defaultPicId) {
        return resPicMapper.get(defaultPicId);
    }

    @Override
    public Map<Long, String> mapPic(List<Long> listPhoto) {
        if (listPhoto == null || listPhoto.isEmpty()) {
            return new HashMap<>(0);
        }

        List<ResPic> listResPic = resPicMapper.listResPic(listPhoto);
        if (!listResPic.isEmpty()) {
            return listResPic.stream().collect(Collectors.toMap(ResPic::getId, ResPic::getPicPath, (p, n) -> p));
        }

        return new HashMap<>(0);
    }
}
