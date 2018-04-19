package com.caipiao.snatch.match.football;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class FootballSnatchFactory {
	@Autowired
	private static ApplicationContext applicationContext;
	
	public static FootballSnatchService getFootballSnatchService(String type) {
		if(type == "test") {
			FootballSnatchService footballSnatchService= (FootballSnatchService)applicationContext.getBean("footballSnatchServiceSporttery");
			return footballSnatchService;
		}
		return null;
	}
}
