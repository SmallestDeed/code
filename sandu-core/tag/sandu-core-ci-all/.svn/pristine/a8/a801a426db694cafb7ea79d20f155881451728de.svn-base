package com.sandu.api.base.input;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Sandu
 * @ClassName InteractiveZoneReplyQuery
 * @date 2018/11/6
 */

@Data
public class InteractiveZoneReplyQuery implements Serializable {

	@NotNull
	private Integer businessId;

	private String content;

		@DateTimeFormat(pattern = "yyyy-MM-dd:HH-mm-ss")
	private Date startTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd:HH-mm-ss")
	private Date endTime;

	private Integer page;

	private Integer limit;


}
