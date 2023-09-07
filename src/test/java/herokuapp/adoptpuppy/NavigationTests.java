package herokuapp.adoptpuppy;

import herokuapp.adoptpuppy.base.TestUtilities;
import herokuapp.adoptpuppy.pages.MainPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NavigationTests extends TestUtilities {

    @Test
    public void previous_btn_first_page_test() {
        MainPage mainPage = new MainPage(driver, log);
        mainPage.open_page();
        Assert.assertEquals(mainPage.get_current_page_number(), "1",
                "Main Page open on wrong page number");
        log.info("Checking if Previous button is active: ");
        Assert.assertTrue(driver.findElements(mainPage.getPREVIOUS_BTN_ENABLED()).isEmpty(),
                "Previous button is enabled, but should be disabled");
        Assert.assertFalse(driver.findElements(mainPage.getPREVIOUS_BTN_DISABLED()).isEmpty(),
                "Previous button is disabled, but should be enabled");
    }

    @Test
    public void previous_btn_test() {
        MainPage mainPage = new MainPage(driver, log);
        mainPage.open_page();
        Assert.assertEquals(mainPage.get_current_page_number(), "1",
                "Main Page open on wrong page number");
        mainPage.click_next_page_btn();
        Assert.assertEquals(mainPage.get_current_page_number(), "2");
        Assert.assertTrue(driver.findElement(mainPage.getNEXT_PAGE_BTN_ENABLED()).isDisplayed());
        mainPage.click_previous_page_btn();
        Assert.assertEquals(mainPage.get_current_page_number(), "1",
                "Page number should be 1 but it is: " + mainPage.get_current_page_number());
    }

}
