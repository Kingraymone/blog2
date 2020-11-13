package top.king.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.king.common.BaseQuery;
import top.king.common.BaseService;
import top.king.common.ResultModel;
import top.king.entity.FareRatio;
import top.king.entity.dto.FareRatioDTO;
import top.king.mapper.FareRatioMapper;
import top.king.service.FareRatioService;

import java.util.List;

@Service
public class FareRatioServiceImpl extends BaseService implements FareRatioService {
    @Autowired
    FareRatioMapper fareRatioMapper;

    @Override
    public ResultModel<List<FareRatio>> selectFareRatio(BaseQuery<FareRatioDTO> param) {
        ResultModel<List<FareRatio>> resultModel = new ResultModel<>();
        try {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
            Page<FareRatio> fareRatios = (Page<FareRatio>) fareRatioMapper.selectFareRatios(param);
            resultModel.setData(fareRatios);
            resultModel.setCount(fareRatios.getTotal());
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("查询费率信息出错！", e);
            resultModel.setMsg("查询费率信息出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel addFareRatio(FareRatio fareRatio) {
        ResultModel resultModel = new ResultModel();
        try {
            fareRatioMapper.insertSelective(fareRatio);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("添加费率出错！", e);
            resultModel.setMsg("添加费率出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel updateFareRatio(FareRatio fareRatio) {
        ResultModel resultModel = new ResultModel();
        try {
            fareRatioMapper.updateFareRatio(fareRatio);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("修改费率出错！", e);
            resultModel.setMsg("修改费率出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel deleteFareRatio(List<String> primaryKey) {
        ResultModel resultModel = new ResultModel();
        try {
            fareRatioMapper.deleteFareRatio(primaryKey);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("删除费率出错！", e);
            resultModel.setMsg("删除费率出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }
}
