package top.king.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.king.common.BaseService;
import top.king.common.ResultModel;
import top.king.entity.WebSite;
import top.king.mapper.WebSiteMapper;
import top.king.service.WebSiteService;

import java.util.List;

@Service
public class WebSiteServiceImpl extends BaseService implements WebSiteService {
    @Autowired
    WebSiteMapper webSiteMapper;

    @Override
    public ResultModel addWebSite(WebSite webSite) {
        ResultModel resultModel = new ResultModel();
        try {
            webSiteMapper.insertSelective(webSite);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("添加网站出错！", e);
            resultModel.setMsg("添加网站出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel deleteWebSite(String uniqueId) {
        ResultModel resultModel = new ResultModel();
        try {
            webSiteMapper.deleteWebSite(uniqueId);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("删除网站出错！", e);
            resultModel.setMsg("删除网站出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel<List<WebSite>> selectWebSite() {
        ResultModel<List<WebSite>> resultModel = new ResultModel<>();
        try {
            List<WebSite> webSites = webSiteMapper.selectWebSite();
            resultModel.setData(webSites);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("查询网站信息出错！", e);
            resultModel.setMsg("查询网站信息出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }
}
