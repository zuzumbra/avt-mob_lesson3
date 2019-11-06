import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class MyThirdClass {
    private AppiumDriver driver;


    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.1");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "D:\\Projects\\avt-mob_lesson2\\apks\\org.wikipedia.apk");
        capabilities.setCapability("orientation", "PORTRAIT");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testSaveTwoArticlesToMyListAndDeleteOneOfThem(){

        String search_line = "Large Hadron Collider";
        String search_result_locator_1 = "Large Hadron Collider";
        String search_result_locator_2 = "LHCb experiment";
        String name_of_folder = "Particle accelerators";

        waitForArticleAndOpen(
                search_line,
                "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + search_result_locator_1 +"']"
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find button 'Got it'",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into article folder input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press 'OK' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        waitForArticleAndOpen(
                search_line,
                "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + search_result_locator_2 +"']"
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find name of saved folder",
                10
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My reading list",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                10
        );

        swipeElementToLeft(
                By.xpath("//*[@text='" + search_result_locator_2 + "']"),
                "Cannot find saved article"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + search_result_locator_1 + "']"),
                "Cannot find saved article '" + search_result_locator_1 + "'",
                10
        );

        WebElement title_element = waitForElementPresent(
                By.xpath("//*[@text='" + search_result_locator_1 + "']"),
                "Cannot find title '" + search_result_locator_1 + "'",
                15
        );

        String actual_title = title_element.getAttribute("text");
        Assert.assertEquals(
                "We see unexpected title",
                search_result_locator_1,
                actual_title
        );

    }

    @Test
    public void testWaitForTitleWithoutWaiting(){

        String search_line = "Rendering";
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Rendering (computer graphics)']"),
                "Cannot find element",
                10
        );

         assertElementPresent(
                 By.xpath("//*[@text='Rendering (computer graphics)']"),
                "We found any results by request " +  search_line
        );

    }

    @Test
    public void checkScreenOrientationWithDesiredCapability() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        String search_line = "PHP";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='PHP']"),
                "Cannot find search title",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text'][@text='PHP']"),
                "Cannot find search article",
                10
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

    }



    private void waitForArticleAndOpen (String search_line, String search_result_locator){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find input",
                5
        );

        waitForElementAndClick(
                By.xpath(search_result_locator),
                "Cannot find element",
                10
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_message)
    {
        return waitForElementPresent(by, error_message, 15);
    }

    private boolean waitForElementNotPresent (By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

/*    private WebElement waitForElementAndSendKeys(By by, String value, String error_massege, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_massege, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }*/

    public WebElement waitForElementAndSendKeys(By locator, String value, String error_message, long timeoutInSeconds)
    {
        MobileElement element = (MobileElement)waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.setValue(value);
        return element;
    }


    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    protected void swipeElementToLeft(By by, String error_message){
        WebElement element = waitForElementPresent(
                by,
                error_message,
                15);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().width;
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().height;
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(400)
                .moveTo(left_x,middle_y)
                .release()
                .perform();
    }

    private void assertElementPresent (By by, String error_message){
        List elements = driver.findElements(by);
        if (elements.size() < 1){
            String default_message = "An element '" + by.toString() + "' supposed to be present" + "\n";
            throw new AssertionError(default_message + " " + error_message);
        }
    }


}



