package top.king.service.impl;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONObject;
import top.king.common.BaseService;
import top.king.common.ResultModel;
import top.king.service.BoomService;

import java.util.HashMap;

public class BoomServiceImpl extends BaseService implements BoomService {
    public static final String APP_ID = "22368124";
    public static final String API_KEY = "K4rpGebhra4MKtXoFcz12z9N";
    public static final String SECRET_KEY = "7Kk64tVTeQjqxLeXWqwdIZaxbh7e5oSC";

    @Override
    public ResultModel boom(String number) {

        // 图片路径
        String path = "scode.jpg";

        return null;
    }

    private String parseValidateCode(String path) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 参数
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "ENG");
        options.put("detect_direction", "false");
        options.put("detect_language", "false");
        options.put("probability", "false");

        // 调用接口
        JSONObject res = client.basicGeneral(path, options);
        //JSONObject res = client.webimageLoc(path, options);
        JSONArray words_result = (JSONArray) res.get("words_result");
        String result = (String)((JSONObject)words_result.get(0)).get("words");
        return result.length() == 4 ? result : "";
    }
}
