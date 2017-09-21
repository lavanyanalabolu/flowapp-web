package testingxperts.web.pages;

import org.openqa.selenium.By;

import utilities.ConfigReader;
import utilities.KeywordUtil;
import utilities.LogUtil;

public class Login extends WebAppPages{

	public static final By txtUserName = By.name("session[email]");
	public static final By txtPassword = By.name("session[password]");
	public static final By btnSignIn=By.xpath("//div[@class='form-submit']/a/span[1]");
	public static final By catchUp =By.xpath("//span[text()='Catch Up']");
	

	public static boolean doLogin(String userName, String password) throws Exception {
		//switchToFrame(iFrame);
		enterUserName(userName);
		enterPassword(password);
		clickLoginButton();
		waitForVisible(catchUp);
		switchEnvironment();
		return isWebElementVisible(catchUp);
	}

	private static void enterUserName(String uName) throws InterruptedException {
		waitForVisible(txtUserName);
		KeywordUtil.inputText(txtUserName, uName);
		LogUtil.infoLog("KeywordActions", KeywordUtil.lastAction);
		//pause(10000);
	}

	private static void enterPassword(String password) throws Exception{
		waitForVisible(txtPassword);
		KeywordUtil.clearInput(txtPassword);
		KeywordUtil.click(txtPassword);
		KeywordUtil.inputText(txtPassword, password);
		LogUtil.infoLog("KeywordActions", KeywordUtil.lastAction );
		//pause(10000);
	}

	private static void clickLoginButton() throws Exception{
		click(btnSignIn);
		LogUtil.infoLog("KeywordActions", KeywordUtil.lastAction );
		pause(3000);
	}

	public static void openLoginPage() throws Exception {
		navigateToUrl(ConfigReader.getValue("BASE_URL"));
	}

	public static boolean isLoginPageOpened() throws Exception {
		return getDriver().
				getCurrentUrl().
				contains(ConfigReader.getValue("BASE_URL"));
	}


}
