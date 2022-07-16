package com.sxpcwlkj;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxpcwlkj.entity.TestEntity;
import com.sxpcwlkj.mapper.TestEntityMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class AppletApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private TestEntityMapper testEntityMapper;

	@Autowired
	private RedisTemplate redisTemplate;


	//查询全部用户,传统的XML 方式查询
	@Test
	void MysqlAllUsers() {

		List<TestEntity>  userList=testEntityMapper.selectUser();

		userList.forEach(System.out::println);
	}

	//查询全部用户
	@Test
	void QueryAllUsers() {

		//参数是一个wrapper条件构造器,这里先不用,写null
		List<TestEntity> userList = testEntityMapper.selectList(null);
		userList.forEach(System.out::println);
	}

	//分页
	@Test
	public void queryUserForPage(){
		IPage<TestEntity> userPage = new Page<>(1, 2);//参数一是当前页，参数二是每页个数
		userPage = testEntityMapper.selectPage(userPage, null);
		List<TestEntity> userList = userPage.getRecords();
		userList.forEach(System.out::println);
	}

	@Test
	public void selectPage(){
		IPage<TestEntity> userPage = new Page<>(1, 2);
		userPage=testEntityMapper.selectPageVo(userPage,"uiopc");
		List<TestEntity> userList = userPage.getRecords();
		userList.forEach(System.out::println);
	}

	//插入用户
	@Test
	public void InsertUser() {
		TestEntity user = new TestEntity();
		user.setName("uiopc");
		user.setAge(38);
		user.setEmail("1241@qq.com");
		testEntityMapper.insert(user);
		//自动返回自增id
		System.out.println(user.toString());
	}

	//更新用户
	@Test
	public void updateUser() {
		TestEntity user = new TestEntity();
		user.setId(5L);
		user.setName("gcc");
		user.setAge(12);
		testEntityMapper.updateById(user);//传入的参数是一个对象
	}

	//删除用户
	@Test
	public void removeUser() {
		testEntityMapper.deleteById(1321732391973847050L);
	}


	/*=========================Redis====================================*/

	/**
	 * 获取用户策略：先从缓存中获取用户，没有则取数据表中 数据，再将数据写入缓存
	 * 在controller中使用
	 */
	public TestEntity findUserById(int id) {
		String key = "user_" + id;
		ValueOperations<String, TestEntity> operations = redisTemplate.opsForValue();
		//判断redis中是否有键为key的缓存
		boolean hasKey = redisTemplate.hasKey(key);
		if (hasKey) {
			TestEntity user = operations.get(key);
			System.out.println("从缓存中获得数据："+user.getName());
			System.out.println("------------------------------------");
			return user;
		} else {
			TestEntity user = testEntityMapper.selectById(id);
			System.out.println("查询数据库获得数据："+user.getName());
			System.out.println("------------------------------------");
			// 写入缓存
			operations.set(key, user, 5, TimeUnit.HOURS);
			return user;
		}
	}

	/**
	 * 更新用户策略：先更新数据表，成功之后，删除原来的缓存，再更新缓存
	 * 在controller中使用
	 */

	public int updateUser(TestEntity user) {
		ValueOperations<String, TestEntity> operations = redisTemplate.opsForValue();
		int result = testEntityMapper.update(user,null);
		if (result != 0) {
			String key = "user_" + user.getId();
			boolean haskey = redisTemplate.hasKey(key);
			if (haskey) {
				redisTemplate.delete(key);
				System.out.println("删除缓存中的key-----------> " + key);
			}
			// 再将更新后的数据加入缓存
			TestEntity userNew = testEntityMapper.selectById(user.getId());
			if (userNew != null) {
				operations.set(key, userNew, 3, TimeUnit.HOURS);
			}
		}
		return result;
	}

	/**
	 * 删除用户策略：删除数据表中数据，然后删除缓存
	 * 在controller中使用
	 */

	public int deleteUserById(int id) {
		int result = testEntityMapper.deleteById(id);
		String key = "user_" + id;
		if (result != 0) {
			boolean hasKey = redisTemplate.hasKey(key);
			if (hasKey) {
				redisTemplate.delete(key);
				System.out.println("删除了缓存中的key:" + key);
			}
		}
		return result;
	}

}
