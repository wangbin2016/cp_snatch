package com.caipiao.snatch.match.football;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.caipiao.lottery.entity.sport.vo.SportFootballMatchAward;
import com.caipiao.lottery.service.sport.SportFootballDataService;
import com.caipiao.snatch.match.football.service.FootballSnatchService;

import lombok.extern.slf4j.Slf4j;

/**
 * 竞彩足球抓取业务
 * 根据缓存配置,取抓取实现,抓取数据
 * 对比缓存数据并更新数据
 * @author WangBin
 *
 */
@Service()
@Slf4j
public class SportFootballDataSnatch {
	@Autowired
	FootballSnatchFactory footballSnatchFactory;
	@Autowired
	SportFootballDataService sportFootballDataService;

	public void snatch() {
		try {
			String serviceName = getCacheConfigService();
			FootballSnatchService footballSnatchService = footballSnatchFactory.getFootballSnatchService(serviceName);
			List<SportFootballMatchAward> list = footballSnatchService.snatchMatch();
			sportFootballDataService.saveOrUpdateFootballData(list);
			log.info("保存竞彩数据成功");
		} catch (Exception e) {
			log.info("抓取竞彩数据异常...");
			e.printStackTrace();
		}
	}

	/**
	 * 从缓存取抓取配置源
	 * 
	 * @return
	 */
	private String getCacheConfigService() {
		return "footballSnatchServiceSporttery";
	}
}
