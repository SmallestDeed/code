package com.sandu.api.res.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 * 房型和户型的关系对象
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/19
 */
@Data
public class ResModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
    /**
     * 系统编码
     **/
    private String sysCode;
    /**
     * 创建者
     **/
    private String creator;
    /**
     * 创建时间
     **/
    private Date gmtCreate;
    /**
     * 修改人
     **/
    private String modifier;
    /**
     * 修改时间
     **/
    private Date gmtModified;
    /**
     * 是否删除
     **/
    private Integer isDeleted;
    /**
     * 模型编码
     **/
    private String modelCode;
    /**
     * 模型名称
     **/
    private String modelName;
    /**
     * 模型文件名称
     **/
    private String modelFileName;
    /**
     * 模型文件类型
     **/
    private String modelType;
    /**
     * 模型大小
     **/
    private String modelSize;
    /**
     * 模型后缀
     **/
    private String modelSuffix;
    /**
     * 模型级别
     **/
    private String modelLevel;
    /**
     * 模型路径
     **/
    private String modelPath;
    /**
     * 模型描述
     **/
    private String modelDesc;
    /**
     * 模型排序
     **/
    private Integer modelOrdering;
    /**
     * 文件key
     */
    private String fileKey;
    /**
     * key标识（多个）
     **/
    private String fileKeys;
    /**
     * 业务IDS
     **/
    private String businessIds;
    /**
     * 字符备用4
     **/
    private String att4;
    /**
     * 字符备用5
     **/
    private String att5;
    /**
     * 字符备用6
     **/
    private String att6;
    /**
     * 时间备用1
     **/
    private Date dateAtt1;
    /**
     * 时间备用2
     **/
    private Date dateAtt2;
    /**
     * 业务id
     */
    private Integer businessId;
    /**
     * 整数备用2
     **/
    private Integer numAtt2;
    /**
     * 物理文件是否存在，0为不存在，1为存在
     **/
    private Double numAtt3;
    /**
     * 归档状态
     **/
    private Double numAtt4;
    /**
     * 备注
     **/
    private String remark;
    /**
     * 长
     */
    private Integer length;
    /**
     * 高
     */
    private Integer height;
    /**
     * 宽
     */
    private Integer width;
    /**
     * 最小高度
     */
    private Integer minHeight;
    /**
     * 是否是模型共享
     **/
    private Integer isModelShare;
    /**
     * 是否解压,Integer,0:false;1:true
     */
    private Integer isDecompress;

    /**
     * 分类ids 用 , 隔开.<p>
     * res_model.category_ids
     */
    private String categoryIds;
    
    /**
     * 缩略图地址 <p>
     * res_model.thumb_pic_path
     */
    private String thumbPicPath;

    /**
     * 分类的names 用, 分开<p>
     * res_model.category_names
     */
    private String categoryNames;

    /**
     * 转换状态 ING 转换中  SUCCESS 转换成功  FAIL 转换失败<p>
     * res_model.trans_status
     */
    private String transStatus;
}
