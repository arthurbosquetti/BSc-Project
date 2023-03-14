package shure.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class TestDataEntry extends DataEntry {
	
	@Column
	private String productName;
	@Column
	private int testsPassed;
	@Column
	private int testsFailed;
	@Column
	private int testsBlocked;
	@Column
	private int testsNoRun;
	@Column
	private int testsNotCompleted;
	@Column
	private int totalTests;
	@Column
	private int target95;
	@Column
	private int leftTarget;
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public int getTestsPassed() {
		return testsPassed;
	}
	
	public void setTestsPassed(int testsPassed) {
		this.testsPassed = testsPassed;
	}
	
	public int getTestsFailed() {
		return testsFailed;
	}
	
	public void setTestsFailed(int testsFailed) {
		this.testsFailed = testsFailed;
	}
	
	public int getTestsBlocked() {
		return testsBlocked;
	}
	
	public void setTestsBlocked(int testsBlocked) {
		this.testsBlocked = testsBlocked;
	}
	
	public int getTestsNoRun() {
		return testsNoRun;
	}
	
	public void setTestsNoRun(int testsNoRun) {
		this.testsNoRun = testsNoRun;
	}
	
	public int getTestsNotCompleted() {
		return testsNotCompleted;
	}
	
	public void setTestsNotCompleted(int testsNotCompleted) {
		this.testsNotCompleted = testsNotCompleted;
	}
	
	public int getTotalTests() {
		return totalTests;
	}
	
	public void setTotalTests(int totalTests) {
		this.totalTests = totalTests;
	}
	
	public int getTarget95() {
		return target95;
	}
	
	public void setTarget95(int target95) {
		this.target95 = target95;
	}
	
	public int getLeftTarget() {
		return leftTarget;
	}
	
	public void setLeftTarget(int leftTarget) {
		this.leftTarget = leftTarget;
	}
	
	
	
}











