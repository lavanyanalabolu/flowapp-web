package testingxperts.web.tests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import listeners.CustomListeners;
import listeners.ExecutionStartEndListner;
import testingxperts.web.pages.WebAppPages;
import testingxperts.web.pages.Login;
import utilities.ConfigReader;
//import utilities.ConfigReader;
import utilities.ExcelTestDataReader;
import utilities.GlobalUtil;
import utilities.HtmlReportUtil;
//import utilities.HtmlReportUtil;
import utilities.KeywordUtil;

@Listeners({CustomListeners.class,ExecutionStartEndListner.class})
public class FLOW_TC_01 extends KeywordUtil
{
	String stepInfo="";
	int retryCount=getIntValue("retryCount");
	static int retryingNumber=1;
	public boolean flag;

	@Test
	(
			testName="FLOW_TC_01",
			description="Login to the application and create projects and tasks",
			dataProvider="getExcelTestData"
			)
	public void test(HashMap<String, String> rowMap) throws Throwable {
		try{
			
			//System.out.println(rowMap);
			setTestCaseID(getClass().getSimpleName());
 		//======================BASIC SETTING FOR TEST==========================================================
			if(retryingNumber==1)
				initTest();

			//.........Script Start...........................

 		    stepInfo="Open Login Page";
			logStep(stepInfo);
			Login.openLoginPage();
			verifyStep(Login.isLoginPageOpened(), stepInfo);

			stepInfo="Login into Application";
			logStep(stepInfo);
			boolean bLoginSuccessful = Login.doLogin(ConfigReader.getValue("loginUser"),ConfigReader.getValue("loginPassword"));			
			if(bLoginSuccessful)logStepPass(stepInfo);
			else logStepFail(stepInfo);
			
			//create a project
			stepInfo="Add a new project";
			logStep(stepInfo);
			if(rowMap.get("ProjectName")!=null)
			flag=WebAppPages.AddProject(rowMap.get("ProjectName"));
			if(flag)logStepPass(stepInfo);
			else logStepFail(stepInfo);

			/*//add project details
			stepInfo="add project description";
			logStep(stepInfo);
			WebAppPages.ProjectDetails("abdcc");
			logStepPass(stepInfo);*/
			
			//create a task
            stepInfo="create a task";
			logStep(stepInfo);
			flag=WebAppPages.createATask();
			if(flag)logStepPass(stepInfo);
			else logStepFail(stepInfo);
			
			//give task name
            stepInfo="give a taskname";
			logStep(stepInfo);
			if(rowMap.get("TaskName")!=null)
			flag=WebAppPages.GiveTaskName(rowMap.get("TaskName"));
			if(flag)logStepPass(stepInfo);
			else logStepFail(stepInfo);

			//assign task to someone
			stepInfo="assign task to someone";
			logStep(stepInfo);
			if(rowMap.get("assignee")!=null)
			WebAppPages.AssignTaskToSomeone(rowMap.get("assignee"));
			logStepPass(stepInfo);

			//duedate
			stepInfo="assign due date";
			logStep(stepInfo);
			//if(rowMap.get("DueDate")!=null)
			WebAppPages.selectDueDate("30-07-17");
			logStepPass(stepInfo);
			
			//select the subscriber
			stepInfo="assign subscriber for task2";
			logStep(stepInfo);
			if(rowMap.get("subscriber")!=null)
			WebAppPages.selectSubscriber(rowMap.get("subscriber"));
			logStepPass(stepInfo);
			
			//save the task
			stepInfo="save the task";
			logStep(stepInfo);
			WebAppPages.saveTheTask();
			logStepPass(stepInfo);
			
			//Logout of the appliction
			stepInfo="logout of the application";
			logStep(stepInfo);
			flag=WebAppPages.LogoutOfTheApplication();
			if(flag)logStepPass(stepInfo);
			else logStepFail(stepInfo);
			
			stepInfo="login with assignee";
			logStep(stepInfo);
			if(rowMap.get("assigneeUname")!=null)
			flag=Login.doLogin(rowMap.get("assigneeUname"),rowMap.get("assigneePwd"));
			Thread.sleep(10000);
			if(flag)logStepPass(stepInfo);
			else logStepFail(stepInfo);
			
			stepInfo="Notification for assignee received";
			logStep(stepInfo);
			flag=WebAppPages.CatchUpCount();
			if(flag)logStepPass(stepInfo);
			else logStepFail(stepInfo);
			
			stepInfo="logout of the assignee account";
			logStep(stepInfo);
			flag=WebAppPages.LogoutOfTheApplication();
			if(flag)logStepPass(stepInfo);
			else logStepFail(stepInfo);
			
			stepInfo="login with subscriber";
			logStep(stepInfo);
			flag=Login.doLogin(rowMap.get("subscriberUname"),rowMap.get("subscriberPwd"));
			if(flag)logStepPass(stepInfo);
			else logStepFail(stepInfo);
			
			stepInfo="Notification for subscriber received";
			logStep(stepInfo);
			flag=WebAppPages.CatchUpCount();
			if(flag)logStepPass(stepInfo);
			else logStepFail(stepInfo);
			
			stepInfo="logout of the subscriber account";
			logStep(stepInfo);
			flag=WebAppPages.LogoutOfTheApplication();
			if(flag)logStepPass(stepInfo);
			else logStepFail(stepInfo);
			

			
			
			
			
 }
		catch (Exception e)
		{
			if(retryCount>0)
			   {
				   String imagePath = takeScreenshot(getDriver(), getTestCaseID()+"_"+ retryingNumber);

				   logStepFail(stepInfo+" - "+KeywordUtil.lastAction);
				   logStepError(e.getMessage());
				   HtmlReportUtil.attachScreenshot(imagePath,false);
			    
				   GlobalUtil.getTestResult().setScreenshotref(imagePath);
			    
				   HtmlReportUtil.stepInfo("Trying to Rerun" + " "+getTestCaseID() +" for " + retryingNumber + " time");
				   retryCount--;
				   retryingNumber++;
				   utilities.LogUtil.infoLog(getClass(), "****************Waiting for " + getIntValue("retryDelayTime") +" Secs before retrying.***********");
				   delay(getIntValue("retryDelayTime"));
			   
			   }
			   else{
				   String imagePath = takeScreenshot(getDriver(), getTestCaseID());
				   logStepFail(stepInfo+" - "+KeywordUtil.lastAction);
				   logStepError(e.getMessage());
				   HtmlReportUtil.attachScreenshot(imagePath,false);
			    
				   GlobalUtil.getTestResult().setScreenshotref(imagePath);
				   GlobalUtil.setTestException(e);
				   throw e;
			   }

		}
	}


	@DataProvider
	public Iterator<Object[]> getExcelTestData() 
	{
		ExcelTestDataReader excelReader = new ExcelTestDataReader();
		final LinkedList<Object[]> dataBeans = excelReader.getRowDataMap( System.getProperty("user.dir")+"\\src\\test\\resources\\ExcelFiles\\FlowTestData.xlsx","FLOW_TC_01");
		return dataBeans.iterator();
	}

}
