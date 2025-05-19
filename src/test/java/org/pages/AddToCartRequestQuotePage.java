package org.pages;

import java.awt.AWTException;
import java.time.Duration;
import java.awt.Robot;

import org.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AddToCartRequestQuotePage extends BaseClass {
	private By addToCartButton = By
			.xpath("//button[contains(@class, 'product-form-cart-submit') and contains(text(),'Add to cart')]");
	private By requestQuoteButton = By
			.xpath("//a[contains(@class, 'add-request-quote-button') and contains(text(),'Request a Quote')]");
	private By increaseQuantityButton = By.xpath("//a[contains(@class, 'qtyBtn plus')]");
	private By decreaseQualityButton = By.xpath("//a[contains(@class, 'qtyBtn minus')]");
	private By confirmationMessage = By.xpath("//div[contains(@class, 'woocommerce-message')]");
	private By clearlist = By.xpath("//button[contains(@class, 'ywraq_clean_list') and contains(text(),'Clear List')]");
	private By downloadlist = By.id("ywraq-list-to-pdf");
	private By updatelist = By.xpath("//input [@value = 'Update List']");

	private By productclick = By.xpath("//span[@class='product-title' and text()='Boiled Milk Flavour']");

	private By firstname = By.id("first_name");
	private By lastname = By.id("last_name");
	private By email = By.id("email");
	private By phone_no = By.id("phone_number");
	private By company_name = By.id("company");
	private By gst_license = By.id("gst_or_business_license");
	//private By State = By.id("select2-state-container");
	private By feedback = By.id("message");

	private By sendRequestButton = By.xpath("//input[@class='button raq-send-request last']");

	private By order_no = By.xpath("//header/h2[contains(text(), 'Quote #')]");

	private By searchfiled = By.id("dgwt-wcas-search-input-3");

	public void search(String productname) throws AWTException {
		enterText(driver.findElement(searchfiled), productname);
		pressEnter();
	}

	public void productclick() {
		clickElement(driver.findElement(productclick));
	}

	public void clickAddToCart() {
		clickElement(driver.findElement(addToCartButton));
	}

	public String getConfirmationMessage() {
		return driver.findElement(confirmationMessage).getText();
	}

//	public void clickRequestQuote() {
//
//		clickElement(driver.findElement(requestQuoteButton));
//	}
//	
	public void clickRequestQuote() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // ✅ updated for Selenium 4

	    try {
	        // Optional: wait a short moment to allow any popup to appear
	        Thread.sleep(2000);

	        // Check for popup close button (customize XPath as needed)
	        WebElement popupCloseBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//button[contains(@class, 'popup-close') or contains(text(), 'Close') or contains(@aria-label, 'Close')]")));

	        // Close the popup
	        popupCloseBtn.click();
	        System.out.println("Popup detected and closed successfully.");
	    } catch (TimeoutException e) {
	        System.out.println("No popup appeared. Proceeding with quote click.");
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }

	    // Ensure the Request Quote button is clickable
	    try {
	        WebElement quoteBtn = wait.until(ExpectedConditions.elementToBeClickable(requestQuoteButton));

	        // JavaScript click for extra reliability
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].click();", quoteBtn);
	        System.out.println("Clicked on Request a Quote using JavaScript.");
	    } catch (Exception e) {
	        System.out.println("Failed to click Request a Quote button: " + e.getMessage());
	    }
	}


	public void fillQuoteForm(String firstName, String lastName, String mail, String phone, String company, String gst,
			String state, String message) {

		enterText(driver.findElement(firstname), firstName);
		enterText(driver.findElement(lastname), lastName);
		enterText(driver.findElement(email), mail);
		enterText(driver.findElement(phone_no), phone);
		enterText(driver.findElement(company_name), company);
		enterText(driver.findElement(gst_license), gst);
		selectState();
		enterText(driver.findElement(feedback), message);

	}

	public void clickSendRequest() {
		
		WebElement sendRequestButton = driver.findElement(By.xpath("//input[@class='button raq-send-request last']"));

		// JavaScript Click
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", sendRequestButton);
	}

	public void clickincreasebtn() {
		clickElement(driver.findElement(increaseQuantityButton));
	}

	public void clickdecrasebtn() {
		clickElement(driver.findElement(decreaseQualityButton));
	}

	public void clearlist() {
		clickElement(driver.findElement(clearlist));
	}

	public void updatelist() {
		clickElement(driver.findElement(updatelist));
	}

	public void downloadlist() {
		clickElement(driver.findElement(downloadlist));
	}

	public void orderno() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // ✅ updated for Selenium 4

		// Wait for the Quote Number to be visible
		WebElement quoteElement = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//header/h2[contains(text(), 'Quote #')]")));

		// Extract the text (e.g., "Quote #4977093 details")
		String quoteText = quoteElement.getText();

		// Extract only the Quote Number using string manipulation
		String quoteNumber = quoteText.replaceAll("\\D+", ""); // Removes non-numeric characters

		// Print the extracted Quote Number
		System.out.println("Quote Number: " + quoteNumber);
	}

	public boolean isCartCountUpdated(String expectedCount) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // ✅ updated for Selenium 4

	    try {
	        WebElement cartCountElement = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'cart-count')]")));

	        boolean isUpdated = wait.until(ExpectedConditions.textToBePresentInElement(cartCountElement, expectedCount));

	        if (isUpdated) {
	            System.out.println("Cart count updated successfully! Current count: " + expectedCount);
	        } else {
	            System.out.println("Cart count mismatch. Expected: " + expectedCount + ", but found: " + cartCountElement.getText());
	        }

	        return isUpdated;
	    } catch (TimeoutException e) {
	        System.out.println("Cart count verification failed: Expected " + expectedCount + " but not updated in time.");
	        return false;
	    }
	}

	public void selectState() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // ✅ updated for Selenium 4
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("state")));

	        // Locate the dropdown element
	        WebElement stateDropdown = driver.findElement(By.id("state"));

	        // Select using <select> dropdown
	        Select select = new Select(stateDropdown);
	        select.selectByValue("TN");  // Select by value
	        // select.selectByVisibleText("Tamil Nadu");  // Alternatively, select by text
	        System.out.println("Selected Tamil Nadu using <select> dropdown");	       
	}

}