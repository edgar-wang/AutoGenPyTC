package com.htc.autotest.python;

import java.util.ArrayList;

public class TestComponent {

	public String componentName;
	public ArrayList<TestCase> testcaseList;
	
	public TestComponent(String component){
		componentName = component;
		testcaseList = new ArrayList<TestCase>();
	}
}
