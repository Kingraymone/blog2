package utils;


import top.king.common.ApplicationInfo;

/**
 * @package utils
 * @date 2020-09-17
 */
public class StringUtils {
    /**
     * 判断空字符串
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return "".equals(str) || str == null;
    }

    /**
     * 文件路径参数
     *
     * @param param
     * @return
     */
    public static String fileParam(String... param) {
        StringBuffer sb = new StringBuffer();
        for (String str : param) {
            sb.append(ApplicationInfo.FILE_SEPARATOR).append(str);
        }
        return sb.toString();
    }
}
