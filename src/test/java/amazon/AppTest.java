package amazon;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */

public class AppTest {

    WebDriver driver = null;

    private static Logger Log = Logger.getLogger(AppTest.class.getName());

    @BeforeMethod
    public void testCaseSetup() {

        System.setProperty("webdriver.chrome.driver", "src/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.get(
                "https://www.amazon.com.tr/ap/signin?openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.com.tr%2F%3Fref_%3Dnav_custrec_signin&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=trflex&openid.mode=checkid_setup&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&");
    }

    @AfterMethod
    public void testCaseTeardown() {
        driver.quit();
    }

    @Test
    public void TC01CheckProduct() throws InterruptedException {
        Log.info("Test is running...");
        WebElement setEmailAdress = driver.findElement(By.xpath("//*[@id='ap_email']"));

        setEmailAdress.click();
        setEmailAdress.sendKeys("notstick61@gmail.com");
        WebElement continueButton = driver
                .findElement(By.xpath("//*[@id='continue']"));
        continueButton.click();
        TimeUnit.SECONDS.sleep(3);

        WebElement setPassword = driver.findElement(By.xpath("//*[@id='ap_password']"));
        setPassword.click();
        setPassword.sendKeys("T1e2s3t4.");
        WebElement signinButton = driver
                .findElement(By.xpath("//*[@id='signInSubmit']"));
        signinButton.click();
        TimeUnit.SECONDS.sleep(3);

        WebElement setSearch = driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']"));
        setSearch.click();
        setSearch.sendKeys("iphone");

        WebElement clickSearchButton = driver.findElement(By.xpath("//*[@id='nav-search-submit-button']"));
        clickSearchButton.click();

        String productPrice = driver.findElement(
                By.xpath(
                        "//*[@id='search']/div[1]/div[1]/div/span[3]/div[2]/div[7]/div/div/div/div/div[2]/div[3]/div/a/span/span[2]/span[1]"))
                .getText();

        WebElement chooseProductElement = driver.findElement(By.xpath(
                "//*[@id='search']/div[1]/div[1]/div/span[3]/div[2]/div[7]/div/div/div/div/div[2]/div[1]/h2/a/span"));
        chooseProductElement.click();
        TimeUnit.SECONDS.sleep(2);

        String chooseProductPrice = driver
                .findElement(By.xpath("//*[@id='corePriceDisplay_desktop_feature_div']/div[1]/span/span[2]/span[1]"))
                .getText();

        if (productPrice.equals(chooseProductPrice)) {
            Log.info("Products are equals.");
        } else {
            Log.info("The selected product's price is not equals the product's price!");
        }

        WebElement addProduct = driver.findElement(By.xpath("//*[@id='add-to-cart-button']"));
        addProduct.click();
        TimeUnit.SECONDS.sleep(2);
        WebElement clickCart = driver.findElement(By.xpath("//*[@id='attach-sidesheet-view-cart-button']/span/input"));
        clickCart.click();

        String checkProductPrice = driver.findElement(By.cssSelector(".sc-product-price")).getText();
        if (checkProductPrice.contains(chooseProductPrice)) {
            Log.info("PASS. The price of the added product is the same as the added product.");
            assertTrue(checkProductPrice.contains(chooseProductPrice) == true);
        } else {
            Log.info("FAIL. The price of the added product is not the same as the added product!");
            assertFalse(checkProductPrice.contains(chooseProductPrice) == false);
        }
        Log.info("Test completed...");

    }

}
