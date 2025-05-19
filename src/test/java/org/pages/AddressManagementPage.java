package org.pages;

import org.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddressManagementPage extends BaseClass {

	// Locator of Address

	private By address = By
			.xpath("//a[@href='https://www.12taste.com/in/my-account/edit-address/' and contains(@class, 'nav-link')]");

	private By edit_billing = By.xpath(
			"//a[@href='https://www.12taste.com/in/my-account/edit-address/billing/' and contains(@class, 'edit')]");

	private By edit_shipping = By.xpath(
			"//a[@href='https://www.12taste.com/in/my-account/edit-address/shipping/' and contains(@class, 'edit')]");
	// Locators for Billing Address Form
	private By billingGSTField = By.id("billing_gst_number_in");
	private By billingPurchaseOrder = By.id("billing_your_purchase_order_number");
	private By billingFirstname = By.id("billing_first_name");
	private By billingLastname = By.id("billing_last_name");
	private By billingCompanyname = By.id("billing_company");
	private By billingCountry = By.id("select2-billing_country-container");
	private By billingStreetAddress_Row1 = By.id("billing_address_1");
	private By billingStreetAdress_Row2 = By.id("billing_address_2");
	private By billingCity = By.id("billing_city");
	private By billingState = By.id("select2-billing_state-container");
	private By billingPincode = By.id("billing_postcode");
	private By billingPhone_no = By.id("billing_phone");
	private By billingEmail_address = By.id("billing_email");

	private By save_button = By.xpath("//button[text()='Save address']");
	// Confirmation Message
	private By confirmationMessage = By.xpath(
			"//div[contains(@class, 'woocommerce-message') and contains(text(), 'Address changed successfully')]");

	// Locators for Shipping Address Form

	private By shipping_gst_or_license = By.id("shipping_gst_number_in");
	private By shipping_purchse_order = By.id("shipping_your_purchase_order_number");
	private By shipping_firstname = By.id("shipping_first_name");
	private By shipping_lastname = By.id("shipping_last_name");
	private By shipping_companyname = By.id("shipping_company");
	private By shipping_country = By.id("shipping_country");
	private By shipping_streetaddress1 = By.id("shipping_address_1");
	private By shipping_streetaddress2 = By.id("shipping_address_2");
	private By shipping_city = By.id("shipping_city");
	private By shipping_state = By.id("shipping_state");
	private By shipping_pincode = By.id("shipping_postcode");

	// Method to Fill Billing Address
	public void fillBillingAddress(String gst_or_license_no, String purchase_order, String first_name, String last_name,
			String company_name, String country, String street_add_row1, String street_add_row2, String city,
			String state, String pincode, String phone_no, String email) {

		enterText(driver.findElement(billingGSTField), gst_or_license_no);
		enterText(driver.findElement(billingPurchaseOrder), purchase_order);
		enterText(driver.findElement(billingFirstname), first_name);
		enterText(driver.findElement(billingLastname), last_name);
		enterText(driver.findElement(billingCompanyname), company_name);
		// selectByVisibleText(driver.findElement(billingCountry), country);
		selectcountry();
		enterText(driver.findElement(billingStreetAddress_Row1), street_add_row1);
		enterText(driver.findElement(billingStreetAdress_Row2), street_add_row2);
		enterText(driver.findElement(billingCity), city);
		selectState();
		enterText(driver.findElement(billingPincode), pincode);
		enterText(driver.findElement(billingPhone_no), phone_no);
		enterText(driver.findElement(billingEmail_address), email);

	}

	public void fillShippingAddress(String gst_or_license_no, String purchase_order, String first_name,
			String last_name, String company_name, String country, String street_add_row1, String street_add_row2,
			String city, String state, String pincode) {

		enterText(driver.findElement(shipping_gst_or_license), gst_or_license_no);
		enterText(driver.findElement(shipping_purchse_order), purchase_order);
		enterText(driver.findElement(shipping_firstname), first_name);
		enterText(driver.findElement(shipping_lastname), last_name);
		enterText(driver.findElement(shipping_companyname), company_name);
		shippingcountry();
		enterText(driver.findElement(shipping_streetaddress1), street_add_row1);
		enterText(driver.findElement(shipping_streetaddress2), street_add_row2);
		enterText(driver.findElement(shipping_city), city);
		shippingstate();
		enterText(driver.findElement(shipping_pincode), pincode);

	}

	public void clickaddress() {
		clickElement(driver.findElement(address));

	}

	public void editbilling() {
		clickElement(driver.findElement(edit_billing));
	}

	public void editshipping() {
		clickElement(driver.findElement(edit_shipping));
	}

	// Method to Click Save Address Button
	public void clickSaveAddressButton() {
		WebElement saveAddressButton = driver.findElement(By.name("save_address"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveAddressButton);

	}

	// Get Confirmation Message
	public String getConfirmationMessage() {
		WebElement messageElement = driver.findElement(confirmationMessage);
		return messageElement.getText();
	}

	public void selectcountry() {
		WebElement countryDropdown = driver.findElement(By.id("billing_country"));
		Select select = new Select(countryDropdown);
		select.selectByValue("IN"); // India
	}

	public void selectState() {
		WebElement countryDropdown = driver.findElement(By.id("billing_state"));
		Select select = new Select(countryDropdown);
		select.selectByValue("TN"); // India

	}

	public void shippingcountry() {
		WebElement countryDropdown = driver.findElement(shipping_country);
		Select select = new Select(countryDropdown);
		select.selectByValue("IN"); // India
	}

	public void shippingstate() {
		WebElement countryDropdown = driver.findElement(By.id("shipping_state"));
		Select select = new Select(countryDropdown);
		select.selectByValue("TN"); // India
	}
}
