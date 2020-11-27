package top.king.impl.authority;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.king.common.BaseQuery;
import top.king.common.BaseService;
import top.king.common.ResultModel;
import top.king.entity.Resources;
import top.king.mapper.ResourcesMapper;
import top.king.service.authority.ResourcesService;

import java.util.List;
import java.util.Map;

@Service
public class ResourcesServiceImpl extends BaseService implements ResourcesService {
    @Autowired
    ResourcesMapper resourcesMapper;

    @Override
    public ResultModel<List<Resources>> queryResources(BaseQuery<Map> param) {
        ResultModel<List<Resources>> resultModel = new ResultModel<>();
        try {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
            Page<Resources> resources = (Page<Resources>) resourcesMapper.queryResources(param);
            resultModel.setData(resources);
            resultModel.setCount(resources.getTotal());
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("查询资源信息出错！", e);
            resultModel.setMsg("查询资源信息出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel deleteResources(List<String> primaryKey) {
        ResultModel resultModel = new ResultModel();
        try {
            resourcesMapper.deleteResources(primaryKey);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("删除资源出错！", e);
            resultModel.setMsg("删除资源出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel updateResources(Resources menu) {
        ResultModel resultModel = new ResultModel();
        try {
            resourcesMapper.updateResources(menu);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("修改资源出错！", e);
            resultModel.setMsg("修改资源出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel insertResources(Resources record) {
        ResultModel resultModel = new ResultModel();
        try {
            resourcesMapper.insertSelective(record);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("添加资源出错！", e);
            resultModel.setMsg("添加资源出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }
}
