package top.king.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class WebSite implements Serializable, Cloneable {
    /**
     * 主键
     */
    private Integer uniqueId;
    /**
     * 链接
     */
    private String weburl;
    /**
     * 标题
     */
    private String title;
    /**
     * 介绍
     */
    private String memo;
    /**
     * 图标
     */
    private String favicon;

    /**
     * 主机
     */
    private String host;

}