package com.sandu.service.task.refresh.service.impl;

import com.google.gson.Gson;
import com.sandu.api.house.model.DesignTempletJumpPositionRel;
import com.sandu.api.task.refresh.model.*;
import com.sandu.api.task.refresh.service.RefreshDoorPositionService;
import com.sandu.service.task.refresh.dao.RefreshDoorPositionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 5:54 2018/7/5 0005
 * @Modified By:
 */
@Slf4j
@Service("refreshDoorPositionService")
public class RefreshDoorPositionServiceImpl implements RefreshDoorPositionService {

    private static final String CLASS_LOG_PREFIX = "[修复门数据服务]";
    private static final Gson GSON = new Gson();
    @Value("${resource.domain}")
    private String resourceDomain;
    @Autowired
    private RefreshDoorPositionMapper refreshDoorPositionMapper;

    @Override
    public boolean refreshDoorPosition() {
        long beginTime = System.currentTimeMillis();

        //获取所有门的配置文件路径及样板房id等信息
        List<FilePathBO> filePathList = null;
        try {
//            filePathList = refreshDoorPositionMapper.getAllFilePath();

        } catch (Exception e) {
            log.error(CLASS_LOG_PREFIX + " ===> exception:{}", e);
        }
        if (filePathList == null || filePathList.size() <= 0) {
            log.warn(CLASS_LOG_PREFIX + " ===> filePathList is empty !");
            return false;
        }
        log.info(CLASS_LOG_PREFIX + " ===> filePathList finish !  size:{}", filePathList.size());

        StringBuffer sb;
        List<DesignTempletJumpPositionRel> positionRelList = new ArrayList<>();

        for (FilePathBO filePathBO : filePathList) {

            try {
                //读取配置文件内容
                String dbPath = filePathBO.getFilePath();
                String fileUrl = resourceDomain + dbPath;
                URL url = new URL(fileUrl);
                InputStream inputStream = url.openConnection().getInputStream();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String readLine = null;
                sb = new StringBuffer();
                while ((readLine = bufferedReader.readLine()) != null) {
                    sb.append(readLine);
                }

                //获取到配置文件里的json数据，解析
                if (sb.length() > 0) {
                    String jsonStr = sb.toString();
                    FileJsonModel fileJsonModel = GSON.fromJson(jsonStr, FileJsonModel.class);
                    if (fileJsonModel == null) {
                        log.warn(CLASS_LOG_PREFIX + " ===> fileJsonModel is null ! fileId:{}", filePathBO.getFileId());
                        continue;
                    }

                    //获取中心位置坐标
                    Position centerPosition = fileJsonModel.getCenterPosition();
                    BigDecimal centerDecimalX = new BigDecimal(centerPosition.getX());
                    BigDecimal centerDecimalY = new BigDecimal(centerPosition.getY());
                    BigDecimal centerDecimalZ = new BigDecimal(centerPosition.getZ());

                    //参数初始化
                    DesignTempletJumpPositionRel positionRel;
                    JumpPosition jumpPosition;
                    Position position;
                    Position rotation;
                    Position modelPositon;

                    //获取房间模型数据集合
                    List<RoomModel> roomModelsDataList = fileJsonModel.getRoomModelsDataList();
                    for (RoomModel roomModel : roomModelsDataList) {
                        //有相同的uniqueId就计算坐标值
                        if (filePathBO.getUniqueId().equals(roomModel.getUniqueId())) {

                            //获取模型位置坐标
                            modelPositon = roomModel.getModelPositon();
                            BigDecimal modelDecimalX = new BigDecimal(modelPositon.getX());
                            BigDecimal modelDecimalY = new BigDecimal(modelPositon.getY());
                            BigDecimal modelDecimalZ = new BigDecimal(modelPositon.getZ());

                            //计算坐标值,输出为普通计数，不是科学计数
                            position = new Position();
                            position.setX((modelDecimalX.subtract(centerDecimalX)).toPlainString());
                            position.setY((modelDecimalY.subtract(centerDecimalY)).toPlainString());
                            position.setZ((modelDecimalZ.subtract(centerDecimalZ)).toPlainString());

                            //格式化坐标值为普通计数
                            rotation = roomModel.getModelEulerAngles();
                            rotation.setX(new BigDecimal(rotation.getX()).toPlainString());
                            rotation.setY(new BigDecimal(rotation.getY()).toPlainString());
                            rotation.setZ(new BigDecimal(rotation.getZ()).toPlainString());

                            //封装参数
                            jumpPosition = new JumpPosition();
                            jumpPosition.setPosition(position);
                            jumpPosition.setRotation(rotation);
                            String jumpPositionStr = GSON.toJson(jumpPosition, JumpPosition.class);

                            Integer originTempletId = filePathBO.getTempletId();
                            Integer targetTempletId = refreshDoorPositionMapper.getTargetTempletId(roomModel.getUniqueId(), originTempletId);

                            if (targetTempletId == null || originTempletId == null || jumpPosition == null || jumpPositionStr.length() <= 0) {
                                log.warn(CLASS_LOG_PREFIX + " ===> insert params have null ! targetTempletId:{}," +
                                        "originTempletId:{},jumpPositionStr:{}", targetTempletId, originTempletId, jumpPositionStr);
                                continue;
                            }

                            //组合insert的数据
                            positionRel = new DesignTempletJumpPositionRel();
                            positionRel.setJumpPosition(jumpPositionStr);
                            positionRel.setOriginId(originTempletId.longValue());
                            positionRel.setTargetId(targetTempletId.longValue());
                            positionRel.setCreator("refreshDoorPosition");
                            positionRel.setModifier("refreshDoorPosition");
                            Date date = new Date();
                            positionRel.setGmtModified(date);
                            positionRel.setGmtCreate(date);
                            positionRel.setDrawDtpId(0L);
                            positionRel.setIsDeleted((short) 0);
                            positionRel.setRemark("批量添加门的位置信息成功");

                            positionRelList.add(positionRel);
                        }

                    }

                    //批量插入
                    if (positionRelList.size() >= 100) {
                        try {
                            refreshDoorPositionMapper.insertBatch(positionRelList);
                            List<Long> originIdList = positionRelList
                                                                .stream()
                                                                .map(DesignTempletJumpPositionRel::getOriginId)
                                                                .collect(Collectors.toList());
                            refreshDoorPositionMapper.updateBatch(originIdList);
                            positionRelList.clear();
                        } catch (Exception e) {
                            log.error(CLASS_LOG_PREFIX + " ===> insertBatch exception: {}", e);
                        }
                    }

                } else {
                    log.warn(CLASS_LOG_PREFIX + " ===> file is empty or read file fail ! fileId:{}", filePathBO.getFileId());
                    continue;
                }

            } catch (IOException e) {
                log.error(CLASS_LOG_PREFIX + " ===> IOException : {}", e);
                continue;

            } catch (Exception e) {
                log.error(CLASS_LOG_PREFIX + " ===> Exception : {}", e);
                continue;
            }
        }

        //批量插入
        if (positionRelList.size() > 0) {
            try {
                refreshDoorPositionMapper.insertBatch(positionRelList);
                List<Long> originIdList = positionRelList
                        .stream()
                        .map(DesignTempletJumpPositionRel::getOriginId)
                        .collect(Collectors.toList());
                refreshDoorPositionMapper.updateBatch(originIdList);
                positionRelList.clear();
            } catch (Exception e) {
                log.error(CLASS_LOG_PREFIX + " ===> insert batch exception: {}", e);
            }
        }

        long endTime = System.currentTimeMillis();
        log.info(CLASS_LOG_PREFIX + " ===> use time {}s", (endTime - beginTime) / 1000);

        return true;
    }


    @Override
    public boolean refreshErrorPosition() {
        long beginTime = System.currentTimeMillis();

        //获取所有门的配置文件路径及样板房id等信息
        List<FilePathBO> filePathList = null;
        try {
            filePathList = refreshDoorPositionMapper.getErrorFilePath();
        } catch (Exception e) {
            log.error(CLASS_LOG_PREFIX + " ===> exception:{}", e);
        }
        if (filePathList == null || filePathList.size() <= 0) {
            log.warn(CLASS_LOG_PREFIX + " ===> filePathList is empty !");
            return false;
        }
        log.info(CLASS_LOG_PREFIX + " ===> filePathList finish !  size:{}", filePathList.size());

        StringBuffer sb;
        List<DesignTempletJumpPositionRel> positionRelList = new ArrayList<>();

        for (FilePathBO filePathBO : filePathList) {

            try {
                //读取配置文件内容
                String dbPath = filePathBO.getFilePath();
                String fileUrl = resourceDomain + dbPath;
                URL url = new URL(fileUrl);
                InputStream inputStream = url.openConnection().getInputStream();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String readLine = null;
                sb = new StringBuffer();
                while ((readLine = bufferedReader.readLine()) != null) {
                    sb.append(readLine);
                }

                //获取到配置文件里的json数据，解析
                if (sb.length() > 0) {
                    String jsonStr = sb.toString();
                    FileJsonModel fileJsonModel = GSON.fromJson(jsonStr, FileJsonModel.class);
                    if (fileJsonModel == null) {
                        log.warn(CLASS_LOG_PREFIX + " ===> fileJsonModel is null ! fileId:{}", filePathBO.getFileId());
                        continue;
                    }

                    //获取中心位置坐标
                    Position centerPosition = fileJsonModel.getCenterPosition();
                    BigDecimal centerDecimalX = new BigDecimal(centerPosition.getX());
                    BigDecimal centerDecimalY = new BigDecimal(centerPosition.getY());
                    BigDecimal centerDecimalZ = new BigDecimal(centerPosition.getZ());

                    //参数初始化
                    DesignTempletJumpPositionRel positionRel;
                    JumpPosition jumpPosition;
                    Position position;
                    Position rotation;
                    Position modelPositon;

                    //获取房间模型数据集合
                    List<RoomModel> roomModelsDataList = fileJsonModel.getRoomModelsDataList();
                    for (RoomModel roomModel : roomModelsDataList) {
                        //有相同的uniqueId就计算坐标值
                        if (filePathBO.getUniqueId().equals(roomModel.getUniqueId())) {

                            //获取模型位置坐标
                            modelPositon = roomModel.getModelPositon();
                            BigDecimal modelDecimalX = new BigDecimal(modelPositon.getX());
                            BigDecimal modelDecimalY = new BigDecimal(modelPositon.getY());
                            BigDecimal modelDecimalZ = new BigDecimal(modelPositon.getZ());

                            //计算坐标值,输出为普通计数，不是科学计数
                            position = new Position();
                            position.setX((modelDecimalX.subtract(centerDecimalX)).toPlainString());
                            position.setY((modelDecimalY.subtract(centerDecimalY)).toPlainString());
                            position.setZ((modelDecimalZ.subtract(centerDecimalZ)).toPlainString());

                            //格式化坐标值为普通计数
                            rotation = roomModel.getModelEulerAngles();
                            rotation.setX(new BigDecimal(rotation.getX()).toPlainString());
                            rotation.setY(new BigDecimal(rotation.getY()).toPlainString());
                            rotation.setZ(new BigDecimal(rotation.getZ()).toPlainString());

                            //封装参数
                            jumpPosition = new JumpPosition();
                            jumpPosition.setPosition(position);
                            jumpPosition.setRotation(rotation);
                            String jumpPositionStr = GSON.toJson(jumpPosition, JumpPosition.class);

                            Integer originTempletId = filePathBO.getTempletId();
                            Integer targetTempletId = refreshDoorPositionMapper.getTargetTempletId(roomModel.getUniqueId(), originTempletId);

                            if (targetTempletId == null || originTempletId == null || jumpPosition == null || jumpPositionStr.length() <= 0) {
                                log.warn(CLASS_LOG_PREFIX + " ===> insert params have null ! targetTempletId:{}," +
                                        "originTempletId:{},jumpPositionStr:{}", targetTempletId, originTempletId, jumpPositionStr);
                                continue;
                            }


                            //组合insert的数据
                            positionRel = new DesignTempletJumpPositionRel();
                            positionRel.setJumpPosition(jumpPositionStr);
                            positionRel.setOriginId(originTempletId.longValue());
                            positionRel.setTargetId(targetTempletId.longValue());
                            //查询是否存在，存在则不添加
                            try {
                                int count = refreshDoorPositionMapper.selectInJumpPosition(positionRel);
                                if (count > 0) {
                                    log.info("数据已经存在，不继续插入：{}",positionRel);
                                    continue;
                                }
                            } catch (Exception e) {
                                log.error(CLASS_LOG_PREFIX+"====> 查询JumpPosition是否存在 exception:{}",e);
                                //查询是否存在抛异常的话就直接插入本条数据
                            }

                            positionRel.setCreator("refreshOtherPosition");
                            positionRel.setModifier("refreshOtherPosition");
                            Date date = new Date();
                            positionRel.setGmtModified(date);
                            positionRel.setGmtCreate(date);
                            positionRel.setDrawDtpId(0L);
                            positionRel.setIsDeleted((short) 0);
                            positionRel.setRemark("添加剩余门坐标位置");
                            //根据uniqueId和originId记录操作过的唯一数据
                            positionRel.setUniqueId(filePathBO.getUniqueId());


                            positionRelList.add(positionRel);

                        }

                    }

                    //批量插入及修改
                    if (positionRelList.size() >= 100) {
                        try {

                            insertAndUpdatebatch(positionRelList);

                        } catch (Exception e) {
                            log.error(CLASS_LOG_PREFIX + " ===> 批量插入和修改 exception: {}", e);
                        }
                    }

                } else {
                    log.warn(CLASS_LOG_PREFIX + " ===> file is empty or read file fail ! fileId:{}", filePathBO.getFileId());
                    continue;
                }

            } catch (IOException e) {
                log.error(CLASS_LOG_PREFIX + " ===> IOException : {}", e);
                continue;

            } catch (Exception e) {
                log.error(CLASS_LOG_PREFIX + " ===> Exception : {}", e);
                continue;
            }
        }

        //批量插入
        if (positionRelList.size() > 0) {
            try {

                insertAndUpdatebatch(positionRelList);

            } catch (Exception e) {
                log.error(CLASS_LOG_PREFIX + " ===> 批量插入和修改 exception: {}", e);
            }
        }

        long endTime = System.currentTimeMillis();
        log.info(CLASS_LOG_PREFIX + " ===> use time {}s", (endTime - beginTime) / 1000);

        return true;
    }



    @Transactional
    private void insertAndUpdatebatch(List<DesignTempletJumpPositionRel> positionRelList) {

        int insertCount = refreshDoorPositionMapper.insertBatch(positionRelList);
        log.info("批量插入的数量：{}，成功插入的数量：{}",positionRelList.size(),insertCount);

        int updateCount = refreshDoorPositionMapper.updateBatchByUniqueId(positionRelList);
        log.info("批量修改记录的数量：{}，成功修改记录的数量：{}",positionRelList.size(),insertCount);

        positionRelList.clear();

    }



















    public static void main(String[] args) {
        String jsonStr = "{\"roomId\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_\",\"roomModelsDataList\":[{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_qiangm_0001\",\"uniqueId\":\"a3f0175f-5b0f-4d98-a813-b043e0c4944a\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":1.50514936,\"y\":1.45,\"z\":3.620763},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":180.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_qiangm_0002\",\"uniqueId\":\"48091e09-ff6a-495a-a9ea-9925653ce353\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":3.16826868,\"y\":1.45,\"z\":1.14424109},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_qiangm_0003\",\"uniqueId\":\"aac8d776-0104-41d3-b3e2-d7996431ad47\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":-0.157970071,\"y\":1.45,\"z\":-0.609149933},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":90.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_qiangm_0004\",\"uniqueId\":\"69a62a75-054a-4ff8-afed-10f56005132b\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":-0.6726418,\"y\":1.45,\"z\":0.113981158},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":-2.504478E-06,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_qiangm_0005\",\"uniqueId\":\"517720d1-98aa-47a7-83e5-0e579e05aaad\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":-0.6726418,\"y\":1.45,\"z\":1.32139158},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":180.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_qiangm_0006\",\"uniqueId\":\"226787c1-2f56-40de-b7bf-8603e3ca1584\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":1.50514936,\"y\":1.45,\"z\":-1.332281},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":2.504478E-06,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_qiangm_0007\",\"uniqueId\":\"25a816bd-1356-47ba-a775-ec4490273708\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":-1.18731356,\"y\":1.45,\"z\":0.717686355},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":90.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_qiangm_0008\",\"uniqueId\":\"f44c19be-44d3-4192-9e12-5e98b3029e2e\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":-0.157970071,\"y\":1.45,\"z\":2.47107744},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":90.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_qiangm_0009\",\"uniqueId\":\"bf611983-53cf-4adf-9301-3d7e4ad82eb1\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":1.50514936,\"y\":1.45,\"z\":5.276619},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":180.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_qiangm_00010\",\"uniqueId\":\"a64f0495-2bca-4dfd-9d36-75b376f1886b\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":3.16826868,\"y\":1.45,\"z\":4.448691},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_qiangm_00011\",\"uniqueId\":\"ce66baae-45fe-4a46-bf16-f82863e8b5fa\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":-0.157970071,\"y\":1.45,\"z\":4.448691},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":90.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_001_qiangm_0001\",\"uniqueId\":\"a17e4d5f-80d6-4f25-8693-f944fee6fb93\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":2.86995888,\"y\":1.3499999,\"z\":3.55076337},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":179.999985,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_001_qiangm_0002\",\"uniqueId\":\"3d366347-fd0f-4972-9b0e-965eff9158cf\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":0.0878511444,\"y\":1.3499999,\"z\":3.550763},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":179.999985,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_001_qiangm_0003\",\"uniqueId\":\"469d35fa-5fe8-47ce-b74b-cde5ec306da7\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":3.09826875,\"y\":1.3499999,\"z\":1.70715272},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_001_qiangm_0004\",\"uniqueId\":\"0b129b27-23f7-40a1-8a37-254f754cdfde\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":3.09826851,\"y\":2.35,\"z\":-0.596457958},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_001_qiangm_0005\",\"uniqueId\":\"ba01adf8-bbd5-40c1-b521-77ed51a46134\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":3.09826851,\"y\":1.3499999,\"z\":-1.15936959},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_001_qiangm_0006\",\"uniqueId\":\"2bbccfb8-cd36-443a-94ca-045dd3bbda7f\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":-0.08797006,\"y\":1.3499999,\"z\":-0.5491499},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":90.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_001_qiangm_0007\",\"uniqueId\":\"f0fcd0f6-561e-443e-af3c-beea06f5ff1a\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":-0.675294459,\"y\":2.35,\"z\":0.183981165},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_001_qiangm_0008\",\"uniqueId\":\"3f23b649-f6e8-4e0f-ad37-a3a4debf65a8\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":-0.16163227,\"y\":1.3499999,\"z\":0.183981165},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_001_qiangm_0009\",\"uniqueId\":\"31f12a73-6bae-4d4a-a231-9fad566327be\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":-0.16163227,\"y\":1.3499999,\"z\":1.25139165},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":180.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_001_qiangm_00010\",\"uniqueId\":\"144dc22d-4f67-4ab0-887a-e30004f86adc\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":-0.675294459,\"y\":2.35,\"z\":1.25139153},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":180.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_001_qiangm_00011\",\"uniqueId\":\"64619b20-be3b-415b-a82d-bfa233222f1a\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":2.66995287,\"y\":1.3499999,\"z\":-1.26228118},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":4.260562E-06,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_001_qiangm_00012\",\"uniqueId\":\"15000cc6-4bb6-40e1-b9a4-24b2410f9213\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":1.931637,\"y\":2.35,\"z\":-1.26228106},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":4.260562E-06,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_001_qiangm_00013\",\"uniqueId\":\"e37df57d-6d53-427a-b4ef-6219c4e731af\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":1.39136791,\"y\":1.3499999,\"z\":-1.26228106},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":4.260562E-06,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_001_qiangm_00014\",\"uniqueId\":\"41c08edf-1e03-4de1-bdfb-cb04b65eee42\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":0.8510988,\"y\":2.35,\"z\":-1.26228106},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":4.260562E-06,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_001_qiangm_00015\",\"uniqueId\":\"61d791e4-dff8-418f-b70c-24c8024b68b7\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":0.226564348,\"y\":1.3499999,\"z\":-1.262281},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":4.260562E-06,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_001_qiangm_00016\",\"uniqueId\":\"1a7e46ec-fa72-409e-81b6-7db2811e3e58\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":-1.11731362,\"y\":1.3499999,\"z\":0.717686355},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":90.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_001_qiangm_00017\",\"uniqueId\":\"662b86e1-56c2-4643-a30a-33e5f97dfc07\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":-0.08797006,\"y\":1.3499999,\"z\":2.41107726},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":90.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_yangt_0001\",\"uniqueId\":\"2e1c87af-a1d5-4694-8f63-d595d99f603e\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":1.4526608,\"y\":1.2,\"z\":3.6207633},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":180.0,\"z\":0.0},\"modelType\":2,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_meng_0002\",\"uniqueId\":\"ac91c11e-6c5e-45f0-88cf-893c3be26dc1\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":3.168269,\"y\":1.05000031,\"z\":-0.5964581},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_meng_0003\",\"uniqueId\":\"09116db9-58c8-4990-a6a2-56168aa1aa05\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":1.93163753,\"y\":1.05000031,\"z\":-1.332281},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":2.504478E-06,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_meng_0004\",\"uniqueId\":\"40bb0744-9f07-4edd-9c48-1fc8336264f4\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":0.8510988,\"y\":1.05000031,\"z\":-1.33228111},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":2.50447829E-06,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_meng_0005\",\"uniqueId\":\"06d99a24-e492-4cfe-bad9-17743cdc6603\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":-0.675294459,\"y\":1.05000031,\"z\":0.113981158},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":-2.504478E-06,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_meng_0006\",\"uniqueId\":\"a172fd56-104d-47bb-93af-4e8bafa3af4c\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":-0.675294459,\"y\":1.05000031,\"z\":1.32139158},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":180.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_dimgd_0001\",\"uniqueId\":\"e45f710b-4217-4def-87ee-73ad84a4ec00_dim\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":-0.6126419,\"y\":0.0,\"z\":0.7176864},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_gdtianh_0001\",\"uniqueId\":\"e45f710b-4217-4def-87ee-73ad84a4ec00_tianh\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":-0.6126419,\"y\":2.9,\"z\":0.7176864},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_001_tijx_0001\",\"uniqueId\":\"21bb42c3-fc3f-4b07-bfc0-c11ae2317adb\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_dimz_0001\",\"uniqueId\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_dim\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":1.50514936,\"y\":0.0,\"z\":1.14424121},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_dimyt_0001\",\"uniqueId\":\"9458b7be-339f-4de4-a65c-db5d4718230d_dimyt\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":1.50514984,\"y\":0.0,\"z\":4.44869137},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_tianh_0001\",\"uniqueId\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_tianh\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":0.990477443,\"y\":3.05000019,\"z\":1.144241},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2_baimo_dimmk_0001\",\"uniqueId\":\"fb343c17-5081-4399-ba09-8c9723821410\",\"productId\":0,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":1.4526608,\"y\":0.0,\"z\":3.6207633},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":180.0,\"z\":0.0},\"modelType\":0,\"productTypeCode\":null,\"samllTypeCode\":null},{\"modelName\":\"baimo_D03_7844_001_dzho_0001\",\"uniqueId\":\"790b8eee-2262-4581-966b-0be21586b873\",\"productId\":1297054,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":1.45266056,\"y\":1.31,\"z\":3.47931314},\"modelScale\":{\"x\":4.243041,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":-180.000015,\"z\":0.0},\"modelType\":1,\"productTypeCode\":\"ho\",\"samllTypeCode\":\"basic_dzho\"},{\"modelName\":\"baimo_s02sf_0001\",\"uniqueId\":\"9f8adb40-9fba-4719-9eb9-77117741de88\",\"productId\":1191,\"groupId\":114837,\"isMainProduct\":1,\"groupType\":0,\"groupUniqueId\":\"65d571ea-9720-4514-b69d-5ed4c6fa5664\",\"modelPositon\":{\"x\":2.6479187,\"y\":0.468021363,\"z\":2.49626422},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":269.999939,\"z\":0.0},\"modelType\":1,\"productTypeCode\":\"sf\",\"samllTypeCode\":\"basic_s02sf\"},{\"modelName\":\"baimo_caho_0002\",\"uniqueId\":\"a2d6eed0-c054-489e-9d14-fae331415ece\",\"productId\":13697,\"groupId\":114837,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":\"65d571ea-9720-4514-b69d-5ed4c6fa5664\",\"modelPositon\":{\"x\":1.84913409,\"y\":0.0175003111,\"z\":2.49507618},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":269.999939,\"z\":0.0},\"modelType\":1,\"productTypeCode\":\"ho\",\"samllTypeCode\":\"basic_caho\"},{\"modelName\":\"baimo_teta_0004\",\"uniqueId\":\"4dea616b-b8ef-46f1-9544-658b403bbb55\",\"productId\":45986,\"groupId\":114837,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":\"65d571ea-9720-4514-b69d-5ed4c6fa5664\",\"modelPositon\":{\"x\":1.6093421,\"y\":0.239398643,\"z\":2.50663042},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":269.999939,\"z\":0.0},\"modelType\":1,\"productTypeCode\":\"ta\",\"samllTypeCode\":\"basic_teta\"},{\"modelName\":\"baimo_arpe_0002\",\"uniqueId\":\"0043a661-d895-4244-9ca7-bf9a17bcbf5f\",\"productId\":172325,\"groupId\":114837,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":\"65d571ea-9720-4514-b69d-5ed4c6fa5664\",\"modelPositon\":{\"x\":1.61573935,\"y\":0.6342894,\"z\":2.508219},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":269.999939,\"z\":0.0},\"modelType\":1,\"productTypeCode\":\"pe\",\"samllTypeCode\":\"basic_arpe\"},{\"modelName\":\"baimo_tvca_0002\",\"uniqueId\":\"1ca593c1-8e4f-4ca9-a913-87da42783a17\",\"productId\":6239,\"groupId\":109851,\"isMainProduct\":1,\"groupType\":0,\"groupUniqueId\":\"f07d4b47-03c1-429c-83ce-858dc7875357\",\"modelPositon\":{\"x\":0.155144513,\"y\":0.207393289,\"z\":2.44582486},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":89.9999847,\"z\":0.0},\"modelType\":1,\"productTypeCode\":\"ca\",\"samllTypeCode\":\"basic_tvca\"},{\"modelName\":\"baimo_tvel_0001\",\"uniqueId\":\"8ad3300c-0219-4767-8e07-db1989ad11b0\",\"productId\":1371,\"groupId\":109851,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":\"f07d4b47-03c1-429c-83ce-858dc7875357\",\"modelPositon\":{\"x\":0.172101438,\"y\":0.7794138,\"z\":2.44193},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":89.9999847,\"z\":0.0},\"modelType\":1,\"productTypeCode\":\"el\",\"samllTypeCode\":\"basic_tvel\"},{\"modelName\":\"baimo_pipe_0006\",\"uniqueId\":\"a834b0b8-cfd4-40bc-b498-c73489074543\",\"productId\":204962,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":3.08048439,\"y\":1.7,\"z\":2.58005071},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0},\"modelType\":1,\"productTypeCode\":\"pe\",\"samllTypeCode\":\"basic_pipe\"},{\"modelName\":\"baimo_pipe_0006\",\"uniqueId\":\"38bee647-2d8f-4bfd-9085-aabe88a24d23\",\"productId\":204962,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":3.08048415,\"y\":1.7,\"z\":0.6589343},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0},\"modelType\":1,\"productTypeCode\":\"pe\",\"samllTypeCode\":\"basic_pipe\"},{\"modelName\":\"baimo_ddli_0003\",\"uniqueId\":\"e1a6d5ae-defb-467a-90d8-160a7163a8ce\",\"productId\":204956,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":1.99865627,\"y\":2.24115443,\"z\":0.6661881},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"modelType\":1,\"productTypeCode\":\"li\",\"samllTypeCode\":\"basic_ddli\"},{\"modelName\":\"baimo_ddli_0001\",\"uniqueId\":\"cc53dbf3-d3d4-48a5-8b6a-9fbb7f8d45fc\",\"productId\":851,\"groupId\":0,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":null,\"modelPositon\":{\"x\":1.64288235,\"y\":2.24115443,\"z\":2.528326},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"modelType\":1,\"productTypeCode\":\"li\",\"samllTypeCode\":\"basic_ddli\"},{\"modelName\":\"baimo_dide_0007\",\"uniqueId\":\"dd09b24c-b0bd-4f7c-a8e0-acdf3cf7e795\",\"productId\":45392,\"groupId\":110434,\"isMainProduct\":1,\"groupType\":0,\"groupUniqueId\":\"c8766368-39a0-4a94-b53c-2d01560f994f\",\"modelPositon\":{\"x\":1.9967494,\"y\":0.378135234,\"z\":0.6758192},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":89.9999847,\"z\":0.0},\"modelType\":1,\"productTypeCode\":\"de\",\"samllTypeCode\":\"basic_dide\"},{\"modelName\":\"baimo_arpe_0002\",\"uniqueId\":\"6d930ce4-fe9f-4eff-bef4-a191851d3d69\",\"productId\":172325,\"groupId\":110434,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":\"c8766368-39a0-4a94-b53c-2d01560f994f\",\"modelPositon\":{\"x\":1.99652255,\"y\":0.922301054,\"z\":0.6591154},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":89.9999847,\"z\":0.0},\"modelType\":1,\"productTypeCode\":\"pe\",\"samllTypeCode\":\"basic_arpe\"},{\"modelName\":\"baimo_dich_0002\",\"uniqueId\":\"e7c7a63d-78fe-4081-aab0-862a6535b5c4\",\"productId\":1193,\"groupId\":110434,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":\"c8766368-39a0-4a94-b53c-2d01560f994f\",\"modelPositon\":{\"x\":1.33041441,\"y\":0.435196042,\"z\":0.6648946},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":89.9999847,\"z\":0.0},\"modelType\":1,\"productTypeCode\":\"ch\",\"samllTypeCode\":\"basic_dich\"},{\"modelName\":\"baimo_dich_0002\",\"uniqueId\":\"df3af9ce-5bd3-4c92-aed9-ba45204b40e3\",\"productId\":1193,\"groupId\":110434,\"isMainProduct\":0,\"groupType\":0,\"groupUniqueId\":\"c8766368-39a0-4a94-b53c-2d01560f994f\",\"modelPositon\":{\"x\":2.65216017,\"y\":0.435196042,\"z\":0.6867423},\"modelScale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0},\"modelEulerAngles\":{\"x\":0.0,\"y\":270.0,\"z\":0.0},\"modelType\":1,\"productTypeCode\":\"ch\",\"samllTypeCode\":\"basic_dich\"}],\"centerPosition\":{\"x\":1.35238564,\"y\":0.0,\"z\":1.11431968},\"renderPointList\":[{\"position\":{\"x\":0.6541536,\"y\":1.5,\"z\":3.13023758},\"eulerAngles\":{\"x\":0.0,\"y\":120.434227,\"z\":0.0}},{\"position\":{\"x\":0.7667546,\"y\":1.5,\"z\":0.93154794},\"eulerAngles\":{\"x\":0.0,\"y\":35.1588745,\"z\":0.0}},{\"position\":{\"x\":2.56837678,\"y\":1.5,\"z\":-0.455226481},\"eulerAngles\":{\"x\":0.0,\"y\":313.137329,\"z\":0.0}}],\"floorAreaBlockData\":null,\"ceilingAreaBlockData\":null,\"originAreaData\":{\"blockName\":\"4c6f0333-803e-4d41-a0c6-3ca0e0550cb2\",\"modelUniqueId\":null,\"mark\":null,\"bounds\":[{\"x\":-0.097970068454742432,\"y\":3.4028871059417725},{\"x\":-0.097970068454742432,\"y\":3.5607631206512451},{\"x\":3.1082687377929688,\"y\":3.5607631206512451},{\"x\":3.1082687377929688,\"y\":-1.2722810506820679},{\"x\":1.3679851293563843,\"y\":-1.2722810506820679},{\"x\":-0.097970068454742432,\"y\":-1.2722810506820679},{\"x\":-0.097970075905323029,\"y\":0.17398115992546082},{\"x\":-1.1273136138916016,\"y\":0.17398115992546082},{\"x\":-1.1273136138916016,\"y\":0.64285218715667725},{\"x\":-1.1273136138916016,\"y\":1.2613916397094727},{\"x\":-0.097970068454742432,\"y\":1.2613916397094727}],\"areaItems\":[{\"itemName\":\"客厅\",\"area\":7.372334,\"bounds\":[{\"x\":-0.097970068454742432,\"y\":1.2613916397094727},{\"x\":-0.097970068454742432,\"y\":3.5607631206512451},{\"x\":3.1082687377929688,\"y\":3.5607631206512451},{\"x\":3.1082687377929688,\"y\":1.2613916397094727}]},{\"itemName\":\"通道\",\"area\":1.11931884,\"bounds\":[{\"x\":-1.1273136138916016,\"y\":0.17398115992546082},{\"x\":-1.1273136138916016,\"y\":1.2613916397094727},{\"x\":-0.097970075905323029,\"y\":1.2613916397094727},{\"x\":-0.097970075905323029,\"y\":0.17398115992546082}]},{\"itemName\":\"餐厅\",\"area\":8.12356,\"bounds\":[{\"x\":-0.097970068454742432,\"y\":-1.2722810506820679},{\"x\":-0.097970068454742432,\"y\":1.2613916397094727},{\"x\":3.1082687377929688,\"y\":1.2613916397094727},{\"x\":3.1082687377929688,\"y\":-1.2722810506820679}]}]},\"cabinetDatas\":[]}";
        Gson gson = new Gson();
        FileJsonModel fileJsonModel = gson.fromJson(jsonStr, FileJsonModel.class);
        System.out.println(fileJsonModel);

        //获取中心位置坐标
        Position centerPosition = fileJsonModel.getCenterPosition();
        BigDecimal centerDecimalX = new BigDecimal(centerPosition.getX());
        BigDecimal centerDecimalY = new BigDecimal(centerPosition.getY());
        BigDecimal centerDecimalZ = new BigDecimal(centerPosition.getZ());

        //参数初始化
        DesignTempletJumpPositionRel positionRel;
        JumpPosition jumpPosition;
        Position position;
        Position rotation;
        Position modelPositon;

        //获取房间模型数据集合
        List<RoomModel> roomModelsDataList = fileJsonModel.getRoomModelsDataList();
        for (RoomModel roomModel : roomModelsDataList) {
            //有相同的uniqueId就计算坐标值
            if ("40bb0744-9f07-4edd-9c48-1fc8336264f4".equals(roomModel.getUniqueId())) {

                //获取模型位置坐标
                modelPositon = roomModel.getModelPositon();
                BigDecimal modelDecimalX = new BigDecimal(modelPositon.getX());
                BigDecimal modelDecimalY = new BigDecimal(modelPositon.getY());
                BigDecimal modelDecimalZ = new BigDecimal(modelPositon.getZ());

                //计算坐标值,输出为普通计数，不是科学计数
                position = new Position();
                position.setX((modelDecimalX.subtract(centerDecimalX)).toPlainString());
                position.setY((modelDecimalY.subtract(centerDecimalY)).toPlainString());
                position.setZ((modelDecimalZ.subtract(centerDecimalZ)).toPlainString());

                //格式化坐标值为普通计数
                rotation = roomModel.getModelEulerAngles();
                rotation.setX(new BigDecimal(rotation.getX()).toPlainString());
                rotation.setY(new BigDecimal(rotation.getY()).toPlainString());
                rotation.setZ(new BigDecimal(rotation.getZ()).toPlainString());

                //封装参数
                jumpPosition = new JumpPosition();
                jumpPosition.setPosition(position);
                jumpPosition.setRotation(rotation);
                String jumpPositionStr = GSON.toJson(jumpPosition, JumpPosition.class);

                Integer originTempletId = 750762;
                Integer targetTempletId = 750943;

                if (targetTempletId == null || originTempletId == null || jumpPosition == null || jumpPositionStr.length() <= 0) {
                    log.warn(CLASS_LOG_PREFIX + " ===> insert params have null ! targetTempletId:{}," +
                            "originTempletId:{},jumpPositionStr:{}", targetTempletId, originTempletId, jumpPositionStr);
                    continue;
                }

                //组合insert的数据
                positionRel = new DesignTempletJumpPositionRel();
                positionRel.setJumpPosition(jumpPositionStr);
                positionRel.setOriginId(originTempletId.longValue());
                positionRel.setTargetId(targetTempletId.longValue());
                positionRel.setCreator("refreshDoorPosition");
                positionRel.setModifier("refreshDoorPosition");
                Date date = new Date();
                positionRel.setGmtModified(date);
                positionRel.setGmtCreate(date);
                positionRel.setDrawDtpId(0L);
                positionRel.setIsDeleted((short) 0);
                positionRel.setRemark("批量添加门的位置信息成功");

                System.out.println(positionRel);
            }

        }
    }
}
