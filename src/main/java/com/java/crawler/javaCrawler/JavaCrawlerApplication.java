package com.java.crawler.javaCrawler;

import com.java.crawler.javaCrawler.main.MyCrawler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaCrawlerApplication {
	public static void main(String[] args) {
		SpringApplication.run(JavaCrawlerApplication.class, args);

		MyCrawler crawler = new MyCrawler();
		String url = "http://dy.chinasarft.gov.cn/shanty.deploy/catalog.nsp?id=0129dffcccb1015d402881cd29de91ec&pageIndex=";
		//crawler.getAllUrl(url,"2016年");
		crawler.getAllPage(url,"2017年",3,6);
	}
}
