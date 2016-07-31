package com.rabbitfragmework.dbase.test.demo;

import java.util.List;
import java.util.Map;

import com.rabbitfragmework.dbase.test.mapper.TestUserMapper;
import com.rabbitfragmework.dbase.test.model.TestUser;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitfragmework.dbase.test.code.DataAccessTestCase;
import com.rabbitframework.dbase.mapping.RowBounds;
import com.rabbitframework.dbase.mapping.param.WhereParamType;

public class TestUserTestCase extends DataAccessTestCase {
	private static final Logger logger = LoggerFactory.getLogger(TestUserTestCase.class);

	// @Test
	public void createTestUser() {
		TestUserMapper testMapper = getMapper(TestUserMapper.class);
		int result = testMapper.createTestUser();
		System.out.println(result);
	}

	// @Test
	public void insertTestUser() {
		TestUserMapper testMapper = getMapper(TestUserMapper.class);
		TestUser testUser = new TestUser();
		testUser.setTestName("testAuto");
		int result = testMapper.insertTest(testUser);
		System.out.println(result);
		System.out.println(testUser.getId());
	}

	// @Test
	public void updateTestUserById() {
		TestUserMapper testMapper = getMapper(TestUserMapper.class);
		int result = testMapper.updateTest(1L, "updateName");
		System.out.println("result:" + result);
	}

	// @Test
	public void updateTestByUser() {
		TestUserMapper testMapper = getMapper(TestUserMapper.class);
		TestUser testUser = new TestUser();
		testUser.setId(5L);
		testUser.setTestName("testAutoupdate");
		testMapper.updateTestByUser(testUser);
	}

	// @Test
	public void selectTestUserAll() {
		TestUserMapper testMapper = getMapper(TestUserMapper.class);
		List<TestUser> testUsers = testMapper.selectTestUser();
		for (TestUser testUser : testUsers) {
			System.out.println("id:" + testUser.getId() + ",name:" + testUser.getTestName());
		}
	}

	@Test
	public void selectTestUserByPage() {
		TestUserMapper testMapper = getMapper(TestUserMapper.class);
		List<TestUser> testUsers = testMapper.selectTestUserByPage(new RowBounds());
		for (TestUser testUser : testUsers) {
			System.out.println("id:" + testUser.getId() + ",name:" + testUser.getTestName());
		}
	}

	// @Test
	public void deleteTestUser() {
		TestUserMapper testMapper = getMapper(TestUserMapper.class);
		int result = testMapper.delTestUser(1L);
		System.out.println("result:" + result);
	}

	// @GeneratorTest
	public void selectTestUserToMap() {
		TestUserMapper testMapper = getMapper(TestUserMapper.class);
		Map<Long, TestUser> testUser = testMapper.selectTestUserToMap();
		System.out.println(testUser.size());
	}

	// @GeneratorTest
	public void selectTesstUserByParamType() {
		TestUserMapper testMapper = getMapper(TestUserMapper.class);
		WhereParamType paramType = new WhereParamType();
		paramType.createCriteria().andFieldIsEqualTo("id", 2L);
		List<TestUser> testUsers = testMapper.selectTesstUserByParamType(paramType);
		System.out.println(testUsers.size());
	}
}
