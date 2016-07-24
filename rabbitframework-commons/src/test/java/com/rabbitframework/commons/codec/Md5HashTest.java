package com.rabbitframework.commons.codec;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5HashTest {
	private static final Logger logger = LoggerFactory.getLogger(Md5HashTest.class);

	@Test
	public void md5Hash() {
		String md5 = Md5Hash.md5("123456");
		logger.debug("md5:" + md5);
		String base64Str = Base64.encodeToString(CodecSupport.toBytes("123456"));
		logger.debug("base64Str:" + base64Str + ",decode:" + Base64.decodeToString(base64Str));

	}
}
