package top.king.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AutoTest {
    public static void main(String[] args) {
        //对应驱动版本需与浏览器版本对应
        //地址：http://chromedriver.storage.googleapis.com/index.html
        System.setProperty("webdriver.chrome.driver","G:\\git\\tools\\tomcat\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com");

        String title = driver.getTitle();
        System.out.printf(title);

        driver.close();
    }
}
