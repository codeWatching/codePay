package org.codepay.common.utils;

/**
 * 组合算法回调接口
 * 
 */
public interface ExtensionCombCallBack {

	/**
	 * 组合算法回调函数
	 * 
	 * @param comb1 胆码组合数组，数组长度为胆码总数，其中选中的值为true
	 * @param m1 选取的胆码数目
	 * @param comb2 拖码组合数组，数组长度为拖码总数，其中选中的值为true
	 * @param m2 选取的拖码数目
	 * @return 是否中止组合拆分程序
	 */
	boolean run(boolean[] comb1, int m1, boolean[] comb2, int m2);
}
