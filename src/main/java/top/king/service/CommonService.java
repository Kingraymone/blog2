package top.king.service;

import org.springframework.web.multipart.MultipartFile;
import top.king.common.ResultModel;

import java.io.File;

public interface CommonService {
    ResultModel<String> uploadImg(MultipartFile file, File img);
}
