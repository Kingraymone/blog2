package top.king.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ArticleSearchDTO implements Serializable {
    private String title;
    private String summary;
    private Integer createTime;
    private String tagId;
    private String typeId;
    private String userId;
    List<String> primaryKey;
    List<String> keyWord;
}
