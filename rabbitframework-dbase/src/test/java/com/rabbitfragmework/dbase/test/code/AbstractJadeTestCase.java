package com.rabbitfragmework.dbase.test.code;

import java.io.IOException;

import org.junit.Before;

public abstract class AbstractJadeTestCase {
	protected abstract void initDbase() throws IOException;

	@Before
	public void before() {
		try {
			initDbase();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
