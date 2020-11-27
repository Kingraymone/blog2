package top.king.entity.authority;

import lombok.Data;

import java.io.Serializable;

@Data
public class Menu implements Serializable, Cloneable {
    /**
     * id菜单
     */
    private Integer id;
    /**
     * 父菜单
     */
    private Integer parentId;
    /**
     * 菜单名
     */
    private String title;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 菜单路径
     */
    private String url;
    /**
     * 菜单等级
     */
    private Integer layer;
    /**
     * 资源名
     */
    private String resourceCode;
    /**
     * 排序
     */
    private Integer orderNum;
}