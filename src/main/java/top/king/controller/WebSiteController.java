package top.king.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.king.common.ResultModel;
import top.king.entity.WebSite;
import top.king.service.WebSiteService;

import java.util.List;

@RestController
@RequestMapping("/website")
public class WebSiteController {
    @Autowired
    WebSiteService webSiteService;

    @RequestMapping("/search")
    public ResultModel<List<WebSite>> selectWebSites(){
        return webSiteService.selectWebSite();
    }

    @RequestMapping("/add")
    public ResultModel addWebSite(@RequestBody WebSite webSite){
        return webSiteService.addWebSite(webSite);
    }

    @RequestMapping("/delete")
    public ResultModel deleteWebSite(String uniqueId){
        return webSiteService.deleteWebSite(uniqueId);
    }
}
