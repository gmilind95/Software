
package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.AbstractComponent;

public class LandingPage extends AbstractComponent  {

	WebDriver driver;

	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//*[contains(text(),'Log in')]")
	WebElement Login;
	@FindBy(id = "signUp-phoneNumber")
	WebElement phoneNumber;
	@FindBy(id = "login-signup-form-login-radio-password")
	WebElement passwordtick;
	@FindBy(id = "login-signup-form__password-input")
	WebElement passwordTxt;
	@FindBy(id = "signUpSubmit")
	WebElement signUpSubmit;
	@FindBy(id = "login-signup-form__name-input")
	WebElement nametxt;
	@FindBy(id = "login-signup-form__email-input")
	WebElement emailtxt;


	@FindBy(css = "[class*='flyInOut']")
	WebElement errorMessage;

	public ProductCatalogue loginApplication(String phone, String password) throws InterruptedException {
		Login.click();
		Thread.sleep(2000);
		if (phone != null) {
			phoneNumber.sendKeys(phone);
			Thread.sleep(1000);
		}
		passwordtick.click();
		if (password != null) {
			passwordTxt.sendKeys(password);
		}
		signUpSubmit.click();
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		return productCatalogue;
	}

	public ProductCatalogue signUp(String phone,String name, String email) throws InterruptedException {
		Login.click();
		Thread.sleep(2000);
		if (phone != null) {
			phoneNumber.sendKeys(phone);
			Thread.sleep(1000);
		}
		nametxt.sendKeys(name);
		if (email != null) {
			emailtxt.sendKeys(email);
		}
		signUpSubmit.click();
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		return productCatalogue;
	}

     public void goTo(String url) {
    	 driver.get(url);
     }

     public String getErrorMessage() {
    	 WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
       return errorMessage.getText();
    	}

}
	  
	 
