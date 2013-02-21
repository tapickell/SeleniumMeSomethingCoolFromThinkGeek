package me.toddpickell.getthinkgeek.getgeekstuff;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    private static final String HTTP_IT_MST_EDU = "http://it.mst.edu";

	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    /* 
     * This test is to represent a user story 
     * Todd is a big BTTF fan and just got an email
     * that ThinkGeek has a sale on BTTF gear.
     * Todd would like to purchase the Marty McFly hat
     * from the ThinkGeek site*/
    
    public void testGetCoolHatFromThinkgeek() throws Exception {
    	WebDriver driver = new FirefoxDriver();
    	driver.navigate().to("http://www.thinkgeek.com");
    	String findName = "ThinkGeek";
    	assertHome(driver, findName);
    	
    	WebElement searchgeek = driver.findElement(By.id("search-term"));
    	searchgeek.sendKeys("back to the future");
    	searchgeek.submit();
    	
    	driver.findElement(By.cssSelector("img[alt='Back to the Future Marty Hat Replica']")).click();
    	String findProduct = "Back to the Future Marty Hat Replica";
    	assertTrue("expected title: Back to the Future Marty Hat Replica, actual title: " + driver.getTitle(), 
    			driver.getTitle().toLowerCase().contains((findProduct.toLowerCase())));
    	
    	driver.findElement(By.id("submitcart")).click();
    	String findCart = "Your Loot!";
    	assertLoot(driver, findCart);
    	
    	driver.findElement(By.className("cart_checkout")).click();
    	String findCheckout = "Warpspeed Checkout";
    	assertWarpspeed(driver, findCheckout);
    	
    	
    	WebElement loginbox = driver.findElement(By.className("loginbox"));
    	WebElement emailbox = loginbox.findElement(By.cssSelector("input[name='un']"));
    	emailbox.sendKeys("myappleguy@gmail.com");
    	WebElement passbox = loginbox.findElement(By.cssSelector("input[name='pass']"));
    	passbox.sendKeys("mypasswordsucks");
    	loginbox.submit();
    	assertWarpspeed(driver, findCheckout);
    	
    	driver.findElement(By.cssSelector("a[title='Click here to edit the contents of your cart.']")).click();
    	assertLoot(driver, findCart);
    	
    	driver.findElement(By.cssSelector("input[name='0_del']")).click();
    	driver.findElement(By.cssSelector("input[type='submit'][value='Update Cart']")).click();
    	
    	driver.findElement(By.className("logolink")).click();
    	assertHome(driver, findName);
    	
    	driver.close();
    }

	private void assertHome(WebDriver driver, String findName) {
		assertTrue("expected title: ThinkGeek, actual title: " + driver.getTitle(), 
    			driver.getTitle().toLowerCase().contains((findName).toLowerCase()));
	}

	private void assertLoot(WebDriver driver, String findCart) {
		assertTrue("expected title: Your Loot!, actual title: " + driver.getTitle(), 
    			driver.getTitle().toLowerCase().contains((findCart.toLowerCase())));
	}

	private void assertWarpspeed(WebDriver driver, String findCheckout) {
		assertTrue("expected title: Warpspeed Checkout, actual title: " + driver.getTitle(), 
    			driver.getTitle().toLowerCase().contains((findCheckout.toLowerCase())));
	}
	
	
    /* 
     * UC 1: Student wants to register IPad on campus network
     * Actor: Student
     * Desired Action: register IPad on campus network
     * */
    
    public void testMstUseCase1() throws Exception {
    	WebDriver driver = mstUseCaseSetup();
    	
//    	1. Click on connect to the S&T wireless network.
    	selectConnectToMSTWifi(driver);
    	
//    	2. Click on Device Registration.
    	driver.findElement(By.xpath("/html/body/div/div[6]/div[3]/div/table/tbody/tr/td/p/a")).click();
    	assertTrue("expected title: Missouri S&T Device Registration, actual title: " + driver.getTitle(), 
    			driver.getTitle().equals("Missouri S&T Device Registration"));
    	
//    	3. Click on Find your Ethernet address(MAC Address)
    	driver.findElement(By.linkText("Find your ethernet address")).click();
    	assertTrue("expected title: Missouri S&T How to Find Your Ethernet Address, actual title: " + driver.getTitle(), 
    			driver.getTitle().equals("Missouri S&T How to Find Your Ethernet Address"));
    	
//    	4. Record your MAC address
    	String myMacAddy = "60:33:4b:0b:3f:cc";
    	
//    	5. Click on Students
//    	driver.findElement(By.linkText("Students")).click();
    	// getting popup that cant get source on. ******  Need to be able to login to continue  ********
    	
//    	6. Login with your SSO User ID and password
//    	7. Click Register Desktop and Laptop Systems
//    	8. Type your SSO User ID in the “Owner User ID” field
//    	9. Click Edit Hosts
//    	10. Select a Hostname (should us ‘device.mst.edu’ as part of the host name)
//    	11. Type in your system’s ethernet MAC address
//    	12. Click Register
//    	13. Verify the device you registered appears in the “Devices Owned by” list.
    	
    	driver.close();
    }

    
    /* UC 3: Nonsponsored visitor wants to connect laptop on campus wireless network
     * Actor: Nonsponsored visitor to campus
     * Desired Action: Connect windows laptop on campus wireless network
     * */
    
    public void testMstUseCase3() throws Exception {
    	WebDriver driver = mstUseCaseSetup();
    	
//    	1. Click on connect to the S&T wireless network.
    	selectConnectToMSTWifi(driver);
    	
//    	2. Click on MST-Public Wireless
    	driver.findElement(By.linkText("MST-Public Wireless")).click();
    	assertTrue("expected title: Missouri S&T Public Wifi Access, actual title: " + driver.getTitle(), 
    			driver.getTitle().equals("Missouri S&T Public Wifi Access"));
    	
    	//user just follows directions on page now
    	
    	driver.close();
    }
    
    /* UC 4: Student wants to setup email on IPhone
     * Actor: Student
     * Desired Action: Setup email on IPhone
     * */
    
    /* UC 6: Student wants to setup email on Android Phone
     * Actor: Student
     * Desired Action: Setup email on Android Phone
     * */
    
    public void testMstUserCase4n6() throws Exception {
    	WebDriver driver = mstUseCaseSetup();
    	
//    	1. Click on Email (Students)
    	clickOnStudentEmail(driver);
    	
//    	2. Click on Setup Mobile Device Email
    	clickOnSetupMobile(driver);
    	
    	//user just follows directions on page now

    	driver.close(); 
    }

    
    private WebDriver mstUseCaseSetup() {
		WebDriver driver = new FirefoxDriver();
    	driver.navigate().to(HTTP_IT_MST_EDU);
    	assertTrue("expected title: Missouri S&T Information Technology, actual title: " + driver.getTitle(), 
    			driver.getTitle().equals("Missouri S&T Information Technology"));
		return driver;
	}
    
	private void selectConnectToMSTWifi(WebDriver driver) {
		driver.findElement(By.cssSelector("a[href='/services/wireless/']")).click();
    	assertTrue("expected title: Missouri S&T Campus Wireless Information, actual title: " + driver.getTitle(), 
    			driver.getTitle().equals("Missouri S&T Campus Wireless Information"));
	}
	
	private void clickOnSetupMobile(WebDriver driver) {
		driver.findElement(By.linkText("Setup Mobile Device Email")).click();
    	assertTrue("expected title: Missouri S&T Mobile Devices, actual title: " + driver.getTitle(), 
    			driver.getTitle().equals("Missouri S&T Mobile Devices"));
	}

	private void clickOnStudentEmail(WebDriver driver) {
		driver.findElement(By.xpath("/html/body/div/div[6]/div/div[2]/div[2]/table[2]/tbody/tr/td/table/tbody/tr[2]/td[2]/p[3]/a")).click();
    	assertTrue("expected title: Missouri S&T Student Email, actual title: " + driver.getTitle(), 
    			driver.getTitle().equals("Missouri S&T Student Email"));
	}

}






















