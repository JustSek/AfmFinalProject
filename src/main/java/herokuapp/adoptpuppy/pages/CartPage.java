package herokuapp.adoptpuppy.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePageObject {

    public By CHEW_TOY = By.id("toy");
    public By TRAVEL_CARRIER = By.id("carrier");
    public By COLLAR_LEASH = By.id("collar");
    public By VET_VISIT = By.id("vet");
    private By COMPLETE_ADOPTION_BTN = By.xpath("//input[@value='Complete the Adoption']");
    private By ADOPT_ANOTHER_BTN = By.xpath("//input[@value='Adopt Another Puppy']");
    private By CHANGE_MIND_BTN = By.xpath("//input[@value='Change your mind']");

    public CartPage(WebDriver driver, Logger log) {
        super(driver, log);
    }

    public void add_additions(By locator) {
        log.info("Adding " + locator + " to cart");
        driver.findElement(locator).click();
    }

    public void add_additions_to_all_dogs(By locator) {
        log.info("Adding " + locator + " to cart");
        select_all_checkboxes(find_multiple_elements(locator));
    }

    public void add_addition_to_selected_dog(int id, By locator) {
       List<WebElement> elements = find_multiple_elements(locator);
       WebElement to_add = elements.get(id);
       to_add.click();
    }

    public OrderPage click_complete_adoption() {
        log.info("Completing the Adoption");
        click(COMPLETE_ADOPTION_BTN);
      return new OrderPage(driver, log);
    }

    public MainPage click_adopt_another_puppy() {
        log.info("Adopting another Puppy");
        click(ADOPT_ANOTHER_BTN);
        return new MainPage(driver, log);
    }

    public MainPage click_change_your_mind() {
        log.info("Changing your mind and not adopting a puppy :(");
        click(CHANGE_MIND_BTN);
        return new MainPage(driver, log);
    }

    public boolean is_chew_toy_selected() {
        return find_single_element(CHEW_TOY).isSelected();
    }


    public boolean is_carrier_selected() {
        return find_single_element(TRAVEL_CARRIER).isSelected();
    }



    public boolean are_all_collar_leashes_selected() {
        for (WebElement element : find_multiple_elements(COLLAR_LEASH)) {
            if (!element.isSelected()) return false;
        }
        return true;
    }


    public boolean is_addition_selected(int id, By locator) {
        List<WebElement> addition = find_multiple_elements(locator);
        WebElement selected_element = addition.get(id);
        boolean is_selected_element_checked = selected_element.isSelected();
        return is_selected_element_checked;
    }

}
