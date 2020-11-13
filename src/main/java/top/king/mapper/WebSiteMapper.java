package top.king.mapper;

import top.king.entity.WebSite;

import java.util.List;

public interface WebSiteMapper {
    int insert(WebSite record);

    int insertSelective(WebSite record);

    int deleteWebSite(String uniqueId);

    List<WebSite> selectWebSite();
}