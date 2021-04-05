package loginTestCases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DataDrivenusingPOI {
	
	WebDriver driver;
	
	static List<String> usernamelist = new ArrayList<String>();
	static List<String> passwordlist = new ArrayList<String>();
	
	
	public void readexcel() throws IOException
	{
		FileInputStream excel = new FileInputStream("D:\\TestData.xlsx");
		
		Workbook workbook = new XSSFWorkbook(excel);
		
	    org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheet("Sheet1");
	    
	    Iterator<Row> rowiterate =  sheet.iterator();
	    
	    while(rowiterate.hasNext())
	    {
	    	Row rowvalue = rowiterate.next();
	    	
	    	Iterator<Cell> columniterator = rowvalue.iterator();
	    	int i = 2;
	    	while(columniterator.hasNext())
	    	{
	    		if(i%2==0)
	    		{
	    			usernamelist.add(columniterator.next().getStringCellValue());
		    		
	    		}
	    		else
	    		{
	    			passwordlist.add(columniterator.next().getStringCellValue());
	    		}
	    		
	    		i++;
	    	}
	    }	
		
	}
	
	public void login(String name, String value)
    {
    	String path = "D:\\chromedriver\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver",path);
        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/");
        
        WebElement username = driver.findElement(By.id("txtUsername"));
        username.sendKeys(name);
        
        WebElement pwd = driver.findElement(By.id("txtPassword"));
        pwd.sendKeys(value);
        
        
        WebElement loginclick = driver.findElement(By.id("btnLogin"));
        loginclick.click();
        
        driver.quit();
    }
	
	public void executetest()
	{
		for (int i=0;i<usernamelist.size();i++)
		{
			login(usernamelist.get(i),passwordlist.get(i));
		}
	}

	public static void main(String[] args) throws IOException {
		
		DataDrivenusingPOI usingPOI = new DataDrivenusingPOI();
		usingPOI.readexcel();
		System.out.println(usernamelist);
		System.out.println(passwordlist);
		usingPOI.executetest();

	}

}
