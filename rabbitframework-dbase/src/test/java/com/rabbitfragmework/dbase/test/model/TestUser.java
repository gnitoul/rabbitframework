package com.rabbitfragmework.dbase.test.model;

import com.rabbitframework.dbase.annontations.Column;
import com.rabbitframework.dbase.annontations.ID;
import com.rabbitframework.dbase.annontations.Table;

@Table
public class TestUser implements java.io.Serializable {
	private static final long serialVersionUID = 6601565142528523969L;
	@ID
	private long id;
	@Column
	private String testName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

}
