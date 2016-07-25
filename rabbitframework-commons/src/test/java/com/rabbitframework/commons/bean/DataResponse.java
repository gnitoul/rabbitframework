package com.rabbitframework.commons.bean;

import java.util.ArrayList;
import java.util.List;

public class DataResponse implements IData {
	private static final long serialVersionUID = 4115707000173426028L;
	private List<IData> data = new ArrayList<IData>();
	public int status = 0;

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public List<IData> getData() {
		return data;
	}
}
