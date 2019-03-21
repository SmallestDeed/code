package com.nork.design.controller.web;

import java.io.Serializable;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 一键装修
 * 
 * @author Steve.Zheng
 * 
 */
@Controller
@RequestMapping("/{style}/web/design/intelligenceDecoration")
public class IntelligenceDecorationController {
	private static Logger logger = Logger
			.getLogger(IntelligenceDecorationController.class);


	public class PosNameInfo implements Serializable{
		
		private static final long serialVersionUID = 8839570865694685026L;

		private String posName;
		
		private Integer deignPlanProductId;

		public String getPosName() {
			return posName;
		}

		public void setPosName(String posName) {
			this.posName = posName;
		}

		public Integer getDeignPlanProductId() {
			return deignPlanProductId;
		}

		public void setDeignPlanProductId(Integer deignPlanProductId) {
			this.deignPlanProductId = deignPlanProductId;
		}
		
	}
	
}
