package com.revature.backend.endtoend.gluecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import com.revature.backend.endtoend.page.HomePage;
import com.revature.backend.endtoend.page.LoginPage;
import com.revature.backend.endtoend.page.ViewSwotPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateSWOTWithNameTest {
	
	public HomePage homePage;
	public LoginPage loginPage;
	public ViewSwotPage viewSwotPage;
	
	@Given("manager is at the login page")
	public void manager_is_at_the_login_page() throws InterruptedException {
//		TimeUnit.SECONDS.sleep(1);
	    this.loginPage = new LoginPage(DriverUtility.driver);
	    assertEquals(DriverUtility.driver.getCurrentUrl(), "http://localhost:4200/login");
	    
	}

	@When("manager inputs their email {string}")
	public void manager_inputs_their_email(String string) {
	    this.loginPage.setEmail(string);
	}

	@When("manager inputs their password {string}")
	public void manager_inputs_their_password(String string) {
	    this.loginPage.setPassword(string);
	}

	@When("manager clicks log in")
	public void manager_clicks_log_in() {
	    this.loginPage.clickLogin();
	}

	@Then("manager is redirected to home page")
	public void manager_is_redirected_to_home_page() throws Exception {
		TimeUnit.SECONDS.sleep(1);
	    this.homePage = new HomePage(DriverUtility.driver);
	    TimeUnit.SECONDS.sleep(1);
		assertEquals(this.homePage.url, "http://localhost:4200/home");
	}
	
	@Given("manager is at home page")
	public void manager_is_at_home_page() {
		
	}

	@When("manager clicks Create Swot")
	public void manager_clicks_create_swot() throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
	    homePage.clickCreateSwotForAssociate(0);
	}

	@When("manager inputs content {string}")
	public void manager_inputs_content(String string) throws Exception {
		TimeUnit.SECONDS.sleep(1);
	    homePage.enterContentField(string);
	}

	@When("manager selects type strength")
	public void manager_selects_type_strength() {
		homePage.selectSwotType("STRENGTH");
	}

	@When("manager clicks Add Item")
	public void manager_clicks_add_item() {
	    homePage.addSwotItem();
	}

	@When("manager inputs description {string}")
	public void manager_inputs_description(String string) {
	    homePage.enterDescriptionField(string);
	}

	@When("manager clicks Submit Swot")
	public void manager_clicks_submit_swot() {
	    homePage.submitSwotItems();
	}

	@When("manager clicks ok in window alert")
	public void manager_clicks_ok_in_window_alert() throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
	    DriverUtility.driver.switchTo().alert().accept();
	}

	@When("manager clicks View Swots")
	public void manager_clicks_view_swots() {
	    homePage.clickViewSwotForAssociate(0);
	}
	
	@Then("manager is redirect to view swots page")
	public void manager_is_redirected_to_view_swots_page() throws Exception {
		TimeUnit.SECONDS.sleep(1);
		this.viewSwotPage = new ViewSwotPage(DriverUtility.driver);
		assertEquals(viewSwotPage.getTitleText(), "SWOT Analysis Info:");
	}
	
	@Then("manager can view created SWOT")
	public void manager_can_view_created_swot() {
	    
	}
	
}
