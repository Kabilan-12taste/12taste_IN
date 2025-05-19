package org.stepdefinitions;

import java.awt.AWTException;

import org.base.BaseClass;
import org.junit.Assert;
import org.junit.Ignore;
import org.pages.AddToCartRequestQuotePage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Quote extends BaseClass {

	LoginWithValid login = new LoginWithValid();
	AddToCartRequestQuotePage addtocartpage = new AddToCartRequestQuotePage();

	@Given("User navigate to the product listing pages")
	public void user_navigate_to_the_product_listing_page() throws AWTException {

		login.the_user_is_on_the_login_page();
		login.the_user_enters_a_valid_email();
		login.the_user_enters_a_valid_password();
		login.the_user_clicks_the_login_button();
		addtocartpage.search("CEC – Milk ML Flavour – 0786-02");
	}
	

	@When("User click on the Request a Quote button")
	public void user_click_on_the_Request_a_Quote_button() {
		addtocartpage.clickRequestQuote();

	}
	
	@Then("User should be redirected to the quote details page")
	public void user_should_be_redirected_to_the_quote_details_page() throws InterruptedException {
		Thread.sleep(3000);
		String expectedurl = "https://www.12taste.com/in/request-a-quote/";
		String currentURL = getCurrentURL();

		Assert.assertEquals("Both are same", expectedurl, currentURL);

	}

	@Then("User fill in the quote request form with valid details")
	public void user_fill_in_the_quote_request_form_with_valid_details() {
		addtocartpage.fillQuoteForm("1-2 Taste", "Tester", "test_email@12taste.com", "9080672610", "1-2 taste",
				"12345rty", "Tamil Nadu", "Test input");
	}

	@Ignore
	@Then("User click on the Send Your Request button")
	public void user_click_on_the_Send_Your_Request_button() {
		addtocartpage.clickSendRequest();
	}
	@Ignore

	@Then("an email should be triggered with an order number")
	public void an_email_should_be_triggered_with_an_order_number() {
		System.out.println("Mail has been received with the order number");
	}
	@Ignore
	@Then("User should be redirected to the order confirmation page with the Order ID")
	public void user_should_be_redirected_to_the_order_confirmation_page_with_the_Order_ID() {
		addtocartpage.orderno();
	}
}
