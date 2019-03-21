package com.sandu.common.pay;

import com.sandu.common.util.ConfigUtil;

public class Config {
	private static int scan_total_fee = Integer.parseInt(ConfigUtil.getConfig("config/pay", "common.scan_total_fee"));

	private static String store_id = ConfigUtil.getConfig("config/pay", "common.store_id");

	public static int getScan_total_fee() {
		return scan_total_fee;
	}

	public static void setScan_total_fee(int scan_total_fee) {
		Config.scan_total_fee = scan_total_fee;
	}

	public static String getStore_id() {
		return store_id;
	}

	public static void setStore_id(String store_id) {
		Config.store_id = store_id;
	}
}
