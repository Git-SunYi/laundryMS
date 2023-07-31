package com.sunyi.laundryms.common.dto;

import lombok.Data;

@Data
public class CommonDataDTO {
    /**
     * 搜索关键字
     */
    private String keyWord;
    /**
     * 分页起始页
     */
    private int start;
    /**
     * 页大小
     */
    private int size;
}
