package top.king.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import top.king.common.ResultModel;

@ControllerAdvice
public class CommonAdviceController {


    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResultModel commonExceptionHandle(Exception ex) {
        return new ResultModel(false, ex.getMessage());
    }
}
