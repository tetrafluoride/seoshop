package com.tetrafluoride.seoshop;

/**
 * Created by avere on 19.11.15.
 */


import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        driver.get(baseUrl + "login.html");
        Assert.assertEquals(baseUrl + "login.html", driver.getCurrentUrl());
        driver.findElement(By.name("login")).clear();
        driver.findElement(By.name("login")).sendKeys("avvere@gmail.com");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.id("session_employee_name"));
        Assert.assertTrue("Wrong redirect: " + driver.getCurrentUrl(), driver.getCurrentUrl().endsWith("?form_name=ui_tab&tab=choose_register"));
    }


    @Test  //Empty fields
    public void testLoginEmpty() throws Exception {
        driver.get(baseUrl + "login.html");
        Assert.assertEquals(baseUrl + "login.html", driver.getCurrentUrl());
        driver.findElement(By.name("login")).clear();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.id("submitButton")).click();
        driver.findElement(By.id("loginFailedMessage"));
        Assert.assertEquals(baseUrl + "login.html", driver.getCurrentUrl());
    }

    @Test  //Wrong Password testing
    public void testLoginWrongPassword() throws Exception {
        driver.get(baseUrl + "login.html");
        Assert.assertEquals(baseUrl + "login.html", driver.getCurrentUrl());
        driver.findElement(By.name("login")).clear();
        driver.findElement(By.name("login")).sendKeys("avvere@gmail.com");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("12345");
        driver.findElement(By.id("submitButton")).click();
        driver.findElement(By.id("loginFailedMessage"));
        Assert.assertEquals(baseUrl + "login.html", driver.getCurrentUrl());
    }

    @Test  // password too long
    public void testLoginPasswordTooLong() throws Exception {
        driver.get(baseUrl + "login.html");
        Assert.assertEquals(baseUrl + "login.html", driver.getCurrentUrl());
        driver.findElement(By.name("login")).clear();
        driver.findElement(By.name("login")).sendKeys("avvere@gmail.com");
        driver.findElement(By.name("password")).clear();
        Random r = new Random();
        for (int i=0; i < 5; i++)
        {driver.findElement(By.name("password")).sendKeys(String.valueOf(r.nextInt(9)));}
        driver.findElement(By.id("submitButton")).click();
        driver.findElement(By.id("loginFailedMessage"));
        Assert.assertEquals(baseUrl + "login.html", driver.getCurrentUrl());
    }

    @Test  //SQL injection testing
    public void testLoginSQL() throws Exception {
        driver.get(baseUrl + "login.html");
        Assert.assertEquals(baseUrl + "login.html", driver.getCurrentUrl());
        driver.findElement(By.name("login")).clear();
        driver.findElement(By.name("login")).sendKeys("DROP TABLE user;");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("12345");
        driver.findElement(By.id("submitButton")).click();
        driver.findElement(By.id("loginFailedMessage"));
        Assert.assertEquals(baseUrl + "login.html", driver.getCurrentUrl());
    }

    @Test  //Forgot password
    public void testForgotPass() throws Exception {
        driver.get(baseUrl + "login.html");
        Assert.assertEquals(baseUrl + "login.html", driver.getCurrentUrl());
        driver.findElement(By.id("forgotPassword")).click();
        waitForLoad(driver);
        Assert.assertTrue("Wrong redirect: " + driver.getCurrentUrl(), driver.getCurrentUrl().endsWith("/reset_password.html"));
    }

    @Test  //Status link
    public void testStatus() throws Exception {
        driver.get(baseUrl + "login.html");
        Assert.assertEquals(baseUrl + "login.html", driver.getCurrentUrl());
        driver.findElement(By.linkText("status.lightspeedretail.com")).click();
        waitForLoad(driver);
        Assert.assertTrue("Wrong redirect: " + driver.getCurrentUrl(), driver.getCurrentUrl().endsWith("status.lightspeedretail.com/"));
    }

    @Test  //Visit help
    public void testVisitHelp() throws Exception {
        driver.get(baseUrl + "login.html");
        Assert.assertEquals(baseUrl + "login.html", driver.getCurrentUrl());
        driver.findElement(By.id("visitHelp")).click();
        ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));

        waitForLoad(driver);
        Assert.assertTrue("Wrong redirect: " + driver.getCurrentUrl(), driver.getCurrentUrl().endsWith("/support/retail/"));
    }

    @Test  //Register
    public void testRegister() throws Exception {
        driver.get(baseUrl + "login.html");
        Assert.assertEquals(baseUrl + "login.html", driver.getCurrentUrl());
        driver.findElement(By.id("signUp")).click();
        waitForLoad(driver);
        Assert.assertTrue("Wrong redirect: " + driver.getCurrentUrl(), driver.getCurrentUrl().endsWith("/register-for-a-free-trial/"));
    }

    @Test  //iPad Cloud link
    public void testCloud() throws Exception {
        driver.get(baseUrl + "login.html");
        Assert.assertEquals(baseUrl + "login.html", driver.getCurrentUrl());
        driver.findElement(By.id("lsCloudForIpad")).click();

        ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));

        waitForLoad(driver);
        Assert.assertTrue("Wrong redirect: " + driver.getCurrentUrl(), driver.getCurrentUrl().endsWith("lightspeed-cloud-point-sale/id848366907"));
    }

    @Test  //Reporting link
    public void testReport() throws Exception {
        driver.get(baseUrl + "login.html");
        Assert.assertEquals(baseUrl + "login.html", driver.getCurrentUrl());
        driver.findElement(By.id("lsCloudReporting")).click();

        ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));

        waitForLoad(driver);
        Assert.assertTrue("Wrong redirect: " + driver.getCurrentUrl(), driver.getCurrentUrl().endsWith("retail/reporting/"));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    void waitForLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
    }

}

