package herokuapp.adoptpuppy.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class OrderPage extends BasePageObject {

    public String get_PAGE_URL() {
        return PAGE_URL;
    }

    private String PAGE_URL = "https://spartantest-puppies.herokuapp.com/orders/new?";
    private By ORDER_NAME = By.id("order_name");
    private By ORDER_ADDRESS = By.id("order_address");
    private By ORDER_EMAIL = By.id("order_email");
    private By ORDER_PAY_TYPE = By.id("order_pay_type");
    private By ORDER_BTN = By.xpath("//input[@name='commit']");
    private By ORDER_ERROR_HEADER = By.xpath("//div[@id='error_explanation']/h2");
    private By ORDER_ERROR_MSG = By.xpath("//div[@id='error_explanation']/ul/li");


    public OrderPage(WebDriver driver, Logger log) {
        super(driver, log);
    }

    public static OrderPage from_main_page(MainPage mainPage, String puppy_name) {
        mainPage.open_page();
        PuppiesPage puppyPage = mainPage.click_view_details(puppy_name);
        CartPage cart = puppyPage.click_adopt_button();
        return cart.click_complete_adoption();
    }

    public static OrderPage fromPuppy(PuppiesPage puppiesPage) {
        CartPage cart = puppiesPage.click_adopt_button();
        return cart.click_complete_adoption();
    }

    public void provide_order_details(String name, String address, String email) {
        log.info("Executing paymnet using name: " + name + ", address: " + address + " and email: " + email);
        type(name, ORDER_NAME);
        type(address, ORDER_ADDRESS);
        type(email, ORDER_EMAIL);
    }


    public String select_payment_method(String paymentMethod) {
        log.info("Selecting " + paymentMethod + " as payment method");
        WebElement dropdownElement = find_single_element(ORDER_PAY_TYPE);
        Select select = new Select(dropdownElement);
        select.selectByValue(paymentMethod);
        return select.getFirstSelectedOption().getText();
    }


    public MainPage click_order_button() {
        log.info("Finalizing an adoption");
        click(ORDER_BTN);
        return new MainPage(driver, log);
    }


    public String get_error_header() {
        log.info("Checking error Header");
        click(ORDER_BTN);
        String error_header_msg = find_single_element(ORDER_ERROR_HEADER).getText();
        return error_header_msg;

    }


    public String get_error_message() {
        log.info("Checking error message");
        click(ORDER_BTN);
        String error_msg = find_single_element(ORDER_ERROR_MSG).getText();
        return error_msg;

    }


}
