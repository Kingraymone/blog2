package top.king.security;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.king.common.ResultModel;

@RestController
public class SecurityController {
    @RequestMapping("security")
    public ResultModel testt() {
        return new ResultModel(true);
    }

    @RequestMapping("test")
    public ResultModel testt2() {
        return new ResultModel(false);
    }
}
