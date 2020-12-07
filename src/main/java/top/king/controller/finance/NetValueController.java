package top.king.controller.finance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.king.common.ResultModel;
import top.king.entity.finance.NetValue;
import top.king.entity.finance.ShareDetail;
import top.king.service.FinanceService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/netvalue")
public class NetValueController {
    @Autowired
    FinanceService financeService;

    @RequestMapping("/search")
    public ResultModel<List<NetValue>> selectFundInfo(String fundcode) {
        return financeService.selectNetValue(fundcode);
    }

    @RequestMapping("/current")
    public ResultModel<Map> selectCurNetValue(String fundcode) {
        return financeService.selectCurNetValue(fundcode);
    }

    @RequestMapping("/update")
    public ResultModel updateHistoryNetValue(String fundcode) {
        return financeService.updateHistoryNetValue(fundcode);
    }

    @RequestMapping("/purchase")
    public ResultModel purchaseHandle(@RequestBody ShareDetail shareDetail) {
        return financeService.purchaseHandle(shareDetail);
    }

    @RequestMapping("/redeem")
    public ResultModel redeemHandle(@RequestBody ShareDetail shareDetail) {
        return financeService.redeemHandle(shareDetail);
    }

    @RequestMapping("/profit")
    public ResultModel calculateProfit(String fundcode) {
        return financeService.calculateProfit(fundcode);
    }

    @RequestMapping("/detail")
    public ResultModel<List<ShareDetail>> selectRedeemShareDeatil(String fundcode,String type) {
        return financeService.selectShareDetails(fundcode,type);
    }

}
