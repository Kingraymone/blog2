package top.king.mapper;

import top.king.common.BaseQuery;
import top.king.entity.FareRatio;
import top.king.entity.dto.FareRatioDTO;

import java.util.List;
import java.util.Map;

public interface FareRatioMapper {
    int insert(FareRatio record);

    int insertSelective(FareRatio record);

    List<FareRatio> selectFareRatios(BaseQuery<FareRatioDTO> param);

    FareRatio selectFareRatioByPM(Map map);

    void deleteFareRatio(List<String> primaryKey);

    void updateFareRatio(FareRatio article);
}