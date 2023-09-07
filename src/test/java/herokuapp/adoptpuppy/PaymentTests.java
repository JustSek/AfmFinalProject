package herokuapp.adoptpuppy;

import herokuapp.adoptpuppy.base.CsvDataProviders;
import herokuapp.adoptpuppy.base.TestUtilities;
import herokuapp.adoptpuppy.pages.MainPage;
import herokuapp.adoptpuppy.pages.OrderPage;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.util.Map;

public class PaymentTests extends TestUtilities {


    @Test (enabled = true, dataProvider = "csv_reader", dataProviderClass = CsvDataProviders.class)
    public void order_form_single_filed_empty_negative_Test(Map<String, String> testData) {
        // Data
        String no = testData.get("no");
        String name = testData.get("name");
        String address = testData.get("address");
        String email = testData.get("email");
        String payment = testData.get("payment");
        String error = testData.get("error");
        String errorHeader = testData.get("errorHeader");
        String description = testData.get("description");

        log.info("Starting Order Form Test #" + no + " for " + description);

        OrderPage orderPage = OrderPage.from_main_page(new MainPage(driver, log), "Brook");
        orderPage.provide_order_details(name, address, email);
        orderPage.select_payment_method(payment);
        orderPage.click_order_button();
        Assert.assertEquals(orderPage.get_error_header(), errorHeader);
        Assert.assertEquals(orderPage.get_error_message(), error);
    }


    @Parameters({ "username", "address", "email" })
    @Test
    public void order_form_positive_Test(String username, String address, String email){
        log.info("Starting Positive order form test with all data");
        MainPage mainPage = new MainPage(driver, log);
        OrderPage orderPage = OrderPage.from_main_page(mainPage, "Brook");
        orderPage.provide_order_details(username, address, email);
        orderPage.select_payment_method("Credit card");
        orderPage.click_order_button();
        Assert.assertEquals(driver.getCurrentUrl(), mainPage.get_page_Url());
        Assert.assertEquals(mainPage.adoption_confirmation(), mainPage.get_expected_adoption_confirmation_message());
    }

}
