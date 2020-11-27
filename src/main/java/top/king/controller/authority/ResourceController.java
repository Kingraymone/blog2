package top.king.controller.authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.Resources;
import top.king.service.authority.ResourcesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    ResourcesService resourcesService;

    @RequestMapping("/search")
    public ResultModel<List<Resources>> selectResourcess(@RequestBody BaseQuery<Map> param) {
        return resourcesService.queryResources(param);
    }

    @RequestMapping("/add")
    public ResultModel insertResources(@RequestBody Resources resources) {
        return resourcesService.insertResources(resources);
    }

    @RequestMapping("/delete")
    public ResultModel deleteResources(@RequestBody ArrayList<String> primaryKey) {
        return resourcesService.deleteResources(primaryKey);
    }

    @RequestMapping("/update")
    public ResultModel updateResources(@RequestBody Resources resources) {
        return resourcesService.updateResources(resources);
    }

}
