package com.caipiao.snatch.match.football;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.caipiao.snatch.match.football.service.FootballSnatchService;

@Component
public class FootballSnatchFactory {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	public FootballSnatchService getFootballSnatchService(String snatchServiceName) {
		FootballSnatchService footballSnatchService= (FootballSnatchService)applicationContext.getBean(snatchServiceName);
		return footballSnatchService;
	}
	
	
}
