package com.sandu.service.volume.room.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.api.area.model.BaseArea;
import com.sandu.api.house.input.DrawBaseHouseNew;
import com.sandu.api.house.model.BaseLiving;
import com.sandu.api.house.model.DrawBaseHouse;
import com.sandu.api.house.model.DrawResHousePic;
import com.sandu.api.house.service.DrawBaseHouseService;
import com.sandu.api.volume.room.model.DrawResLfFile;
import com.sandu.api.volume.room.model.DrawVolumeRoom;
import com.sandu.api.volume.room.model.VolumeRoomData;
import com.sandu.api.volume.room.service.DrawVolumeRoomService;
import com.sandu.api.user.model.SysUser;
import com.sandu.common.constant.FileKey;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.SystemConfigEnum;
import com.sandu.common.constant.house.DrawBaseHouseConstant;
import com.sandu.common.constant.house.SystemConstant;
import com.sandu.common.constant.kechuang.VolumeRoomConstant;
import com.sandu.exception.BizException;
import com.sandu.exception.BusinessException;
import com.sandu.service.area.dao.BaseAreaMapper;
import com.sandu.service.house.dao.DrawBaseHouseMapper;
import com.sandu.service.volume.room.dao.DrawResLfFileMapper;
import com.sandu.service.volume.room.dao.DrawVolumeRoomMapper;
import com.sandu.service.living.dao.BaseLivingMapper;
import com.sandu.service.pic.dao.DrawResHousePicMapper;
import com.sandu.service.user.dao.SysUserMapper;
import com.sandu.util.*;
import com.sandu.util.http.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.sandu.common.constant.kechuang.PlatformType;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 科创量房
 *
 * @since v1.0.10
 */
@Slf4j
@Service
public class KCDrawVolumeRoomServiceImpl implements DrawVolumeRoomService {

    public static final int IS_DELETED = 1;
    public static final int NON_DELETED = 0;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DrawResLfFileMapper drawResLfFileMapper;

    @Autowired
    DrawVolumeRoomMapper drawVolumeRoomMapper;

    @Autowired
    DrawResHousePicMapper drawResHousePicMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    BaseLivingMapper baseLivingMapper;

    @Autowired
    BaseAreaMapper baseAreaMapper;

    @Autowired
    DrawBaseHouseService drawBaseHouseService;

    @Autowired
    DrawBaseHouseMapper drawBaseHouseMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveVolumeRoom(RequestBody requestBody, MultipartFile zipFile) {
        if (!StringUtils.isNotEmpty(requestBody.getData())) {
            throw new BusinessException(false, ResponseEnum.PARAM_ERROR);
        }

//        if (!Signature.isSignature(requestBody)) {
//            throw new BusinessException(false, ResponseEnum.SIGNATURE_VERIFY_FAIL);
//        }

        VolumeRoomData volumeRoomData = this.parseJSON(requestBody.getData(), VolumeRoomData.class);
        if (volumeRoomData == null) {
            throw new BusinessException(false, ResponseEnum.PARAM_ERROR);
        }

        // projectKey、 houseName、 mobile
        if (StringUtils.isEmpty(volumeRoomData.getProjectKey())
                || StringUtils.isEmpty(volumeRoomData.getHouseName())
                || StringUtils.isEmpty(volumeRoomData.getMobile())) {
            throw new BusinessException(false, ResponseEnum.PARAM_ERROR);
        }

        // bind user
        SysUser sysUser = sysUserMapper.getSysUserByMobile(volumeRoomData);
        if (sysUser == null) {
            throw new BusinessException(false, ResponseEnum.USER_NOT_FOUND);
        }

        this.handlerDrawVolumeRoom(zipFile, volumeRoomData, sysUser);
    }


    /**
     * 处理量房status
     *
     * @param drawHouse
     * @param updateStatus
     * @param applyStatus
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer handlerVolumeRoom(DrawBaseHouse drawHouse, Integer updateStatus, Integer applyStatus) {
        if (!PlatformType.SANDU.getType().equals(drawHouse.getPlatformType()) && drawHouse.getId() != null) {
            log.info("处理量房户型, {}(houseId)", drawHouse.getId());
            return drawVolumeRoomMapper.updateVolumeRoomByLock(drawHouse.getId(), updateStatus, applyStatus);
        }

        return -1;
    }


    /**
     * @param volumeRoomData
     * @param multipartFile
     * @return
     */
    private void handlerDrawVolumeRoom(MultipartFile multipartFile, VolumeRoomData volumeRoomData, SysUser sysUser) {
        // query exists volume room by project key
        DrawVolumeRoom drawVolumeRoom = drawVolumeRoomMapper.getDrawVolumeRoomByKey(volumeRoomData);
        if (drawVolumeRoom == null || drawVolumeRoom.getId() == null) {
            // add volume house
            drawVolumeRoom = getDrawVolumeRoom(new DrawVolumeRoom(), volumeRoomData);
            drawVolumeRoom.setHouseUuid(volumeRoomData.getHouseUUID());
            drawVolumeRoom.setProjectKey(volumeRoomData.getProjectKey());
            drawVolumeRoom.setCreator(volumeRoomData.getMobile());
            drawVolumeRoom.setGmtCreate(new Date());

            // 处理资源zip、lf、ppng文件
            this.handlerResource(multipartFile, drawVolumeRoom, volumeRoomData);
            // add draw house
            DrawBaseHouse drawHouse = this.addDrawHouseByVolumeRoom(drawVolumeRoom, sysUser);
            drawVolumeRoom.setDrawHouseId(drawHouse.getId());

            // add
            drawVolumeRoomMapper.insertSelective(drawVolumeRoom);
        } else {
            if (drawVolumeRoom.getIsDeleted() == null || drawVolumeRoom.getIsDeleted() == IS_DELETED) {
                throw new BizException(drawVolumeRoom.getProjectKey() + "量房数据被删除");
            }

            drawVolumeRoom = getDrawVolumeRoom(drawVolumeRoom, volumeRoomData);
            // 处理资源zip、lf、ppng文件
            this.handlerResource(multipartFile, drawVolumeRoom, volumeRoomData);
            // update draw house
            DrawBaseHouse drawHouse = this.addDrawHouseByVolumeRoom(drawVolumeRoom, sysUser);
            drawVolumeRoom.setDrawHouseId(drawHouse.getId());

            // update
            drawVolumeRoomMapper.updateByPrimaryKeySelective(drawVolumeRoom);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DrawBaseHouse addDrawHouseByVolumeRoom(DrawVolumeRoom drawVolumeRoom, SysUser sysUser) {
        // draw base house id
        if (drawVolumeRoom == null) {
            throw new BusinessException(false, ResponseEnum.ERROR);
        }

        if (drawVolumeRoom.getDrawHouseId() == null || drawVolumeRoom.getDrawHouseId() <= 0) {
            // add
            return this.addDrawHouse(drawVolumeRoom, sysUser);
        } else {
            // draw house id
            DrawBaseHouse drawHouse = drawBaseHouseMapper.selectByPrimaryKey(drawVolumeRoom.getDrawHouseId());
            if (drawHouse == null || drawHouse.getId() == null) {
                // add
                return this.addDrawHouse(drawVolumeRoom, sysUser);
            } else {
                // ! 草稿、待完善、完善中
                if (!Objects.equals(drawHouse.getHouseStatus(), DrawBaseHouseConstant.CHECK_INIT.toString())
                        && !Objects.equals(drawHouse.getHouseStatus(), DrawBaseHouseConstant.HOUSE_WAIT_PERFECT.toString())
                        && !Objects.equals(drawHouse.getHouseStatus(), DrawBaseHouseConstant.HOUSE_PERFECT_PROCESSING.toString())) {
                    throw new BizException(drawVolumeRoom.getHouseName() + "正在烘焙，请烘焙完后再操作！");
                }

                drawHouse.setGmtModified(new Date());
                drawHouse.setCreator(sysUser.getMobile());
                drawHouse.setHouseName(drawVolumeRoom.getHouseName());
                drawHouse.setHouseCommonCode(drawVolumeRoom.getHouseName());
                // 小区
                BaseLiving living = getBaseLiving(drawVolumeRoom, sysUser);
                drawHouse.setLivingId(null == living ? 0 : living.getId().intValue());
                drawHouse.setAreaLongCode(null == living ? "" : living.getAreaLongCode());
                // 图片
                drawHouse.setSnapPicId(drawVolumeRoom.getHousePicId());
                drawHouse.setPicRes1Id(drawVolumeRoom.getHousePicId().intValue());

                drawBaseHouseMapper.updateByPrimaryKeySelective(drawHouse);

                return drawHouse;
            }
        }
    }

    /**
     * @param drawVolumeRoom
     * @param sysUser
     * @return
     */
    private DrawBaseHouse addDrawHouse(DrawVolumeRoom drawVolumeRoom, SysUser sysUser) {
        DrawBaseHouse drawHouse = new DrawBaseHouse();
        // 小区
        BaseLiving living = getBaseLiving(drawVolumeRoom, sysUser);

        String randomNum = Utils.random();
        drawHouse.setUserId(sysUser.getId());
        drawHouse.setSysCode(Utils.getSysCode(6));
        drawHouse.setHouseName(drawVolumeRoom.getHouseName());
        drawHouse.setHouseCommonCode(drawVolumeRoom.getHouseName());
        drawHouse.setTotalArea("");
        drawHouse.setIsDeleted(NON_DELETED);
        drawHouse.setLivingId(null == living ? 0 : living.getId().intValue());
        drawHouse.setAreaLongCode(null == living ? "" : living.getAreaLongCode());
        drawHouse.setHouseCode(null == living ? ("0" + randomNum) : (living.getLivingCode() + randomNum));
        // 待完善
        drawHouse.setHouseStatus(DrawBaseHouseConstant.HOUSE_WAIT_PERFECT.toString());
        drawHouse.setGmtCreate(new Date());
        drawHouse.setGmtModified(new Date());
        drawHouse.setCreator(sysUser.getMobile());
        drawHouse.setModifier(sysUser.getMobile());
        // 公开
        drawHouse.setIsPublic(DrawBaseHouseConstant.HOUSE_IS_PUBLIC);
        drawHouse.setDataType(DrawBaseHouseConstant.DATA_PERSONAL);

        drawHouse.setPicRes1Id(drawVolumeRoom.getHousePicId().intValue());
        drawHouse.setSnapPicId(drawVolumeRoom.getHousePicId());
        // 来自科创量房
        drawHouse.setOrigin(DrawBaseHouseConstant.ORIGIN_KC_VOLUME_ROOM);

        // 科创平台
        drawHouse.setPlatformType(PlatformType.KC.getType());

        // add
        drawBaseHouseMapper.insertSelective(drawHouse);

        return drawHouse;
    }

    /**
     * @param drawVolumeRoom
     * @param sysUser
     * @return
     */
    BaseLiving getBaseLiving(DrawVolumeRoom drawVolumeRoom, SysUser sysUser) {
        List<BaseArea> baseAreas = baseAreaMapper.listAreaByName(drawVolumeRoom.getProvince(),
                drawVolumeRoom.getCity(), drawVolumeRoom.getDist());
        if (baseAreas == null || baseAreas.isEmpty()) {
            throw new BizException("省市区信息不能为空！");
        }

        DrawBaseHouseNew data = new DrawBaseHouseNew();
        int size = baseAreas.size() - 1;
        data.setFirstAreaCode((size >= 0) ? baseAreas.get(0).getAreaCode() : null);
        data.setSecondAreaCode((size >= 1) ? baseAreas.get(1).getAreaCode() : null);
        data.setThirdAreaCode((size >= 2) ? baseAreas.get(2).getAreaCode() : null);

//        data.setLivingId(-1L);
//        baseLivingMapper.getLivingByName(baseAreas.get(0).getAreaCode(), drawVolumeRoom.getHouseName());
        data.setLivingName(drawVolumeRoom.getLivingName());

        data.setUserId(sysUser.getId());

        return drawBaseHouseService.getBaseLiving(data);
    }

    /**
     * @param drawVolumeRoom
     * @param volumeRoomData
     * @return
     */
    private DrawVolumeRoom getDrawVolumeRoom(DrawVolumeRoom drawVolumeRoom,
                                             VolumeRoomData volumeRoomData) {
        drawVolumeRoom = drawVolumeRoom == null ? new DrawVolumeRoom() : drawVolumeRoom;
        drawVolumeRoom.setMobile(volumeRoomData.getMobile());
        drawVolumeRoom.setGmtModified(new Date());
        drawVolumeRoom.setModifier(volumeRoomData.getMobile());
        drawVolumeRoom.setHouseName(volumeRoomData.getHouseName());
        // 省市区
        drawVolumeRoom.setProvince(volumeRoomData.getProvince());
        drawVolumeRoom.setCity(volumeRoomData.getCity());
        drawVolumeRoom.setDist(volumeRoomData.getDist());
        // 小区名
        drawVolumeRoom.setLivingName(volumeRoomData.getLivingName());
        // defaults(0 -> 待解析)
        drawVolumeRoom.setStatus(VolumeRoomConstant.DEFAULTS.getStatus());

        return drawVolumeRoom;
    }

    /**
     * 量房的资源处理
     *
     * @param multipartFile
     * @param drawVolumeRoom
     * @param volumeRoomData
     */
    private void handlerResource(MultipartFile multipartFile, DrawVolumeRoom drawVolumeRoom,
                                 VolumeRoomData volumeRoomData) {
        // save zip file
        DrawResLfFile drawZipFile = this.handlerZipFile(multipartFile, drawVolumeRoom, volumeRoomData.getMobile());
        drawVolumeRoom.setZipFileId(drawZipFile.getId());

        // 获取压缩文件
        File zipFile = new File(SystemConstant.UPLOAD_ROOT + drawZipFile.getFilePath());

        // lf 文件处理
        Long lfFileId = this.handlerLfFile(zipFile, drawVolumeRoom, volumeRoomData.getMobile());
        drawVolumeRoom.setLfFileId(lfFileId);

        // house pic 文件处理
        Long housePicId = this.handlerHousePic(zipFile, drawVolumeRoom, volumeRoomData.getMobile());
        drawVolumeRoom.setHousePicId(housePicId);
    }

    /**
     * 保存文件
     *
     * @param file            文件
     * @param drawVolumeRoom  量房
     * @param defaultsCreator 默认创建人
     * @return
     */
    private DrawResLfFile handlerZipFile(MultipartFile file, DrawVolumeRoom drawVolumeRoom, String defaultsCreator) {
        String filePath;
        try {
            filePath = this.createFile(file.getInputStream(), FileType.ZIP);
        } catch (IOException e) {
            throw new BusinessException(false, ResponseEnum.FILE_COPY_FAILED, e);
        }

        DrawResLfFile drawResLfFile = drawResLfFileMapper.selectByPrimaryKey(drawVolumeRoom.getZipFileId());
        drawResLfFile = (drawResLfFile == null ? new DrawResLfFile() : drawResLfFile);
        drawResLfFile.setFilePath(filePath);

        return this.saveVolumeRoomFile(drawResLfFile, drawVolumeRoom, defaultsCreator, FileKey.KECHUANG_ZIP_FILE);
    }

    /**
     * 处理 lf 文件
     *
     * @param zipFile
     * @param drawVolumeRoom
     * @param defaultsCreator
     * @return
     */
    private Long handlerLfFile(File zipFile, DrawVolumeRoom drawVolumeRoom, String defaultsCreator) {
        // create file
        String lfFilePath;
        try {
            lfFilePath = this.createFile(this.getStream(zipFile, drawVolumeRoom.getProjectKey() + FileType.LF), FileType.LF);
        } catch (IOException e) {
            throw new BusinessException(false, ResponseEnum.FILE_COPY_FAILED, e);
        }

        DrawResLfFile drawResLfFile = drawResLfFileMapper.selectByPrimaryKey(drawVolumeRoom.getLfFileId());
        drawResLfFile = (drawResLfFile == null ? new DrawResLfFile() : drawResLfFile);
        drawResLfFile.setFilePath(lfFilePath);

        // save
        DrawResLfFile lfFile = this.saveVolumeRoomFile(drawResLfFile, drawVolumeRoom, defaultsCreator, FileKey.KECHUANG_LF_FILE);

        return lfFile.getId();
    }

    /**
     * 获取zip file 包里的文件input stream
     *
     * @param file
     * @param key
     * @return
     * @throws IOException
     */
    private InputStream getStream(File file, String key) throws IOException {
        if (key == null) {
            throw new BusinessException(false, ResponseEnum.ERROR);
        }

        ZipFile zipFile = new ZipFile(file);

        ZipEntry zipEntry;
        InputStream defStream = null;
        String fileSuffix = UploadUtils.getFileSuffix(key);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while (entries.hasMoreElements() && (zipEntry = entries.nextElement()) != null) {
            // defaults value
            if (zipEntry.getName().endsWith(fileSuffix)) {
                log.info("defaults value => {}", defStream);
                defStream = (defStream == null ? zipFile.getInputStream(zipEntry) : defStream);
            }

            if (zipEntry.getName().equalsIgnoreCase(key)) {
                // close defaults stream
                if (defStream != null) {
                    defStream.close();
                }

                return zipFile.getInputStream(zipEntry);
            }
        }

        return defStream;
    }

    private DrawResLfFile saveVolumeRoomFile(DrawResLfFile drawResLfFile, DrawVolumeRoom drawVolumeRoom,
                                             String defaultsCreator, FileKey fileKey) {
        if (drawResLfFile == null) {
            throw new BusinessException(false, ResponseEnum.FILE_COPY_FAILED);
        }

        if (drawResLfFile.getId() == null || drawResLfFile.getId() <= 0) {
            // file name
            String fileName = UploadUtils.getFileName(drawResLfFile.getFilePath());
            drawResLfFile.setFileName(fileName);
            drawResLfFile.setFileOriginalName(fileName);
            drawResLfFile.setFileCode(Utils.getSysCode(6));
            drawResLfFile.setFileKey(fileKey != null ? fileKey.getFileKey() : null);
            drawResLfFile.setBusinessId(drawVolumeRoom.getId() != null ? drawVolumeRoom.getId().intValue() : 0);
            drawResLfFile.setCreator(Objects.toString(drawVolumeRoom.getCreator(), defaultsCreator));
            drawResLfFile.setModifier(Objects.toString(drawVolumeRoom.getModifier(), defaultsCreator));

            drawResLfFileMapper.insertSelective(drawResLfFile);
        } else {
            drawResLfFile.setIsDeleted(NON_DELETED);

            drawResLfFileMapper.updateByPrimaryKeySelective(drawResLfFile);
        }

        return drawResLfFile;
    }

    /**
     * 处理量房户型图
     *
     * @param zipFile
     * @param drawVolumeRoom
     * @param defaultsCreator
     * @return
     */
    private Long handlerHousePic(File zipFile, DrawVolumeRoom drawVolumeRoom,
                                 String defaultsCreator) {
        // create file
        String housePicPath;
        try {
            housePicPath = this.createFile(this.getStream(zipFile, drawVolumeRoom.getProjectKey() + FileType.PPNG), FileType.PNG);
        } catch (IOException e) {
            throw new BusinessException(false, ResponseEnum.FILE_COPY_FAILED, e);
        }

        DrawResHousePic drawResHousePic = drawResHousePicMapper.selectByPrimaryKey(drawVolumeRoom.getHousePicId());
        if (drawResHousePic == null || drawResHousePic.getId() <= 0) {
            drawResHousePic = new DrawResHousePic();
            drawResHousePic.setPicPath(housePicPath);
            String fileName = UploadUtils.getFileName(housePicPath);
            drawResHousePic.setPicName(fileName);
            drawResHousePic.setPicFileName(fileName);
            drawResHousePic.setPicCode(Utils.getSysCode(6));
            drawResHousePic.setFileKey(FileKey.HOUSE_DRAW_PIC.getFileKey());
            drawResHousePic.setBusinessId(drawVolumeRoom.getId() != null ? drawVolumeRoom.getId().intValue() : 0);
            drawResHousePic.setCreator(Objects.toString(drawVolumeRoom.getCreator(), defaultsCreator));
            drawResHousePic.setModifier(Objects.toString(drawVolumeRoom.getModifier(), defaultsCreator));

            drawResHousePicMapper.insertSelective(drawResHousePic);
        } else {
            drawResHousePic.setPicPath(housePicPath);
            drawResHousePic.setIsDeleted(NON_DELETED);

            drawResHousePicMapper.updateByPrimaryKeySelective(drawResHousePic);
        }

        return drawResHousePic.getId().longValue();
    }

    /**
     * create file
     *
     * @param stream
     * @param fileType
     * @return
     */
    private String createFile(InputStream stream, FileType fileType) {
        String uploadRootDir = SystemConstant.UPLOAD_ROOT;
        String volumeFileDir = Utils.getValue(SystemConfigEnum.DRAW_VOLUME_ROOM_FILE_UPLOAD.getFileKey());
        String fileName = Utils.getFileName(UploadUtils.getFileSuffix(fileType.toString()));
        File targetFile = new File(uploadRootDir + volumeFileDir + fileName);

        try {
            org.apache.commons.io.FileUtils.copyInputStreamToFile(stream, targetFile);
        } catch (IOException e) {
            throw new BusinessException(false, ResponseEnum.FILE_COPY_FAILED, e);
        }

        return volumeFileDir + fileName;
    }

    private <T> T parseJSON(String data, Class<T> clazz) {
        try {
            return objectMapper.readValue(data, clazz);
        } catch (IOException e) {
            throw new BusinessException(false, ResponseEnum.JSON_FORMAT_ERROR, e);
        }
    }
}
