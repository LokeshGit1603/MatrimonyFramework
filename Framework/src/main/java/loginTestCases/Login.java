package loginTestCases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Login {
	
	String [][] data= null;
	 WebDriver driver;
	@BeforeTest
	public void beforetest() {
		String path = "D:\\chromedriver\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver",path);
        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/");
	}
	
	@AfterTest
	public void aftertest() throws InterruptedException
	{
		 Thread.sleep(2000);
	        
	        driver.quit();
	}
	
	public String[][] excelread() throws BiffException, IOException
	{
		FileInputStream excel = new FileInputStream("D:\\TestData.xls");
		
	Workbook workbook = Workbook.getWorkbook(excel);
	
	Sheet sheet = workbook.getSheet("Sheet1");
	
	int rowcount = sheet.getRows();
	int columncount = sheet.getColumns();
	
	String testdata[][] = new String[rowcount-1][columncount];
	
	for(int i = 1;i<rowcount; i++)
	{
		for(int j=0;j<columncount;j++)
		{
			testdata[i-1][j] = sheet.getCell(j, i).getContents();
			
			System.out.println(testdata[i-1][j]);
		}
	}
	
	return testdata;
	
		
	}
	@DataProvider(name = "Data")
	public String[][] dataprovider() throws BiffException, IOException
	{
		data=excelread();
		return data;
	}
	
	@Test (dataProvider = "Data" )
	public void bothcorrect(String name, String value) throws InterruptedException
	{
        
        WebElement username = driver.findElement(By.id("txtUsername"));
        username.sendKeys(name);
        
        WebElement pwd = driver.findElement(By.id("txtPassword"));
        pwd.sendKeys(value);
        
        
        WebElement loginclick = driver.findElement(By.id("btnLogin"));
        loginclick.click();
        
        System.out.println("All executed");
            
        
	}

}
