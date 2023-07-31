package com.sunyi.laundryms.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CommentVO {
    /**
     * 评论内容
     */
    private String content;
    /**
     * 创建时间
     */
//    private Date createTime; //没开数据库下划线驼峰转换和resultMap映射，所以取不到数据库create_time值
    private Date create_time;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 头像
     */
    private String head_portrait;
    /**
     * 用户类型
     */
    private String user_type;
    /**
     * 时间差
     */
    private String timeDifference;


}
