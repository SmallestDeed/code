package com.sandu.matadata.enums;

/***
 * 
 * @author 平台类别
 *
 */
public enum PlatformType {
	/***
	 *同城联盟
	 */
	Union(3),
	/***
	 *选装网
	 */
	Decoration(2),
	/***
	 *企业小程序
	 */
	MiniApp(1);
	private int value = 0;

	private PlatformType(int value) {
		this.value = value;
	}

	public int value() {
		return this.value;
	}
}
