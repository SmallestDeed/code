package com.sandu.api.base.output;

import com.sandu.api.base.model.InteractiveZoneReply;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 * @ClassName InteractiveZoneReplyVO
 * @date 2019/3/18-19:49$
 */
@Data
public class InteractiveZoneReplyVO extends InteractiveZoneReply implements Serializable {
	private Integer likeCount;

	private String authorPic;

	private String userName;

	private String planPicUrl;

	private String housePicUrl;

	private List<PicBean> pics;

}
