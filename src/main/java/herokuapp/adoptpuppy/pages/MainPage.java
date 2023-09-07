package herokuapp.adoptpuppy.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

import java.util.Collections;
import java.util.List;

public class MainPage extends BasePageObject {

    public String get_page_Url() {
        return PAGE_URL;
    }

    public String get_expected_adoption_confirmation_message() {
        return EXPECTED_ADOPTION_CONFIRMATION_MSG;
    }

    public String getEXPECTED_CHANGE_MIND_MSG() {
        return EXPECTED_CHANGE_MIND_MSG;
    }

    public By getCHANGE_MIND_MSG() {
        return CHANGE_MIND_MSG;
    }

    public String getEXPECTED_CHANGE_MIND_MSG_COLOUR() {
        return EXPECTED_CHANGE_MIND_MSG_COLOUR;
    }

    public By getPREVIOUS_BTN_DISABLED() {
        return PREVIOUS_BTN_DISABLED;
    }

    public By getPREVIOUS_BTN_ENABLED() {
      return PREVIOUS_BTN_ENABLED;
    }

    public By getNEXT_PAGE_BTN_ENABLED() {
        return NEXT_PAGE_BTN_ENABLED;
    }


    private String PAGE_URL = "https://spartantest-puppies.herokuapp.com/";
    private By ALL_PUPPIES_LABEL = By.xpath("//div[@class='name']/h3");
    private By VIEW_DETAILS = By.xpath("./../../div[@class='view']/form");
    private By NEXT_PAGE = By.xpath("//a[@class='next_page']");
    private By ADOPTION_CONFIRMATION_MSG = By.id("notice");
    private String EXPECTED_ADOPTION_CONFIRMATION_MSG = "Thank you for adopting a puppy!";
    private By CHANGE_MIND_MSG = By.xpath("//p[@id='notice']");
    private String EXPECTED_CHANGE_MIND_MSG = "Your cart is currently empty";
    private String EXPECTED_CHANGE_MIND_MSG_COLOUR = "rgba(0, 128, 0, 1)";
    private By CURRENT_PAGE_NUMBER = By.xpath("//em[@class='current']");
    private By PREVIOUS_BTN_DISABLED = By.xpath("//span[@aria-label='Previous page']");
    private By PREVIOUS_BTN_ENABLED = By.xpath("//a[@aria-label='Previous page']");
    private By NEXT_PAGE_BTN_ENABLED = By.xpath("//a[@aria-label='Next page']");


    public MainPage(WebDriver driver, Logger log) {
        super(driver, log);
    }


    public void open_page() {
        log.info("Opening page: " + PAGE_URL);
        open_url(PAGE_URL);
        log.info("Page opened");
    }


    private WebElement find_puppy_locator(String puppy_name) {
        while (true) {
            List<WebElement> all_puppies_on_page = driver.findElements(ALL_PUPPIES_LABEL);
            for (WebElement puppy_name_label : all_puppies_on_page) {
                String puppy_label = puppy_name_label.getText();
                if (puppy_label.equals(puppy_name)) {
                    return puppy_name_label.findElement(VIEW_DETAILS);
                }
            }
            try {
                WebElement next_page = driver.findElement(NEXT_PAGE);
                next_page.click();
            } catch (NoSuchElementException e) {
                break;
            }
        }
        return null;
    }


    public PuppiesPage click_view_details(String puppyName) {
        log.info("Clicking View Details on " + puppyName);
        WebElement view_details = find_puppy_locator(puppyName);
        if (view_details != null) {
            view_details.click();
        } else throw new IllegalArgumentException(puppyName + " not found!");
        return new PuppiesPage(driver, log);
    }


    public String adoption_confirmation() {
        log.info("Getting confirmation text after adoption");
        return find_single_element(ADOPTION_CONFIRMATION_MSG).getText();
    }

    public String change_mind_confirmation() {
        log.info("Getting confirmation message after changing your mind about adoption");
        return find_single_element(CHANGE_MIND_MSG).getText();
    }

    public String change_mind_confirmation_msg_color() {
        log.info("Checking the confirmation message colour");
       String color = driver.findElement(CHANGE_MIND_MSG).getCssValue("color");
       return color;
    }

    public String get_current_page_number() {
        log.info("Checking current page number");
        return find_single_element(CURRENT_PAGE_NUMBER).getText();
    }

    public void click_next_page_btn() {
        log.info("Going to next page");
        click(NEXT_PAGE_BTN_ENABLED);
    }

    public void click_previous_page_btn() {
        log.info("Going to previous page");
        click(PREVIOUS_BTN_ENABLED);
    }


/*    private OrderPage get_to_order_page() {
        MainPage mainPage = new MainPage(driver, log);
        return get_to_order_page(mainPage);
    }*/

    private OrderPage get_to_order_page(MainPage mainPage) {
        mainPage.open_page();
        PuppiesPage puppyPage = mainPage.click_view_details("Brook");
        CartPage cart = puppyPage.click_adopt_button();
        return cart.click_complete_adoption();
    }

}
