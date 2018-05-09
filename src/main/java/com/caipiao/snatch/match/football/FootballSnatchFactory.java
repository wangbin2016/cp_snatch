package com.caipiao.snatch.match.football;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.caipiao.snatch.match.football.service.FootballSnatchService;

@Service
public class FootballSnatchFactory {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	public FootballSnatchService getFootballSnatchService(String snatchServiceName) {
		FootballSnatchService footballSnatchService= (FootballSnatchService)applicationContext.getBean(snatchServiceName);
		return footballSnatchService;
	}
	
	
}
