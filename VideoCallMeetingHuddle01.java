package automation;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VideoCallMeetingHuddle01 {
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		ChromeOptions options = new ChromeOptions();
		options.addArguments("use-fake-ui-for-media-stream");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\karishma.oberoi\\Documents\\QA\\chromedriver_win\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://beta.huddle01.com/");
		driver.findElement(By.xpath("//img[@alt='start-meeting']")).click();

		// Login as Guest 1
		driver.findElement(By.xpath("//input[@placeholder='Please Enter Your E-mail']")).sendKeys("karishmaoberoi94@gmail.com");
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 20).ignoring(StaleElementReferenceException.class);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Enter Meeting']")));
		driver.findElement(By.xpath("//button[text()='Enter Meeting']")).click();
		String roomMeetingUrl=driver.getCurrentUrl();
		
		//opening 2nd window for Guest2
		((JavascriptExecutor)driver).executeScript("window.open(arguments[0])", roomMeetingUrl);
		
		//GetWindows
		Set<String> windows=driver.getWindowHandles();
		Iterator i= windows.iterator();
		String firstWin = (String) i.next();
		String secondWin = (String) i.next();
		
		//switch to second window
		driver.switchTo().window(secondWin);
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Please Enter Your Name']")));
		driver.findElement(By.xpath("//input[@placeholder='Please Enter Your E-mail']")).sendKeys("karishmaoberoi97@gmail.com");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Enter Meeting']")));
		driver.findElement(By.xpath("//button[text()='Enter Meeting']")).click();
		
		//switch to first window to admit Guest2 in meeting
		
		driver.switchTo().window(firstWin);
		
		driver.findElement(By.xpath("//img[contains(@src, 'LockInactiveNotif')]")).click();
		driver.findElement(By.xpath("//button[text()='Admit All']")).click();
		
		//End meeting
		Thread.sleep(10000);
		driver.findElement(By.cssSelector("button[class='btn-sm exit-btn']")).click();
		
		driver.quit();
		
		
	}

}
