package com.sandu.test.utils;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.api.house.input.DrawBaseProductDTO;
import com.sandu.api.house.input.PublicProductInfoDTO;
import com.sandu.common.DrawType;
import com.sandu.common.constant.SpaceCommonStatusConstant;
import com.sandu.common.constant.SpaceLayoutType;
import com.sandu.common.constant.SystemConfigEnum;
import com.sandu.common.constant.bake.BakeTaskQueue;
import com.sandu.common.constant.house.DrawBaseHouseConstant;
import com.sandu.service.templet.impl.DrawDesignTempletProductServiceImpl;
import com.sandu.util.Regex;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.sandu.common.constant.RegionMarkConstant;
import com.sandu.util.UploadUtils;
import com.sandu.util.Utils;

import lombok.extern.slf4j.Slf4j;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn <p>
 * 2018年1月19日
 */

@Slf4j
@SpringBootTest
public class UtilTests {

    @Test
    public void test1() {
//    	File sFile = new File("ff/fff.png");
//		List<String> DECSuffixList = new ArrayList<>();
//		DECSuffixList.add("txt");
//		boolean b = DECSuffixList.indexOf(sFile.getPath().substring(sFile.getPath().lastIndexOf(".") + 1)) != -1;
//		log.info("b {}", b);

		String key = "41e5c74dd46e4ddcb942dc8ce6211111";

		try {
			FileUtils.writeByteArrayToFile(new File("C:\\Users\\Sandu\\Desktop\\text.txt"), key.getBytes(), 0, key.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	@Test
	public void testHttp() {
//		BitSet bitSet = new BitSet();
//		bitSet.set(1);
//		log.debug("{}", bitSet.get(2));
//		log.debug("{}", 50 >> 6);

		Integer a = 3;
//		log.debug("{}", Objects.equals(a, DrawType.DRAW_AUDITOR_EDIT));
//		log.debug("{}", a != DrawType.DRAW_AUDITOR_EDIT);

		// 非完善户型和审核时编辑
		String houseStatus = "8";

		log.debug("{}", !houseStatus.equals(DrawBaseHouseConstant.HOUSE_PERFECT_PROCESSING.toString()));
		log.debug("{}", !houseStatus.equals(DrawBaseHouseConstant.HOUSE_WAIT_PERFECT.toString()));
		log.debug("{}", !houseStatus.equals(DrawBaseHouseConstant.HOUSE_WAIT_PERFECT.toString())
				|| !houseStatus.equals(DrawBaseHouseConstant.HOUSE_PERFECT_PROCESSING.toString()));

		log.debug("{}", (houseStatus != null && (!houseStatus.equals(DrawBaseHouseConstant.HOUSE_WAIT_PERFECT.toString())
				&& !houseStatus.equals(DrawBaseHouseConstant.HOUSE_PERFECT_PROCESSING.toString()))) || 1 != 3);


//		if ((houseStatus != null && (!houseStatus.equals(DrawBaseHouseConstant.HOUSE_WAIT_PERFECT.toString())
//				|| !houseStatus.equals(DrawBaseHouseConstant.HOUSE_PERFECT_PROCESSING.toString())))
//				|| 1 != DrawType.DRAW_AUDITOR_EDIT) {
//			log.debug("sfdfasdf");
//		}
	}

//	@Test
	public void testTotalArea() {
		BigDecimal totalArea = new BigDecimal(0);
		String[] area = {"12.59912", "11.22159", "9.932627", "8.586089", "8.327637"};
		for (String arg : area) {
			totalArea = getSpaceTotalArea(totalArea, arg);
		}

		log.debug("{}", Math.round(totalArea.doubleValue()));
		log.debug("{}", Utils.getDecimalFormat(totalArea.doubleValue()));
		log.debug("{}", totalArea.doubleValue());
	}

	public BigDecimal getSpaceTotalArea(BigDecimal totalArea, String spaceTotalArea) {
		return (StringUtils.isNoneBlank(spaceTotalArea) && spaceTotalArea.matches(Regex.DOUBLE_NUMBER.getValue()))
				? totalArea.add(new BigDecimal(spaceTotalArea)) : totalArea;
	}


//	@Test
	public void test() {
		log.debug("{}", "AB".matches(Regex.SPACE_LAYOUT_B_TYPE.getValue()));
		log.debug("{}", "BA".matches(Regex.SPACE_LAYOUT_B_TYPE.getValue()));
		log.debug("{}", "fBA".matches(Regex.SPACE_LAYOUT_B_TYPE.getValue()));
		log.debug("{}", "BAf".matches(Regex.SPACE_LAYOUT_B_TYPE.getValue()));
	}

//	@Test
	public void testReplace() {
//		log.debug("{}",  BakeTaskQueue.INTERNAL.toString());
//		String pic = "/AA2/c_basedesign/2018/04/12/05/draw/spaceCommon/pic/7e2726aa-d0fa-4906-8a82-b155f367bdef-5144472913981577.jpg";
//		
//		log.debug("{}", Utils.getNewFileName(pic));
//		log.debug("{}",  Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_1");
//		log.debug("{}",  System.nanoTime());
//		log.debug("{}",  System.currentTimeMillis());
//		
//		String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis()));
//		log.debug("{}", format);
//		
//		log.debug("{}", Utils.generateRandomDigitString(6));
//		log.debug("{}", getDesignCode("C09_5509498118024248_569325"));
//		String regex = "^2018041[4-6]\\d+_{1}\\d{6}(\\.txt)$";
//		log.debug("{}", "2018041".matches(regex));
//		log.debug("{}", "20180414140638014_115970.txt".matches(regex));
//		log.debug("{}", "20180415024564-556456".matches(regex));
//		log.debug("{}", "20180416024564-556456".matches(regex));
//		log.debug("{}", "20190416024564-556456".matches(regex));
//		log.debug("{}", "20180416024564556456".matches(regex));
//		log.debug("{}", "20180416024564556-456".matches(regex));
//		log.debug("{}", "2018051".matches(regex));
//		log.debug("{}", "2018041".matches(regex));
	}
	
	File createDir(String dir, String path) {
		path = path.substring(0, path.lastIndexOf("/"));
		File file = new File(dir + path);
		if (!file.exists()) {
			file.mkdirs();
		}

		return file;
	}
	
	public List<String> getFiles() throws Exception {
		List<String> files = new ArrayList<>();
		File file = new File("C:\\Users\\Sandu\\Desktop\\config.txt");
		BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

		String context = "";
		while (StringUtils.isNoneBlank((context = buf.readLine()))) {
			files.add(context);
		}

		buf.close();

		return files;
	}
	
	String getDesignCode(String arg) {
		if (arg == null) {
			return Utils.random();
		}

		String[] split = arg.split("_");
		if (split != null && split.length > 0) {
			split[split.length - 1] = Utils.generateRandomDigitString(6);
			return Arrays.asList(split).stream().collect(Collectors.joining("_"));
		}

		return Utils.random();
	}
	
//	@Test
	public void testThumbnail() {
		String source = "C:\\Users\\Sandu\\Desktop\\test.png";
		String target = UploadUtils.getThumbnailPath(source, UploadUtils.LARGE_THUMBNAIL_MARK);
		String thumbnail = UploadUtils.thumbnail(source, target, UploadUtils.SpaceImageStaff.IPAD_W_H);
		log.debug("target ==> {}", target);
		log.debug("thumbnail ==> {}", thumbnail);
	}
	
	Integer getValue(Object arg) {
		return Integer.valueOf(Objects.toString(arg, "-999"));
	}
	
	@Test
	public void testRegionMark() {
		List<RegionMark> regions = this.buildRegionMark();
		for (int i = 0; i < 3; i++) {
			this.reset(regions);
			for (int j = 0; j < 5; j++) {
				int regionMark = getRegionMark(regions, RegionMarkConstant.MAIN_PLATFOND.getValuekey());
				log.debug("{} ==> {}", RegionMarkConstant.MAIN_PLATFOND.getValuekey(), regionMark);
			}
			
			log.debug("\n");
		}
	}
	
//	@Test
	public void testContains() {
		Assert.assertTrue(RegionMarkConstant.contains("basic_tianh"));
		Assert.assertTrue(!RegionMarkConstant.contains("ffff"));
		Assert.assertTrue(RegionMarkConstant.contains("basic_dimgd"));
	}
	
	public int getRegionMark(List<RegionMark> regions, String valuekey) {
		if (regions == null || regions.isEmpty()) {
			return -1;
		}

		for (RegionMark region : regions) {
			int regionMark = region.getRegionMark(valuekey);
			if (regionMark > 0) {
				return regionMark;
			}
		}
		return -1;
	}

	/**
	 * @return
	 */
	List<RegionMark> buildRegionMark() {
		List<RegionMark> regions = new ArrayList<>();
//		// 地面
//		regions.add(new RegionMark(RegionMarkConstant.PORCH_FLOOR));// 玄关地面 => 20
//		regions.add(new RegionMark(RegionMarkConstant.AISLE_FLOOR));// 过道地面 => 30
//		regions.add(new RegionMark(RegionMarkConstant.MAIN_FLOOR));// 主地面地面 => 10
//
//		// 天花
//		regions.add(new RegionMark(RegionMarkConstant.PORCH_PLATFOND));// 玄关天花 => 20
//		regions.add(new RegionMark(RegionMarkConstant.AISLE_PLATFOND));// 过道天花 => 30
//		regions.add(new RegionMark(RegionMarkConstant.MAIN_PLATFOND));// 主地面天花 => 10

		RegionMarkConstant[] regionMarks = RegionMarkConstant.values();
		for (RegionMarkConstant regionMark : regionMarks) {
			regions.add(new RegionMark(regionMark));
		}

		return regions;
	}
	
	/**
	 * 重置index
	 * @param regions
	 */
	void reset(List<RegionMark> regions) {
		regions.forEach(RegionMark::reset);
	}
	
	/**
	 * 区域标识处理类
	 * @author songjianming@sanduspace.cn
	 * 2018 2018年3月19日
	 *
	 */
	class RegionMark {

		private int index;
		private RegionMarkConstant region;

		RegionMark(RegionMarkConstant region) {
			this.region = region;
			this.index = region.getIndex();
		}

		synchronized int getRegionMark(String valuekey) {
			if (this.region.getValuekey().equals(valuekey)) {
				return index++;
			}
			return -1;
		}
		
		void reset() {
			this.index = region.getIndex();
		}
	}
	
//	@Test
	public void testGroupIndex() {
		Map<String, String> map = new HashMap<>();
		log.info(getGroupIndex(map, "sdfsdf44"));
		log.info(getGroupIndex(map, "sdfsdffff44"));
		log.info(getGroupIndex(map, "sdfsdf44"));
	}
	
	String getGroupIndex(Map<String, String> map, String groupUniqueId) {
		if (!StringUtils.isEmpty(groupUniqueId)) {
			if (map.containsKey(groupUniqueId)) {
				return map.get(groupUniqueId);
			} else {
				String groupIndex = Objects.toString(System.nanoTime());
				map.put(groupUniqueId, groupIndex);
				return groupIndex;
			}
		}

		return null;
	}
}
