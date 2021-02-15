package com.revature.backend.endtoend.runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(

		//features = {"features/Toast.feature", "features/ToastLocation.feature"},
//		features = {"features/Toast.feature"},
//		features = {"features/ToastLocation.feature"},
//		features = {"features/ToastText.feature"},
		features = {"features/CreateSWOTWithName.feature"},
		glue = {"com.revature.backend.endtoend.gluecode"}
		)
public class TestRunners {

}
