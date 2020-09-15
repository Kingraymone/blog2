package top.king.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class User implements Serializable, Cloneable {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 座右铭
     */
    private String motto;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 邮箱
     */
    private String mail;
    /**
     * 状态
     */
    private String status;

    /**
     * 添加字段：是否记住密码
     */
    private String remember;
}

