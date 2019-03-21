package com.sandu.util;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Strings;
import com.sandu.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.SystemConfigEnum;
import com.sandu.common.constant.house.DrawBaseHouseConstant;
import com.sandu.exception.BusinessException;
import com.sandu.util.auth.HouseAuthUtils;

public class Utils {

	private static Map<String, String> cacheConfigMap = new ConcurrentHashMap<>();

	public static final String DATETIMESSS = "yyyyMMddHHmmssSSS";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String SYSTEM_CONFIG = "system";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String _DOT = ".";
	public static final String _LINE_SPARTA = "_";
	public static final String _LIUNX_FILE_SEPARATOR = "/";
	public static final String _WINDOWS_FILE_SEPARATOR = "\\";

	public final static String VOID_VALUE = "";
	public final static String ZERO_VALUE = "0";
	public final static String _ONE_VALUE = "-1";
	public static final String RES_DIR_AA_PREFIX = "/AA";
	public static final String RES_DIR_AA2_PREFIX = "/AA2";

	public static final int ONE_VALUE = 1;

	private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

	public static String getCurrentDate() {
		return getCurrentDateTime(YYYY_MM_DD);
	}

	public static Date parseDate(String date, String newFormat) {
		if ((date == null) || (newFormat == null)) {
			return null;
		}

		try {
			SimpleDateFormat formatter = new SimpleDateFormat(newFormat);
			return formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getCurrentDateTime(String _dtFormat) {
		String currentdatetime = "";
		try {
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dtFormat = new SimpleDateFormat(_dtFormat);
			currentdatetime = dtFormat.format(date);
		} catch (Exception e) {
			LOG.error("时间格式不正确", e);
		}
		return currentdatetime;
	}

	public static String getFilePath(String fileKey) {
		String volumeFileDir = Utils.getValue(fileKey);
		String uploadRootDir = Utils.getValue(SystemConfigEnum.UPLOAD_ROOT_DIR.getFileKey());
		return uploadRootDir + volumeFileDir;
	}

	/**
	 * 获取配置文件中value
	 * 
	 * @param key
	 *            配置文件中key
	 * @return
	 */
	public static String getValue(String key) throws BusinessException {
		return getValue(SYSTEM_CONFIG, key);
	}

	/**
	 * @param key
	 * @param replaceStr
	 * @return
	 * @throws BusinessException
	 */
	public static String getValueWithReplaceable(String key, String replaceStr) throws BusinessException {
		String value = getValue(key);
		if (Strings.isNullOrEmpty(value)) {
			return null;
		}

		if (value.startsWith(RES_DIR_AA2_PREFIX)) {
			return value.replace(RES_DIR_AA2_PREFIX, replaceStr);
		}

		return value;
	}

	/**
	 * 获取配置
	 * @param configKey
	 * @param valueKey
	 * @return
	 * @throws BusinessException
	 */
	public static String getValue(String configKey, String valueKey) throws BusinessException {

		Objects.requireNonNull(configKey, "configKey不能为空！");
		Objects.requireNonNull(valueKey, "valueKey不能为空！");

		String cacheKey = configKey + ":" + valueKey;
		if (cacheConfigMap.containsKey(cacheKey)) {
			return replaceDate(cacheConfigMap.get(cacheKey));
		}

		try {
			ResourceBundle app = ResourceBundle.getBundle(configKey);
			String propVal = app.containsKey(valueKey) ? app.getString(valueKey) : app.getString(valueKey + ".upload.path");
			// 添加到本地缓存里
			if (StringUtils.isNoneBlank(propVal)) {
				cacheConfigMap.put(cacheKey, propVal);
			}

			return replaceDate(propVal);
		} catch (Exception e) {
			LOG.error("获取 {}(configKey), {}(valueKey)配置文件值异常", configKey, valueKey, e);
			throw new BusinessException(false, ResponseEnum.ERROR);
		}
	}

	public static int getYear(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 产生指定长度的无规律数字字符串
	 *
	 * @param length
	 *            生成的随机数的长度
	 * @return 生成的随机字符串 throws 卡号生成异常
	 */
	public static String generateRandomDigitString(int length) {
		StringBuilder buf = new StringBuilder();
		do {
			buf.append((int) (Math.random() * 10));
		} while (buf.length() < length);
		
		return buf.toString();
	}
	
	public static String random(Integer... len) {
		int length = (len == null || len.length <= 0) ? 10 : len[0];
		SecureRandom random = new SecureRandom();
		StringBuilder buf = new StringBuilder(String.valueOf(Math.abs(random.nextInt())));
		while (buf.length() < length) {
			buf.append(String.valueOf(Math.abs(random.nextInt())));
		}

		return buf.substring(0, length);
	}

	public static String getMonth(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int m = c.get(Calendar.MONTH) + 1;
		return m < 10 ? "0" + m : "" + m;
	}

	public static String getHouseRealStoreDir(String key) {
		return getRealStoreDir(SystemConfigEnum.UPLOAD_ROOT_DIR.getKey(), SystemConfigEnum.UPLOAD_ROOT_DIR.getKey());
	}

	public static String getRealStoreDir(String rootDir, String key) {
		String filePath = getValue(key);
		if (StringUtils.isNotBlank(rootDir)) {
			return (rootDir + replaceDate(filePath)).replace(" ", "");
		}
		return replaceDate(filePath).replace(" ", "");
	}

	private static String replaceDate(String filePath) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String dateInfo = simpleDateFormat.format(new Date());
		String[] dateInfoArrays = dateInfo.split("_");
		if (filePath.indexOf("[yyyy]") != -1) {
			filePath = filePath.replace("[yyyy]", dateInfoArrays[0]);
		}
		if (filePath.indexOf("[MM]") != -1) {
			filePath = filePath.replace("[MM]", dateInfoArrays[1]);
		}
		if (filePath.indexOf("[dd]") != -1) {
			filePath = filePath.replace("[dd]", dateInfoArrays[2]);
		}
		if (filePath.indexOf("[HH]") != -1) {
			filePath = filePath.replace("[HH]", dateInfoArrays[3]);
		}
		if (filePath.indexOf("[mm]") != -1) {
			filePath = filePath.replace("[mm]", dateInfoArrays[4]);
		}
		if (filePath.indexOf("[ss]") != -1) {
			filePath = filePath.replace("[ss]", dateInfoArrays[5]);
		}

		return filePath;
	}

	public static String getConverterValue(String... args) {
		if (args != null && args.length >= 2) {
			return StringUtils.isNotBlank(args[0]) ? args[0] : args[1];
		} else if (args != null && args.length >= 1) {
			return StringUtils.isNotBlank(args[0]) ? args[0] : VOID_VALUE;
		}
		return VOID_VALUE;
	}

	/**
	 * 不同系统对应的路径处理
	 * 
	 * @author huangsongbo
	 * @param uploadRoot
	 * @param valueByFileKey
	 * @return
	 */
	public static String dealWithPath(String uploadRoot, String valueByFileKey) {

		// *参数验证 ->start
		if (StringUtils.isEmpty(uploadRoot)) {
			return null;
		}
		if (valueByFileKey == null) {
			valueByFileKey = "linux";
		}
		// *参数验证 ->end

		if (StringUtils.equals("linux", valueByFileKey.trim())) {
			uploadRoot = uploadRoot.replace("\\", "/");
		} else {
			uploadRoot = uploadRoot.replace("/", "\\");
		}
		return uploadRoot;
	}

	/**
	 * 通过路径获取文件名
	 * 
	 * @author huangsongbo
	 * @param picPath
	 * @return
	 */
	public static String getFileNameByPath(String picPath) {
		// 参数验证 ->start
		if (StringUtils.isEmpty(picPath)) {
			return null;
		}
		// 参数验证 ->end

		return picPath.substring(picPath.lastIndexOf("/") + 1, picPath.length());
	}

	/**
	 * 根据文件类型生成名字
	 *
	 * @param fileType .jpg(jpg)、png(.png)等
	 * @return 时间戳 + '-' + 6位随机 +"_1" + fileType
	 */
	public static String getFileName(String fileType) {
		fileType = fileType.matches(Regex.FILE_SUFFIX.getValue()) ? fileType : _DOT + fileType;
		return random(6) + _LINE_SPARTA + Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_1" + fileType;
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static String replaceString(String regex, String replacement) {
		String result = "";
		if (StringUtils.isEmpty(regex)) {
			return regex;
		}
		if (regex.contains(replacement)) {
			String posIndexPath = regex.replaceAll("\"", "");
			if (StringUtils.isEmpty(posIndexPath)) {
				return regex;
			}
			String[] posIndexPathArr = posIndexPath.split(replacement);
			for (String posIndex : posIndexPathArr) {
				if (StringUtils.isNotEmpty(posIndex)) {
					if (posIndex.length() == 1) {
						result += "0" + posIndex;
					} else {
						result += posIndex;
					}
				}
			}
			return result;
		} else {
			return regex;
		}
	}

	/**
	 * 上传文件/copy文件重命名 copy from 通用版本
	 * 
	 * @author huangsongbo
	 * @param resourcesPath
	 * @return
	 */
	public static String getNewFileName(String resourcesPath) {
		// 参数验证 ->start
		if (StringUtils.isEmpty(resourcesPath)) {
			return Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_1";
		}
		// 参数验证 ->end

		String fileName = resourcesPath.substring(resourcesPath.lastIndexOf("/") + 1, resourcesPath.length());
		return getFileName(fileName.substring(fileName.lastIndexOf(".")));
	}

	/**
	 * List to Str eg: ("aaa","bbb") ->aaa,bbb
	 * 
	 * @author huangsongbo
	 * @param list
	 * @return
	 */
	public static String listToStr(List<Long> list) {
		// 参数验证 ->start
		if (list == null || list.size() == 0) {
			return null;
		}
		// 参数验证 ->end

		StringBuilder stringBuffer = new StringBuilder();
		for (Long num : list) {
			stringBuffer.append(num).append(",");
		}
		return stringBuffer.substring(0, stringBuffer.length() - 1);
	}

	public static List<Long> getLongListByStr(String drawBindParentProductidListStr) {
		String logMesPrefix = "Utils.getLongListByStr -> ";
		List<Long> returnList = new ArrayList<Long>();
		String[] strs = drawBindParentProductidListStr.split(",");
		if (strs.length == 0) {
			return null;
		}
		for (String str : strs) {
			try {
				returnList.add(Long.valueOf(str));
			} catch (Exception e) {
				LOG.error(logMesPrefix + e);
				return null;
			}
		}
		return returnList;
	}

	public static Integer getSmallPicId(String args, String type) {
		if (StringUtils.isBlank(args)) {
			return -1;
		}

		Map<String, String> msgMap = getMapFromStr(args);
		String picIdStr = msgMap.get(type);
		return StringUtils.isBlank(msgMap.get(type)) ? null : Integer.valueOf(picIdStr);
	}

	public static Map<String, String> getMapFromStr(String fileDesc) {
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
	 * 0、平台数据； 1、个人数据
	 * 
	 * @return
	 */
	public static Integer getUserDataType() {
		return HouseAuthUtils.hasDataPermission() ? DrawBaseHouseConstant.DATA_PERSONAL : DrawBaseHouseConstant.DATA_PLATFORM;
	}
	
	/**
	 * 户型公开状态 0、非公开 1、公开
	 * 
	 * @return
	 */
	public static Integer getHouseIsPublic() {
		// v1.0.8 JIRA COMMON-83
		return DrawBaseHouseConstant.HOUSE_IS_PUBLIC;
		// v1.0.2，绘制员数据需要手动公开，其它自动公开
		// return HouseAuthUtils.hasDrawPermission() ? DrawBaseHouseConstant.HOUSE_NON_PUBLIC : DrawBaseHouseConstant.HOUSE_IS_PUBLIC;
	}

	public static String replace(String message, Object... args) {
		Objects.requireNonNull(message, "参数 message不能为空");
		if (args == null || args.length <= 0) {
			return message;
		}
		return String.format(message, args);
	}

	public static String getDecimalFormat(double arg) {
		if (arg <= 0) {
			return "0.00";
		}
		return new DecimalFormat("######0.00").format(arg);
	}
	
	/**
	 * 获取sysCode
	 * @param len
	 * @return
	 */
	public static String getSysCode(int len) {
		return Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + random(len <= 0 ? 6 : len);
	}

	/**
	 * @param arg
	 */
	public static void requireGreaterZero(Object arg) {
		requireGreaterZero(arg, null);
	}

	/**
	 * @param arg
	 * @param message
	 */
	public static void requireGreaterZero(Object arg, String message) {
		if (arg == null) {
			throw new NullPointerException(message);
		}

		if (arg instanceof Integer) {
			Integer iVal = Integer.parseInt(arg + "");
			if (iVal <= 0) {
				throw new BizException(message);
			}
		} else if (arg instanceof Long) {
			Long lVal = Long.parseLong(arg + "");
			if (lVal <= 0) {
				throw new BizException(message);
			}
		}
	}
}