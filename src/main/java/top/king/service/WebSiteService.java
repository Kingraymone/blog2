package top.king.service;

import top.king.common.ResultModel;
import top.king.entity.WebSite;

import java.util.List;

public interface WebSiteService {
    ResultModel addWebSite(WebSite webSite);

    ResultModel deleteWebSite(String uniqueId);

    /**
     * 查询所有网站
     *
     * @return
     */
    ResultModel<List<WebSite>> selectWebSite();
}
