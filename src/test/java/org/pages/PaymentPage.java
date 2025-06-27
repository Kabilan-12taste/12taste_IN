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
	private By cartTotalAmount = By.cssSelector(".cart-subtotal .woocommerce-Price-amount bdi");
	private By minOrderPopup = By.cssSelector(".woocommerce-info");
	private By plusButton = By.xpath("//a[contains(@class, 'qtyBtn') and contains(@class, 'plus')]");

	public void viewtocartbtn() {
		clickElement(driver.findElement(viewtocartbtn));
	}

	public void clickProcessToPayment() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    try {
	        // Wait until the button is clickable and visible
	        WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//a[contains(@class, 'checkout-button') and not(contains(@class, 'disabled'))]")));

	        // Optional: Scroll into view if it's not visible
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkoutButton);

	        // Use JS click in case normal click doesn't work
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkoutButton);
	        System.out.println("✅ Proceed to Payment button clicked.");
	    } catch (Exception e) {
	        System.out.println("❌ Failed to click Proceed to Payment button: " + e.getMessage());
	        throw new RuntimeException("Checkout button not clickable or still disabled.");
	    }
	}


	public void toShippingDetails() {
		WebElement toShippingButton = driver.findElement(Toshippingbtn);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", toShippingButton);
	}

	public void toOrderComments() {
		WebElement toOrdercommments = driver.findElement(orderComments);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", toOrdercommments);
	}

	public void ordertypecomments() {
		enterText(driver.findElement(ordertypecomments), "Testing Purpose, Please Ignore");
	}

	public void topayment() {
		WebElement topayment = driver.findElement(ToPayment);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", topayment);
	}

	public void acceptTermsAndConditions() {
		WebElement terms = driver.findElement(termsAndConditions);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", terms);
	}

	public void proceedToPayment() {
		WebElement checkoutButton = driver.findElement(proceedToPayment);
		Actions actions = new Actions(driver);
		actions.moveToElement(checkoutButton).click().perform();
	}

	public void verifyOrderConfirmation() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement orderIdElement = wait.until(ExpectedConditions.visibilityOfElementLocated(orderIdLocator));
		String orderId = orderIdElement.getText();
		System.out.println("Order Confirmation Page Opened. Order ID: " + orderId);
	}

	public boolean isQRCodeDisplayed() {
		return driver.findElement(qrCodePopup).isDisplayed();
	}

	public void waitForPaymentSelection() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		System.out.println("Waiting for user to manually select a payment method...");

		wait.until(ExpectedConditions.or(ExpectedConditions.elementToBeSelected(driver.findElement(payWithUPI)),
				ExpectedConditions.elementToBeSelected(driver.findElement(bankTransfer)),
				ExpectedConditions.elementToBeSelected(driver.findElement(paytmPayment)),
				ExpectedConditions.elementToBeSelected(driver.findElement(ccAvenue)),
				ExpectedConditions.elementToBeSelected(driver.findElement(cashOnDelivery))));
	}

	// ✅ New logic for minimum order validation

	public boolean isMinOrderValuePopupDisplayed() {
		try {
			return driver.findElement(minOrderPopup).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getMinOrderValuePopupText() {
		try {
			return driver.findElement(minOrderPopup).getText();
		} catch (Exception e) {
			return "";
		}
	}

	public int getCartTotalAmount() {
		try {
			WebElement subtotalElement = driver
					.findElement(By.xpath("//span[@class='money' and @data-title='Subtotal']//bdi"));
			String amountText = subtotalElement.getText().replaceAll("[^0-9]", "");
			return Integer.parseInt(amountText);
		} catch (Exception e) {
			System.out.println("Could not read cart total: " + e.getMessage());
			return 0;
		}
	}

	public void increaseQuantityUntilMinimumReached(int minimumAmount) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		int currentTotal = getCartTotalAmount();
		int attempts = 0;

		while (currentTotal < minimumAmount && attempts < 20) {
			try {
				WebElement plusBtn = wait
						.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.qtyBtn.plus")));
				plusBtn.click();

				// Wait for subtotal to change
				int previousTotal = currentTotal;

				boolean isUpdated = wait.until(driver -> {
					int newTotal = getCartTotalAmount();
					return newTotal > previousTotal;
				});

				if (isUpdated) {
					currentTotal = getCartTotalAmount();
					System.out.println("Attempt " + (attempts + 1) + ": Cart total is ₹" + currentTotal);
				} else {
					System.out.println("Cart total did not increase. Possibly no effect from click.");
					break;
				}

				attempts++;

			} catch (Exception e) {
				System.out.println("Error during quantity increase: " + e.getMessage());
				break;
			}
		}

		if (currentTotal < minimumAmount) {
			throw new RuntimeException("❌ Could not reach ₹" + minimumAmount + ". Final total: ₹" + currentTotal);
		}

		System.out.println("✅ Final cart value: ₹" + currentTotal);
	}

}
