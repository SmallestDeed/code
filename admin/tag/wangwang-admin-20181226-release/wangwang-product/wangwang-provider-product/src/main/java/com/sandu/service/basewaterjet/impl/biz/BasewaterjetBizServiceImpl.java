package com.sandu.service.basewaterjet.impl.biz;

import com.google.common.base.Strings;
import com.sandu.api.basewaterjet.input.BasewaterjetAdd;
import com.sandu.api.basewaterjet.input.BasewaterjetQuery;
import com.sandu.api.basewaterjet.input.BasewaterjetUpdate;
import com.sandu.api.basewaterjet.model.Basewaterjet;
import com.sandu.api.basewaterjet.output.BrandNameVO;
import com.sandu.api.basewaterjet.service.BasewaterjetService;
import com.sandu.api.basewaterjet.service.biz.BasewaterjetBizService;
import com.sandu.api.company.common.FileModel;
import com.sandu.api.company.common.FileUtils;
import com.sandu.api.company.common.StringUtil;
import com.sandu.api.groupproducct.model.ResFile;
import com.sandu.api.storage.model.ResPic;
import com.sandu.api.storage.service.ResPicService;
import com.sandu.common.exception.BizException;
import com.sandu.commons.LoginUser;
import com.sandu.commons.Utils;
import com.sandu.service.basewaterjet.dao.BasewaterjetMapper;
import com.sandu.service.groupproduct.dao.ResFileDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.sandu.util.CodeUtil.formatCode;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * base_waterjet
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-09 10:03
 */
@Slf4j
@Service("basewaterjetBizService")
public class BasewaterjetBizServiceImpl implements BasewaterjetBizService {

    @Autowired
    private BasewaterjetService basewaterjetService;

    @Autowired
    private BasewaterjetMapper basewaterjetMapper;

    @Autowired
    private ResPicService resPicService;

    @Autowired
    private ResFileDao resFileDao;

    @Override
    public int create(BasewaterjetAdd input,LoginUser loginUser) {
        Basewaterjet.BasewaterjetBuilder builder = Basewaterjet.builder();
        Basewaterjet basewaterjet = builder.build();

        BeanUtils.copyProperties(input, basewaterjet);
        systemInfo(basewaterjet,loginUser);

        basewaterjetService.insert(basewaterjet);
        if(basewaterjet.getId() > 0){
            Basewaterjet updateObj = new Basewaterjet();
            updateObj.setId(basewaterjet.getId());
            updateObj.setTemplateCode(formatCode("W", basewaterjet.getId() - 1 +""));
            updateObj.setType("add");
            basewaterjetService.update(updateObj);

            //回填businessId
            this.setBusinessId(basewaterjet.getId());
        }

        return basewaterjet.getId();
    }

    //回填businessId
    private void setBusinessId(Integer id){
        Basewaterjet basewaterjetBu = this.getById(id);
        if(null != basewaterjetBu.getTemplatePicId()){

            //回填图片
            ResPic resPic = resPicService.getResPicDetail(new Long(basewaterjetBu.getTemplatePicId()));
            resPic.setBusinessId(basewaterjetBu.getId());
            resPicService.saveResPic(resPic);
            if(StringUtils.isNotBlank(resPic.getSmallPicInfo())){
                //web:7611230;ipad:7611229;
                String[] webpads = resPic.getSmallPicInfo().split(";");
                if(null != webpads && webpads.length > 0){
                    for (int i = 0 ; i < webpads.length ; i++){
                        String[] ids = webpads[i].split(":");
                        if(null != ids && ids.length > 1){
                            ResPic resPicSmall = resPicService.getResPicDetail(new Long(ids[1]));
                            resPicSmall.setBusinessId(basewaterjetBu.getId());
                            resPicService.saveResPic(resPicSmall);
                        }
                    }
                }
            }

            //回填CAD文件
            if(null != basewaterjetBu.getCadSourceFileId()){
                ResFile resFile = resFileDao.selectByPrimaryKey(new Long(basewaterjetBu.getCadSourceFileId()));
                if(null != resFile){
                    resFile.setBusinessId(basewaterjetBu.getId().intValue());
                    resFileDao.updateByPrimaryKeySelective(resFile);
                }
            }

            //回填水刀介绍文件businessId
            if(StringUtils.isNotBlank(basewaterjetBu.getTemplateDescribe())){
                ResFile resFile = resFileDao.selectByPrimaryKey(new Long(basewaterjetBu.getTemplateDescribe()));
                if(null != resFile){
                    resFile.setBusinessId(basewaterjetBu.getId());
                    resFileDao.updateByPrimaryKeySelective(resFile);
                }
            }
        }
    }

    @Override
    public int update(BasewaterjetUpdate input,LoginUser loginUser) {
        Basewaterjet.BasewaterjetBuilder builder = Basewaterjet.builder();
        Basewaterjet basewaterjet = builder.build();

        BeanUtils.copyProperties(input, basewaterjet);
        basewaterjet.setId(input.getBasewaterjetId());

        systemInfo(basewaterjet,loginUser);

        basewaterjet.setType("edit");
        int count = basewaterjetService.update(basewaterjet);
        if(count > 0){
            //回填businessId
            this.setBusinessId(basewaterjet.getId());
        }
        return  count;
    }

    @Override
    public int delete(String basewaterjetId,LoginUser loginUser) {
        if (Strings.isNullOrEmpty(basewaterjetId))
            throw new BizException("参数不能为空");

        Set<Integer> basewaterjetIds = new HashSet<>();
        List<String> list = Arrays.stream(basewaterjetId.split(",")).collect(Collectors.toList());
        list.stream().forEach(id -> basewaterjetIds.add(Integer.valueOf(id)));

        return basewaterjetService.delete(basewaterjetIds,loginUser.getLoginName());
    }

    @Override
    public Basewaterjet getById(int basewaterjetId) {
        Basewaterjet basewaterjet = basewaterjetService.getById(basewaterjetId);
        return basewaterjet;
    }

    @Override
    public List<Basewaterjet> query(BasewaterjetQuery query) {
        query.setPage( (query.getPage() > 0 ? query.getPage()-1 : 0)*query.getLimit());
        List<Basewaterjet> basewaterjetList =  basewaterjetService.findAll(query);
        return basewaterjetList;
    }
    @Override
    public int queryCount(BasewaterjetQuery query) {
        return basewaterjetService.findAllCount(query);
    }

    /**
     * 获取当前所属品牌
     * @param userId 用户id
     * */
    @Override
    public List<BrandNameVO> getBrandNameList(Integer userId,LoginUser loginUser){
        List<BrandNameVO>  brandNameVOList = basewaterjetMapper.selectBrandNameList(userId,loginUser.getUserType());
        return brandNameVOList;
    }

    /**
     * 上下架
     * @author chenqiang
     * @param basewaterjetIds
     * @return
     * @date 2018/11/10 0010 16:20
     */
    public int upperandlowerstatus(String basewaterjetIds,Integer templateStatus,LoginUser loginUser){
        if (Strings.isNullOrEmpty(basewaterjetIds) || null == templateStatus)
            throw new BizException("参数不能为空");

        Set<Integer> idList = new HashSet<>();
        List<String> list = Arrays.stream(basewaterjetIds.split(",")).collect(Collectors.toList());
        list.stream().forEach(id -> idList.add(Integer.valueOf(id)));

        return basewaterjetMapper.upperandlowerstatus(idList,templateStatus,loginUser.getLoginName());
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
            Long idl = resPicService.addResPic(resPic);
            if(idl > 0)
                id = idl.intValue();
        }

        return id;
    }

    /**
     * 将文件上传信息保存到resfile
     * @author chenqiang
     * @param map 上传服务器文件的详细信息
     * @param businessId 该资源所关联的表id
     * @param loginUser 登录名
     * @return 新增数据主键id
     */
    public Integer saveUploadFile(Map map, Integer businessId, LoginUser loginUser, String mes){
        Integer id = null;
        if( map != null && map.size() > 0 ) {
            ResFile resFile = new ResFile();

            //设置数据
            String dbFilePath = map.get(FileUtils.DB_FILE_PATH).toString();
            String fileName = map.get(FileModel.FILE_NAME).toString();
            resFile.setIsDeleted(0);
            resFile.setFileName(fileName);//文件名
            resFile.setFileOriginalName(fileName);
            resFile.setFileType(mes);
            resFile.setFileSize(map.get(FileModel.FILE_SIZE).toString());
            resFile.setFileSuffix(map.get(FileModel.FILE_SUFFIX).toString());
            resFile.setFileLevel("0");
            resFile.setFilePath(dbFilePath);
            resFile.setFileOrdering(0);
            resFile.setFileKey(map.get(FileModel.FILE_KEY).toString());
            resFile.setBusinessId(businessId);//该资源所关联的表id

            this.saveSystemInfo(resFile,loginUser);

            //保存
            resFileDao.insertSelective(resFile);
            if(resFile.getId() > 0)
                id = resFile.getId().intValue();
        }

        return id;
    }

    private void systemInfo(Basewaterjet basewaterjet,LoginUser loginUser){

        if(basewaterjet.getId() == null){
            basewaterjet.setTemplateCode("W");
            basewaterjet.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
            basewaterjet.setCreator(loginUser.getLoginName());
            basewaterjet.setGmtCreate(new Date());
        }

        basewaterjet.setModifier(loginUser.getLoginName());
        basewaterjet.setGmtModified(new Date());
    }
    public void saveSystemInfo(ResPic pic, LoginUser loginUser) {
        if(pic != null){
            //新增
            if(pic.getId() == null){
                pic.setGmtCreate(new Date());
                pic.setCreator(loginUser.getLoginName());
                pic.setIsDeleted(0);
                if(pic.getSysCode()==null || "".equals(pic.getSysCode())){
                    pic.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                    pic.setPicCode(pic.getSysCode());
                }
            }
            //修改
            pic.setGmtModified(new Date());
            pic.setModifier(loginUser.getLoginName());
        }
    }
    public void saveSystemInfo(ResFile file, LoginUser loginUser) {
        if(file != null){
            //新增
            if(file.getId() == null){
                file.setGmtCreate(new Date());
                file.setCreator(loginUser.getLoginName());
                file.setIsDeleted(0);
                if(file.getSysCode()==null || "".equals(file.getSysCode())){
                    file.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                    file.setFileCode(file.getSysCode());
                }
            }
            //修改
            file.setGmtModified(new Date());
            file.setModifier(loginUser.getLoginName());
        }
    }
}
