package com.caipiao.snatch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.caipiao.lottery.entity.sport.vo.SportFootballMatchAward;
import com.caipiao.lottery.service.sport.SportFootballDataService;
import com.caipiao.snatch.match.football.FootballSnatchFactory;
import com.caipiao.snatch.match.football.service.FootballSnatchService;

import lombok.extern.slf4j.Slf4j;

/**
 * 竞彩足球数据抓取线程
 * @author WangBin
 *
 */
@Slf4j
public class SportFootballSnatchThread implements Runnable{
	@Autowired
	FootballSnatchFactory footballSnatchFactory;
	@Autowired
	SportFootballDataService sportFootballDataService;
	@Override
	public void run() {
		log.info("竞彩足球数据抓取线程启动");
		try {
			Thread.sleep(2000);
			log.info("竞彩足球数据抓取线程  休眠2秒中");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
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
