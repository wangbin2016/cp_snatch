package com.caipiao.snatch.match.football.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.caipiao.common.utils.DateUtils;
import com.caipiao.lottery.entity.sport.SportFootballAward;
import com.caipiao.lottery.entity.sport.SportLeagueInfo;
import com.caipiao.lottery.entity.sport.award.BFAward;
import com.caipiao.lottery.entity.sport.award.BQCAward;
import com.caipiao.lottery.entity.sport.award.JQSAward;
import com.caipiao.lottery.entity.sport.award.RQSPFAward;
import com.caipiao.lottery.entity.sport.award.SPFAward;
import com.caipiao.lottery.entity.sport.vo.SportFootballMatchAward;
import com.caipiao.lottery.service.sport.SportLeagueInfoService;
import com.caipiao.snatch.match.football.service.FootballSnatchService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("footballSnatchServiceSporttery")
public class FootballSnatchServiceImpl implements FootballSnatchService {
	@Autowired
	private SportLeagueInfoService sportLeagueInfoService_;
	
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
		body = body.substring(index, end).replace("data=", "");
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
	 * 解释并创建竞彩对阵实体数据
	 * 
	 * @param match
	 * @return
	 */
	public SportFootballMatchAward createSportFootballMatchAward(JSONArray match) {
		SportFootballMatchAward sportFootballMatchAward = new SportFootballMatchAward();
		saveSportLeagueInfo(sportFootballMatchAward, match);
		interpretingMatchData(sportFootballMatchAward, match);// 解释竞彩对阵数据
		interpretingAwardData(sportFootballMatchAward, match);// 解释竞彩对陈sp
		return sportFootballMatchAward;
	}

	/**
	 * 保存竞彩联赛数据
	 * @param sportFootballMatchAward
	 * @param match
	 */
	private void saveSportLeagueInfo(SportFootballMatchAward sportFootballMatchAward, JSONArray match) {
		JSONArray matchData = match.getJSONArray(0);// 对阵信息
		String shortName = matchData.getString(1);//"亚冠"
		SportLeagueInfo s = sportLeagueInfoService_.selectByPrimaryKey(1);
		System.out.println(s);
		SportLeagueInfo sportLeagueInfo = sportLeagueInfoService_.selectByLikeName(shortName);
		if(sportLeagueInfo == null) {
			Integer id = sportLeagueInfoService_.selectTableId();
			String color = matchData.getString(5);
			String dcName = "";
			String jcName = shortName;
			String name = matchData.getString(6);
			sportLeagueInfo = new SportLeagueInfo();
			sportLeagueInfo.setId(id);
			sportLeagueInfo.setColor(color);
			sportLeagueInfo.setDcName(dcName);
			sportLeagueInfo.setJcName(jcName);
			sportLeagueInfo.setName(name);
			sportLeagueInfo.setShortName(shortName);
			sportLeagueInfoService_.insert(sportLeagueInfo);
			log.info("保存联赛数据成功："+sportLeagueInfo);
		}
		sportFootballMatchAward.setLeagueInfoId(sportLeagueInfo.getId());
		sportFootballMatchAward.setSportLeagueInfo(sportLeagueInfo);
		// ["周三001", "亚冠", "鹿岛鹿角$-1$上海上港", "18-05-09 18:00", "107808", "#336600","亚洲冠军联赛", "鹿岛鹿角", "上海上港", "", "", "2018-05-09"]
		// ["周三002", "日联赛杯", "新泻天鹅$+1$东京FC", "18-05-09 18:00", "107809", "#08855C","日本联赛杯", "新泻天鹅", "东京FC", "A组3", "A组4", "2018-05-09"]
	}
	
	public static void main(String[] args) {
		String matchDate = "20180512001";
		matchDate = matchDate.substring(1, matchDate.length());
		System.out.println(Integer.valueOf(matchDate).intValue());
	}

	/**
	 * 解释对阵sp数据
	 * @param sportFootballMatchAward
	 * @param match
	 */
	private void interpretingAwardData(SportFootballMatchAward sportFootballMatchAward, JSONArray match) {
		SportFootballAward SportFootballAward = new SportFootballAward();
		JSONArray rqspfArr = match.getJSONArray(1);//上球胜平负
		JSONArray bfArr = match.getJSONArray(2);//比分
		JSONArray jqsArr = match.getJSONArray(3);//进球数
		JSONArray bqcArr = match.getJSONArray(4);//半全场
		JSONArray spfArr = match.getJSONArray(5);//胜平负
		
		RQSPFAward rqspfAward = new RQSPFAward(rqspfArr);
		BFAward bfAward = new BFAward(bfArr);
		BQCAward bqcAward = new BQCAward(bqcArr);
		JQSAward jqsAward = new JQSAward(jqsArr);
		SPFAward spfAward = new SPFAward(spfArr);		
		
		SportFootballAward.setRqspfAward(rqspfAward.toString());
		SportFootballAward.setBfAward(bfAward.toString());
		SportFootballAward.setBqcAward(bqcAward.toString());
		SportFootballAward.setJqsAward(jqsAward.toString());
		SportFootballAward.setSpfAward(spfAward.toString());
		SportFootballAward.setId(sportFootballMatchAward.getId());
		SportFootballAward.setLineId(sportFootballMatchAward.getLineId());
		SportFootballAward.setIntTime(sportFootballMatchAward.getIntTime());
	}
	
	public double getSP(JSONArray spArr,int index) {
		return spArr.getDoubleValue(index);
	}

	/**
	 * 解释对阵数据
	 * @param sportFootballMatchAward
	 * @param match
	 */
	private void interpretingMatchData(SportFootballMatchAward sportFootballMatchAward, JSONArray match) {
		JSONArray matchData = match.getJSONArray(0);// 对阵信息
		// ["周三001", "亚冠", "鹿岛鹿角$-1$上海上港", "18-05-09 18:00", "107808", "#336600","亚洲冠军联赛", "鹿岛鹿角", "上海上港", "", "", "2018-05-09"]
		// ["周三002", "日联赛杯", "新泻天鹅$+1$东京FC", "18-05-09 18:00", "107809", "#08855C","日本联赛杯", "新泻天鹅", "东京FC", "A组3", "A组4", "2018-05-09"]		
		String weekInfo = matchData.getString(0);//周三001	
		String[] team = matchData.getString(2).split("$");//"鹿岛鹿角$-1$上海上港"
		String homeGroup = matchData.getString(9);
		String guestGroup = matchData.getString(10);
		String matchDate = matchData.getString(11).replaceAll("-", "");//"2018-05-09" ->20180509
		matchDate = matchDate.substring(1, matchDate.length());
		String matchTimeStr = matchData.getString(3);		
		String homeTeam = team[0];
		int conncede = Integer.valueOf(team[1]);
		String guestTeam = team[2];	
		String lineId = weekInfo.substring(2, weekInfo.length());
		Date matchTime = DateUtils.StringToDate(matchTimeStr, DateUtils.yyMMdd_HHmm);
		sportFootballMatchAward.setConncede(conncede);
		sportFootballMatchAward.setHomeGroup(homeGroup);
		sportFootballMatchAward.setGuestGroup(guestGroup);
		sportFootballMatchAward.setHomeTeam(homeTeam);
		sportFootballMatchAward.setGuestTeam(guestTeam);
		sportFootballMatchAward.setMatchTime(matchTime);
		sportFootballMatchAward.setLineId(lineId);
		sportFootballMatchAward.setIntTime(matchDate);
		sportFootballMatchAward.setId(Integer.valueOf(matchDate+lineId));
		sportFootballMatchAward.setCreateDate(new Date());
	}

}
