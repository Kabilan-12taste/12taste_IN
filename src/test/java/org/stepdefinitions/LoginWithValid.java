package org.stepdefinitions;

import org.base.BaseClass;
import org.junit.Assert;
import org.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginWithValid extends BaseClass {

	LoginPage login = new LoginPage();

	
	@Given("the user is on the login page")
	public void the_user_is_on_the_login_page() {

		setupBrowser();
		openUrl("https://www.12taste.com/in/my-account/");
	}

	@When("the user enters a valid email")
	public void the_user_enters_a_valid_email() {
		login.enterUsername("test_email@12taste.com");
	}

	@When("the user enters a valid password")
	public void the_user_enters_a_valid_password() {

		login.enterPassword("test_email@12taste.com");
	}

	@When("the user clicks the login button")
	public void the_user_clicks_the_login_button() {
		//login.clickLogin();
		login.clickLoginButton();
	}
	
	@Then("the user should be redirected to the dashboard")
	public void the_user_should_be_redirected_to_the_dashboard() {
		login.getWelcomeMessageText();
	}

}