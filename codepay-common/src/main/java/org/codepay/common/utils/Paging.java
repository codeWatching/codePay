package org.codepay.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 * 
 * @date 2015年6月11日
 * @author xiake
 */
public class Paging {

    /**
     * 默认页码
     */
    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 返回指定页码的数据
     * 
     * @param List data 待分页的数据
     * @param pageNo 页码
     * @param pageSize 每页的数据量
     * @return 返回指定页的数据
     */
    private static <T> List<T> getDataBypageNo(List<T> data, int pageNo, int pageSize) {
        if (null == data || data.isEmpty()) {
            return null;
        }
        int[] res = getStartEnd(data.size(), pageNo, pageSize);
        return data.subList(res[0], res[1]);
    }

    /**
     * @param len 总数据量
     * @param pageNo 页码
     * @param pageSize 每页数据量
     * @return 返回每页的开始位置和结束位置
     */
    private static int[] getStartEnd(int len, int pageNo, int pageSize) {
        // 总页码
        int pages = len / pageSize;
        // 总数据除以每页的数据如果有余，页码加1
        if (len % pageSize > 0) {
            pages++;
        }
        // 每页的结束位置
        int end = pageNo * pageSize;
        // 如果传入的参数页码大于总页码，只返回最后一页的数据
        if (pageNo >= pages) {
            pageNo = pages;
            end = len;
        }
        int start = (pageNo - 1) * pageSize;
        return new int[] { start, end };
    }
    /**
     * @param list 待处理的数据
     * @param pageSize 每页数据量
     * @param pageNo 页码
     * @return 返回总页数和当前页码的数据
     */
    public static <T> List<T> getResult(List<T> list, int pageSize, int pageNo) {
        if (null == list || list.isEmpty()) {
            return null;
        }
        int pNo = pageNo;
        int pSize = pageSize;
        if (pageNo <= 0 || pageSize <= 0) {
            pNo = 1;
            pSize = list.size() > DEFAULT_PAGE_SIZE ? DEFAULT_PAGE_SIZE : list.size();
        }
        return Paging.getDataBypageNo(list, pNo, pSize);
    }
    
    /**
     * 返回指定页码的数据
     *    如果页码大于总页数,返回空集合
     * @param  data 待分页的数据
     * @param pageNo 页码
     * @param pageSize 每页的数据量
     * @return 返回指定页的数据
     */
    private static <T> List<T> getSubListByPageNo(List<T> data, int pageNo, int pageSize) {
        int len = data.size();
        // 总页码
        int pages = len / pageSize;
        // 总数据除以每页的数据如果有余，页码加1
        if (len % pageSize > 0) {
            pages++;
        }
        // 每页的结束位置
        int end = pageNo * pageSize;
        if (pageNo ==  pages) {
            pageNo = pages;
            end = len;
        }
        // 如果传入的参数页码大于总页码，只返回空集合
        if (pageNo > pages) {
            return new ArrayList<T>();
        }
        int start = (pageNo - 1) * pageSize;
        return data.subList(start, end);
    }
    /**
     * @param list 待处理的数据
     * @param pageSize 每页数据量
     * @param pageNo 页码
     * @return 返回总页数和当前页码的数据
     */
    public static <T> List<T> getPageResult(List<T> list, int pageSize, int pageNo) {
        if (null == list || list.isEmpty()) {
            return null;
        }
        int pNo = pageNo;
        int pSize = pageSize;
        if (pageNo <= 0 || pageSize <= 0) {
            pNo = 1;
            pSize = list.size() > DEFAULT_PAGE_SIZE ? DEFAULT_PAGE_SIZE : list.size();
        }
        return Paging.getSubListByPageNo(list, pNo, pSize);
    }
}
