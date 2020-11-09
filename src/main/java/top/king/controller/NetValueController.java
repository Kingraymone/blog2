package top.king.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.NetValue;
import top.king.service.FinanceService;

import java.util.List;

@RestController
@RequestMapping("/netvalue")
public class NetValueController {
    @Autowired
    FinanceService financeService;

    @RequestMapping("/search")
    public ResultModel<List<NetValue>> selectFundInfo(String fundcode) {
        return financeService.selectNetValue(fundcode);
    }

}
