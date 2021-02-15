package com.revature.backend.endtoend.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ViewPage {

	private WebDriver driver;

	// Add new item button
	@FindBy(xpath = "//button[contains(.,'Add new item')]")
	private WebElement addNewButton;

	// inside "add new item" button
	@FindBy(xpath = "//*[@id='type']")
	private WebElement addNewButtonItemType;

	@FindBy(xpath = "//*[@id='content']")
	private WebElement addNewButtonItemText;
	
	@FindBy(xpath = "//*[@type='submit']")
	private WebElement addItemButtonInsideAddNewModal;

	// Update button
	@FindBy(xpath = "//button[contains(.,'Update')]")
	private WebElement updateButton;

	// Path same as addItem, if conflicts emerge look for more ways to differentiate
	@FindBy(xpath = "//*[@id='type']")
	private WebElement updateButtonItemType;
	//
	@FindBy(xpath = "//*[@id='content']")
	private WebElement updateButtonItemText;
	
	@FindBy(xpath = "//*[@type='submit'")
	private WebElement updateItemButtonInsideUpdateModal;

	// Delete button
	@FindBy(xpath = "//button[contains(.,'Delete')]")
	private List<WebElement> deleteButton;

	// Back to home page link
	@FindBy(xpath = "//*[@id='homeLink']")
	private WebElement homePageButton;

	public ViewPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void clickAddNewItem() {
		this.addNewButton.click();
	}
	
	public void addItemSelectType(String string) {
		Select drpType = new Select(driver.findElement(By.name("type")));
		drpType.selectByValue(string);
	}
	
	public void addItemTypeText(String text) {
		this.addNewButtonItemText.clear();
		this.addNewButtonItemText.sendKeys(text);
	}
	
	public void submitAdd() {
		this.addItemButtonInsideAddNewModal.click();
	}

	public void clickUpdateItem() {
		this.updateButton.click();
	}
	
	public void updateItemSelectType(String type) {
		Select drpType = new Select(driver.findElement(By.name("type")));
		drpType.selectByValue(type);
	}
	
	public void updateItemTypeText(String text) {
		this.addNewButtonItemText.clear();
		this.addNewButtonItemText.sendKeys(text);
	}
	
	public void submitUpdate() {
		this.updateItemButtonInsideUpdateModal.click();
	}

	public void clickDeleteItem(int id) {
		this.deleteButton.get(id).click();
	}

	public void clickReturnToHomepage() {
		this.homePageButton.click();
	}

	public WebElement getAddNewButton() {
		return addNewButton;
	}

	public WebElement getAddNewButtonItemType() {
		return addNewButtonItemType;
	}

	public WebElement getAddNewButtonItemText() {
		return addNewButtonItemText;
	}

	public WebElement getAddItemButtonInsideAddNewModal() {
		return addItemButtonInsideAddNewModal;
	}

	public WebElement getUpdateButton() {
		return updateButton;
	}

	public WebElement getUpdateButtonItemType() {
		return updateButtonItemType;
	}

	public WebElement getUpdateButtonItemText() {
		return updateButtonItemText;
	}

	public WebElement getUpdateItemButtonInsideUpdateModal() {
		return updateItemButtonInsideUpdateModal;
	}

	public List<WebElement> getDeleteButton() {
		return deleteButton;
	}

	public WebElement getHomePageButton() {
		return homePageButton;
	}

	
}
