package me.toddpickell.getthinkgeek.getgeekstuff;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.NodeList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	private static final String SEARCH_TERM = "Discrete Mathematics";
	private static final String THINK_GEEK_PRODUCT = "Back to the Future Marty Hat Replica";
	private static final String HTTP_IT_MST_EDU = "http://it.mst.edu";

	// private static final String HTTP_IT_MST_EDU =
	// "https://dl.dropbox.com/u/21041180/Sites/it.mst.edu/it.mst.edu/index.html";
	// private static final String HTTP_IT_MST_EDU =
	// "file:///Users/toddpickell/Dropbox/Public/Sites/it.mst.edu/it.mst.edu/index.html";

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
	}

	public void testGoogleTopHitUsingJS() throws Exception {
		WebDriver driver = new FirefoxDriver();
		driver.navigate().to("http://www.google.com");
		WebDriverWait wait = new WebDriverWait(driver, 10);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		assertPageTitleJS(js, "Google");

		WebElement searchbox = driver.findElement(By.name("q"));
		searchbox.sendKeys(SEARCH_TERM);
		searchbox.click();
		wait.until(ExpectedConditions.titleContains("Google Search"));

		WebElement tophit = (WebElement) js.executeScript("return document.getElementById('rso')");

		tophit.findElement(By.cssSelector("a[class='l']")).click();
		wait.until(ExpectedConditions.titleContains("Wikipedia"));
		assertTrue(takeScreenshot(driver, "googleTopHit"));

		driver.close();
	}

	private void assertPageTitleJS(JavascriptExecutor js, String expectedTitle) {
		String actualTitle = (String) js.executeScript("return document.title");
		assertTrue(
				"expected title: " + expectedTitle + ", actual title: "
						+ actualTitle,
				actualTitle.toLowerCase().contains(
						(expectedTitle).toLowerCase()));
	}

	public void testGoogleTopHit() throws Exception {
		WebDriver driver = new FirefoxDriver();
		driver.navigate().to("http://www.google.com");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		assertPageTitle(driver, "Google");

		WebElement searchbox = driver.findElement(By.name("q"));
		searchbox.sendKeys(SEARCH_TERM);
		searchbox.submit();
		wait.until(ExpectedConditions.titleContains("Google Search"));

		WebElement tophit = driver.findElement(By.id("rso"));
		tophit.findElement(By.cssSelector("a[class='l']")).click();
		wait.until(ExpectedConditions.titleContains("Wikipedia"));
		assertTrue(takeScreenshot(driver, "googleTopHit"));

		driver.close();
	}

	/*
	 * This test is to represent a user story Todd is a big BTTF fan and just
	 * got an email that ThinkGeek has a sale on BTTF gear. Todd would like to
	 * purchase the Marty McFly hat from the ThinkGeek site
	 */
	public void GetCoolHatFromThinkgeek() throws Exception {
		// public void testGetCoolHatFromThinkgeek() throws Exception {
		WebDriver driver = new FirefoxDriver();
		driver.navigate().to("http://www.thinkgeek.com");
		assertPageTitle(driver, "ThinkGeek");

		WebElement searchgeek = driver.findElement(By.id("search-term"));
		searchgeek.sendKeys("back to the future");
		searchgeek.submit();

		driver.findElement(
				By.cssSelector("img[alt='Back to the Future Marty Hat Replica']"))
				.click();
		assertPageTitle(driver, THINK_GEEK_PRODUCT);

		driver.findElement(By.id("submitcart")).click();
		assertPageTitle(driver, "Your Loot!");

		driver.findElement(By.className("cart_checkout")).click();
		assertPageTitle(driver, "Warpspeed Checkout");

		WebElement loginbox = driver.findElement(By.className("loginbox"));
		WebElement emailbox = loginbox.findElement(By
				.cssSelector("input[name='un']"));
		emailbox.sendKeys("myappleguy@gmail.com");
		WebElement passbox = loginbox.findElement(By
				.cssSelector("input[name='pass']"));
		passbox.sendKeys("mypasswordsucks");
		loginbox.submit();
		assertPageTitle(driver, "Warpspeed Checkout");
		assertTrue(takeScreenshot(driver, THINK_GEEK_PRODUCT));

		driver.findElement(
				By.cssSelector("a[title='Click here to edit the contents of your cart.']"))
				.click();
		assertPageTitle(driver, "Your Loot!");

		WebElement checkBox = driver.findElement(By
				.cssSelector("input[name='0_del']"));
		checkBox.click();
		assertTrue(checkBox.isSelected());
		driver.findElement(
				By.cssSelector("input[type='submit'][value='Update Cart']"))
				.click();

		driver.findElement(By.className("logolink")).click();
		assertPageTitle(driver, "ThinkGeek");

		driver.close();
	}

	private Boolean takeScreenshot(WebDriver driver, String name) {
		try {
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("screenshots/" + name + " "
					+ new Date() + new Date().getTime() + ".png"));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void assertPageTitle(WebDriver driver, String expectedTitle) {
		String actualTitle = driver.getTitle();
		assertTrue(
				"expected title: " + expectedTitle + ", actual title: "
						+ actualTitle,
				actualTitle.toLowerCase().contains(
						(expectedTitle).toLowerCase()));
	}

	/*
	 * UC 1: Student wants to register IPad on campus network Actor: Student
	 * Desired Action: register IPad on campus network
	 */

	public void MstUseCase1() throws Exception {
		// public void testMstUseCase1() throws Exception {
		WebDriver driver = mstUseCaseSetup();

		// 1. Click on connect to the S&T wireless network.
		selectConnectToMSTWifi(driver);

		// 2. Click on Device Registration.
		driver.findElement(
				By.xpath("/html/body/div/div[6]/div[3]/div/table/tbody/tr/td/p/a"))
				.click();
		assertTrue(
				"expected title: Missouri S&T Device Registration, actual title: "
						+ driver.getTitle(),
				driver.getTitle().equals("Missouri S&T Device Registration"));

		// 3. Click on Find your Ethernet address(MAC Address)
		driver.findElement(By.linkText("Find your ethernet address")).click();
		assertTrue(
				"expected title: Missouri S&T How to Find Your Ethernet Address, actual title: "
						+ driver.getTitle(),
				driver.getTitle().equals(
						"Missouri S&T How to Find Your Ethernet Address"));

		// 4. Record your MAC address
		String myMacAddy = "60:33:4b:0b:3f:cc";

		// 5. Click on Students
		// driver.findElement(By.linkText("Students")).click();
		// getting popup that cant get source on. ****** Need to be able to
		// login to continue ********

		// 6. Login with your SSO User ID and password
		// 7. Click Register Desktop and Laptop Systems
		// 8. Type your SSO User ID in the “Owner User ID” field
		// 9. Click Edit Hosts
		// 10. Select a Hostname (should us ‘device.mst.edu’ as part of the host
		// name)
		// 11. Type in your system’s ethernet MAC address
		// 12. Click Register
		// 13. Verify the device you registered appears in the “Devices Owned
		// by” list.

		driver.close();
	}

	/*
	 * UC 3: Nonsponsored visitor wants to connect laptop on campus wireless
	 * network Actor: Nonsponsored visitor to campus Desired Action: Connect
	 * windows laptop on campus wireless network
	 */
	public void MstUseCase3() throws Exception {
		// public void testMstUseCase3() throws Exception {
		WebDriver driver = mstUseCaseSetup();

		// 1. Click on connect to the S&T wireless network.
		selectConnectToMSTWifi(driver);

		// 2. Click on MST-Public Wireless
		driver.findElement(By.linkText("MST-Public Wireless")).click();
		assertTrue(
				"expected title: Missouri S&T Public Wifi Access, actual title: "
						+ driver.getTitle(),
				driver.getTitle().equals("Missouri S&T Public Wifi Access"));

		// user just follows directions on page now

		driver.close();
	}

	/*
	 * UC 4: Student wants to setup email on IPhone Actor: Student Desired
	 * Action: Setup email on IPhone
	 */

	/*
	 * UC 6: Student wants to setup email on Android Phone Actor: Student
	 * Desired Action: Setup email on Android Phone
	 */
	public void MstUserCase4n6() throws Exception {
		// public void testMstUserCase4n6() throws Exception {
		WebDriver driver = mstUseCaseSetup();

		// 1. Click on Email (Students)
		clickOnStudentEmail(driver);

		// 2. Click on Setup Mobile Device Email
		clickOnSetupMobile(driver);

		// user just follows directions on page now

		driver.close();
	}

	private WebDriver mstUseCaseSetup() {
		WebDriver driver = new FirefoxDriver();
		driver.navigate().to(HTTP_IT_MST_EDU);
		assertTrue(
				"expected title: Missouri S&T Information Technology, actual title: "
						+ driver.getTitle(),
				driver.getTitle().equals("Missouri S&T Information Technology"));
		return driver;
	}

	private void selectConnectToMSTWifi(WebDriver driver) {
		driver.findElement(By.cssSelector("a[href='/services/wireless/']"))
				.click();
		assertTrue(
				"expected title: Missouri S&T Campus Wireless Information, actual title: "
						+ driver.getTitle(),
				driver.getTitle().equals(
						"Missouri S&T Campus Wireless Information"));
	}

	private void clickOnSetupMobile(WebDriver driver) {
		driver.findElement(By.linkText("Setup Mobile Device Email")).click();
		assertTrue(
				"expected title: Missouri S&T Mobile Devices, actual title: "
						+ driver.getTitle(),
				driver.getTitle().equals("Missouri S&T Mobile Devices"));
	}

	private void clickOnStudentEmail(WebDriver driver) {
		driver.findElement(
				By.xpath("/html/body/div/div[6]/div/div[2]/div[2]/table[2]/tbody/tr/td/table/tbody/tr[2]/td[2]/p[3]/a"))
				.click();
		assertTrue("expected title: Missouri S&T Student Email, actual title: "
				+ driver.getTitle(),
				driver.getTitle().equals("Missouri S&T Student Email"));
	}

}
