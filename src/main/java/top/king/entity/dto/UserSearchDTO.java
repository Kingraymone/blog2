package top.king.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserSearchDTO implements Serializable {
    private String username;
    private String status;
    private Integer createTime;
}
