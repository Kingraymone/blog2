package top.king.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.king.common.BaseService;
import top.king.common.ResultModel;
import top.king.service.CommonService;

import java.io.File;

@Service
public class CommonServiceImpl extends BaseService implements CommonService {
    @Override
    public ResultModel<String> uploadImg(MultipartFile file, File img) {
        ResultModel<String> resultModel = new ResultModel<>();
        try {
            File parent = img.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            if (!img.exists()) {
                img.createNewFile();
            }
            file.transferTo(img);
            resultModel.setData(img.getPath());
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("图片上传失败！", e);
            resultModel.setMsg("图片上传失败！");
            resultModel.setResult(false);
            return resultModel;
        }
    }
}
