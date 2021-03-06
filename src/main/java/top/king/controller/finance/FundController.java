package top.king.controller.finance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.dto.FundInfoDTO;
import top.king.entity.finance.FundInfo;
import top.king.service.FinanceService;

import java.util.List;

@RestController
@RequestMapping("/fund")
public class FundController {
    @Autowired
    FinanceService financeService;

    @ModelAttribute("king")
    public String test() {
        return "测试";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/add")
    public ResultModel addFund(String fundcode) {
        return financeService.addFund(fundcode);
    }

    @RequestMapping("/edit")
    public ResultModel addFund(@RequestBody FundInfo fundInfo) {
        return financeService.editFund(fundInfo);
    }

    @RequestMapping("/search")
    public ResultModel<List<FundInfo>> selectFundInfo(@RequestBody BaseQuery<FundInfo> param) {
        return financeService.selectFundInfo(param);
    }

    @RequestMapping("/dict")
    public ResultModel<List> selectFundDict() {
        return financeService.selectFundDict();
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/delete")
    public ResultModel deleteFundInfos(@RequestBody FundInfoDTO fundInfoDTO) {
        return financeService.deleteFundInfos(fundInfoDTO.getPrimaryKey());
    }


}
