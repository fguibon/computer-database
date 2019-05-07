package com.excilys.test.webdriver;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumTest {

	private static ChromeDriverService service;
	private WebDriver driver;


	@BeforeClass
	public static void createAndStartService() throws IOException {
		service = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File("/home/excilys/Documents/chromedriver"))
				.usingAnyFreePort()
				.build();
		service.start();
	}

	@AfterClass
	public static void createAndStopService() {
		service.stop();
	}

	@Before
	public void createDriver() {
		driver = new RemoteWebDriver(service.getUrl(),
				new ChromeOptions());
	}

	@After
	public void quitDriver() {
		driver.quit();
	}

	@Test
	public void ComputerDatabaseTitleTest() {

		driver.get("http://localhost:8080/computer-database/");

		assertEquals("localhost", driver.getTitle());

	}

}
