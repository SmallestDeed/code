package com.sandu.api.base.output;

import com.sandu.api.base.model.InteractiveZoneTopic;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 * @ClassName InteractiveZoneTopicDetail
 * @date 2019/3/14-19:59$
 */
@Data
public class InteractiveZoneTopicDetail extends InteractiveZoneTopic implements Serializable {


	/***********biz field***********/
	private Integer reviewsCount;

	private Integer viewCount;

	private String authorPic;

	private List<PicBean> pics;

	private String planPicUrl;

	private String housePicUrl;


}
