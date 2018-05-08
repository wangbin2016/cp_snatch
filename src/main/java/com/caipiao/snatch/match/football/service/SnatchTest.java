package com.caipiao.snatch.match.football.service;


import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;


public class SnatchTest {
	public static void main(String[] args) throws IOException {
		String url = "http://info.sporttery.cn/interface/interface_mixed.php?action=fb_list&pke=0.7773626391180328&_=1525769234663";
		/*System.out.println(HttpUtil.getUrlByChrome(url, "utrf-8"));*/
		
		//Jsoup.parse("http://info.sporttery.cn/interface/interface_mixed.php?action=fb_list&pke=0.7773626391180328&_=1525769234663", "3000");
		
		
		Connection conn = Jsoup.connect("http://info.sporttery.cn/interface/interface_mixed.php?action=fb_list");
		conn.ignoreContentType(true);
		conn.execute();
		String body = conn.response().body();
		System.out.println(body);
		
	}
}
