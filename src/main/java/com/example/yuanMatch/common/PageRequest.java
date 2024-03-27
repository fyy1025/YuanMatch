package com.example.yuanMatch.common;

import lombok.Data;

/*
 *@author  fyy
 *@version 1.0
 *@time    2024-03-26 12:38
 *@project YuanMatch
 */
@Data
public class PageRequest
{
    private static final long serialVersionUID = -5860707094194210842L;

    /**
     * 页面大小
     */
    protected int pageSize = 10;

    /**
     * 当前是第几页
     */
    protected int pageNum = 1;
}
