package top.king.common;

public class ApplicationInfo {
    /** path */
    public final static String ROOT = System.getProperty("user.dir");
    /** windows:\ linux:/ */
    public final static String FILE_SEPARATOR = System.getProperty("file.separator");
    /** /r/n */
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    /** zh */
    public final static String LANGUAGE = System.getProperty("user.language");
    /** utf-8 */
    public final static String ENCODING = System.getProperty("file.encoding");
    /** Windows 10 */
    public final static String SYSTEM = System.getProperty("os.name");

    /** 作者 */
    public final static String AUTHOR = "Raymone";

}
