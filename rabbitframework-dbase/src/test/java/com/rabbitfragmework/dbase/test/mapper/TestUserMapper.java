package com.rabbitfragmework.dbase.test.mapper;

import java.util.List;
import java.util.Map;

import com.rabbitfragmework.dbase.test.model.TestUser;
import com.rabbitframework.dbase.annontations.CacheNamespace;
import com.rabbitframework.dbase.annontations.Create;
import com.rabbitframework.dbase.annontations.Delete;
import com.rabbitframework.dbase.annontations.Insert;
import com.rabbitframework.dbase.annontations.MapKey;
import com.rabbitframework.dbase.annontations.Mapper;
import com.rabbitframework.dbase.annontations.Param;
import com.rabbitframework.dbase.annontations.Select;
import com.rabbitframework.dbase.annontations.Update;
import com.rabbitframework.dbase.mapping.RowBounds;
import com.rabbitframework.dbase.mapping.param.WhereParamType;

@Mapper
public interface TestUserMapper {
    public static final String table = "test_user";

    @Create("create table test_user (id int primary key auto_increment, test_name varchar(200))")
    public int createTestUser();

    // @Insert("insert into test_user(test_name) values(#{testName})")
    @Insert
    public int insertTest(TestUser testUser);

    @Update("update test_user set test_name=#{testName} where id=#{id}")
    public int updateTest(@Param("id") long id, @Param("testName") String testName);

    @Update
    public int updateTestByUser(TestUser testUser);

    @Delete("delete from test_user where id=#{id}")
    public int delTestUser(long id);

    @Select("select * from @{table}")
    @CacheNamespace(pool = "defaultCache", key = {"seltestuser"})
    public List<TestUser> selectTestUser();

    @Select("select * from test_user")
    @MapKey("id")
    public Map<Long, TestUser> selectTestUserToMap();

    @Select("select * from @{table}")
    public List<TestUser> selectTestUserByPage(RowBounds rowBounds);

    @Select("select * from test_user")
    public List<TestUser> selectTesstUserByParamType(WhereParamType paramType);
}
