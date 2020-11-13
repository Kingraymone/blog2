package top.king.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.FareRatio;
import top.king.entity.dto.FareRatioDTO;
import top.king.service.FareRatioService;

import java.util.List;

@RestController
@RequestMapping("/fareratio")
public class FareRatioController {
    @Autowired

    FareRatioService fareRatioService;

    @RequestMapping("/search")
    public ResultModel<List<FareRatio>> selectFareRatio(@RequestBody BaseQuery<FareRatioDTO> param) {
        return fareRatioService.selectFareRatio(param);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/add")
    public ResultModel addFareRatio(@RequestBody FareRatio article) {
        return fareRatioService.addFareRatio(article);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/delete")
    public ResultModel deleteFareRatio(@RequestBody FareRatioDTO fareRatioDTO) {
        return fareRatioService.deleteFareRatio(fareRatioDTO.getPrimaryKey());
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/update")
    public ResultModel updateFareRatio(@RequestBody FareRatio fareRatio) {
        return fareRatioService.updateFareRatio(fareRatio);
    }

}
