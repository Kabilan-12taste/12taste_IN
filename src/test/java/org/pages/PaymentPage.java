package org.pages;

import java.time.Duration;

import org.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaymentPage extends BaseClass {

//	private By processToPaymentBtn = By.cssSelector("a.checkout-button");
	private By billingDetails = By.xpath("//a [@class= 'nav-link active']");
	private By shippingDetails = By.xpath("//a[contains(text(),'Shipping')]");
	private By Toshippingbtn = By.xpath("//button[contains(@class, 'btnNext') and contains(text(), 'To shipping')]");
	private By orderComments = By.xpath("//button[contains(@class, 'btnNext') and contains(., 'To order comments')]");
	private By ordertypecomments = By.id("order_comments");
	private By ToPayment = By.xpath("//button[contains(@class, 'btnNext') and contains(text(), 'To payment')]");
	private By payWithUPI = By.id("payment_method_wc-upi");
	private By bankTransfer = By.xpath("//input[@id='payment_method_bacs']");
	private By paytmPayment = By.id("payment_method_paytm");
	private By ccAvenue = By.id("payment_method_ccavenue");
	private By cashOnDelivery = By.id("payment_method_cod");
	private By termsAndConditions = By.id("terms");
	private By proceedToPayment = By.id("place_order");
	private By orderIdLocator = By.xpath("(//span[@class='upiwc-payment-order-id'])[last()]");
	private By qrCodePopup = By.id("qr_code");

	private By viewtocartbtn = By.xpath("//a[@href='https://www.12taste.com/in/cart/']");

	public void verifyOrderConfirmation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // ✅ updated for Selenium 4
        
        // Wait until Order ID element is visible
        WebElement orderIdElement = wait.until(ExpectedConditions.visibilityOfElementLocated(orderIdLocator));
        
        // Extract Order ID text
        String orderId = orderIdElement.getText();
        
        // Print Order ID to console
        System.out.println("Order Confirmation Page Opened. Order ID: " + orderId);
    }
	public void viewtocartbtn() {
		clickElement(driver.findElement(viewtocartbtn));
	}

	public void clickProcessToPayment() {

		WebElement checkoutButton = driver.findElement(By.xpath("//a[contains(@class, 'checkout-button')]"));
		Actions actions = new Actions(driver);
		actions.moveToElement(checkoutButton).click().perform();

		// clickElement(driver.findElement(processToPaymentBtn));
	}

	public void toBillingDetails() {

	}

	public void toShippingDetails() {

		WebElement toShippingButton = driver.findElement(Toshippingbtn);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", toShippingButton);

	}

	public void toOrderComments() {
		WebElement toOrdercommments = driver.findElement(orderComments);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", toOrdercommments);
	}

	public void ordertypecomments() {
		enterText(driver.findElement(ordertypecomments), "I need this product soon");
	}

	public void topayment() {
		WebElement topayment = driver.findElement(ToPayment);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", topayment);
	}

	public void selectPaymentOption(String option) {
		switch (option.toLowerCase()) {
		case "pay with upi":
			clickElement(driver.findElement(payWithUPI));
			break;
		case "direct bank transfer":
			clickElement(driver.findElement(bankTransfer));
			break;
		case "paytm payment gateway":
			WebElement paytmpayment = driver.findElement(By.id("payment_method_paytm"));
			// Use JavaScript to click the element
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", paytmpayment);
			break;
		// clickElement(driver.findElement(paytmPayment));
		case "ccavenue":

			WebElement paymentOption = driver.findElement(By.id("payment_method_ccavenue"));
			// Use JavaScript to click the element
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", paymentOption);
			break;
		case "cash on delivery":
			WebElement cod = driver.findElement(cashOnDelivery);

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", cod);
			break;
		default:
			throw new IllegalArgumentException("Invalid Payment Option: " + option);
		}
	}

	public void tickcheckbox() {
		WebElement tick = driver.findElement(By.id("customer_pcod"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", tick);

	}

	public void acceptTermsAndConditions() {

		WebElement terms = driver.findElement(termsAndConditions);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", terms);

	}

	public void proceedToPayment() {
		WebElement checkoutButton = driver.findElement(By.xpath("//button[@id='place_order']"));
		Actions actions = new Actions(driver);
		actions.moveToElement(checkoutButton).click().perform();

		//clickElement(driver.findElement(proceedToPayment));
	}

	public boolean isQRCodeDisplayed() {
		return driver.findElement(qrCodePopup).isDisplayed();
	}
	
	public void waitForPaymentSelection() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // ✅ updated for Selenium 4

	    System.out.println("Waiting for user to manually select a payment method...");

	    wait.until(ExpectedConditions.or(
	        ExpectedConditions.elementToBeSelected(driver.findElement(payWithUPI)),
	        ExpectedConditions.elementToBeSelected(driver.findElement(bankTransfer)),
	        ExpectedConditions.elementToBeSelected(driver.findElement(paytmPayment)),
	        ExpectedConditions.elementToBeSelected(driver.findElement(ccAvenue)),
	        ExpectedConditions.elementToBeSelected(driver.findElement(cashOnDelivery))
	    ));

	    System.out.println("Payment method detected. Resuming automation...");
	}
}
