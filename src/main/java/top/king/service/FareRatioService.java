package top.king.service;

import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.FareRatio;
import top.king.entity.dto.FareRatioDTO;

import java.util.List;

public interface FareRatioService {
    ResultModel<List<FareRatio>> selectFareRatio(BaseQuery<FareRatioDTO> param);

    ResultModel addFareRatio(FareRatio article);

    ResultModel updateFareRatio(FareRatio article);

    ResultModel deleteFareRatio(List<String> primaryKey);

}
