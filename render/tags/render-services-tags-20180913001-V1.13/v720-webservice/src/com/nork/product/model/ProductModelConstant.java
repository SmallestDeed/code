package com.nork.product.model;

import com.nork.common.properties.AppProperties;
import com.nork.common.util.Utils;

/**
 * 产品模块常量
 * 
 * @author huangsongbo
 *
 */
public class ProductModelConstant {
	
	/**
	 * 墙面结构产品,用于识别上/中/下墙的属性code
	 */
	public static final String PRODUCTATTRCODE_QIANGM = "group";
	
	/**
	 * 地面结构产品,用于识别是第几圈的产品的属性code
	 */
	public static final String PRODUCTATTRCODE_DIM = "structureSign";

	/**
	 * 品牌配置:显示无品牌产品
	 */
	public static final Integer STATUSSHOWWU_SHOW = 1;
	
	/**
	 * 品牌配置:不显示无品牌产品
	 */
	public static final Integer STATUSSHOWWU_NOSHOW = 0;

	/**
	 * 无品牌英文简称
	 */
	public static final String BRAND_REFERRED_WU = "wu";


	/**
	 * 床头柜朝向属性
	 */
	public static final String PRODUCTATTRCODE_CHAOXIANG = "chaoxiang";
	
	public static final String SERIESCONFIG = Utils.getValueByFileKey(AppProperties.APP, AppProperties.DESIGN_PRODUCT_SERIES_CONFIG_FILEKEY, 
			"[{\"smallTypeValuekeyInfo\":[{\"seriesProductType\":\"basic_cmki,basic_ccki,basic_cski,basic_cxki,basic_czki,basic_cqki,\",\"seriesSign\":\"1\"},{\"seriesProductType\":\"basic_ctki\",\"seriesSign\":\"2\"},{\"seriesProductType\":\"basic_dmki,basic_dfki,basic_dyki,basic_dzki,basic_dqki\",\"seriesSign\":\"3\"}],\"key\":\"S\"}]");

	/**
	 * 产品搜索全铺长度拉伸缩放分类长度比例配置value
	 */
	public static final String PRODUCT_SEARCH_STRETCH_CONFIG = Utils.getValueByFileKey(AppProperties.APP, AppProperties.APP_SEARCH_STRETCH_ZOOM_PRODUCT_TYPE,
			"[{\"productTypeKey\":\"basic_cmki,cmki,basic_ccki,ccki,basic_cski,cski,basic_czki,czki,basic_cqki,cqki,basic_ctki,ctki,basic_ctzki,ctzki,basic_ctyki,ctyki,basic_ctjki,ctjki,basic_ctski,ctski,basic_dmki,dmki,basic_dfki,dfki,basic_dzki,dzki,basic_dqki,dqki,basic_dyba,dyba,basic_meba,meba,basic_btba,btba\",\"stretchLength\":\"5\",\"isHeightFilter\":\"no\"},{\"productTypeKey\":\"dians,shaf,cant,chuangt,xingx,beijing,basic_dians,basic_shaf,basic_cant,basic_chuangt,basic_xingx,basic_beij\",\"stretchLength\":\"10\",\"isHeightFilter\":\"yes\"},{\"productTypeKey\":\"basic_ahba,ahba,basic_dyca,dyca,basic_waca,waca,basic_boca,boca,basic_deca,deca\",\"stretchLength\":\"10\",\"isHeightFilter\":\"no\"},{\"productTypeKey\":\"basic_dzho,dzho\",\"stretchLength\":\"20\",\"isHeightFilter\":\"yes\"}]");

	//是否根据高度过滤key
	public static final String IS_HEIGHT_FILTER = "isHeightFilter";

	//是否根据高度过滤value = yes
	public static final String HEIGHT_FILTER_VALUE_YES = "yes";

	//是否根据高度过滤value = no
	public static final String HEIGHT_FILTER_VALUE_NO = "no";

	//伸缩长度key
	public static final String STRETCH_LENGTH = "stretchLength";

	/**
	 * 产品互搜、互相匹配配置value
	 */
	public static final String PRODUCT_SEARCHPRODUCT_SHOWMORESMALLTYPE_VALUE = Utils.getValueByFileKey(AppProperties.APP, AppProperties.PRODUCT_SEARCHPRODUCT_SHOWMORESMALLTYPE_FILEKEY,
			"[{\"checkType\":\"yaox\",\"showType\":\"yaox,qiangz,qiangs\"},{\"checkType\":\"bodx\",\"showType\":\"bodx,diz,dis\"},{\"checkType\":\"tijx\",\"showType\":\"tijx,diz,dis\"},{\"checkType\":\"dians,shaf,cant,chuangt,xingx,beijing,zuh\",\"showType\":\"dians,shaf,cant,chuangt,xingx,beijing,zuh\"},{\"checkType\":\"arpe,otpe\",\"showType\":\"arpe,otpe\"},{\"checkType\":\"crpe,pepe,pipe,mipe\",\"showType\":\"crpe,pepe,pipe,mipe\"},{\"checkType\":\"dyba\",\"showType\":\"dyba,btba\"},{\"checkType\":\"dyca\",\"showType\":\"dyca,waca\"},{\"checkType\":\"cmki,ccki,cski\",\"showType\":\"cmki,ccki,cski\"},{\"checkType\":\"dmki,dfki\",\"showType\":\"dmki,dfki\"}]");

}
