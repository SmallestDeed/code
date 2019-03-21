package com.sandu.service.pic.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.sandu.api.space.bo.DrawSpaceFileBO;
import com.sandu.common.constant.FileKey;
import com.sandu.common.mysql.MysqlExecutor;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.api.house.model.DrawResHousePic;
import com.sandu.api.res.model.ResPic;
import com.sandu.api.house.output.UploadFileVO;
import com.sandu.api.house.service.DrawResHousePicService;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.SystemConfigEnum;
import com.sandu.common.constant.house.DrawBaseHouseConstant;
import com.sandu.exception.DrawBusinessException;
import com.sandu.service.pic.dao.DrawResHousePicMapper;
import com.sandu.service.pic.dao.ResPicMapper;
import com.sandu.util.FileUtils;
import com.sandu.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("DrawResHousePicService")
public class DrawResHousePicServiceImpl implements DrawResHousePicService {

	@Autowired
	private ResPicMapper resPicMapper;
	
	@Autowired
	private DrawResHousePicMapper drawResHousePicMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long createByUploadFileVO(UploadFileVO fileVO) {
		// 参数验证 ->start
		if (fileVO == null) {
			return null;
		}
		// 参数验证 ->end

		DrawResHousePic drawResHousePic = getDrawPic(fileVO);

		return this.add(drawResHousePic);
	}

	private Long add(DrawResHousePic drawResHousePic) {
		// 参数验证 ->start
		if (drawResHousePic == null) {
			return null;
		}
		// 参数验证 ->end

		drawResHousePicMapper.saveDrawResHousePic(drawResHousePic);
		return drawResHousePic.getId() == null ? null : Long.valueOf(drawResHousePic.getId() + "");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DrawResHousePic> addBatchPic(List<UploadFileVO> pics) {
		List<DrawResHousePic> list = new ArrayList<>();
		if (pics != null && !pics.isEmpty()) {
			for (UploadFileVO pic : pics) {
				list.add(getDrawPic(pic));
			}
			
			drawResHousePicMapper.addBatchPic(list);
		}
		
		return list;
	}
	
	DrawResHousePic getDrawPic(UploadFileVO pic) {
		DrawResHousePic drawResHousePic = new DrawResHousePic();
		String randomNumStr = Utils.generateRandomDigitString(6);
		String sysCode = Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + randomNumStr;
		String picPath = Utils.dealWithPath(pic.getFilePath(), null);

		drawResHousePic.setBusinessId(pic.getBusinessId() == null ? null : Integer.valueOf(pic.getBusinessId() + ""));
		drawResHousePic.setPicHigh(pic.getHeight());
		drawResHousePic.setPicWeight(pic.getWidth());
		drawResHousePic.setPicSuffix(pic.getFileSuffix());
		drawResHousePic.setPicSize(pic.getFileSize());
		drawResHousePic.setPicPath(Utils.dealWithPath(picPath.replace(Utils.getValue(SystemConfigEnum.UPLOAD_ROOT_DIR.getKey()), ""), null));
		drawResHousePic.setPicName(pic.getFileName());
		drawResHousePic.setPicFormat("");
		drawResHousePic.setSysCode(sysCode);
		drawResHousePic.setPicCode(sysCode);
		drawResHousePic.setIsDeleted(DrawBaseHouseConstant.IS_DELETE_INIT);
		drawResHousePic.setPicType("");
		drawResHousePic.setPicFileName(pic.getFileOriginalName());
		drawResHousePic.setGmtCreate(Utils.parseDate(Utils.getCurrentDate(), Utils.YYYY_MM_DD));
		drawResHousePic.setSmallPicInfo("");

		if (SystemConfigEnum.SPACE_PIC_UPLOAD.equals(pic.getFileType())) {
			// 空间图片
			drawResHousePic.setFileKey(FileKey.BASE_HOUSE_PIC.getFileKey());
		} else if (SystemConfigEnum.DESIGN_TEMPLET_PIC.equals(pic.getFileType())) {
			// 样板房图
			drawResHousePic.setFileKey(FileKey.BASE_HOUSE_PIC.getFileKey());
		} else {
			// 户型图
			drawResHousePic.setFileKey(FileKey.BASE_HOUSE_PIC.getFileKey());
		}

		return drawResHousePic;
	}

	@Override
	public DrawResHousePic getDrawResHousePic(Long id) {
		if (id == null || id <= 0) {
			return null;
		}
		return drawResHousePicMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer update(DrawResHousePic drawPic) {
		if (drawPic == null) {
			return -1;
		}
		
		return drawResHousePicMapper.update(drawPic);
	}
	
	/**
	 * 复制图片
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long copyFile(DrawResHousePic source, String fileKey, boolean isSmall) {
		return copyFile(source, fileKey, isSmall, false).getId();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ResPic copyFile(DrawResHousePic source, String fileKey, boolean isSmall, boolean isAADir) {
		if (source == null) {
			log.warn("参数 source为空");
			return null;
		}

		if (StringUtils.isEmpty(source.getPicPath())) {
			log.warn("picPath为空 ==> {}", source.getPicPath());
			return null;
		}

		try {
			// copy file
			String targetPath = !isAADir ? FileUtils.copyFile(source.getPicPath(), fileKey)
					: FileUtils.copyFileWithReplaceable(source.getPicPath(), fileKey, Utils.RES_DIR_AA_PREFIX);

			Long smallPicId = -1L;
			// 缩略图
			if (isSmall) {
				log.debug("处理转化的缩略图, {}", source.getSmallPicInfo());
				DrawResHousePic smallPic = this.getDrawResHousePic(getPicId(source.getSmallPicInfo()));
				smallPicId = this.copyFile(smallPic, fileKey, false);
			}

			ResPic pic = new ResPic();
			BeanUtils.copyProperties(pic, source);
			pic.setId(null);
			pic.setPicPath(targetPath);
			pic.setSmallPicInfo("ipad:" + smallPicId + ";");
			log.debug("同步空间图片资源信息, {}", pic);
			// save
			resPicMapper.insertSelective(pic);

			return pic;
		} catch (IOException | IllegalAccessException | InvocationTargetException e) {
			throw new DrawBusinessException(false, ResponseEnum.FILE_COPY_FAILED, e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer clearDrawResHousePicResourceWithError(List<Long> picIds) {
		if (picIds == null || picIds.isEmpty()) {
			return null;
		}

		List<DrawSpaceFileBO> drawSpaceFileBOS = drawResHousePicMapper.listDrawResHousePicByIds(picIds);
		if (drawSpaceFileBOS == null || drawSpaceFileBOS.isEmpty()) {
			return null;
		}

		List<String> filePaths = drawSpaceFileBOS.stream().filter(f -> !StringUtils.isEmpty(f.getFilePath()))
				.map(DrawSpaceFileBO::getFilePath).collect(Collectors.toList());
		if (filePaths != null && !filePaths.isEmpty()) {
			FileUtils.delete(filePaths.toArray(new String[]{}));
		}

		return this.batchDeletedDrawResHousePic(picIds);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer batchDeletedDrawResHousePic(List<Long> picIds) {
		if (picIds == null || picIds.isEmpty()) {
			return null;
		}

		log.info("删除图片资源数据库记录，picIds => {}", picIds);

		// 如果需要删除的数据，小于默认值（100）直接执行删除动作
		if (picIds.size() <= MysqlExecutor.BATCH.DELETE.getCount()) {
			return this.batchDeletedDrawResHousePic2(picIds);
		}

		Integer updateCount = 0;
		List<Long> deleteFiles = new ArrayList<>();

		for (Long file : picIds) {
			deleteFiles.add(file);
			// 每一百条数据删除一次
			if (deleteFiles.size() >= MysqlExecutor.BATCH.DELETE.getCount()) {
				updateCount += this.batchDeletedDrawResHousePic2(deleteFiles);
				deleteFiles.clear();
			}
		}

		// 最后不足的100的数据删除
		if (!deleteFiles.isEmpty()) {
			updateCount += this.batchDeletedDrawResHousePic2(deleteFiles);
		}

		return updateCount;
	}

	@Override
	public List<DrawResHousePic> listDrawHousePicByIds(List<Long> picIds) {
		if (picIds == null || picIds.isEmpty()) {
			return null;
		}

		return drawResHousePicMapper.listDrawHousePicByIds(picIds);
	}

	/**
     *
     * @param args
     * @return
     */
    private Integer batchDeletedDrawResHousePic2(List<Long> args) {
        if (args == null || args.isEmpty()) {
            return 0;
        }

        List<Integer> collect = args.stream().map(Long::intValue).collect(Collectors.toList());
        Integer[] params = collect.toArray(new Integer[]{});

        if (params.length > 0) {
            return drawResHousePicMapper.deleteDrawResHousePic(params);
        }

        return 0;
    }

	private Long getPicId(String args) {
		if (!StringUtils.isEmpty(args)) {
			String[] split = args.split(";");
			for (String arg : split) {
				if (arg.startsWith("ipad:")) {
					return Long.parseLong(arg.split(":")[1]);
				}
			}
		}
		return -1L;
	}
}
