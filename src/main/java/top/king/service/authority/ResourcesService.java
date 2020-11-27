package top.king.service.authority;

import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.Resources;

import java.util.List;
import java.util.Map;

public interface ResourcesService {
    ResultModel insertResources(Resources record);

    ResultModel<List<Resources>> queryResources(BaseQuery<Map> param);

    ResultModel deleteResources(List<String> primaryKey);

    ResultModel updateResources(Resources resources);

}
