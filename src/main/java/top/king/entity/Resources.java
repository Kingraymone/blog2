package top.king.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Resources implements Serializable, Cloneable {
    /**
     * 资源id
     */
    private Integer id;
    /**
     * 资源名称
     */
    private String resourceName;
    /**
     * 资源代码
     */
    private String resourceCode;
    /**
     * 资源目录
     */
    private String resourceCategory;
}