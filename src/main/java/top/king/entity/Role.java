package top.king.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Role implements Serializable, Cloneable {
    private Integer roleId;

    private String rolename;

    private String statement;

    private String status;

    private String roleCode;
}