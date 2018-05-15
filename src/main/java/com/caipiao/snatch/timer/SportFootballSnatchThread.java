package com.caipiao.snatch.timer;

import org.springframework.beans.factory.annotation.Autowired;
import com.caipiao.snatch.match.football.SportFootballDataSnatch;
import lombok.extern.slf4j.Slf4j;

/**
 * 竞彩足球数据抓取线程
 * @author WangBin
 *
 */
@Slf4j
public class SportFootballSnatchThread implements Runnable{
	@Autowired
	SportFootballDataSnatch sportFootballDataSnatch;
	@Override
	public void run() {
		log.info("竞彩足球数据抓取线程启动");
		try {
			Thread.sleep(2000);
			log.info("竞彩足球数据抓取线程  休眠2秒中");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		sportFootballDataSnatch.snatch();
		log.info("保存竞彩数据成功");
	}

}
