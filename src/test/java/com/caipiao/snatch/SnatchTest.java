package com.caipiao.snatch;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.druid.pool.DruidDataSource;
import com.caipiao.snatch.match.football.SportFootballDataSnatch;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class SnatchTest {
	
	private SportFootballDataSnatch sportFootballDataSnatch;
	
	@Before
	public void befor() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{"spring.xml","spring-mybatis.xml"});
		sportFootballDataSnatch = (SportFootballDataSnatch)ac.getBean("sportFootballDataSnatch");
		DruidDataSource dataSource = (DruidDataSource)ac.getBean("dataSource");
		System.out.println(dataSource.getUsername() +"   "+dataSource.getPassword());
		log.info("初始化配置");
		ac.toString();
	}
	
	//@Test
	public void exe() {
	}
	
	public static void main(String[] args) {
		String path = SnatchTest.class.getClassLoader().getResource("/").getPath();
		System.out.println(path);
	
	}
	
	@Test
	public void find() {
		sportFootballDataSnatch.snatch();
	}
	
	@After
	public void after() {
		log.info("测试完成");
	}
}
