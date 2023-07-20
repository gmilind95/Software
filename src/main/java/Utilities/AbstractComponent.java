
  package Utilities;
  
  import java.time.Duration;
  
  import org.openqa.selenium.By; import org.openqa.selenium.WebDriver; import
  org.openqa.selenium.WebElement; import org.openqa.selenium.support.FindBy;
  import org.openqa.selenium.support.PageFactory; import
  org.openqa.selenium.support.ui.ExpectedConditions; import
  org.openqa.selenium.support.ui.WebDriverWait;

import PageObjects.CartPage;
  
 
  
  public class AbstractComponent {
  
  WebDriver driver;
  
  public AbstractComponent(WebDriver driver) {
  
  this.driver = driver; 
  PageFactory.initElements(driver, this);
  
  }
  

  @FindBy(css = "[routerlink*='cart']") 
  WebElement cartHeader;
  
  @FindBy(css = "[routerlink*='myorders']")
  WebElement orderHeader;
  
  
  public void waitForElementToAppear(By productsBy) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5).toMillis());
	    wait.until(ExpectedConditions.visibilityOf((WebElement) productsBy));
	}

  public void waitForElementToDisappear(WebElement ele) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5).toMillis());
	    wait.until(ExpectedConditions.invisibilityOf(ele));
	}
  
  public CartPage goToCartPage() {
	    cartHeader.click(); 
		CartPage cartPage = new CartPage(driver);
        return cartPage;
  }
  
  public void goToOrderPage() {
	  orderHeader.click();    
  }
  }  
 

  
 