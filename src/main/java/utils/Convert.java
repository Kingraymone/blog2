package utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class Convert {
    public final static String CHARSET_UTF8 = "UTF-8";
    private static final char[] hexadecimal = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 默认使用UTF-8编码字节
     *
     * @param hex
     * @return String
     * @author wangqi26959
     */
    public static String hex2Str(String hex) {
        return hex2Str(hex, CHARSET_UTF8);
    }

    /**
     * 将一个字节转换为高低4位的16进制数字还原
     *
     * @param hex charset
     * @return String
     * @author king
     */
    public static String hex2Str(String hex, String charset) {
        try (ByteArrayOutputStream bao = new ByteArrayOutputStream(hex.length() << 1)) {
            int count = 1;
            byte prev = 0;
            for (char c : hex.toCharArray()) {
                byte cur = Byte.parseByte(String.valueOf(c), 16);
                if ((count & 1) == 0) {
                    byte in = (byte) (prev << 4 | cur);
                    bao.write(in);
                } else {
                    prev = cur;
                }
                count++;
            }
            return bao.toString(charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将字符串转换为高低4位的16进制数字
     *
     * @param str
     * @return String
     */
    public static String str2Hex(String str) {
        try {
            byte[] bytes = str.getBytes(CHARSET_UTF8);
            char[] buffer = new char[bytes.length << 1];
            for (int i = 0; i < bytes.length; i++) {
                int low = bytes[i] & 0x0f;
                int high = (bytes[i] & 0xf0) >> 4;
                buffer[i * 2] = hexadecimal[high];
                buffer[i * 2 + 1] = hexadecimal[low];
            }
            return new String(buffer);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
