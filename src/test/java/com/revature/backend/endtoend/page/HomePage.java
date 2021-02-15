package com.revature.backend.endtoend.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


public class HomePage {
	public final String url = "http://localhost:4200/home";
	
	@FindBy(xpath = "//*[@class='btn btn-primary logout-btn']")
	private WebElement logoutButton;
	
	@FindBy(xpath = "//button[text()='Create SWOT']")
	private List<WebElement> createSwotButtons;
	
	@FindBy(id = "type")
	private WebElement SwotTypeDropdown;
	
	@FindBy(id = "name")
	private WebElement contentField;
	
	@FindBy(xpath = "//button[text()='ADD ITEM']")
	private WebElement addItem;
	
	@FindBy(id = "description")
	private WebElement descriptionField;
	
	@FindBy(xpath = "//button[text()='SUBMIT']")
	private WebElement submitSwots;
	
	@FindBy(xpath = "//button[text()='View SWOTs']")
	private List<WebElement> viewSwots;
	
	@FindBy(xpath = "//*[@id=\"data-row\"]/td[8]/button")
	private WebElement updateBatchButton;
	
	@FindBy(xpath = "//*[@id=\"newBatch\"]")
	private WebElement updateBatchInput;
	
	@FindBy(xpath = "//*[@id=\"data-row\"]/td[6]")
	private WebElement assocBatchId;
	
	@FindBy(xpath = "//*[@id=\"data-row\"]/td[7]")
	private WebElement assocStatus;
	
	@FindBy(xpath = "/html/body/ngb-modal-window/div/div/app-update-associate/div/div/div/form/div[4]/div/button")
	private WebElement submitUpdateChangesButton;
	
	@FindBy(xpath = "//*[@id=\"status\"]")
	private WebElement selectStatus;

	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void clickCreateSwotForAssociate(int rowId) {
		WebElement createButton = this.createSwotButtons.get(rowId);
		createButton.click();
	}
	
	public void clickViewSwotForAssociate(int rowId) {

		WebElement viewButton = this.viewSwots.get(rowId);
		viewButton.click();

	}
	
	public void selectSwotType(String type) {
		Select swotOption = new Select(SwotTypeDropdown);
		swotOption.selectByValue(type);
	}
	
	public void enterContentField(String content) {
		this.contentField.clear();
		this.contentField.sendKeys(content);
	}
	
	public void enterDescriptionField(String description) {
		this.descriptionField.clear();
		this.descriptionField.sendKeys(description);
  }
  
	public void enterNewBatchNum(String id) {
		this.updateBatchInput.clear();
		this.updateBatchInput.sendKeys(id);
	}
	
	public void addSwotItem() {
		this.addItem.click();
	}
	
	public void submitSwotItems() {
		this.submitSwots.click();
	}
	
	public void clickViewSwots(int index) {
		this.viewSwots.get(index).click();
	}

	public WebElement getLogoutButton() {
		return logoutButton;
	}

	public List<WebElement> getCreateSwotButtons() {
		return createSwotButtons;
	}

	public WebElement getSwotTypeDropdown() {
		return SwotTypeDropdown;
	}

	public WebElement getContentField() {
		return contentField;
	}

	public WebElement getAddItem() {
		return addItem;
	}

	public WebElement getSubmitSwots() {
		return submitSwots;
	}

	public List<WebElement> getViewSwots() {
		return viewSwots;
	}
	
	public void clickChangeBatchBttn() {
		this.updateBatchButton.click();
	}
	
	public Select getSelectOptions() {
        return new Select(selectStatus);
    }
	
	public void clickSubmitUpdateChangesButton() {
		this.submitUpdateChangesButton.click();
	}
	
	public WebElement getUpdateBatchInput() {
		return updateBatchInput;
	}
	
	public WebElement getAssocBatchId() {
		return assocBatchId;
	}
	
	public WebElement getAssocStatus() {
		return assocStatus;
	}
	
	public WebElement getselectStatus() {
		return selectStatus;
	}
	
	
	
}
