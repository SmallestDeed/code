package com.sandu.commons;

import com.sandu.util.JsonParser;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
    public static String getValueByFileKeyOriginal(ResourceBundle configUtil,
                                                   String key, String defalut) {
        String value = "";
        try {
            /* 如果配置文件为res.properties,key值应该加上.upload.path */
//            if (configUtil.equals(ResProperties.RES)) {
//                if (StringUtils.equals("product.groupProduct.file.location.path", key)) {
//                    key = "product.groupProduct.file.location";
//                }
//                key = key.replace(".upload.path", "");
//                key += ".upload.path";
//            }
            // value=configUtil.getValue(key);
            value = configUtil.getString(key);
            /* 检测带有%%的情况 */
            /* value="%product.baseProduct.pic.upload.path%web/" */
            int num = value.length() - value.replace("%", "").length();
            /* 两个"%" */
            if (num == 2) {
                int startIndex = value.indexOf("%");
                int endIndex = value.lastIndexOf("%");
                /* key="product.baseProduct.pic.upload.path" */
                String appKey = value.substring(startIndex + 1, endIndex);
                String value3 = value.substring(endIndex + 1, value.length());
                // String value2=configUtil.getValue(appKey);
                String value2 = configUtil.getString(appKey);
                value = value2 + value3;
            }
            /* 检测带有%%的情况->end */

            // 处理时间 ->start
            /*value = Utils.replaceDate(value);*/
            // 处理时间 ->end

        } catch (Exception e) {
            // e.printStackTrace();
            value = defalut;
        }
        return value;
    }

    public static String getValueByFileKey(ResourceBundle configUtil,
                                           String key, String defalut) {
        String returnStr = getValueByFileKeyOriginal(configUtil, key, defalut);
        return replaceDate(returnStr, null);
    }


    /**
     * 路径中含有/[yyyy]/[MM]/[dd]/[HH]/[mm]/[ss]/
     *
     * @param filePath
     * @return
     * @author huangsongbo
     */
    private static String replaceDate(String filePath, Date date) {
        if (date == null) {
            date = new Date();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String dateInfo = simpleDateFormat.format(date);
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

    /**
     * String转化成List<JSONObject>
     *
     * @param str
     * @return
     */
    public static List<Map> getJSONObjectByString(String str) {
        List<Object> encryptWayJsonArray = JsonParser.fromJson2List(str, Object.class);
//        JSONArray encryptWayJsonArray = JSONArray.fromObject(str);
        List<Map> jsonObjectList = new ArrayList<>();
        if (encryptWayJsonArray != null && encryptWayJsonArray.size() > 0) {
            for (int i = 0; i < encryptWayJsonArray.size(); i++) {
                jsonObjectList.add((Map) encryptWayJsonArray.get(i));
            }
        }
        return jsonObjectList;
    }


    /**
     * str(逗号隔开格式)转化为list
     *
     * @param str
     * @return
     * @author huangsongbo
     */

    public static List<String> getListFromStr(String str) {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isBlank(str)) {
            return list;
        }
        String[] strs = str.split(",");
        list = Arrays.asList(strs);
        return list;
    }

    public static int getIntValue(String v) {
        return getIntValue(v, -1);
    }

    /***** 将给出的字符串v转换成整形值返回，如果例外则返回预给值def ************/
    public static int getIntValue(String v, int def) {
        try {
            return Integer.parseInt(v);
        } catch (Exception ex) {
            return def;
        }
    }

	public static String getRelativeUrlByAbsolutePath(String serverFilePath) {
		if (StringUtils.isEmpty(serverFilePath)) {
			return "";
		} else {
			serverFilePath = dealWithPath(serverFilePath, "linux");
			String[] modelNames = new String[]{"AA", "BB", "CC", "DD", "EE", "FF"};
			List<String> modelNameList = Arrays.asList(modelNames);
			int index = -1;
			Iterator var4 = modelNameList.iterator();

			while(var4.hasNext()) {
				String modelName = (String)var4.next();
				int indexTemp = serverFilePath.indexOf("/" + modelName + "/");
				if (indexTemp != -1) {
					index = indexTemp;
					break;
				}
			}

			return index == -1 ? "" : serverFilePath.substring(index);
		}
	}
	public static String dealWithPath(String uploadRoot, String valueByFileKey) {
		if (StringUtils.isEmpty(uploadRoot)) {
			return null;
		} else {
			if (valueByFileKey == null) {
				valueByFileKey = "linux";
			}

			if (StringUtils.equals("linux", valueByFileKey.trim())) {
				uploadRoot = uploadRoot.replace("\\", "/");
			} else {
				uploadRoot = uploadRoot.replace("/", "\\");
			}

			return uploadRoot;
		}
	}
}
