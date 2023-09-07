package herokuapp.adoptpuppy.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePageObject {

    protected WebDriver driver;
    protected Logger log;

    public BasePageObject(WebDriver driver, Logger log) {
        this.driver = driver;
        this.log = log;
    }

    protected void open_url(String url) {
        driver.get(url);
    }

    protected WebElement find_single_element(By locator) {
        return driver.findElement(locator);
    }

    protected List<WebElement> find_multiple_elements(By locator) {
        return driver.findElements(locator);
    }

    protected void click(By locator) {
        wait_for_visibility_of(locator, Duration.ofSeconds(5));
        find_single_element(locator).click();
    }

    public void select_all_checkboxes(List<WebElement> elements){
        for (WebElement checkbox : elements) {
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }

    protected void type(String text, By locator) {
        wait_for_visibility_of(locator, Duration.ofSeconds(5));
        find_single_element(locator).sendKeys(text);
    }

    private void wait_for(ExpectedCondition<WebElement> condition, Duration timeOut) {
        timeOut = timeOut != null ? timeOut : Duration.ofSeconds(30);
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        wait.until(condition);
    }

    protected void wait_for_visibility_of(By locator, Duration... timeOut) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                wait_for(ExpectedConditions.visibilityOfElementLocated(locator),
                        (timeOut.length > 0 ? timeOut[0] : null));
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
    }


}
