package org.codepay.model.account.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结算日汇总记录状态
 */
public enum SettDailyCollectStatusEnum {

	/**
	 * 已结算
	 */
	SETTLLED("已结算"),

	/**
	 * 未结算
	 */
	UNSETTLE("未结算");

	/** 描述 */
	private String desc;

	private SettDailyCollectStatusEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static SettDailyCollectStatusEnum getEnum(String enumName) {
		SettDailyCollectStatusEnum resultEnum = null;
		SettDailyCollectStatusEnum[] enumAry = SettDailyCollectStatusEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].name().equals(enumName)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		SettDailyCollectStatusEnum[] ary = SettDailyCollectStatusEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].name()));
			map.put("value", String.valueOf(ary[num].name()));
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		SettDailyCollectStatusEnum[] ary = SettDailyCollectStatusEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].name()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
}
