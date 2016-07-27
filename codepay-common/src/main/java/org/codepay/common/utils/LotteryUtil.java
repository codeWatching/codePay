package org.codepay.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
 
/**
 * 不同概率抽奖工具包
 * @date 2015年11月20日
 * @author xueguolin
 */
public class LotteryUtil {
    /**
     * 抽奖
     *
     * @param orignalRates
     *            原始的概率列表，保证顺序和实际物品对应
     * @return
     *         物品的索引
     */
    public static int lottery(List<Double> orignalRates) {
        if (orignalRates == null || orignalRates.isEmpty()) {
            return -1;
        }
 
        int size = orignalRates.size();
 
        // 计算总概率，这样可以保证不一定总概率是1
        double sumRate = 0d;
        for (double rate : orignalRates) {
            sumRate += rate;
        }
 
        // 计算每个物品在总概率的基础下的概率情况
        List<Double> sortOrignalRates = new ArrayList<Double>(size);
        Double tempSumRate = 0d;
        for (double rate : orignalRates) {
            tempSumRate += rate;
            sortOrignalRates.add(tempSumRate / sumRate);
        }
 
        // 根据区块值来获取抽取到的物品索引,随机数抽取的索引的概率刚好是物品在总概率下的概率
        double nextDouble = Math.random();
        sortOrignalRates.add(nextDouble);
        Collections.sort(sortOrignalRates);
 
        return sortOrignalRates.indexOf(nextDouble);
    }
}
