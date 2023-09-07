package herokuapp.adoptpuppy;

import herokuapp.adoptpuppy.base.TestUtilities;
import herokuapp.adoptpuppy.pages.CartPage;
import herokuapp.adoptpuppy.pages.MainPage;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import herokuapp.adoptpuppy.pages.OrderPage;
import herokuapp.adoptpuppy.pages.PuppiesPage;

public class E2eTests extends TestUtilities {

    @Parameters({ "username", "address", "email" })
    @Test
    public void adopt_one_dog_Test(String username, String address, String email) {
        // Adopt Brooke,
        MainPage mainPage = new MainPage(driver, log);
        mainPage.open_page();
        PuppiesPage puppyPage = mainPage.click_view_details("Brook");
        CartPage cart = puppyPage.click_adopt_button();

        // add a Chewy Toy and a Travel Carrier
        cart.add_additions(cart.CHEW_TOY);
        cart.add_additions(cart.TRAVEL_CARRIER);
        Assert.assertTrue(cart.is_chew_toy_selected(), "Chew toy not selected");
        Assert.assertTrue(cart.is_carrier_selected(), "Carrier not selected");
        OrderPage orderPage = cart.click_complete_adoption();

        // pay with Check
        orderPage.provide_order_details(username, address, email);
        String selectedPaymentMethod = orderPage.select_payment_method("Check");
        Assert.assertEquals(selectedPaymentMethod, "Check");
        orderPage.click_order_button();
        String confiramtionAdoptionMessage = mainPage.adoption_confirmation();

        Assert.assertEquals(confiramtionAdoptionMessage, mainPage.get_expected_adoption_confirmation_message());
    }


    @Parameters({ "username", "address", "email" })
    @Test
    public void adopt_two_random_dogs_with_the_same_addition_Test(String username, String address, String email)  {
        // Adopt first dog
        MainPage mainPage = new MainPage(driver, log);
        mainPage.open_page();
        PuppiesPage puppyPage = mainPage.click_view_details("Maggie Mae");
        CartPage cart = puppyPage.click_adopt_button();

        // Adopt second dog
        puppyPage.go_back_to_addoption_page();
        Assert.assertEquals(mainPage.get_page_Url(), "https://spartantest-puppies.herokuapp.com/");

        mainPage.click_view_details("Twinkie");
        puppyPage.click_adopt_button();

        // add a Collar & Leash to each
        cart.add_additions_to_all_dogs(cart.COLLAR_LEASH);
        Assert.assertTrue(cart.are_all_collar_leashes_selected(), "Collar & Leash not selected");
        OrderPage orderPage = cart.click_complete_adoption();

        // pay with Credit Card
        orderPage.provide_order_details(username, address, email);
        String selectedPaymentMethod = orderPage.select_payment_method("Credit card");
        Assert.assertEquals(selectedPaymentMethod, "Credit card");
        orderPage.click_order_button();
        Assert.assertEquals(mainPage.adoption_confirmation(), mainPage.get_expected_adoption_confirmation_message());
    }

    @Parameters({ "username", "address", "email" })
    @Test
    public void adopt_two_dogs_and_add_3_items_to_one(String username, String address, String email) {
        // Adopt first dog
        MainPage mainPage = new MainPage(driver, log);
        mainPage.open_page();
        PuppiesPage puppyPage = mainPage.click_view_details("Ruby Sue");
        CartPage cart = puppyPage.click_adopt_button();

        // Adopt second dog
        puppyPage.go_back_to_addoption_page();
        mainPage.click_view_details("Sparky");
        puppyPage.click_adopt_button();

        // add 3 Random Accessories to one of them [in this case to second dog]
        cart.add_addition_to_selected_dog(1, cart.COLLAR_LEASH);
        cart.add_addition_to_selected_dog(1,cart.CHEW_TOY);
        cart.add_addition_to_selected_dog(1, cart.VET_VISIT);
        Assert.assertTrue(cart.is_addition_selected(1, cart.COLLAR_LEASH));
        Assert.assertTrue(cart.is_addition_selected(1, cart.CHEW_TOY));
        Assert.assertTrue(cart.is_addition_selected(1, cart.VET_VISIT));
        OrderPage orderPage = cart.click_complete_adoption();

        // pay with Credit Card
        orderPage.provide_order_details(username, address, email);
        String selectedPaymentMethod = orderPage.select_payment_method("Credit card");
        Assert.assertEquals(selectedPaymentMethod, "Credit card");
        orderPage.click_order_button();
        Assert.assertEquals(mainPage.adoption_confirmation(), mainPage.get_expected_adoption_confirmation_message());

    }
}



