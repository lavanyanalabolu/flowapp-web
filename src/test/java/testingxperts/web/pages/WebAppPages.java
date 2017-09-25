package testingxperts.web.pages;




import java.awt.AWTException;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

import utilities.ConfigReader;
import utilities.GlobalUtil;
import utilities.KeywordUtil;
import utilities.LogUtil;

public class WebAppPages extends KeywordUtil 
{

	
	public static By createProject=By.xpath("//*[normalize-space(text())='Projects']//following-sibling::a");
	public static By NewProject=By.xpath("//*[normalize-space(text())='New Project']");
	public static By txt_Projectname=By.xpath("//input[contains(@placeholder,'Enter project')]");
    public static By btn_Next=By.xpath("//button[@role='button']");
    public static By btn_Createprojec=By.xpath("//*[normalize-space(text())='Create project']");
    public static By txt_projectDescriptn=By.xpath("//*[@data-content-placeholder='Add a project description']");
    public static By btn_savechanges=By.xpath("//a[text()='Save Changes']");
    public static By btn_createaTask=By.xpath("//a[text()='Create your first task']");
    public static By txt_taskName=By.xpath("//*[@placeholder='New Task']");
    public static By assignToSomeone=By.xpath("//*[@data-helpout-title='Assign to someone']");
    public static By txt_assignee=By.xpath("//span[text()='Assign to someone']/preceding-sibling::input");
    public static By select_assigne=By.xpath("//*[@role='menuitem']");
    public static By DueDate=By.xpath("//*[@data-helpout-title='Set due date']");
    public static By txt_duedate=By.xpath("//span[text()='Set due date']/preceding-sibling::input");
    public static By subscriber= By.xpath("//*[@data-helpout-title='Subscribe members']");
    public static By assign_subscriber=By.xpath("//span[text()='Subscribe people']/preceding-sibling::input");
    public static By btn_save=By.xpath("//button[text()='Save']");
    public static By tx_logout=By.xpath(".//h1[@class='app-header-organization-name']");
    public static By logout=By.xpath("//a[text()='Logout']");
    public static By catchUpnum=By.xpath("//span[text()='Catch Up']");
    public static By assign_subscriber1=By.xpath("//span[text()='Subscribe people']");
    public static By catchup_screeen=By.xpath("//a[text()='Catch Up']");
    public static By ctachup_count=By.xpath("///a[text()='Catch Up']//following-sibling::div");
    public static By env=By.xpath("//h1[text()='TX']");
    public static By switch_env=By.xpath("//a[@href='/organizations/16']");
    public static By quick_task= By.xpath("//div[@class='quick-task-form-icon']");
    public static By Today_task=By.xpath("//div[@data-content='Today']");
    
	public static String getPageTitle(WebDriver driver) 
	{
		return driver.getTitle();
	}
	
	public static void openHomePage() throws Exception {
		navigateToUrl(ConfigReader.getValue("BASE_URL"));

	}

	public static boolean isHomePageOpened() throws Exception {
		return getDriver().
				getCurrentUrl().
				equalsIgnoreCase(ConfigReader.getValue("BASE_URL"));
	}
	public static boolean switchEnvironment()
	{ boolean flag=true;
		if(isWebElementPresent(env))
		{flag=false;
			click(env);
			click(switch_env);
			if(isWebElementPresent(createProject))
			{
				flag=true;
			}
		}
		return  flag;
		}
	public static boolean AddProject(String projectname) {
		click(createProject);
		if(isWebElementVisible(NewProject))
			click(NewProject);
		if(isWebElementVisible(txt_Projectname))
			KeywordUtil.inputText(txt_Projectname,projectname);
		    click(btn_Next);
		    if(isWebElementVisible(btn_Next))
		    	click(btn_Next);
		        click(btn_Createprojec);
		return isWebElementVisible(btn_createaTask);
		
	}
	
	/*public static void ProjectDetails(String description)
	{
	  KeywordUtil.inputText(txt_projectDescriptn,description);
	  click(btn_savechanges);
	  }
*/
   public static boolean createATask()
   {
	   if(isWebElementVisible(btn_createaTask))
			   click(btn_createaTask);
	   
	   return isWebElementVisible(txt_taskName);
	   
	  }
   
   public static boolean GiveTaskName(String taskname)
   {
	   if(isWebElementPresent(quick_task))
		   click(quick_task);
	   KeywordUtil.inputText(txt_taskName,taskname);
	   
	   return isWebElementVisible(assignToSomeone);
   }
   public static void AssignTaskToSomeone(String assigneeName) throws InterruptedException
   {
	   if(isWebElementPresent(assignToSomeone));
	   click(assignToSomeone);
	   KeywordUtil.inputText(txt_assignee,assigneeName);
	   KeywordUtil.pressEnterKey(txt_assignee);
	   Thread.sleep(1000);
   }
	
   public static void selectDueDate(String Date)
   {
	 
	   if(isWebElementVisible(DueDate))
	   click(DueDate);
	   KeywordUtil.inputText(txt_duedate,Date);
	   KeywordUtil.pressEnterKey(txt_duedate);
	   
	   }
   public static void selectSubscriber(String SubscriberName) throws InterruptedException
   {
	   if(isWebElementPresent(subscriber))
		   click(subscriber);
	   KeywordUtil.inputText(assign_subscriber, SubscriberName);
	   KeywordUtil.pressEnterKey(assign_subscriber);
	   click(assign_subscriber1);
	   Thread.sleep(1000);
	   
	   
   }
   public static void saveTheTask()
   {
	   if(isWebElementPresent(btn_save));
	   click(btn_save);
   }
   public static boolean LogoutOfTheApplication() throws InterruptedException
   {boolean flag=true;
	   if(isWebElementPresent(tx_logout));
	   click(tx_logout);
	   if(isWebElementPresent(logout))
		click(logout);
	   Thread.sleep(1000);
	   flag=true;
	   return flag;
	    }
    public static boolean CatchUpCount() throws InterruptedException
    { boolean flag= true;
    	int catchupcount=0;
    	if(isWebElementPresent(catchUpnum))
    		click(catchUpnum);
    	if(isWebElementPresent(catchup_screeen))
    		click(catchup_screeen);
    	Thread.sleep(1000);
    	if(isWebElementPresent(Today_task)){
    	flag= true;
    	}
    	return flag;
    	
    }
    
}

