package com.revature.backend.endtoend.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewSwotPage {
	
	private WebDriver driver;
	
	@FindBy(xpath = "//h5")
	private WebElement title;
	
	public String getTitleText() {
		return title.getText();
	}
	
	public ViewSwotPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
}
