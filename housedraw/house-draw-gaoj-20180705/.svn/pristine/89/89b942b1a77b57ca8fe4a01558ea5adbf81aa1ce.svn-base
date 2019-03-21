package com.sandu.common.constant.house;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * 产品上下状态 house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年1月23日
 */

public interface ProductPutawayState {
	
	enum Product {
		NO_SHELVE(0, "未上架"), SHELVE(1, "已上架"), TESTING(2, "测试中"), RELEASE(3, "已发布"), OFF_SHELVE(4, "已下架");
		
		private int value;
		private String remark;

		public int getValue() {
			return value;
		}

		public String getRemark() {
			return remark;
		}

		Product(int value) {
			this.value = value;
		}

		Product(int value, String remark) {
			this.value = value;
			this.remark = remark;
		}
	}
	
	enum GroupProduct {
		SHELVE(1, "已上架"), RELEASE(3, "发布");

		public Integer value;
		private String remark;

		public Integer getValue() {
			return value;
		}

		public String getRemark() {
			return remark;
		}

		GroupProduct(Integer value, String remark) {
			this.value = value;
			this.remark = remark;
		}
	}
}
