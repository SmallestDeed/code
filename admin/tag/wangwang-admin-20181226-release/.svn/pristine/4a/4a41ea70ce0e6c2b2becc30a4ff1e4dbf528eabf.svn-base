package com.sandu.service.storage.impl;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.sandu.api.storage.model.ResPic;
import com.sandu.api.storage.service.ResPicService;
import com.sandu.service.storage.dao.ResPicMapper;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sandu.common.util.file.FilePathUtil.*;
import static com.sandu.constant.Punctuation.*;
import static com.sandu.util.Commoner.isEmpty;
import static com.sandu.util.Commoner.isNotEmpty;
import static java.util.stream.Collectors.toMap;

/**
 * creator by bvvy
 */
@Slf4j
@Service
public class ResPicServiceImpl implements ResPicService {

    @Value("${file.storage.path}")
    private String rootPath;

    @Value("${upload.pic.compression.filekeys}")
    private String compressionPicFileKeys;

    @Resource
    private ResPicMapper resPicMapper;

    @Override
    public Long addResPic(ResPic resPic) {
        int result = resPicMapper.insertSelective(resPic);
        if (result > 0) {
            // 产品生成缩略图
            if ("product.baseProduct.piclist".equals(resPic.getFileKey())
                    || "product.baseWaterjetTemplate.templatePic".equals(resPic.getFileKey())) {
                // 产品、水刀
                // 生成缩略图
                final long webImageId = this.generateThumbnailImage(resPic, "c_basedesign", "web");
                final long ipadImageId = this.generateThumbnailImage(resPic, "c_basedesign", "ipad");
                // 更新主图的smallPicInfo字段
                this.updateSmallPicInfoField(resPic.getId(), webImageId, ipadImageId, 0, 0, 0);
            } else if ("system.resTexture.pic".equals(resPic.getFileKey())) {
                // 材质
                // 生成缩略图
                final long iosImageId = this.generateThumbnailImage(resPic, "c_basedesign", "ios");
                final long androidImageId = this.generateThumbnailImage(resPic, "c_basedesign", "android");
                // 更新主图的smallPicInfo字段
                this.updateSmallPicInfoField(resPic.getId(), 0, 0, iosImageId, androidImageId, 0);
            } else if (compressionPicFileKeys.contains(resPic.getFileKey())) {
                //商品图片,富文本图片,店铺logo等.
                // 生成缩略图
                String fileKeyTmp = resPic.getFileKey();
                String module = "c_" + fileKeyTmp.substring(0, fileKeyTmp.lastIndexOf(DOT)).replace(DOT, "");
                final long smallImageId = this.generateThumbnailImage(resPic, module, "small");
                // 更新主图的smallPicInfo字段
                this.updateSmallPicInfoField(resPic.getId(), 0, 0, 0, 0, smallImageId);
                return smallImageId;
            }
        }
        return resPic.getId();
    }

    /**
     * 生成缩略图
     *
     * <p>主图存放路径规则： /{platform}/{module}/{year}/{month}/{day}/{hour}/{fileKey}/{fileName}
     * fileKey=product.baseProduct.piclist 示例：
     * /AA/c_basedesign/2018/07/09/19/product/baseProduct/piclist/671728_20180709191957079_1.jpg
     *
     * <p>缩略图存放路径规则：/{platform}/{module}/{year}/{month}/{day}/{hour}/{fileKey}/small/{web|ipad}/{fileName}
     * fileKey=product.baseProduct.pic.{ipad|web} 示例：
     * /AA/c_basedesign/2017/12/16/18/product/baseProduct/pic/small/ipad/ipad_wu_zaox_5401_B_1.jpg
     *
     * @param originImage 原图对象
     * @param module      模块名
     * @param scene       缩略图应用场景： ipad,web
     * @return long 缩略图ID
     */
    @Override
    public long generateThumbnailImage(ResPic originImage, String module, String scene) {
        final String parentFileKey = originImage.getFileKey();

        String tempFileKey =
                parentFileKey.replace(parentFileKey.substring(parentFileKey.lastIndexOf(DOT) + 1), "pic");
        if ("product.baseWaterjetTemplate.templatePic".equals(parentFileKey)) {
            tempFileKey = parentFileKey;
        }
        final String fileKey = tempFileKey;

        StringBuffer tempThumbnailFileKey =
                new StringBuffer(String.format("%s%s%s", fileKey, DOT, scene));
        if ("product.baseWaterjetTemplate.templatePic".equals(parentFileKey)) {
            tempThumbnailFileKey =
                    new StringBuffer(String.format("%s%s%s", fileKey, DOT, "small"))
                            .append(".")
                            .append(scene);
        }
        final String thumbnailFileKey = tempThumbnailFileKey.toString();

        // 默认ipad缩略图尺寸
        String picType = "产品ipad端缩略图";
        int width = 256;
        int height = 256;
        // 如果是web，改变尺寸
        switch (scene) {
            case "web":
                picType = "web端缩略图";
                width = 132;
                height = 132;
                break;
            case "ios":
                picType = "ios端缩略图";
                width = 682;
                height = 512;
                break;
            case "android":
                picType = "android端缩略图";
                width = 682;
                height = 512;
                break;
            case "small":
                picType = "width: 750 压缩图";
                width = 750;
                if (originImage.getPicHigh() == null || originImage.getPicWeight() == null) {
                    height = width;
                } else {
                    height = Integer.valueOf(originImage.getPicHigh()) * width / Integer.valueOf(originImage.getPicWeight());
                }
                break;
            default:
                break;
        }


        /**
         * 生成缩略图存放路径
         *
         * <p>缩略图存放路径规则：/{platform}/{module}/{year}/{month}/{day}/{hour}/{fileKey}/small/{web|ipad}/{fileName}
         * fileKey=product.baseProduct.pic.{ipad|web} 示例：
         * /AA/c_basedesign/2017/12/16/18/product/baseProduct/pic/small/ipad/ipad_wu_zaox_5401_B_1.jpg
         */
        final StringBuilder filePath =
                new StringBuilder("AA")
                        .append(SLASH)
                        .append(module) // c_basedesign
                        .append(SLASH);

        final LocalDateTime currentTime = LocalDateTime.now();
        filePath
                .append(currentTime.getYear())
                .append(SLASH)
                .append(currentTime.getMonthValue())
                .append(SLASH)
                .append(currentTime.getDayOfMonth())
                .append(SLASH)
                .append(currentTime.getHour())
                .append(SLASH);

        if (!"product.baseWaterjetTemplate.templatePic".equals(parentFileKey)) {
            filePath.append(fileKey.replace(DOT, SLASH)).append(SLASH).append("small");
        } else {
            filePath
                    .append(fileKey.replace(DOT, SLASH))
                    .append(SLASH)
                    .append("small")
                    .append(SLASH)
                    .append(scene);
        }

        // 原图完整路径
        final String originFullPath = this.rootPath + originImage.getPicPath().substring(1);
        final String fileExtension = Files.getFileExtension(originFullPath);
        log.debug("Origin Image: path={}, extension={}", originFullPath, fileExtension);
        // 生成缩略图文件名
        final StringBuilder fileName =
                new StringBuilder("thumbnial-")
                        .append(originImage.getId())
                        .append(MIDLINE)
                        .append(scene)
                        .append(DOT)
                        .append(fileExtension);

        // 获取纯对存放路径，为空则创建相应目录
        final String fullFilePath = this.rootPath + filePath.toString();
        final File dir = new File(fullFilePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        log.debug("Thumbnail: path={}, name={}, full path={}", filePath, fileName, fullFilePath);
        try {
            // 1、生成缩略图，并写入磁盘
            BufferedImage bufferedImage =
                    Thumbnails.of(originFullPath).size(width, height).asBufferedImage();
            ImageIO.write(
                    bufferedImage,
                    fileExtension,
                    new File(String.format("%s%s%s", fullFilePath, SLASH, fileName.toString())));

            // Get Thumbnail image size
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, fileExtension, baos);
            final int size = baos.size();

            // 2、插入缩略图记录
            final Date now = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
            final String suffix = DOT + fileExtension;
            final ResPic thumbnailRes =
                    ResPic.builder()
                            .fileKey(thumbnailFileKey)
                            .picSize(size)
                            .picPath(
                                    String.format("%s%s%s%s", SLASH, filePath.toString(), SLASH, fileName.toString()))
                            .picWeight(String.valueOf(bufferedImage.getWidth()))
                            .picHigh(String.valueOf(bufferedImage.getHeight()))
                            .picSuffix(suffix)
                            .picFormat(fileExtension)
                            .picType(picType)
                            .picCode(fileName.toString().replace(suffix, ""))
                            .picName(fileName.toString())
                            .picFileName(fileName.toString())
                            .creator(originImage.getCreator())
                            .modifier(originImage.getModifier())
                            .gmtCreate(now)
                            .gmtModified(now)
                            .remark("auto-generate-thumbnail-image")
                            .isDeleted(0)
                            .build();
            if (originImage.getFileKey().equals("system.resTexture.pic")) {
                thumbnailRes.setFileKey(
                        String.format("%s%s%s", originImage.getFileKey(), ".small.", scene));
                thumbnailRes.setPicType("材质缩略图_" + scene);
            }
            log.debug("Thumbnail: {}", thumbnailRes);
            final int result = resPicMapper.insertSelective(thumbnailRes);

            baos.close();
            bufferedImage.flush();

            if (result > 0) {
                return thumbnailRes.getId();
            }
        } catch (IOException e) {
            log.error("Thumbnail generate error", e);
        }

        return 0L;
    }

    /**
     * 更新主图的smallInfo字段
     *
     * @param masterImageId 原图ID
     * @param webImageId    web缩略图ID
     * @param ipadImageId   ipad缩略图ID
     * @return int 更新记录数
     */
    @Override
    public int updateSmallPicInfoField(
            long masterImageId,
            long webImageId,
            long ipadImageId,
            long iosImageId,
            long androidImageId,
            long normalImageId) {
        // 更新主图的small_pic_info 字段
        final StringBuilder smallPicInfo = new StringBuilder();
        if (webImageId > 0L) {
            smallPicInfo.append("web:").append(webImageId).append(";");
        }
        if (ipadImageId > 0L) {
            smallPicInfo.append("ipad:").append(ipadImageId).append(";");
        }
        if (androidImageId > 0L) {
            smallPicInfo.append("android:").append(androidImageId).append(";");
        }
        if (iosImageId > 0L) {
            smallPicInfo.append("ios:").append(iosImageId).append(";");
        }
        if (normalImageId > 0) {
            smallPicInfo.append("normal:").append(normalImageId).append(";");
        }

        if (smallPicInfo.length() > 0) {
            final ResPic updateImage =
                    ResPic.builder()
                            .id(masterImageId)
                            .smallPicInfo(smallPicInfo.toString())
                            .gmtModified(new Date())
                            .build();

            return this.saveResPic(updateImage);
        }

        return 0;
    }

    @Override
    public ResPic getResPicDetail(Long resPicId) {
        return resPicMapper.selectByPrimaryKey(resPicId);
    }

    @Override
    public List<ResPic> getResPicDetailByIds(List<Long> resPicId) {
        if (resPicId.isEmpty()) {
            return Collections.emptyList();
        }
        return resPicMapper.listByIds(
                resPicId.stream().map(Long::intValue).collect(Collectors.toList()));
    }

    @Override
    public int saveResPic(ResPic resPic) {
        return resPicMapper.updateByPrimaryKeySelective(resPic);
    }

    @Override
    public List<ResPic> listResPic(ResPic resPic) {
        return resPicMapper.selectResPic();
    }

    @Override
    public int deleteResPic(Long resPicId) {
        return resPicMapper.deleteByPrimaryKey(resPicId);
    }

    @Override
    public String getPathById(Integer id) {
        ResPic resPic = this.getResPicDetail(Long.valueOf(id));
        if (isNotEmpty(resPic) && isNotEmpty(resPic.getPicPath())) {
            String tPath = removeStartSlash(resPic.getPicPath());
            String path = formatPath(serverPath()) + tPath;
            return path;
        }
        return "";
    }

    @Override
    public Map<Integer, String> idAndPathMap(List<Integer> picIds) {
        if (isEmpty(picIds)) {
            return Collections.emptyMap();
        }
        picIds = picIds.stream().distinct().collect(Collectors.toList());
        List<ResPic> resPics = resPicMapper.listByIds(picIds);
        return resPics
                .stream()
                .collect(
                        toMap(
                                resPic -> resPic.getId().intValue(),
                                resPic -> {
                                    if (isNotEmpty(resPic) && isNotEmpty(resPic.getPicPath())) {
                                        return formatPath(serverPath()) + removeStartSlash(resPic.getPicPath());
                                    }
                                    return "";
                                }));
    }

    @Override
    public void updatePicByPicPath(ResPic resPic, String sourceThumbPic) {
        ResPic pic = resPicMapper.getByPicPath(sourceThumbPic);
        pic.setBusinessId(resPic.getBusinessId());
        pic.setPicPath(resPic.getPicPath());
        pic.setRemark(Strings.nullToEmpty(resPic.getRemark()) + Strings.nullToEmpty(pic.getRemark()));
        resPicMapper.updateByPrimaryKeySelective(pic);
    }
}
