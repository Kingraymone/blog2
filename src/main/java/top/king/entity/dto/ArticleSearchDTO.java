package top.king.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleSearchDTO implements Serializable {
    private String title;
    private String summary;
    private Integer createTime;
    private String tagId;
    private String typeId;
}
