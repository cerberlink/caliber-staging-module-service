package com.revature.backend.endtoend.gluecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import com.revature.backend.endtoend.page.HomePage;
import com.revature.backend.endtoend.page.LoginPage;
import com.revature.backend.endtoend.page.ViewPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ToastTest {
	public HomePage homePage;
	public LoginPage loginPage;
	public ViewPage viewPage;
	
	
	// Scenario 1
	@Given("User is at the login page")
	public void user_is_at_the_login_page() {
		this.loginPage = new LoginPage(DriverUtility.driver);
		assertEquals(DriverUtility.driver.getCurrentUrl(), "http://localhost:4200/login");
	}

	@When("user inputs their email <{string}>")
	public void user_inputs_their_email(String string) {
		loginPage.setEmail(string);
	}

	@When("user inputs their password <{string}>")
	public void user_inputs_their_password(String string) {
		loginPage.setPassword(string);
	}

	@When("user clicks log in")
	public void user_clicks_log_in() {	
		loginPage.clickLogin();
	}

	@When("user clicks view swots")
	public void user_clicks_view_swots() throws InterruptedException {
		this.homePage = new HomePage(DriverUtility.driver);
		TimeUnit.SECONDS.sleep(5);
		assertEquals(DriverUtility.driver.getCurrentUrl(), "http://localhost:4200/home");
	}

	@When("user clicks add new item")
	public void user_clicks_add_new_item() throws InterruptedException {
		this.viewPage = new ViewPage(DriverUtility.driver);
		TimeUnit.SECONDS.sleep(5);
		assertEquals(DriverUtility.driver.getCurrentUrl(), "http://localhost:4200/view/*");
		this.viewPage.clickAddNewItem();
		
	}

	@When("user selects type <{string}>")
	public void user_selects_type(String string) throws InterruptedException {
		TimeUnit.SECONDS.sleep(3);
		this.viewPage.addItemSelectType(string);
	}

	@When("user submits item name <{string}>")
	public void user_submits_item_name(String string) throws InterruptedException {
		TimeUnit.SECONDS.sleep(3);
		this.viewPage.addItemTypeText(string);
	}

	@When("user clicks add item")
	public void user_clicks_add_item() throws InterruptedException {
		TimeUnit.SECONDS.sleep(3);
		this.viewPage.submitAdd();
	}

	@Then("user can click add new item again without clearing the message")
	public void user_can_click_add_new_item_again_without_clearing_the_message() {
		this.viewPage.clickAddNewItem();
	}

	// Scenario 2
	@When("user clicks update for recently created item")
	public void user_clicks_update_for_recently_created_item() {
		this.viewPage = new ViewPage(DriverUtility.driver);
		this.viewPage.clickUpdateItem();
	}

	@When("user changes item type <{string}>")
	public void user_changes_type(String string) throws InterruptedException {
		TimeUnit.SECONDS.sleep(3);
		this.viewPage.updateItemSelectType(string);
	}

	// This one might have an issue finding specific elements in the table as is
	@When("user clicks delete for the same item")
	public void user_clicks_delete_for_the_same_item() {
		// Write code here that turns the phrase above into concrete actions
//		this.viewPage.clickDeleteItem();
	}

	@Then("the three messages should occupy separate locations on the screen")
	public void the_three_messages_should_occupy_separate_locations_on_the_screen() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	// Scenario 3
	@When("user clicks create swot")
	public void user_clicks_create_swot() throws InterruptedException {
		this.homePage = new HomePage(DriverUtility.driver);
		this.homePage.clickCreateSwotForAssociate(1);
		TimeUnit.SECONDS.sleep(3);
	}

	@When("user selects type two <{string}>")
	public void user_selects_type_two(String string) throws InterruptedException {
		TimeUnit.SECONDS.sleep(3);
		this.homePage.selectSwotType(string);
	}

	@When("user selects type three <{string}>")
	public void user_selects_type_three(String string) throws InterruptedException {
		TimeUnit.SECONDS.sleep(3);
		this.homePage.selectSwotType(string);
	}

	@Then("the user should have four toast messages")
	public void the_user_should_have_four_toast_messages() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

}
