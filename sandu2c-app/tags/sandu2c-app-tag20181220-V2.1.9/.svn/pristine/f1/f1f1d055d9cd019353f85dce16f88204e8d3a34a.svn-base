package com.sandu.user.model.view;

import com.sandu.user.model.UserBehavior;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

@Data
public class UserBehaviorVO extends UserBehavior implements Serializable {

    public static final Map<String, String> keyMap = new HashMap<>();

    public static final String FIELD_SUFFIX = "_click_count";
    public static final String WX_MARK = "_wx";
    public static final String PYQ_MARK = "_pyq";

    static {
        Field[] fields = UserBehaviorVO.class.getFields();
        List<Field> markList = new ArrayList<>();
        List<Field> keyWordList = new ArrayList<>();
        List<Integer> behaviorTypeList = Arrays.asList(UserBehavior.MEDIATION_USER, UserBehavior.NORMAL_USER);
        for (Field field : fields) {
            if (field.getName().endsWith("_MARK")) {
                markList.add(field);
            } else {
                keyWordList.add(field);
            }
        }
        for (Field k : keyWordList) {
            for (Integer type : behaviorTypeList) {
                String typeSuffix = "_" + type;
                keyMap.put(k.getName().toLowerCase() + UserBehaviorVO.FIELD_SUFFIX + typeSuffix, 0 + "");
                for (Field m : markList) {
                    String mark = m.getName().toLowerCase().replace("_mark", "");
                    keyMap.put(k.getName().toLowerCase() + "_" + mark + UserBehaviorVO.FIELD_SUFFIX + typeSuffix, 0 + "");
                }
            }
        }
    }

    @ApiModelProperty("日期")
    private Date count;
    @ApiModelProperty("新增中介数")
    private Integer intermediary_new_count;
    @ApiModelProperty("中介总数量")
    private Integer intermediary_total_count;
    @ApiModelProperty("新增客户数")
    private Integer user_new_count;
    @ApiModelProperty("客户总数量")
    private Integer user_total_count;

    /**
     * 立即邀请点击总次数
     **/
    public static final String INVITE = "invite";
    /**
     * 分享方案点击总次数
     **/
    public static final String PLAN_RECOMMEND = "plan_recommend";
    /**
     * 首页立即邀请--【立即推荐】的点击次数
     **/
    public static final String INDEX_RECOMMEND = "index_recommend";
    /**
     * 首页我的邀请--【立即邀请】的点击次数
     **/
    public static final String INDEX_MINE_INVITE = "index_mine_invite";
    /**
     * 首页分享赚banner--【我要分享】的点击次数
     **/
    public static final String INDEX_BANNER_SHARE = "index_banner_share";
    /**
     * 首页分享赚banner--【去分享赚佣金】的点击次数
     **/
    public static final String INDEX_BANNER_SHARE_TO = "index_banner_share_to";
    /**
     * 首页方案列表的分享赚
     **/
    public static final String INDEX_PLAN_SHARE = "index_plan_share";
    /**
     * 720详情分享按钮点击次数
     **/
    public static final String PLAN720_SHARE = "plan720_share";

}
