package com.boke.auth.utils;

import com.github.pagehelper.Page;
import com.boke.auth.vo.resp.PageVO;

import java.util.List;

/**
* @ClassName:       PageUtils
*                   分页工具类
* @Author:          as
* @CreateDate:      2019/10/19 13:26
* @UpdateUser:      as
* @UpdateDate:      2019/10/19 13:26
* @Version:         0.0.1
*/
public class PageUtils {
	private PageUtils() {}

    public static <T> PageVO<T> getPageVO(List<T> list) {
        PageVO<T> result = new PageVO<>();
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            result.setTotalRows(page.getTotal());
            result.setTotalPages(page.getPages());
            result.setPageNum(page.getPageNum());
            result.setPageSize(page.getPageSize());
            result.setCurPageSize(page.size());
            result.setList(page.getResult());
        }
        return result;
    }
}
