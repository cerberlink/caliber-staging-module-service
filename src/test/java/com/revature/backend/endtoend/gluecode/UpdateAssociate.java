package com.revature.backend.endtoend.gluecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import com.revature.backend.endtoend.page.HomePage;
import com.revature.backend.endtoend.page.LoginPage;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdateAssociate {
	
	public HomePage homePage;
	public LoginPage loginPage;
	
	@When("user inputs their email {string}")
	public void user_inputs_their_email(String string) {
		loginPage = new LoginPage(DriverUtility.driver);
	    loginPage.setEmail(string);
	    
	}
	@When("user inputs their password {string}")
	public void user_inputs_their_password(String string) {
		loginPage.setPassword(string);
	}
	@When("a user is on the home page")
	public void a_user_is_on_the_home_page() throws InterruptedException {
		homePage = new HomePage(DriverUtility.driver);
		TimeUnit.SECONDS.sleep(2);
		assertEquals("http://localhost:4200/home", DriverUtility.driver.getCurrentUrl());
	}
	@When("clicks update batch button")
	public void clicks_update_batch_button() throws InterruptedException {
		homePage.clickChangeBatchBttn();
		assertEquals(true, homePage.getUpdateBatchInput().isDisplayed());
	}
	@Then("inputs the new {string}")
	public void inputs_the_new(String string) throws InterruptedException {
	    homePage.enterNewBatchNum(string);
	}
	@Then("selects the project option for status")
	public void selects_the_project_option_for_status() throws InterruptedException {
	    homePage.getSelectOptions().selectByValue("0");
	}
	@Then("submits the changes")
	public void submits_the_changes() throws InterruptedException {
		homePage.clickSubmitUpdateChangesButton();
		TimeUnit.SECONDS.sleep(1);
	}
	@Then("the user should see the updated batch id")
	public void the_user_should_see_the_updated_batch_id() {
		assertEquals("2", homePage.getAssocBatchId().getText());
	}
	@Then("the user should see the updated associate status")
	public void the_user_should_see_the_updated_associate_status() throws InterruptedException {
		assertEquals("STAGING", homePage.getAssocStatus().getText());
	}

}
