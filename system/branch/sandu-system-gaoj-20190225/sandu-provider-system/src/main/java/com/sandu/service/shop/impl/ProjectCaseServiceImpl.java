package com.sandu.service.shop.impl;

import com.sandu.api.file.model.ResFile;
import com.sandu.api.file.service.ResFileService;
import com.sandu.api.pic.model.ResPic;
import com.sandu.api.pic.model.po.ResPicBusinessUpdate;
import com.sandu.api.pic.service.ResPicService;
import com.sandu.api.shop.common.constant.ShopResTypeConstant;
import com.sandu.api.shop.input.ProjectCaseAdd;
import com.sandu.api.shop.input.ProjectCaseQuery;
import com.sandu.api.shop.input.ProjectCaseUpdate;
import com.sandu.api.shop.model.ProjectCase;
import com.sandu.api.shop.output.ProjectCaseDetailsVO;
import com.sandu.api.shop.output.ProjectCaseVO;
import com.sandu.api.shop.service.ProjectCaseService;
import com.sandu.commons.LoginUser;
import com.sandu.commons.Utils;
import com.sandu.commons.util.StringUtils;
import com.sandu.service.shop.dao.CompanyShopArticleDao;
import com.sandu.service.shop.dao.ProjectCaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 工程案例实现
 *
 * @auth xiaoxc
 * @data 2018-06-19
 */
@Service("projectCaseService")
public class ProjectCaseServiceImpl implements ProjectCaseService {

    private Logger logger = LoggerFactory.getLogger(ProjectCaseServiceImpl.class);
    @Value("${upload.base.path}")
    private String rootPath;
    @Value("${company.shop.caseFile.upload.path}")
    private String caseUpdateFilePath;
    @Autowired
    private ProjectCaseDao projectCaseDao;
    @Autowired
    private ResFileService resFileService;
    @Autowired
    private ResPicService resPicService;
    @Autowired
    private CompanyShopArticleDao articleDao;

    @Override
    public int add(ProjectCaseAdd caseAdd, LoginUser loginUser) {
        ProjectCase projectCase = caseAdd.getProjectCaseFromProjectCaseAdd(caseAdd);
        String content = projectCase.getContent();
        // 存储富文本文件
        if (StringUtils.isNotEmpty(content)) {
            Integer resId = resFileService.saveFile(caseUpdateFilePath, content, loginUser, null,
                    ShopResTypeConstant.SHOP_RES_CASE_FILE_KEY, ShopResTypeConstant.SHOP_RES_CASE_FILE_TYPE);
            if (resId == null || resId == 0) {
                logger.error("ProjectCaseServiceImpl.add{}:保存配置文件失败");
                return 0;
            } else {
                projectCase.setFileId(BigInteger.valueOf(resId));
            }
        }
        //将富文本中所有图片Id都保存
        if (StringUtils.isNotEmpty(projectCase.getPicIds())) {
            projectCase.setPicIds(projectCase.getPicIds());
        }

        // 设置系统字段值
        saveSystemInfo(projectCase, loginUser);
        // 插入数据库
        int addResult = projectCaseDao.insert(projectCase);
        boolean flag = false;
        if (addResult > 0) {
            // 回填资源业务ID
            flag = backfill(projectCase);
            if (caseAdd.getShopType() == null || caseAdd.getShopType() == 2) {
                Integer mainShopId = articleDao.getMainCompanyShopId(caseAdd.getShopId());
                if (mainShopId != null && mainShopId != 0) {
                    projectCase.setShopId(mainShopId);
                    projectCase.setSid(projectCase.getId());
                    projectCaseDao.insert(projectCase);
                }
            }
        }
        if (flag) {
            return projectCase.getId();
        }
        return 0;
    }

    @Override
    public int delete(Integer id, LoginUser loginUser, Integer shopType) {
        ProjectCase projectCase = findById(id);
        //存储系统字段值
        saveSystemInfo(projectCase, loginUser);
        Integer res = projectCaseDao.delete(projectCase);
        if (res > 0) {
            if (shopType == null || shopType == 2) {
                Integer mainShopId = articleDao.getMainCompanyShopId(projectCase.getShopId());
                if (mainShopId != null && mainShopId != 0) {
                    projectCase.setShopId(mainShopId);
                    Integer mainCaseId = projectCaseDao.getMainShopCaseId(id, mainShopId);
                    if (mainCaseId != null && mainCaseId != 0) {
                        projectCase.setId(mainCaseId);
                        projectCaseDao.delete(projectCase);
                    }
                }
            }
        }
        return res;
    }

    @Override
    public int batchDelByShopId(Integer shopId, LoginUser loginUser) {
        ProjectCase projectCase = new ProjectCase();
        projectCase.setShopId(shopId);
        projectCase.setModifier(loginUser.getLoginName());
        projectCase.setGmtModified(new Date());
        return projectCaseDao.batchDelByShopId(projectCase);
    }

    @Override
    public int update(ProjectCaseUpdate caseUpdate, LoginUser loginUser) {
        //未修改之前的值
        ProjectCase oldProjectCase = findById(caseUpdate.getCaseId());
        //修改的对象
        ProjectCase projectCase = caseUpdate.getProjectCaseFromProjectCaseUpdate(caseUpdate);
        // 更新富文本内容
        BigInteger oldResId = oldProjectCase.getFileId();
        String content = projectCase.getContent();
        if (oldResId == null || oldResId.intValue() == 0) {
            if (StringUtils.isNotEmpty(content)) {
                Integer resId = resFileService.saveFile(caseUpdateFilePath, content, loginUser, caseUpdate.getCaseId(),
                        ShopResTypeConstant.SHOP_RES_CASE_FILE_KEY, ShopResTypeConstant.SHOP_RES_CASE_FILE_TYPE);
                if (resId == null || resId == 0) {
                    logger.error("ProjectCaseServiceImpl.update{}:保存配置文件失败");
                    return 0;
                } else {
                    projectCase.setFileId(BigInteger.valueOf(resId));
                }
            }
        } else {
            boolean falg = resFileService.updateFile(oldResId.longValue(), content);
            if (!falg) {
                logger.error("ProjectCaseServiceImpl.resFileService.updateFile{}:更新配置文件失败");
                return 0;
            }
        }
        // 存储富文本内图片Ids
        if (StringUtils.isNotEmpty(projectCase.getPicIds())) {
            projectCase.setPicIds(projectCase.getPicIds());
        }else{
            projectCase.setPicIds(0+"");
        }
        projectCase.setGmtModified(new Date());
        // 插入数据库
        int updateResult = projectCaseDao.update(projectCase);
        if (updateResult > 0) {
            if (caseUpdate.getShopType() == null || caseUpdate.getShopType() == 2) {
                this.updateMainCase(projectCase);
            }
            return updateResult;
        }
        return 0;
    }

    @Override
    public int updateCase(ProjectCase projectCase, LoginUser loginUser, Integer shopType) {
        // 更新数据库
        projectCase.setGmtModified(new Date());
        projectCase.setModifier(loginUser.getLoginName());
        int updateResult = projectCaseDao.update(projectCase);
        if (updateResult > 0) {
            if (shopType == null || shopType == 2) {
                this.updateMainCase(projectCase);
            }
            return updateResult;
        }
        return 0;
    }

    @Override
    public ProjectCase findById(Integer id) {
        return projectCaseDao.findById(id);
    }

    @Override
    public ProjectCaseDetailsVO getDetails(Integer id) {
        ProjectCaseDetailsVO detailsVO = new ProjectCaseDetailsVO();
        ProjectCase projectCase = this.findById(id);
        logger.info("查询工程案例详情 ProjectCase:{}",projectCase);
        if (projectCase != null && projectCase.getId() != null) {
            detailsVO.setCaseId(projectCase.getId());
            detailsVO.setCaseTitle(projectCase.getCaseTitle());
            detailsVO.setPicIds(projectCase.getPicIds());
            if (projectCase.getFileId() != null && projectCase.getFileId().intValue() > 0) {
                ResFile resFile = resFileService.getById(projectCase.getFileId().longValue());
                String filePath = resFile != null ? resFile.getFilePath() : "";
//                detailsVO.setFilePath(filePath);
                if (StringUtils.isNotEmpty(filePath)) {
                    // 读取配置
                    String fileContext = Utils.getFileContext(rootPath + filePath);
                    detailsVO.setContent(fileContext);
                }
            }
            //处理图片地址
            if (StringUtils.isNotEmpty(projectCase.getPicIds())) {
                List<Integer> idList = Utils.getIntegerListFromStringList(projectCase.getPicIds());
                if (idList != null && 0 < idList.size()) {
                    List<ResPic> list=new ArrayList<>();
                    for (Integer picId:idList){
                        if (0!=picId){
                            ResPic resPic = resPicService.selectByPrimaryKey(Long.valueOf(picId));
                            logger.info("查询店铺博文列表  查询展示图片信息结果 ResPic:{}",resPic);
                            if (null!=resPic){
                                list.add(resPic);
                            }
                        }
                    }
                    detailsVO.setResPics(list);
                }
            }
        }
        return detailsVO;
    }

    @Override
    public int getCount(ProjectCaseQuery query) {
        return projectCaseDao.findCount(query);
    }

    @Override
    public List<ProjectCaseVO> findList(ProjectCaseQuery query) {
        List<ProjectCaseVO> list = projectCaseDao.findList(query);
        for (ProjectCaseVO caseVO:list) {
            String picIds = caseVO.getPicIds();
            if (!"0".equals(picIds)){
                List<Integer> idList = Utils.getIntegerListFromStringList(caseVO.getPicIds());
                if (idList != null && 0 < idList.size()) {
                    ResPic resPic = resPicService.selectByPrimaryKey(Long.valueOf(idList.get(0)));
                    logger.info("查询工程案例列表  查询展示图片信息结果 ResPic:{}",resPic);
                    if (null!=resPic){
                        caseVO.setPicPath(resPic.getPicPath());
                    }
                }
            }
        }
        return list;
    }

    @Override
    public Integer deleteCaseByShopId(List<Integer> shopIds,String loginUserName) {
        return projectCaseDao.deleteCaseByShopId(shopIds,loginUserName);
    }

    @Override
    public Integer getCaseByShopId(List<Integer> shopIds) {
        return projectCaseDao.getCaseByShopId(shopIds);
    }

    @Override
    public Integer updateMainShopCaseStatus(Integer mainShopId, Integer shopId, Integer status) {
        return projectCaseDao.updateMainShopCaseStatus(mainShopId, shopId, status);
    }

    /**
     * 回填资源业务ID
     *
     * @param companyShop
     * @return
     */
    @SuppressWarnings("all")
    private boolean backfill(ProjectCase projectCase) {
        BigInteger fileId = projectCase.getFileId();
        String picIds = projectCase.getPicIds();
        //富文本文件
        if (fileId != null && fileId.intValue() > 0) {
            ResFile resFile = new ResFile();
            resFile.setId(fileId.intValue());
            resFile.setBusinessId(projectCase.getId());
            try {
                resFileService.update(resFile);
            } catch (Exception e) {
                logger.error("backfill{}: caseId="+projectCase.getId()+";fileId="+fileId);
                return false;
            }
        }
        //富文本图片ids
        if (StringUtils.isNotEmpty(picIds)) {
            List<Integer> idList = Utils.getIntegerListFromStringList(picIds);
            if (idList != null && 0 < idList.size()) {
                ResPicBusinessUpdate resPic = new ResPicBusinessUpdate();
                resPic.setIdList(idList);
                resPic.setBusinessId(projectCase.getId());
                try {
                    resPicService.batchUpdateBusinessId(resPic);
                } catch (Exception e) {
                    logger.error("backfill{}: caseId="+projectCase.getId()+";picIds="+picIds);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 自动存储系统字段
     *
     * @param ProjectCase model对象
     * @param loginUser   当前用户对象
     */
    @SuppressWarnings("all")
    private void saveSystemInfo(ProjectCase projectCase, LoginUser loginUser) {
        if (projectCase != null) {
            //新增
            if (projectCase.getId() == null) {
                projectCase.setGmtCreate(new Date());
                projectCase.setCreator(loginUser.getLoginName());
                projectCase.setIsDeleted(0);
                if (projectCase.getSysCode() == null || "".equals(projectCase.getSysCode())) {
                    projectCase.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                }
            }
            projectCase.setGmtModified(new Date());
            projectCase.setModifier(loginUser.getLoginName());
        }
    }

    private void updateMainCase(ProjectCase projectCase){
        ProjectCase old = projectCaseDao.get(projectCase.getId());
        Integer mainShopId = articleDao.getMainCompanyShopId(old.getShopId());
        if (mainShopId != null && mainShopId != 0) {
            projectCase.setShopId(mainShopId);
            Integer mainCaseId = projectCaseDao.getMainShopCaseId(projectCase.getId(), mainShopId);
            if (mainCaseId != null && mainCaseId != 0) {
                projectCase.setId(mainCaseId);
                projectCase.setShopId(mainShopId);
                projectCaseDao.update(projectCase);
            }
        }
    }
}
