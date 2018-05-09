package com.caipiao.snatch.match.football.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.caipiao.lottery.entity.sport.vo.SportFootballMatchAward;
import com.caipiao.snatch.match.football.service.FootballSnatchService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("footballSnatchServiceSporttery")
public class FootballSnatchServiceImpl implements FootballSnatchService {

	@Override
	public List<SportFootballMatchAward> snatchMatch() {
		String url = "http://info.sporttery.cn/interface/interface_mixed.php?action=fb_list&pke=0.7773626391180328&_=1525769234663";
		System.out.println(url);
		Connection conn = Jsoup.connect("http://info.sporttery.cn/interface/interface_mixed.php?action=fb_list");
		conn.ignoreContentType(true);
		try {
			conn.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String body = conn.response().body();
		int index = body.indexOf("data=");
		int end = body.indexOf(";getData");
		body = body.substring(index, end);
		log.info("竞彩足球抓取数据：" + body);
		JSONArray arr = JSON.parseArray(body);
		List<SportFootballMatchAward> list = new ArrayList<SportFootballMatchAward>();
		int size = arr.size();
		for (int i = 0; i < size; i++) {
			JSONArray match = arr.getJSONArray(i);
			list.add(createSportFootballMatchAward(match));
		}

		return list;
	}

	/**
	 * 创建竞彩对阵实体数据
	 * 
	 * @param match
	 * @return
	 */
	public SportFootballMatchAward createSportFootballMatchAward(JSONArray match) {
		SportFootballMatchAward sportFootballMatchAward = new SportFootballMatchAward();
		interpretingMatchData(sportFootballMatchAward, match);// 解释竞彩对阵数据
		interpretingAwardData(sportFootballMatchAward, match);// 解释竞彩对陈sp
		interpretingInfoData(sportFootballMatchAward, match);
		// 解释
		/*
		 * JSONArray matchData = match.getJSONArray(0);//对阵信息 JSONArray rspfAward =
		 * match.getJSONArray(1);//上球胜平负 JSONArray bfAward = match.getJSONArray(2);//比分
		 * JSONArray jqsAward = match.getJSONArray(3);//总进球数 JSONArray bqcspfAward =
		 * match.getJSONArray(4);//半全场胜平负 JSONArray spfAward =
		 * match.getJSONArray(5);//胜平负
		 */ return sportFootballMatchAward;
	}

	private void interpretingInfoData(SportFootballMatchAward sportFootballMatchAward, JSONArray match) {
		// ["周三001", "亚冠", "鹿岛鹿角$-1$上海上港", "18-05-09 18:00", "107808", "#336600","亚洲冠军联赛", "鹿岛鹿角", "上海上港", "", "", "2018-05-09"]
		// ["周三002", "日联赛杯", "新泻天鹅$+1$东京FC", "18-05-09 18:00", "107809", "#08855C","日本联赛杯", "新泻天鹅", "东京FC", "A组3", "A组4", "2018-05-09"]
		JSONArray matchData = match.getJSONArray(0);// 对阵信息
		String weekInfo = matchData.getString(0);
		String weekday = weekInfo.substring(0, 2);// 星期与lineId
		String lineId = weekInfo.substring(2, weekInfo.length());
		String leagueShortName = matchData.getString(1);

	}

	private void interpretingAwardData(SportFootballMatchAward sportFootballMatchAward, JSONArray match) {
		// TODO Auto-generated method stub

	}

	private void interpretingMatchData(SportFootballMatchAward sportFootballMatchAward, JSONArray match) {
		// TODO Auto-generated method stub

	}

}
