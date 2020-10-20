package top.king.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Article implements Serializable, Cloneable {
    /**
     * id;博客编号
     */
    private Integer uniqueId;
    /**
     * title;博客标题
     */
    private String title;
    /**
     * summary;博客摘要
     */
    private String summary;
    /**
     * createDate;发布时间
     */
    private Timestamp createTime;
    /**
     * tag_id;标签
     */
    private String tagId;
    /**
     * type_id;分类
     */
    private String typeId;
    /**
     * content;内容
     */
    private String content;
    /**
     * click;点击量
     */
    private Integer click;
    /**
     * comment;评论数量
     */
    private Integer comment;
    /**
     * keyWord;关键词 保存 0  发表 1
     */
    private String keyWord;
    /**
     * user_id;用户id
     */
    private Integer userId;
    /**
     * appreciate;点赞数
     */
    private Integer appreciate;
}