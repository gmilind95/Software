package TestComponents;
import PageObjects.LandingPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.LogManager;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;
public  class BaseTest {
	public WebDriver driver;
	public LandingPage landingPage;
	Properties prop = new Properties();
	protected static final Logger log = Logger.getLogger(BaseTest.class);
	public WebDriver initializeDriver() throws IOException {
		PropertyConfigurator.configure(System.getProperty("user.dir")+"/ConfigDirectory/log4j.properties");
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "/ConfigDirectory/Config.properties");
		prop.load(fis);
		String browserName = System.getProperty("browser") != null ?
				System.getProperty("browser"): prop.getProperty("browser");

		if (browserName.contains("chrome")) {
			log.debug("[ Chrome Browser Launched ]");
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			if (browserName.contains("headless")) {
				options.addArguments("headless");
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("autofill.profile_enabled", false);
				prefs.put("profile.default_content_setting_values.notifications", 2);
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-dev-shm-usage");
				options.setExperimentalOption("prefs", prefs);
				options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
			}
			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new org.openqa.selenium.Dimension(1440, 900));
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		return driver;
	}
	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		log.debug("[ Navigated to: " + prop.getProperty("url")+"]");
		landingPage.goTo(prop.getProperty("url"));
		return landingPage;
	}
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		if (driver != null) {
			driver.close();
			//driver.quit();
		}
		log.debug("[ Test Execution Completed ]");
	}
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}

	protected void log(String message) {
		Date timestamp = new Date();
		String formattedMessage = "[" + timestamp + "] " + message;
		System.out.println(formattedMessage);
	}

	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		ObjectMapper objMapper = new ObjectMapper();
		List<HashMap<String, String>> data = objMapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
	}

	public void click(By locators)
		{
			try
			{
				waitFor(2);
				highlight(locators);
				driver.findElement(locators).click();
				waitFor(1);
			} catch (Exception e)
			{
				e.printStackTrace();
				Assert.fail("Failed to click on element [" + locators + "] ");
			}
		}

		public void clickIfPresent(By locators)
		{
			try
			{
				waitFor(1);
				driver.findElement(locators).click();
//            waitFor(1);
			} catch (NoSuchElementException e)
			{
				log("Element : " + locators + " is not present");
//            e.printStackTrace();
			}
		}

		public void clickWithMoreWait(By locators)
		{
			try
			{
				waitFor(5);
				highlight(locators);
				driver.findElement(locators).click();
				waitFor(10);
				waitForJsLoad(60);
				waitForNBLoad(60);
			} catch (Exception e)
			{
				e.printStackTrace();
				Assert.fail("Failed to click on element [" + locators + "] ");
			}
		}

		public void clear(WebElement webElement)
		{
			try
			{
				waitFor(1);
				highlight(webElement);
				webElement.clear();
				waitFor(1);
			} catch (Exception e)
			{
				e.printStackTrace();
				Assert.fail("Failed to click on element [" + webElement + "] ");
			}
		}

		public void pressBack(int times)
		{
			for (int i = 1; i <= times; i++)
			{
				driver.navigate().back();
				waitFor(1);
			}

		}

		public void click(WebElement webElement)
		{
			try
			{
				waitFor(1);
				highlight(webElement);
				webElement.click();
				waitFor(2);

			} catch (Exception e)
			{
				e.printStackTrace();
				Assert.fail("Failed to click on element [" + webElement + "] ");
			}
		}

		public void scrollUpto(WebElement e)
		{
			try
			{
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].scrollIntoView();", e);
			} catch (Exception ea)
			{
				ea.printStackTrace();
				Assert.fail("Failed to scroll upto [" + e + "] element.");
			}
		}


		public void sendKeys(By locators, String values)
		{
			try
			{
				highlight(locators);
				driver.findElement(locators).sendKeys(values);
			} catch (Exception e)
			{
				e.printStackTrace();
				Assert.fail("Failed to send String [" + values + "]");

			}
		}

		public void sendKeys(WebElement webelement, String str)
		{
			try
			{
//            waitFor(1);
				highlight(webelement);
				webelement.sendKeys(str);
//            waitFor(1);
			} catch (Exception e)
			{
				e.printStackTrace();
				Assert.fail("Failed to send string [" + str + "]");
			}
		}

		public void clearAndSendKeys(WebElement webelement, String str)
		{
			try
			{
				waitFor(1);
				highlight(webelement);
				webelement.clear();
				webelement.sendKeys(str);
				waitFor(1);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		public void sendKeyCharByChar(WebElement webElement, String str)
		{
			try
			{
				waitFor(2);
				highlight(webElement);
//            String[] strArray = str.split(" ");
				char[] strArray = str.toCharArray();

				for (int i = 0; i < str.length(); i++)
				{
					webElement.sendKeys(String.valueOf(strArray[i]));
					waitFor(1);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}


		}


		public boolean isElementPresent(By locatorKey)
		{
			try
			{
				driver.findElement(locatorKey);
				return true;
			} catch (org.openqa.selenium.NoSuchElementException e)
			{
				return false;
			}
		}



		public String getRandomValueFromArray(List<String> values)
		{
			Random rand = new Random();
			String randomElement = values.get(rand.nextInt(values.size()));
			if (randomElement.equalsIgnoreCase("Select"))
				return getRandomValueFromArray(values);
			else
				return randomElement;
		}



		public boolean isWebElementDisplayed(WebElement webElement)
		{
			try
			{
				waitFor(1);
//            highlight(webElement);
				return webElement.isDisplayed();
			} catch (Exception e)
			{
				return false;
			}
		}

		public boolean isElementVisible(By locator)
		{
			try
			{
				return driver.findElement(locator).isDisplayed();
			} catch (Exception e)
			{
				return false;
			}
		}

		public boolean isElementDisplayed(By locator)
		{
			waitFor(2);
			highlight(locator);
			Coordinates coordinate = ((Locatable) driver.findElement(locator)).getCoordinates();
			coordinate.onPage();
			coordinate.inViewPort();
			return driver.findElement(locator).isDisplayed();
		}

		public String getText(By locator)
		{
			try
			{
				waitFor(2);
				highlight(locator);
				return driver.findElement(locator).getText();
			} catch (Exception e)
			{
				e.printStackTrace();
				Assert.fail("Failed to get String for element [" + locator + "]");
			}
			return null;
		}

		public String getTextWithoutWait(By locator)
		{
			try
			{
				highlight(locator);
				return driver.findElement(locator).getText();
			} catch (Exception e)
			{
				e.printStackTrace();
				Assert.fail("Failed to get String for element [" + locator + "]");
			}
			return null;
		}

		public void highlight(By element)
		{
			try
			{
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute('style','background: ; border: 2px solid red;');", driver.findElement(element));
			} catch (Exception e)
			{
				e.printStackTrace();
				Assert.fail("Failed to highlight [" + element + "] element.");
			}
		}

		public void highlight(WebElement element)
		{
			try
			{
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute('style','background: ; border: 2px solid red;');", element);
			} catch (Exception e)
			{
				e.printStackTrace();
				Assert.fail("Failed to highlight [" + element + "] element.");
			}
		}

		public void waitFor(int i)
		{
			try
			{
				Thread.sleep(1000 * i);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		public void scrollDownTillLast()
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			waitFor(1);
		}

		public void scrollUpTillTop()
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(document.body.scrollHeight,0)");
		}

		public void scrollDown()
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,750)");
		}

		public void scrollDownBy(int a)
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,'"+a+"')");
		}

		public void refresh()
		{
			try
			{
//            waitFor(1);
				driver.navigate().refresh();
//            waitFor(2);
			} catch (Exception e)
			{
				Assert.fail("Failed to refresh page..");
			}
		}

		public boolean isElementTextDisplayed(String textVal)
		{
			String xpath = String.format("//*[text()='%s' or contains(text(),'%s')]", textVal, textVal);

			try
			{
				return driver.findElement(By.xpath(xpath)).isDisplayed();
			} catch (Exception e)
			{
				return false;
			}
		}

		public boolean isElementWithTextEnabled(String textVal)
		{
			String xpath = String.format("//*[text()='%s' or contains(text(),'%s')]", textVal, textVal);
			return driver.findElement(By.xpath(xpath)).isEnabled();
		}

		public void clickElementWithText(String textVal)
		{
			String xpath = String.format("//*[text()='%s' or contains(text(),'%s')]", textVal, textVal);
//        waitFor(2);
			click(By.xpath(xpath));
		}

		protected String getAttribute(By locator, String attribute)
		{
			String attributeValue = driver.findElement(locator).getAttribute(attribute);

			return attributeValue != null ? attributeValue : "null";
		}

		protected void scrollClick(By locator)
		{
			WebElement element = driver.findElement(locator);
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("arguments[0].scrollIntoView(true);", element);
			waitFor(1);
			js.executeScript("arguments[0].click();", element);
		}

		protected void scrollClick(WebElement element)
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("arguments[0].scrollIntoView(true);", element);
			waitFor(1);
			js.executeScript("arguments[0].click();", element);
		}

		protected void scrollUntilElementVisible(WebElement e)
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("arguments[0].scrollIntoView(true);", e);
			waitFor(1);
//        js.executeScript("arguments[0].click();", e);  // commented by Nancy on April 5,2022
		}

		public void scrollClickText(String textVal)
		{
			String xpath = String.format("//*[text()='%s' or contains(text(),'%s')]", textVal, textVal);
			scrollClick(By.xpath(xpath));
		}

		//created by Nancy to click the same text and not another text containing thi text (eg:career getting clicked instead of car)
		public void scrollClickExactText(String textVal)
		{
			String xpath = String.format("//*[text()='%s']", textVal, textVal);
			scrollClick(By.xpath(xpath));
		}

		public void switchToChildWindow()
		{
			String parentWindow = driver.getWindowHandle();
			Set<String> handles = driver.getWindowHandles();
			for (String windowHandle : handles)
			{
				if (!windowHandle.equals(parentWindow))
				{
					driver.switchTo().window(windowHandle);

					//closing child window
					driver.switchTo().window(parentWindow); //cntrl to parent window
					driver.close();
					driver.switchTo().window(windowHandle);
					System.out.println("closing Parent window and control on the new open window");
				}
			}
		}

		public void switchToChildWindowWithoutClosingParent()
		{
			String parentWindow = driver.getWindowHandle();
			Set<String> handles = driver.getWindowHandles();
			for (String windowHandle : handles)
			{
				if (!windowHandle.equals(parentWindow))
				{
					driver.switchTo().window(windowHandle);
					System.out.println("closing Parent window and control on the new open window");
				}
			}
		}

		public void switchToTab(int tabNo)
		{
			ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(tabNo));
		}

		protected void clickAction(By locator) throws InterruptedException
		{
			Actions action = new Actions(driver);
			action.click(driver.findElement(locator)).perform();
		}

		protected void waitForElementDisplay(By locator, int timeoutSeconds)
		{
			try
			{
				new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
			} catch (Exception e)
			{
				System.err.println("Waited for element [" + locator + "] for " + timeoutSeconds + " seconds");
			}
		}

		protected void waitForElementDisplay(WebElement locator, int timeoutSeconds)
		{
			try
			{
				new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.visibilityOf(locator));
			} catch (Exception e)
			{
				System.err.println("Waited for element [" + locator + "] for " + timeoutSeconds + " seconds");
			}
		}

		protected void waitForElementPresent(By locator, int timeoutSeconds)
		{
			try
			{
				new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.presenceOfElementLocated(locator));
			} catch (Exception e)
			{
				System.err.println("Waited for element [" + locator + "] for " + timeoutSeconds + " seconds");
			}
		}

		protected void waitForElementClickable(By locator, int timeoutSeconds)
		{
			try
			{
				new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.elementToBeClickable(locator));
			} catch (Exception e)
			{
				System.err.println("Waited for element [" + locator + "] to be clickable for " + timeoutSeconds + " seconds");
			}
		}

		protected void waitForElementClickable(WebElement locator, int timeoutSeconds)
		{
			try
			{
				new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.elementToBeClickable(locator));
			} catch (Exception e)
			{
				System.err.println("Waited for element [" + locator + "] to be clickable for " + timeoutSeconds + " seconds");
			}
		}


		public void waitForJsLoad(int seconds)
		{
			JavascriptExecutor js = (JavascriptExecutor) driver; // waits for loading indicator in browser header
			String ready;
//        waitFor(2);
			try
			{
				for (int i = 0; i < seconds; i++)
				{
					ready = js.executeScript("return document.readyState").toString();
					System.out.println(ready);
					if (ready.equals("complete"))
						break;
					else
						waitFor(1);
				}
				waitFor(1);
			} catch (Exception e)
			{
				System.err.println("Error in waiting for browser load" + e.getMessage());
			}
		}

		public boolean isTextInPage(String text)
		{
			try
			{
				return driver.getPageSource().contains(text);
			} catch (Exception e)
			{
				return false;
			}
		}

		public void closeTab()
		{
			try
			{
//            Thread.sleep(3);
				log("trying to close selenium WebDriver");
				if (driver != null)
					driver.close();
				log("Selenium WebDriver is closed successfully.");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		public void closeTab(int tab)
		{
			try
			{
				Thread.sleep(3);
				if (driver != null)
				{
					ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
					driver.switchTo().window(tabs2.get(tab));
					driver.close();
				}
				log("Tab "+tab+"is closed successfully.");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		public void waitForNBLoad(int seconds)
		{
			try
			{
				for (int i = 0; i < seconds / 2; i++)
				{
					waitFor(1);
					if (!driver.getPageSource().contains("Loading..."))
						break;
					else
						System.out.println("Loading...");
				}

			} catch (Exception e)
			{
				System.err.println("Loading error");
			}

		}

		public void waitForNBLoad(String loadingId, int seconds) throws InterruptedException
		{
			try
			{
				for (int i = 0; i < seconds / 2; i++)
				{
					waitFor(1);
					if (!driver.getPageSource().contains(loadingId))
						break;
					else
						System.out.println("Waiting for [" + loadingId + "]");
				}

			} catch (Exception e)
			{
				System.err.println("Loading error");
			}

		}




		public int getNumericValueInt(String numberTxt) // returns 1000 from " ₹ 1,000 " for calculations
		{
			String number = "";
			for (char c : numberTxt.toCharArray())
				if (Character.isDigit(c) || c == '.')
					number += c;

			return Integer.parseInt(number);
		}

		public List<String> getTextValuesFromElements(By locator, int limit)
		{
			int count = 0;

			List<WebElement> elements = driver.findElements(locator);
			List<String> textValues = new ArrayList<String>();

			for (WebElement element : elements)
			{
				if (count <= limit)
				{
					textValues.add(element.getText());
					count++;
				} else
					return textValues;
			}
			return textValues;
		}

		public String waitForLoadingIconDisappear()
		{
			try
			{
				(new WebDriverWait(driver, 40)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//div[@class='busy-holder']/div/img[@src='/static/img/loadingIcon_2.png']")));
				return null;
			} catch (TimeoutException e)
			{
				return "progress bar/loading icon is still appear even after wait for 40 sec.";
			}
		}

		public void getBrowserConsoleLog()
		{
			log("=== Browser Console logs ===");
			for (LogEntry entry : driver.manage().logs().get(LogType.BROWSER).getAll())
				log(entry.getLevel() + " " + entry.getMessage());
			log("============================");
		}

		public String getParagraphContaining(String identifierTxt)
		{
			String xpath = String.format("//*[contains(text(),'%s')]", identifierTxt);
			return getText(By.xpath(xpath));
		}



		public void sendKeysAction(CharSequence... text)
		{
			Actions action = new Actions(driver);
			action.sendKeys(text).build().perform();
		}

		public void scrollClickBtnWithText(String textVal)
		{
			String xpath = String.format("//button[text()='%s']", textVal, textVal);

			waitFor(1);
			scrollClick(By.xpath(xpath));
			waitFor(1);
		}

		public void waitForElementText(String textVal, int timeoutSeconds)
		{
			String xpath = String.format("//*[text()='%s' or contains(text(),'%s')]", textVal, textVal);

			waitForElementDisplay(By.xpath(xpath), timeoutSeconds);
		}

		public void getToUrl(String url)
		{
			try
			{
				driver.get(url);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		public boolean containsIgnoreCase(String parent, String child)
		{
			return parent.toLowerCase().contains(child.toLowerCase());
		}


		public void selectDropDownValue(int positionNeedToSelect)
		{
			Actions action = new Actions(driver);
			for (int i = 1; i <= positionNeedToSelect; i++)
			{
				action.sendKeys(Keys.DOWN);
			}
			action.sendKeys(Keys.ENTER);
			action.perform();
			waitFor(2);
		}


		public void hoverElement(WebElement el)
		{
			Actions action = new Actions(driver);
			action.moveToElement(el).perform();
			waitFor(1);
			action.release();

		}


		public void mouseMoveByOffset(int x, int y)
		{
			Actions action = new Actions(driver);
			action.moveByOffset(x, y).perform();
		}

		public void ScratchCard(By ele1)
		{
			WebElement ele = driver.findElement(ele1);
			Actions act = new Actions(driver);
			for (int i = 300; i >= -150; i -= 30) // i = 270
			{
				for (int j = 300; j >= -150; j = j - 30) // y 300- -150
				{
					try
					{
						act.clickAndHold(ele)
								.moveToElement(ele, i, j)
								.release().perform();
					} catch (StaleElementReferenceException E)
					{
						log("exiting the loop");
						break;
					}


				}
			}
		}

		public void selectByOption(By locator, String option)
		{
			Select action = new Select(driver.findElement(locator));
			action.selectByVisibleText(option);

		}


		public List<String> getAllOptions(By locator)
		{
			Select action = new Select(driver.findElement(locator));
			List<WebElement> options = action.getOptions();
			List<String> values = new ArrayList<String>();
			int size = options.size();
			for (int i = 0; i < size; i++)
			{
				String op = options.get(i).getText();
				values.add(op);
			}
			return values;
		}


		public WebElement findElementFromFrame(String xPath)
		{

			List<WebElement> frames = driver.findElements(By.xpath("//iframe"));
			int index = 0;
			for (WebElement frame : frames)
			{
				try
				{
					driver.switchTo().defaultContent();
					return driver.switchTo().frame(index).findElement(By.xpath(xPath));
				} catch (NoSuchFrameException | NoSuchElementException e)
				{
					index = index + 1;
				}
			}
			return driver.findElement(By.xpath(xPath));
		}

		public WebElement delete_findElementFromFrame(String xPath)
		{
			try
			{
				driver.switchTo().defaultContent();
				return driver.switchTo().frame(1).findElement(By.xpath(xPath));
			} catch (NoSuchFrameException | NoSuchElementException e)
			{
				driver.switchTo().defaultContent();
				return driver.switchTo().frame(0).findElement(By.xpath(xPath));
			}

		}

		public String getCurrentValueOfDropDownList(By by)
		{
			Select select = new Select(driver.findElement(by));
			return select.getFirstSelectedOption().getText();
		}

		public boolean isElementEnabled(By by)
		{
			return driver.findElement(by).isEnabled();
		}

		public boolean isElementSelected(By by)
		{
			return driver.findElement(by).isSelected();
		}

		public void clickOnTextSpan(String textVal)
		{
			String xpath = String.format("//span[.='%s']", textVal);
			click(By.xpath(xpath));
		}

		public void pressESCKey()
		{
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ESCAPE).perform();
			waitFor(3);
		}

		public String getCurrentDate()
		{
			Date date = new Date();
			DateFormat dateFormat2 = new SimpleDateFormat("dd MMM, yyyy");
			String date1 =  dateFormat2.format(date);
			System.out.println(date1);
			return date1;
		}

		public String getTomorrowDate()
		{

			DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
			Date date = new Date();

			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, 1);
			date = c.getTime();
			System.out.println(dateFormat.format(date));


			DateFormat dateFormat2 = new SimpleDateFormat("dd MMM, yyyy");
			System.out.println(dateFormat2.format(date));

			return dateFormat2.format(date);
		}

		public String getYesterdaysDate()
		{
			Date date = DateUtils.addDays(new Date(), -1);
			SimpleDateFormat sdf = new SimpleDateFormat("d");
			return sdf.format(date);
		}

		public String getFutureDate(int days)
		{
			Date date = DateUtils.addDays(new Date(), days);
			SimpleDateFormat sdf = new SimpleDateFormat("d");
			return sdf.format(date);
		}

		public List<String> getTextList(By locator)
		{
			List<String> textList = new ArrayList<>();
			List<WebElement> list = driver.findElements(locator);

			for(WebElement ele : list)
			{
				try
				{
					waitFor(1);
					highlight(ele);
					textList.add(ele.getText());
				} catch (Exception e)
				{
					e.printStackTrace();
					Assert.fail("Failed to get String for element [" + ele + "]");
				}
			}
			return textList;
		}

		public String getCurrentTime()
		{

			DateFormat dateFormat = new SimpleDateFormat("hh:mma");
			Date date = new Date();
			System.out.println(dateFormat.format(date));

			return dateFormat.format(date); //12:08 Am
		}

		public static String getMovementDate(int days)
		{
			Date date = DateUtils.addDays(new Date(), days);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			return sdf.format(date);
		}

		public void scrolldown() {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,250)");
		}

		public void javascriptClick(By locators) {
			try {
				waitFor(1);
				WebElement element = driver.findElement(locators);
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);

				waitFor(2);
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail("Failed to click on element [" + locators + "] ");
			}
		}

		public boolean isElementDisplayedUsingSize(By locator) throws InterruptedException {
			waitFor(2);
			//highlight(locator);
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			int size = driver.findElements(locator).size();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			if (size > 0) {
				return true;
			} else {
				return false;
			}
		}

		public void doubleClick(By locators)
		{
			try
			{
				waitFor(2);
				highlight(locators);
				Actions action = new Actions(driver);
				action.doubleClick(driver.findElement(locators)).perform();
				waitFor(1);
			} catch (Exception e)
			{
				e.printStackTrace();
				Assert.fail("Failed to click on element [" + locators + "] ");
			}
		}

		public void openNewWindowsTab(String url) {

			((JavascriptExecutor) driver).executeScript("window.open()");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			System.out.println("After Opening " + tabs.size());
			driver.switchTo().window(tabs.get(1));
			driver.get(url);
		}
		public String getFutureDate(int days,String format)
		{
			Date date = DateUtils.addDays(new Date(), days);
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}
		public void pressDownArrow()
		{
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ARROW_DOWN).perform();
			waitFor(3);
		}
		public void pressEnterKey()
		{
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ENTER).perform();
			waitFor(3);
		}



	public static String generateRandomMobileNumber()
	{

		return "9453" + RandomStringUtils.randomNumeric(4) + "000";
	}
	public static String generateRandomTestMobileNumber()
	{

		return "1" + RandomStringUtils.randomNumeric(9);
	}
	public static String generateRandomAmount()
	{
		return RandomStringUtils.randomNumeric(5);
	}
	public static String generateRandomDigit()
	{
		return RandomStringUtils.randomNumeric(1);
	}
	public static String generateRandomDigits(int length)
	{
		return RandomStringUtils.randomNumeric(length);
	}

	public static String generateRandomAlphabets(int length)
	{
		return RandomStringUtils.randomAlphabetic(length);
	}

	public static String generateRandomName()
	{
		return RandomStringUtils.randomAlphanumeric(5);
	}
	public static String generateRandomNameForSSN()
	{
		return RandomStringUtils.randomAlphabetic(5);
	}
	public static String generateRandomNameForPayRent()
	{
		return RandomStringUtils.randomAlphanumeric(5).concat("_Test _Automation");
	}

	public static String generateRandomEmail()
	{
		return RandomStringUtils.randomNumeric(4) + "@gmail.com";
	}

	public static HashMap<String, HashMap<String, Integer>> EXTENT_METHOD_COUNT = new HashMap<>();
	public static String suiteCount = "", proj = "";

	public static String generateRandomText(int length)
	{
		return RandomStringUtils.randomAlphanumeric(length);
	}
	public WebElement getRandomValueFromGivenList(List<WebElement> givenList) {
		int l = givenList.size();
		Random rand = new Random(); // Declare and initialize the Random object
		WebElement randomElement = givenList.get(rand.nextInt(l));
		while (randomElement.getText().equalsIgnoreCase("select")) {
			randomElement = givenList.get(rand.nextInt(l));
		}
		return randomElement;
	}
	public int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		Random r = new Random();
		return r.nextInt(max - min + 1) + min;
	}

}

