package org.codepay.common.utils;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

/**
 * Description: 线程工具类.<br>
 * Copyright: Copyright (c) 2014 <br>
 * Company: www.zgzcw.com
 * 
 * @author caiqs
 * @create-time 2014年8月4日
 * @version 1.0
 * @E_mail <a href="mailto:caiqiusheng@v1.cn">caiqiusheng@v1.cn</a>
 */
public final class ThreadUtil {

    /**
     * 获取系统线程组.
     * 
     * @return
     */
    public static ThreadGroup getRootThreadGroup() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup rootGroup = null;
        while (group != null) {
            rootGroup = group;
            group = group.getParent();
        }
        return rootGroup;
    }

    /**
     * 查找指定线程组下指定名字的线程组，返回第一个名字匹配的线程组，如果没有查到，则返回null.
     * 
     * @param rootGroup
     *            祖先线程组
     * @param groupName
     *            指定线程组名
     * @return 返回第一个和指定线程组名字匹配的线程，如果没有找到则返回null.
     */
    public static ThreadGroup findThreadGroup(ThreadGroup rootGroup, String groupName) {
        int count = rootGroup.activeGroupCount();
        if (count > 0) {
            ThreadGroup[] groups = new ThreadGroup[count];
            rootGroup.enumerate(groups);
            for (ThreadGroup group : groups) {
                if (group.getName().equals(groupName)) {
                    return group;
                }
            }
            for (ThreadGroup group : groups) {
                group = findThreadGroup(group, groupName);
                if (null != group) {
                    return group;
                }
            }
        }
        return null;
    }

    /**
     * 首选缓存中匹配的线程组，再从系统中查找线程组，如果没有查到，则返回null.
     * 
     * @param groupName
     *            指定线程组名
     * @return
     */
    public static ThreadGroup findThreadGroup(String groupName) {
        ThreadGroup group = THREADGROUP_CACHE.get(groupName);
        if (null == group) {
            group = findThreadGroup(getRootThreadGroup(), groupName);
            addThreadGroupCache(groupName, group);
        }
        return group;
    }

    /**
     * 添加緩存線程組
     * 
     * @param groupName
     *            線程組名稱
     * @param group
     *            線程組
     */
    private static void addThreadGroupCache(String groupName, ThreadGroup group) {
        if (StringUtils.isBlank(groupName) || null == group) {
            return;
        }
        if (THREADGROUP_CACHE.size() > MAX_CACHE) {
            THREADGROUP_CACHE.clear();
        }
        THREADGROUP_CACHE.put(groupName, group);
    }

    /**
     * 最大缓存数量
     */
    private static final int MAX_CACHE = 50;

    /**
     * 缓存ThreadGroup
     */
    private static final Map<String, ThreadGroup> THREADGROUP_CACHE = Maps.newConcurrentMap();

    /**
     * 在指定的线程组中查找指定名称的线程状态.如果没有查到则返回null.
     * 
     * @param group
     *            指定的线程组
     * @param threadName
     *            要查找的线程名
     * @return 指定线程的运行状态，没有找到线程则返回null
     */
    public static Thread.State findThreadState(ThreadGroup group, String threadName) {
        if (null == group || StringUtil.isEmpty(threadName)) {
            return null;
        }
        int count = group.activeCount();
        if (count > 0) {
            Thread[] threads = new Thread[count];
            group.enumerate(threads);
            for (Thread thread : threads) {
                if (null == thread) {
                    continue;
                }
                if (thread.getName().equals(threadName)) {
                    return thread.getState();
                }
            }
        }
        return null;
    }

    /**
     * 根据父线程组名和线程名查找指定线程的状态.
     * 
     * @param groupName
     *            线程所属的线程组
     * @param threadName
     *            线程名
     * @return 没有找到线程组或者线程时，返回null.
     */
    public static Thread.State findThreadByName(String groupName, String threadName) {
        if (StringUtil.isEmpty(groupName) || StringUtil.isEmpty(threadName)) {
            return null;
        }
        if (threadName.contains("calcBonusThread_SFCR9_14093")) {
            System.out.println("");
        }
        return findThreadState(findThreadGroup(groupName), threadName);
    }

    /**
     * 返回指定名称的线程组. 在跟线程组下查找，如果没有则在系统线程组下创建一个指定名称的献策线程组.
     * 
     * @param groupName
     *            线程组名称.
     * @return 如果没有查找到则创建一个线程组
     */
    public static ThreadGroup getThreadGroupFromSystemThreadGroup(String groupName) {
        if (StringUtils.isBlank(groupName)) {
            return null;
        }
        ThreadGroup resultGroup = findThreadGroup(groupName);
        if (null == resultGroup) {
            ThreadGroup rootGroup = getRootThreadGroup();
            resultGroup = new ThreadGroup(rootGroup, groupName);
            addThreadGroupCache(groupName, resultGroup);
        }
        return resultGroup;
    }

}
