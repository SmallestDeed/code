package com.sandu.product.model.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.sandu.common.properties.AppProperties;
import com.sandu.common.util.Utils;
import com.sandu.common.util.collections.Lists;
import com.sandu.product.model.SpecialProductTypeInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ProductUtilsConstant {

	/**
	 * 白膜背景墙valukeyList
	 */
	public static List<String> beijingValuekeyList = Utils.getListFromStr(
				Utils.getValueByFileKey(AppProperties.APP, AppProperties.SMALLPRODUCTTYPE_BEIJINGWALL_FILEKEY, "basic_dians,basic_shaf,basic_cant,basic_chuangt,basic_xingx,basic_beij")
				);
	
	public static Map<String, Object> showMoreSmallTypeMap = getshowMoreSmallTypeMap();
	
	public static Map<String, List<String>> specialProductTypeMap = getSpecialProductTypeMap();
	
	/**
	 * 解析配置special.productType 
	 * 
	 * @author huangsongbo
	 * @return
	 */
	private static Map<String, List<String>> getSpecialProductTypeMap() {
		Map<String, List<String>> returnMap = new HashMap<String, List<String>>();
		
		String specialProductTypeStr =  Utils.getValueByFileKey(AppProperties.APP, AppProperties.SPECIAL_PRODUCTTYPE_FILEKEY, "[{\"productTypeKey\":\"dim\",\"productSmallTypeKeys\":\"dic,bodx,tijx\"},{\"productTypeKey\":\"meng\",\"productSmallTypeKeys\":\"wosm,yusm,yangtm,weisjm,chufm,fangjm,ruhm,mens,ment,fangjms,fangjmt,fangjmbs\"},{\"productTypeKey\":\"pe\",\"productSmallTypeKeys\":\"arpe,otpe,crpe,pepe,pipe,mipe\"},{\"productTypeKey\":\"ho\",\"productSmallTypeKeys\":\"caho,cuho,boho\"},{\"productTypeKey\":\"el\",\"productSmallTypeKeys\":\"coel,hmel,wael,arel,frel,tvel\"},{\"productTypeKey\":\"li\",\"productSmallTypeKeys\":\"tali,ldli,bdli\"},{\"productTypeKey\":\"kiel\",\"productSmallTypeKeys\":\"hese,diki,ovki,miki,stki,laki,coki\"},{\"productTypeKey\":\"ki\",\"productSmallTypeKeys\":\"agki,juki,soki,elki,riki,meki,dgki,acki,cuki,haki,daki,drki,buki,deki\"},{\"productTypeKey\":\"ba\",\"productSmallTypeKeys\":\"dyba,ltba,mpba,btba,edba,meba,coba,sqba,haba,acba,yuba,asba,baba,toba,miba,ahba,caba\"},{\"productTypeKey\":\"qiangm\",\"productSmallTypeKeys\":\"mengk,chuangk,dians,shaf,cant,chuangt,xingx,beijing,yaox,zuh\"},{\"productTypeKey\":\"kp\",\"productSmallTypeKeys\":\"cskp,hokp,crkp,ctkp,chkp,fpkp,rskp,sskp,kfkp,sikp\"},{\"productTypeKey\":\"bp\",\"productSmallTypeKeys\":\"wbbp,fwbp,tbbp,chbp,ptbp,hobp,trbp,twbp,smbp,tfbp,tcbp\"},{\"productTypeKey\":\"ca\",\"productSmallTypeKeys\":\"tvca,scca\"},{\"productTypeKey\":\"ta\",\"productSmallTypeKeys\":\"cota\"},{\"productTypeKey\":\"cuki\",\"productSmallTypeKeys\":\"cmki,ccki,cski,cxki,czki,cqkii,ctki,ctski,ctjki,ctyki,ctzki\"},{\"productTypeKey\":\"dgki\",\"productSmallTypeKeys\":\"dmki,dfki,dyki,dzki,dqki\"}]");
		JSONArray jsonArray=JSONArray.fromObject(specialProductTypeStr);
		@SuppressWarnings({ "deprecation", "unchecked" })
		List<SpecialProductTypeInfo> specialProductTypeInfoList = JSONArray.toList(jsonArray, SpecialProductTypeInfo.class);
		if(Lists.isEmpty(specialProductTypeInfoList)) {
			return null;
		}
		for(SpecialProductTypeInfo specialProductTypeInfo : specialProductTypeInfoList) {
			returnMap.put(specialProductTypeInfo.getProductTypeKey(), Utils.getListFromStr(specialProductTypeInfo.getProductSmallTypeKeys()));
		}
		return returnMap;
	}
	
	/**
	 * 得到单品搜索相关的配置(同时显示多个小类)
	 * @author huangsongbo
	 * @return
	 */
	public static Map<String, Object> getshowMoreSmallTypeMap() {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String showMoreSmallType = Utils.getValue(
				"product.searchProduct.showMoreSmallType", 
				"[{\"checkType\":\"yaox\",\"showType\":\"yaox,qiangz\"},{\"checkType\":\"bodx\",\"showType\":\"bodx,diz\"},{\"checkType\":\"tijx\",\"showType\":\"tijx,diz\"},{\"checkType\":\"dians,shaf,cant\",\"showType\":\"dians,shaf,cant\"}]");
		if(StringUtils.isBlank(showMoreSmallType)){
			return null;
		}
		JSONArray showMoreSmallTypeJson = JSONArray.fromObject(showMoreSmallType);
		// product.searchProduct.showMoreSmallType=[{"checkType":"chufm,yangtm","showType":"chufm,yangtm,weisjm"}]
		Map<String, List<String>> showMoreSmallTypeInfoMap = new HashMap<String, List<String>>();
		// chufm,yangtm
		List<String> keyList = new ArrayList<String>();
		// chufm,yangtm
		List<String> valueList = new ArrayList<String>();
		if(showMoreSmallTypeJson != null && showMoreSmallTypeJson.size() > 0){
			for (int i = 0; i < showMoreSmallTypeJson.size(); i++) {
				JSONObject jsonObject = showMoreSmallTypeJson.getJSONObject(i);
				String checkType = jsonObject.getString("checkType");
				if(StringUtils.isBlank(checkType)){
					continue;
				}
				String showType = jsonObject.getString("showType");
				List<String> checkTypeList = Utils.getListFromStr(checkType);
				List<String> showTypeList = Utils.getListFromStr(showType);
				keyList.removeAll(checkTypeList);
				keyList.addAll(checkTypeList);
				valueList.removeAll(showTypeList);
				valueList.addAll(showTypeList);
				for(String checkTypeItem : checkTypeList){
					if(showMoreSmallTypeInfoMap.containsKey(checkTypeItem)){
						// 更新
						List<String> showTypeListUpdate = new ArrayList<String>();
						showTypeListUpdate.addAll(showMoreSmallTypeInfoMap.get(checkTypeItem));
						showTypeListUpdate.removeAll(showTypeList);
						showTypeListUpdate.addAll(showTypeList);
						showMoreSmallTypeInfoMap.put(checkTypeItem, showTypeListUpdate);
					}else{
						// 添加新元素
						showMoreSmallTypeInfoMap.put(checkTypeItem, showTypeList);
					}
				}
			}
		}
		
		// 记录diz:tjx(地砖能被踢脚线搜出,用于后面查看地砖能不能搜出踢脚线) ->start
		// diz:tjx(地砖能被踢脚线搜出,用于后面查看地砖能不能搜出踢脚线)
		// valueList和keyList差集(决定搜索是否看白膜,比如腰线能搜出地砖,地砖不能搜出腰线,则,地砖产品搜索看白膜)
		List<String> differenceList = new ArrayList<String>();
		Map<String, List<String>> differenceListMap = new HashMap<String, List<String>>();
		/*List<String> infoList = new ArrayList<String>();*/
		for(String key : showMoreSmallTypeInfoMap.keySet()){
			for(String str : showMoreSmallTypeInfoMap.get(key)){
				/*infoList.add(str + ":" + key);*/
				if(showMoreSmallTypeInfoMap.containsKey(str)){
					// diz有配置,看看diz能不能搜出tijx
					if(showMoreSmallTypeInfoMap.get(str).indexOf(key) != -1){
						// diz能搜出tijx
						
					}else{
						// diz不能搜出tijx
						if(differenceListMap.containsKey(str)){
							differenceListMap.get(str).remove(key);
							differenceListMap.get(str).add(key);
						}else{
							List<String> list = new ArrayList<String>();
							list.add(key);
							differenceListMap.put(str, list);
						}
					}
				}else{
					// 没有diz的配置
					if(differenceListMap.containsKey(str)){
						differenceListMap.get(str).remove(key);
						differenceListMap.get(str).add(key);
					}else{
						List<String> list = new ArrayList<String>();
						list.add(key);
						differenceListMap.put(str, list);
					}
				}
			}
		}
		// 记录diz:tjx(地砖能被踢脚线搜出,用于后面查看地砖能不能搜出踢脚线) ->end
		returnMap.put("differenceListMap", differenceListMap);
		returnMap.put("differenceList", differenceList);
		returnMap.put("showMoreSmallTypeInfoMap", showMoreSmallTypeInfoMap);
		returnMap.put("keyList", keyList);
		returnMap.put("valueList", valueList);
		return returnMap;
	}
	
}
