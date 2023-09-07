package herokuapp.adoptpuppy.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PuppiesPage extends BasePageObject{

    private By ADOPT_ME_BTN = By.xpath("//input[@value='Adopt Me!']");
    private By ADOPT_A_PUPPY_PAGE = By.linkText("Adopt a Puppy");

    public PuppiesPage(WebDriver driver, Logger log) {
        super(driver, log);
    }


    public CartPage click_adopt_button() {
        log.info("Adopting a Puppy!");
        click(ADOPT_ME_BTN);
        return new CartPage(driver, log);
    }


    public MainPage go_back_to_addoption_page() {
        log.info("Going back in order to adopt another Puppy!");
        click(ADOPT_A_PUPPY_PAGE);
        return new MainPage(driver, log);
    }

}
