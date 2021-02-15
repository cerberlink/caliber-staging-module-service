package com.revature.backend.endtoend.gluecode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

 import com.revature.backend.endtoend.page.HomePage;
 import com.revature.backend.endtoend.page.LoginPage;
 import com.revature.backend.endtoend.page.ViewPage;

 import io.cucumber.java.en.Given;
 import io.cucumber.java.en.Then;
 import io.cucumber.java.en.When;

public class ToastLocationTest {
	public HomePage homePage;
	public LoginPage loginPage;
	public ViewPage viewPage;
	WebDriverWait wait = new WebDriverWait(DriverUtility.driver, 2);

	@Given("a user is logged into the welcome page of Revature Staging Module")
	public void a_user_is_logged_into_the_welcome_page_of_revature_staging_module() {
		DriverUtility.driver.manage().window().maximize();
		this.loginPage = new LoginPage(DriverUtility.driver);
		this.loginPage.setEmail("test@revature.com");
		this.loginPage.setPassword("password");
		this.loginPage.clickLogin();
		wait.until(ExpectedConditions.urlContains("home"));
		this.homePage = new HomePage(DriverUtility.driver);
		assertEquals(this.homePage.url, DriverUtility.driver.getCurrentUrl());
	}
	@When("a user clicks Create SWOT for associate in table row {string}")
	public void a_user_clicks_create_swot_for_associate_in_table_row(String string) {
		int associateId = Integer.parseInt(string);
		WebElement createSwot = this.homePage.getCreateSwotButtons().get(associateId - 1);
		wait.until(ExpectedConditions.elementToBeClickable(createSwot));
		this.homePage.clickCreateSwotForAssociate(associateId - 1);
	}
	@When("selects their SWOT type {string}")
	public void selects_their_swot_type_strength(String string) {
		this.homePage.selectSwotType(string);
	}
	@When("types into Enter Item field {string}")
	public void types_into_enter_item_field(String string) {
		this.homePage.enterContentField(string);
	}
	@When("clicks add item")
	public void clicks_add_item() {
		this.homePage.addSwotItem();
	}
	@When("clicks submit")
	public void clicks_submit() {
		this.homePage.submitSwotItems();
	}


	@Given("a user is logged in and viewing a SWOT page for associate id\"{int}\"")
	public void a_user_is_logged_in_and_viewing_a_swot_page_for_associate_id(Integer int1) {
		this.a_user_is_logged_into_the_welcome_page_of_revature_staging_module();
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//button[text()='View SWOTs']")));
		this.homePage.clickViewSwots(int1 - 1);
		this.viewPage = new ViewPage(DriverUtility.driver);
		assertTrue(DriverUtility.driver.getCurrentUrl().contains("view/"+int1));
	}
	@When("a user clicks a SWOT delete button for {string}")
	public void a_user_clicks_a_swot_delete_button_for(String string) {
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//button[contains(.,'Delete')]")));
		this.viewPage.clickDeleteItem(0);
	}

	@Given("a user is logged in and viewing a SWOT page for associate id {string}")
	public void a_user_is_logged_in_and_viewing_a_swot_page_for_associate_id(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	@When("a user clicks a SWOT update button for {string}")
	public void a_user_clicks_a_swot_update_button_for(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	@When("clicks enter item field with text {string}")
	public void clicks_enter_item_field_with_text(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	@When("clears field of all data")
	public void clears_field_of_all_data() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	@When("adds text {string}")
	public void adds_text(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	@When("clicks Update SWOT")
	public void clicks_update_swot() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}


	@Then("a toast notification should appear in the lower left")
	public void a_toast_notification_should_appear_in_the_lower_left() {
	}
}
