package com.sandu.node.constant;

public class NodeInfoConstant {
    // 每次数据同步条数
    public static final int SYNCHRONIZE_DATA_SIZE = 10000;
    // 节点信息桶前缀
    public static final String BUCKET_KEY_PREFIX_NODE_INFO = "cn:";
    // 节点详情桶前缀
    public static final String BUCKET_KEY_PREFIX_NODE_INFO_DETAIL = "nn:";
    // 用户节点关系桶前缀
    public static final String BUCKET_KEY_PREFIX_USER_NODE_INFO_REL = "un:";
    // 用户内容关系桶前缀
    public static final String BUCKET_KEY_PREFIX_USER_CONTENT_REL = "uc:";
    // 需要从缓存同步到数据库的数据集合名称
    public static final String NODE_INFO_DATA_SYNCHRONIZED_SET = "syncSet";
    // 节点类型
    public static final String NODE_INFO_TYPE = "nodeType";
    // 详情类型
    public static final String NODE_INFO_DETAIL_TYPE = "detailType";
    // 桶的位数，例：假如大约有1亿数据，最接近1亿的是2的27次方减1，则bit=26
    public static final int BUCKET_KEY_BIT_LENGTH = 16;
    // 数据版本key
    public static final String NODE_INFO_DATA_VERSION_KEY = "nodeInfoDataVersion";
    // 数据同步线程KEY
    public static final String NODE_INFO_DATA_SYNCHRONIZED_THREAD_KEY = "nodeInfoDataSyncThread";


    // 节点类型-推荐方案（对应数据字典中type为nodeType的value）
    public static final byte SYSTEM_DICTIONARY_NODE_TYPE_RECOMMENDED = 1;
    // 节点类型-全屋方案（对应数据字典中type为nodeType的value）
    public static final byte SYSTEM_DICTIONARY_NODE_TYPE_FULL_HOUSE = 2;
    // 节点类型-户型（对应数据字典中type为nodeType的value）
    public static final byte SYSTEM_DICTIONARY_NODE_TYPE_HOUSE = 3;
    // 节点类型-供求信息（对应数据字典中type为nodeType的value）
    public static final byte SYSTEM_DICTIONARY_NODE_TYPE_SUPPLY_DEMAND = 4;
    // 节点类型-用户评论（对应数据字典中type为nodeType的value）
    public static final byte SYSTEM_DICTIONARY_NODE_TYPE_USER_REVIEW = 5;
    // 节点类型-店铺（对应数据字典中type为nodeType的value）
    public static final byte SYSTEM_DICTIONARY_NODE_TYPE_SHOP = 6;



    // 详情类型-收藏（对应数据字典中type为detailType的value）
    public static final byte SYSTEM_DICTIONARY_DETAIL_TYPE_FAVORITE = 1;
    // 详情类型-点赞（对应数据字典中type为detailType的value）
    public static final byte SYSTEM_DICTIONARY_DETAIL_TYPE_LIKE = 2;
    // 详情类型-浏览（对应数据字典中type为detailType的value）
    public static final byte SYSTEM_DICTIONARY_DETAIL_TYPE_VIEW = 3;
    // 详情类型-评论（对应数据字典中type为detailType的value）
    public static final byte SYSTEM_DICTIONARY_DETAIL_TYPE_COMMENT = 4;
    // 详情类型-虚拟点赞（对应数据字典中type为detailType的value）
    public static final byte SYSTEM_DICTIONARY_DETAIL_TYPE_VIRTUAL_FAVORITE = 5;
    // 详情类型-虚拟收藏（对应数据字典中type为detailType的value）
    public static final byte SYSTEM_DICTIONARY_DETAIL_TYPE_VIRTUA_LIKE = 6;
    // 详情类型-虚拟浏览（对应数据字典中type为detailType的value）
    public static final byte SYSTEM_DICTIONARY_DETAIL_TYPE_VIRTUA_VIEW = 7;
}
