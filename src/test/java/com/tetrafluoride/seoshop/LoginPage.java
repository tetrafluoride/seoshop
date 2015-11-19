package com.tetrafluoride.seoshop;

/**
 * Created by avere on 19.11.15.
 */


import java.util.Random;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class LoginPage {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new ChromeDriver();
        baseUrl = "https://cloud.merchantos.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }


    @Test  //Normal login testing
    public void testLoginSuccess() throws Exception {
        driver.get(baseUrl + "/login.html");
        Assert.assertEquals(baseUrl + "/login.html", driver.getCurrentUrl());
        driver.findElement(By.name("login")).clear();
        driver.findElement(By.name("login")).sendKeys("avvere@gmail.com");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.name("ShopAddress1"));
        Assert.assertTrue("Wrong redirect: " + driver.getCurrentUrl(), driver.getCurrentUrl().endsWith("/firststart.php"));
    }


    @Test  //Empty fields
    public void testLoginEmpty() throws Exception {
        driver.get(baseUrl + "/login.html");
        Assert.assertEquals(baseUrl + "/login.html", driver.getCurrentUrl());
        driver.findElement(By.name("login")).clear();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.id("submitButton")).click();
        driver.findElement(By.id("loginFailedMessage"));
        Assert.assertEquals(baseUrl + "/login.html", driver.getCurrentUrl());
    }

    @Test  //Wrong Password testing
    public void testLoginWrongPassword() throws Exception {
        driver.get(baseUrl + "/login.html");
        Assert.assertEquals(baseUrl + "/login.html", driver.getCurrentUrl());
        driver.findElement(By.name("login")).clear();
        driver.findElement(By.name("login")).sendKeys("avvere@gmail.com");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("12345");
        driver.findElement(By.id("submitButton")).click();
        driver.findElement(By.id("loginFailedMessage"));
        Assert.assertEquals(baseUrl + "/login.html", driver.getCurrentUrl());
    }

    @Test  // password too long
    public void testLoginPasswordTooLong() throws Exception {
        driver.get(baseUrl + "/login.html");
        Assert.assertEquals(baseUrl + "/login.html", driver.getCurrentUrl());
        driver.findElement(By.name("login")).clear();
        driver.findElement(By.name("login")).sendKeys("avvere@gmail.com");
        driver.findElement(By.name("password")).clear();
        Random r = new Random();
        for (int i=0; i < 500; i++)
        {driver.findElement(By.name("password")).sendKeys(String.valueOf(r.nextInt(9)));}
        driver.findElement(By.id("submitButton")).click();
        driver.findElement(By.id("loginFailedMessage"));
        Assert.assertEquals(baseUrl + "/login.html", driver.getCurrentUrl());
    }

    @Test  //SQL injection testing
    public void testLoginSQL() throws Exception {
        driver.get(baseUrl + "/login.html");
        Assert.assertEquals(baseUrl + "/login.html", driver.getCurrentUrl());
        driver.findElement(By.name("login")).clear();
        driver.findElement(By.name("login")).sendKeys("DROP TABLE user;");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("12345");
        driver.findElement(By.id("submitButton")).click();
        driver.findElement(By.id("loginFailedMessage"));
        Assert.assertEquals(baseUrl + "/login.html", driver.getCurrentUrl());
    }


    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }


}

