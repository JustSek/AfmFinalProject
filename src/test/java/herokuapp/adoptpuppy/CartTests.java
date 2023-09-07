package herokuapp.adoptpuppy;

import herokuapp.adoptpuppy.base.TestUtilities;
import herokuapp.adoptpuppy.pages.CartPage;
import herokuapp.adoptpuppy.pages.MainPage;
import herokuapp.adoptpuppy.pages.OrderPage;
import herokuapp.adoptpuppy.pages.PuppiesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CartTests extends TestUtilities {

    @Test
    public void complete_the_adoption_btn_test() {
        MainPage mainPage = new MainPage(driver, log);
        mainPage.open_page();
        PuppiesPage puppyPage = mainPage.click_view_details("Brook");
        CartPage cart = puppyPage.click_adopt_button();
        OrderPage orderPage = cart.click_complete_adoption();
        Assert.assertEquals(driver.getCurrentUrl(), orderPage.get_PAGE_URL(), "Wrong URL");
    }

    @Test
    public void adopt_another_puppy_btn_test() {
        MainPage mainPage = new MainPage(driver, log);
        mainPage.open_page();
        PuppiesPage puppyPage = mainPage.click_view_details("Brook");
        CartPage cart = puppyPage.click_adopt_button();
        cart.click_adopt_another_puppy();
        Assert.assertEquals(driver.getCurrentUrl(), mainPage.get_page_Url() + "?", "Wrong URL");
    }

    @Test
    public void change_mind_btn_test() {
        MainPage mainPage = new MainPage(driver, log);
        mainPage.open_page();
        PuppiesPage puppyPage = mainPage.click_view_details("Brook");
        CartPage cart = puppyPage.click_adopt_button();
        cart.click_change_your_mind();
        Assert.assertEquals(driver.getCurrentUrl(), mainPage.get_page_Url(), "Wrong URL");
        Assert.assertEquals(mainPage.change_mind_confirmation(), mainPage.getEXPECTED_CHANGE_MIND_MSG(),
                "Wrong message");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(mainPage.change_mind_confirmation_msg_color(),
                mainPage.getEXPECTED_CHANGE_MIND_MSG_COLOUR(),
                "Expected message colour is not the same as actual");
        softAssert.assertAll();
    }


}
