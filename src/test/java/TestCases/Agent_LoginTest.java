package TestCases;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import PageObjects.LandingPage;
import TestComponents.BaseTest;

public class Agent_LoginTest extends BaseTest {
	@Test(dataProvider = "getData")
    //public void loginError(String email ,String password,String productName) throws IOException, InterruptedException {
	public void TC02_AgentFlow(HashMap<String, String> input) throws IOException, InterruptedException {
		log.debug("[ Redirecting to home Service Page ] ");
		LandingPage landingPage = launchApplication();
		log.debug("[ Entering PhoneNumber and Password ] ");
		landingPage.loginApplication(input.get("phone"), input.get("password"));
	}

	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "/resources/TestData/loginCredentials.json");
		if (data.size() >= 2) {
			return new Object[][] { { data.get(0) }, { data.get(1) } };
		} else if (data.size() == 1) {
			return new Object[][] { { data.get(0) } };
		} else {
			throw new IllegalArgumentException("Insufficient data in the JSON file.");
		}
	}
}







/*
	 * HashMap<String,String> map = new HashMap<String,String>(); map.put("email",
	 * "anshika@gmail.com"); map.put("password", "Iamking@000"); map.put("product",
	 * "ZARA COAT 3");
	 * 
	 * HashMap<String,String> map1 = new HashMap<String,String>(); map1.put("email",
	 * "shetty@gmail.com"); map1.put("password", "Iamking@000"); map1.put("product",
	 * "ADIDAS ORIGINAL"); return new Object[][] { { map }, { map1 } };
	 */

// return new Object[][] {{"anshika@gmail.com","Iamking@000","ZARA COAT 3"},
// {"shetty@gmail.com","Iamking@000","ADIDAS ORIGINAL" } };
