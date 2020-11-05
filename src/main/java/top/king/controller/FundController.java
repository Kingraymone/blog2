package top.king.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.Article;
import top.king.entity.FundInfo;
import top.king.service.FinanceService;

import java.util.List;

@RestController
@RequestMapping("/fund")
public class FundController {
    @Autowired
    FinanceService financeService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/add")
    public ResultModel addFund(String fundcode) {
        return financeService.addFund(fundcode);
    }

    @RequestMapping("/search")
    public ResultModel<List<FundInfo>> selectArticles(@RequestBody BaseQuery<FundInfo> param) {
        return financeService.selectFundInfo(param);
    }

}
