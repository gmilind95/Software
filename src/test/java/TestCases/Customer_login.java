package TestCases;
import PageObjects.LandingPage;
import TestComponents.BaseTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class Customer_login extends BaseTest {

    String name = "NewTest"+generateRandomName().toString();
    String email =name+generateRandomEmail();
    String phone = generateRandomMobileNumber();

    @Test
  public void  TC01_CustomerFlow() throws IOException, InterruptedException {

        LandingPage landingPage = launchApplication();
        log.debug("Entering Phone:"+phone +" Entering Name:"+ name+" Entering Email:" + email);
        landingPage.signUp((phone),(name),(email));

    }
}
