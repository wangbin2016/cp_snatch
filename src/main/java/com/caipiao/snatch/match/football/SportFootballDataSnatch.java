package com.caipiao.snatch.match.football;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caipiao.lottery.entity.sport.vo.SportFootballMatchAward;
import com.caipiao.lottery.service.sport.SportFootballDataService;
import com.caipiao.snatch.match.football.service.FootballSnatchService;

import lombok.extern.slf4j.Slf4j;

/**
 * 竞彩足球抓取类
 * @author WangBin
 *
 */
@Component
@Slf4j
public class SportFootballDataSnatch {
	@Autowired
	FootballSnatchFactory footballSnatchFactory;
	@Autowired
	SportFootballDataService sportFootballDataService;
	
	public void snatch() {
		String serviceName = getCacheConfigService();		
		FootballSnatchService footballSnatchService = footballSnatchFactory.getFootballSnatchService(serviceName);
		List<SportFootballMatchAward> list = footballSnatchService.snatchMatch();
		sportFootballDataService.saveFootballData(list);
		log.info("保存竞彩数据成功");
	}
	
	/**
	 * 从缓存取抓取配置源
	 * @return
	 */
	public String getCacheConfigService() {
		return "footballSnatchServiceSporttery";
	}
}
