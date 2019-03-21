package com.nork.pano.model.scene;

import com.nork.cityunion.model.vo.UnionStoreVo;
import com.nork.design.model.ProductsCostType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */
public class UnionStoreProductsCostModel implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 同城联盟打包分享方案发布Id **/
	private Integer releaseId ;
	
	/** 当前场景图id**/
	private Integer renderPicId;

    public Integer getReleaseId() {
      return releaseId;
    }
  
    public void setReleaseId(Integer releaseId) {
      this.releaseId = releaseId;
    }

    public Integer getRenderPicId() {
      return renderPicId;
    }

    public void setRenderPicId(Integer renderPicId) {
      this.renderPicId = renderPicId;
    }
  
   
	
	
}
