package com.caipiao.snatch.match.football;

import java.util.List;

import com.caipiao.lottery.entity.sport.vo.SportFootballMatchAward;

/**
 * 竞彩足球赛事抓取接口
 * @author WangBin
 *
 */
public interface FootballSnatchService {
	public List<SportFootballMatchAward> snatchMatch();
}
