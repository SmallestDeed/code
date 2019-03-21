package com.nork.repair.service.impl;

import com.google.gson.reflect.TypeToken;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.GsonUtil;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.product.model.BaseProduct;
import com.nork.product.service.BaseProductService;
import com.nork.repair.dao.RepairMapper;
import com.nork.repair.job.RepairModelJob;
import com.nork.repair.job.RepairTextureJob;
import com.nork.repair.model.ModelAreaRel;
import com.nork.repair.model.ModelAreaTextureRel;
import com.nork.repair.model.SplitTexturesInfo;
import com.nork.repair.service.RepairService;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResTexture;
import com.nork.system.model.search.ResModelSearch;
import com.nork.system.model.search.ResTextureSearch;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResTextureService;
import com.nork.threadpool.ThreadPool;
import com.nork.threadpool.ThreadPoolManager;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("repairService")
@Slf4j
public class RepairServiceImpl implements RepairService{

    private static Logger logger = LoggerFactory.getLogger(RepairServiceImpl.class);

    @Autowired
    private ThreadPoolManager threadPoolManager;
    @Autowired
    private ResModelService resModelService;
    @Autowired
    private ResTextureService resTextureService;
    @Autowired
    private BaseProductService baseProductService;
    @Autowired
    private ResPicService resPicService;
    @Autowired
    private RepairMapper repairMapper;

    @Override
    public void repairModel(Integer sourceCompanyId, Integer sourceBrandId, Integer targetCompanyId){
        logger.error("sourceCompanyId={}, targetCompanyId={}",sourceCompanyId, targetCompanyId);
        if( sourceCompanyId == null){
            return;
        }
        logger.error("开始处理模型数据");
        ResModelSearch modelSearch = new ResModelSearch();
        modelSearch.setCompanyId(sourceCompanyId);
        modelSearch.setIsDeleted(0);
        modelSearch.setFileKey("product.baseProduct.u3dmodel.windowsPc");
        modelSearch.setTransStatus("SUCCESS");
        Integer totalCount = resModelService.getCount(modelSearch);
        logger.error("总共需要处理{}条数据", totalCount);
        if( totalCount < 1 ){
            return;
        }

        ThreadPool threadPool = threadPoolManager.getThreadPool();
        List<ResModel> modelList = resModelService.getList(modelSearch);
        for( ResModel resModel : modelList ){
            RepairModelJob repairModelJob = new RepairModelJob(resModel, sourceCompanyId, sourceBrandId, targetCompanyId);
            threadPool.submit(repairModelJob);
        }
    }

    @Override
    public void repairModelDo(ResModel resModel, Integer sourceCompanyId, Integer targetCompanyId, Integer sourceBrandId){
        Integer sourceModelId = resModel.getId();
        logger.error("开始处理模型：{};产品ID：{}", sourceModelId, resModel.getProductId());
        // 之前使用此模型的产品
        Integer productId = resModel.getProductId();

        // 赋值
        resModel.setCompanyId(targetCompanyId);
        resModel.setCreator("sys");
        resModel.setGmtCreate(new Date());
        resModel.setModifier("sys");
        resModel.setGmtModified(new Date());
        resModel.setRemark("[20180817-repair:copy from " +  resModel.getId() + " ]");

        // 需要清空的值
        resModel.setId(null);

        // 需要复制的文件
        String modelPath = resModel.getModelPath();// 模型文件
        if( StringUtils.isNotEmpty(modelPath) ) {
            logger.error("{}:模型路径：{}", sourceModelId, modelPath);
            String modelFileSuffix = ".assetbundle";
            String newModelFileName = Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6) + modelFileSuffix;
            String targetModelPath = modelPath.substring(0, modelPath.lastIndexOf("/") + 1) + newModelFileName;
            logger.error("{}:复制模型到：{}", sourceModelId, targetModelPath);
            FileUploadUtils.fileCopy(Utils.getAbsolutePath(modelPath, null), Utils.getAbsolutePath(targetModelPath, null));
            resModel.setModelPath(targetModelPath);
            resModel.setModelFileName(newModelFileName);
        }

        String thumbPicPath = resModel.getThumbPicPath();// 缩略图文件
        if( StringUtils.isNotEmpty(thumbPicPath) ) {
            logger.error("{}:模型缩略图路径：{}", sourceModelId, thumbPicPath);
            String picFileSuffix = ".jpg";
            String newPicFileName = Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6) + picFileSuffix;
            String targetThumbPicPath = thumbPicPath.substring(0, thumbPicPath.lastIndexOf("/") + 1) + newPicFileName;
            logger.error("{}:复制模型缩略图到：{}", sourceModelId, targetThumbPicPath);
            FileUploadUtils.fileCopy(Utils.getAbsolutePath(thumbPicPath, null), Utils.getAbsolutePath(targetThumbPicPath, null));
            resModel.setThumbPicPath(targetThumbPicPath);
        }

        // 保存到数据库
        resModelService.add(resModel);
        updateModelCode(resModel.getId());
        logger.error("{}:复制模型数据(resModel)成功，id={}", sourceModelId, resModel.getId());

        // 多维材质模型处理
        logger.error("{}:多材质模型处理", sourceModelId);
        repairMultipartModel(productId, sourceModelId, resModel.getId(), sourceCompanyId, targetCompanyId);

        // 如果模型之前被伯克利品牌的产品用到过，则需要使用最新的模型
        logger.error("{}:处理使用此模型的产品", sourceModelId);
        updateProductModelId(productId, sourceBrandId, resModel.getId(), sourceModelId);

    }

    public Integer updateProductModelId(Integer productId, Integer sourceBrandId, Integer newModelId, Integer oldModelId){
        logger.error("{}:处理使用此模型的产品{}.{}", oldModelId, productId, (productId != null && productId.intValue() > 0));
        if( productId != null && productId.intValue() > 0 ) {
            logger.error("{}:updateProductModelId productId={},sourceBrandId={},newModelId={},oldModelId={}", oldModelId, productId, sourceBrandId, newModelId, oldModelId);
            BaseProduct baseProduct = baseProductService.get(productId);
            Integer productBrandId = baseProduct.getBrandId();
            if( productBrandId != null && productBrandId.intValue() == sourceBrandId.intValue() ){
                BaseProduct productUpdate = new BaseProduct();
                productUpdate.setId(baseProduct.getId());
                productUpdate.setWindowsU3dModelId(newModelId);

                // 释放以前的老模型
                ResModel modelUpdate = new ResModel();
                modelUpdate.setId(oldModelId);
                modelUpdate.setProductId(0);
                resModelService.update(modelUpdate);
                baseProductService.update(productUpdate);
                logger.error("{}:释放老模型关联的productId", oldModelId);
                return 1;
            }else{
                ResModel modelUpdate = new ResModel();
                modelUpdate.setId(newModelId);
                modelUpdate.setProductId(0);
                resModelService.update(modelUpdate);
                logger.error("{}:清空新模型的productId", oldModelId);
                return 1;
            }
        }
        return 0;
    }

    /**
     * 多维材质模型处理
     * @param oldModelId
     */
    public void repairMultipartModel(Integer productId, Integer oldModelId, Integer newModelId, Integer sourceCompanyId, Integer targetCompanyId){
        if( oldModelId != null ){
            logger.error("{}:开始处理模型多材质", oldModelId);
            List<ModelAreaRel> oldModelAreaRels = repairMapper.getModelAreaRelList(oldModelId);
            if( oldModelAreaRels == null && oldModelAreaRels.size() == 0 ){
                logger.error("{}:模型没有多维材质数据需要处理", oldModelId);
                return;
            }
            logger.error("{}:需要处理 {} 条数据", oldModelId, oldModelAreaRels.size());

            /*String splitTextureInfo = "";
            List<SplitTexturesInfo> splitTextureInfos = new ArrayList<>();
            SplitTexturesInfo splitTexturesInfo = null;*/
            List<ModelAreaTextureRel> modelAreaTextureRels = null;
            for( ModelAreaRel modelAreaRel : oldModelAreaRels ){
                modelAreaTextureRels = repairMapper.getAreaTextureList(modelAreaRel.getId());
                // 有些数据有模型区域，但是没有关联材质信息。这种数据不处理
                if( modelAreaTextureRels == null || modelAreaTextureRels.size() == 0 ){
                    logger.error("{}:模型的区域 {} 有 0 条材质数据", oldModelId, modelAreaRel.getCode());
                    continue;
                }
                logger.error("{}:模型的区域 {} 有 {} 条材质数据", oldModelId, modelAreaRel.getCode(), modelAreaTextureRels.size());

                /*splitTexturesInfo = new SplitTexturesInfo();*/

                modelAreaRel.setModelId(newModelId);
                modelAreaRel.setGmtCreate(new Date());
                modelAreaRel.setGmtModified(new Date());
                modelAreaRel.setCreator("-1");
                modelAreaRel.setId(null);
                repairMapper.insertModelAreaRelSelective(modelAreaRel);

                for( ModelAreaTextureRel modelAreaTextureRel : modelAreaTextureRels ){
                    Integer textureId = modelAreaTextureRel.getTextureId();
                    ResTexture resTexture = resTextureService.get(textureId);
                    // 没有材质数据的不需要处理
                    Integer newTextureId = textureId;
                    if( resTexture != null && resTexture.getCompanyId() != null ){
                        if( resTexture.getCompanyId().intValue() == sourceCompanyId.intValue() ){
                            // 复制材质
                            newTextureId = repairTextureDo(resTexture, targetCompanyId);
                        }
                    }
                    modelAreaTextureRel.setId(null);
                    modelAreaTextureRel.setTextureId(newTextureId);
                    modelAreaTextureRel.setGmtCreate(new Date());
                    modelAreaTextureRel.setGmtModified(new Date());
                    modelAreaTextureRel.setCreator("sys");
                    modelAreaTextureRel.setAreaId(modelAreaRel.getId().toString());
                    logger.error("{}:add modelAreaTextureRel={}", oldModelId, modelAreaTextureRel.toString());
                    repairMapper.insertModelAreaTextureRelSelective(modelAreaTextureRel);
                }
            }

            // 重新组装splitTextureInfo
            /*BaseProduct baseProduct = baseProductService.get(productId);
            if( baseProduct != null && StringUtils.isNotEmpty(baseProduct.getSplitTexturesInfo()) ){
                List<SplitTexturesInfo> splitTexturesInfos = GsonUtil.json2Bean(baseProduct.getSplitTexturesInfo(), new TypeToken<List<SplitTexturesInfo>>(){}.getType());
                List<ModelAreaRel> newModelAreaRels = repairMapper.getModelAreaRelList(newModelId);
            }*/
        }
    }

    @Override
    public void repairTexture(Integer sourceCompanyId, Integer targetCompanyId) {
        logger.error("sourceCompanyId={}, targetCompanyId={}",sourceCompanyId, targetCompanyId);
        if( sourceCompanyId == null){
            return;
        }
        logger.error("开始处理材质数据");
        ResTextureSearch textureSearch = new ResTextureSearch();
        textureSearch.setCompanyId(sourceCompanyId);
        textureSearch.setIsDeleted(0);
        Integer totalCount = resTextureService.getCount(textureSearch);
        logger.error("总共需要处理{}条数据", totalCount);
        if( totalCount < 1 ){
            return;
        }

        ThreadPool threadPool = threadPoolManager.getThreadPool();
        List<ResTexture> textureList = resTextureService.getList(textureSearch);
        for( ResTexture resTexture : textureList ){
            RepairTextureJob repairModelJob = new RepairTextureJob(resTexture, targetCompanyId);
            threadPool.submit(repairModelJob);
        }
    }

    @Override
    public Integer repairTextureDo(ResTexture resTexture, Integer targetCompanyId){
        Integer newResTextureId = 0;
        Integer sourceTextureId = resTexture.getId();
        logger.error("开始处理材质：{};材质图片：{};材质球模型：{}", sourceTextureId, resTexture.getPicId(), resTexture.getTextureBallFileId());
        logger.error("{}isProcessed={}", sourceTextureId, isProcessed(sourceTextureId));
        if( isProcessed(sourceTextureId).intValue() > 0 ){
            logger.error("{}:材质已在处理模型多维材质时处理", sourceTextureId);
            return 0;
        }
        // 需要复制的文件
        Integer picId = resTexture.getPicId();
        ResPic resPic = null;
        if( picId != null ) {
            resPic = resPicService.get(picId);
            if (resPic != null) {
                logger.error("{}:材质图片路径：{}", sourceTextureId, resPic.getPicPath());
                String picPath = resPic.getPicPath();// 材质图片文件
                String picFileSuffix = ".jpg";
                String newPicFileName = Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6) + picFileSuffix;
                String targetPicPath = picPath.substring(0, picPath.lastIndexOf("/") + 1) + newPicFileName;
                logger.error("{}:复制材质图片到：{}", sourceTextureId, targetPicPath);
                FileUploadUtils.fileCopy(Utils.getAbsolutePath(picPath, null), Utils.getAbsolutePath(targetPicPath, null));

                resPic.setCreator("sys");
                resPic.setGmtCreate(new Date());
                resPic.setModifier("sys");
                resPic.setGmtModified(new Date());
                resPic.setRemark("[20180817-repair:copy from " + resPic.getId() + " ]");
                resPic.setPicFileName(newPicFileName.substring(0, newPicFileName.lastIndexOf(".")));
                resPic.setPicPath(targetPicPath);
                resPic.setId(null);

                resPicService.add(resPic);
                updatePicCode(resPic.getId());
                logger.error("{}:复制材质图片资源数据(resPic)成功，id={}", sourceTextureId, resPic.getId());
            }
        }
        Integer textureBallFileId = resTexture.getTextureBallFileId();
        ResModel resModel = null;
        if( textureBallFileId != null ) {
            resModel = resModelService.get(textureBallFileId);
            if (resModel != null) {
                logger.error("{}:材质图片路径：{}", sourceTextureId, resModel.getModelPath());
                String modelPath = resModel.getModelPath();// 模型文件
                String modelFileSuffix = ".assetbundle";
                String newModelFileName = Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6) + modelFileSuffix;
                String targetModelPath = modelPath.substring(0, modelPath.lastIndexOf("/") + 1) + newModelFileName;
                logger.error("{}:复制材质球模型到：{}", sourceTextureId, targetModelPath);
                FileUploadUtils.fileCopy(Utils.getAbsolutePath(modelPath, null), Utils.getAbsolutePath(targetModelPath, null));

                resModel.setCreator("sys");
                resModel.setGmtCreate(new Date());
                resModel.setModifier("sys");
                resModel.setGmtModified(new Date());
                resModel.setRemark("[20180817-repair:copy from " + resModel.getId() + " ]");
                resModel.setModelPath(targetModelPath);
                if( resPic != null ) {
                    resModel.setThumbPicPath(resPic.getPicPath());
                }
                resModel.setCompanyId(targetCompanyId);
                resModel.setId(null);

                resModelService.add(resModel);
                updateModelCode(resModel.getId());
                logger.error("{}:复制材质球模型资源数据(resModel)成功，id={}", sourceTextureId, resModel.getId());
            }
        }

        // 赋值
        resTexture.setCompanyId(targetCompanyId);
        resTexture.setCreator("sys");
        resTexture.setGmtCreate(new Date());
        resTexture.setModifier("sys");
        resTexture.setGmtModified(new Date());
        resTexture.setRemark("[20180817-repair:copy from " +  resTexture.getId() + " ]");

        // 新图片
        if( resPic != null ) {
            resTexture.setPicId(resPic.getId());
            resTexture.setFilePath(resPic.getPicPath());
        }

        // 新材质球模型
        if( resModel != null ){
            resTexture.setTextureBallFileId(resModel.getId());
        }

        // 需要清空的值
        resTexture.setId(null);

        resTextureService.add(resTexture);
        updateTextureCode(resTexture.getId());
        logger.error("{}:复制材质数据(resTexture)成功，id={}", sourceTextureId, resTexture.getId());
        newResTextureId = resTexture.getId();
        return newResTextureId;
    }

    public Integer isProcessed(Integer resTextureId){
        ResTextureSearch search = new ResTextureSearch();
        search.setSchRemark_("copy from " + resTextureId);
        return resTextureService.getCount(search);
    }

    public void updateModelCode(Integer modelId){
        String str = String.format("%010d", modelId);
        ResModel resModel = new ResModel();
        resModel.setId(modelId);
        resModel.setModelCode("M"+str);
        resModelService.update(resModel);
    }

    public void updatePicCode(Integer picId){
        String str = String.format("%010d", picId);
        ResPic resPic = new ResPic();
        resPic.setId(picId);
        resPic.setPicCode("P"+str);
        resPicService.update(resPic);
    }

    public void updateTextureCode(Integer textureId){
        String str = String.format("%010d", textureId);
        ResTexture resTexture = new ResTexture();
        resTexture.setId(textureId);
        resTexture.setFileCode("C"+str);
        resTextureService.update(resTexture);
    }
}
