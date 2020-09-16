package top.king.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserSearchDTO implements Serializable {
    private String username;
    private List<String> status;
    private Integer createTime;
    List<String> primaryKey;
}
