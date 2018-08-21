package com.java.crawler.javaCrawler.main;


import com.java.crawler.javaCrawler.domain.ExportDto;
import com.java.crawler.javaCrawler.domain.MoveInfoDto;
import com.java.crawler.javaCrawler.link.LinkFilter;
import com.java.crawler.javaCrawler.link.Links;
import com.java.crawler.javaCrawler.page.Page;
import com.java.crawler.javaCrawler.page.PageParserTool;
import com.java.crawler.javaCrawler.page.RequestAndResponseTool;
import com.java.crawler.javaCrawler.util.ExprtExcelUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
public class MyCrawler {

    /**
     * 使用种子初始化 URL 队列
     *
     * @param seeds 种子 URL
     * @return
     */
    private void initCrawlerWithSeeds(List<String> seeds) {
        for (int i = 0; i < seeds.size(); i++){
            Links.addUnvisitedUrlQueue(seeds.get(i));
        }
    }

    public void getAllPage(String baseurl ,String dataTime,int startPage,int endPage){
        //根据URL得到page;
        List<Page> pageList = new ArrayList<>();
        for (int p=startPage;p<=endPage;p++){
            Page page = RequestAndResponseTool.sendRequstAndGetResponse(baseurl+p);
            pageList.add(page);
        }
        //ExecutorService executor = new ThreadPoolExecutor(20,30,0L,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());

       for (Page p:pageList) {
            //executor.submit(new Runnable() {
               // @Override
              //  public void run() {*/
                    getAllUrl(p,dataTime);
           }
            //});
        //}
       // executor.shutdown();
    }

    public void getAllUrl(Page page ,String dataTime){

        //根据URL得到page;
        //Page page = RequestAndResponseTool.sendRequstAndGetResponse(baseurl);
        Elements ulEs = page.getDoc().select("ul");
        List<String> urlList = new ArrayList<String>();
        Map<String,String> urlMap = new HashMap<String,String>();
        for (Element e:ulEs) {
            String herf= e.select("a").attr("href");
            if("".equals(herf)){
                continue;
            }
            String text= e.select("li").text();
            int start = text.indexOf("局关于");
            int end = text.indexOf("全国电影");
            if(start==-1||end==-1){
                continue;
            }
            String data= text.substring(start+3,end);
            urlMap.put(data,herf);
        }
        for (Map.Entry<String, String> entry : urlMap.entrySet()) {
            String key = entry.getKey();
            if(key.startsWith(dataTime)){
                String url = entry.getValue();
                urlList.add("http://dy.chinasarft.gov.cn"+url);
            }
        }
       // ExecutorService executorService = new ThreadPoolExecutor(20,30,0L,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
        for (String u:urlList){
            //executorService.submit(new Runnable() {
               // @Override
                //public void run() {
                    crawling(u);
                //}
           // });
        }
        //executorService.shutdown();


    }

    /**
     * 抓取过程
     *
     * @param seed
     * @return
     */
    public void crawling(String seed) {
        Long startTime = System.currentTimeMillis();
        System.err.println("线程名:"+Thread.currentThread().getName());
/*
        //初始化 URL 队列
        initCrawlerWithSeeds(seeds);

        //定义过滤器，提取以 http://www.baidu.com 开头的链接
        LinkFilter filter = new LinkFilter() {
            @Override
            public boolean accept(String url) {
                if (url.startsWith("http://dy.chinasarft.gov.cn")){
                    return true;
                }else{
                    return false;
                }
            }
        };
*/

    /*    //循环条件：待抓取的链接不空且抓取的网页不多于 1000
        while (!Links.unVisitedUrlQueueIsEmpty()  && Links.getVisitedUrlNum() <= 1000) {

            //先从待访问的序列中取出第一个；
            String visitUrl = (String) Links.removeHeadOfUnVisitedUrlQueue();
            if (visitUrl == null){
                continue;
            }
*/
            //根据URL得到page;
            Page page = RequestAndResponseTool.sendRequstAndGetResponse(seed);

            //对page进行处理： 访问DOM的某个标签
            //Elements es = PageParserTool.select(page,"a");
            //获得表单标题头
            String esheadingText = PageParserTool.select(page,"div.heading").text();
            //获得表单分类信息

            Elements es = PageParserTool.select(page,"div.ta1").select("li");
            List<ExportDto> exportDtoList = new ArrayList<ExportDto>();

            for (Element e:es){
                ExportDto exportDto = new ExportDto();
                String fenleiText = e.text();
                //System.out.println("分类信息:"+fenleiText);
                String click= e.attr("onclick");
                int start= click.indexOf("(");
                String after = click.substring(start+1,start+2);
                String before = click.substring(start-4,start);
                String fenleiName = before+after;
                //System.out.println("分类链接:"+fenleiName);

                List<MoveInfoDto> moveInfoDtoList = new ArrayList<MoveInfoDto>();
                Elements  esDetailInfo= PageParserTool.select(page,"div#"+fenleiName).select("tr");
                for(Element eDetailInfo:esDetailInfo){

                    MoveInfoDto moveInfoDto = new MoveInfoDto();
                    String detailInfoText= eDetailInfo.text();
                    String[] detailInfoTexts= detailInfoText.split(" ");
                    moveInfoDto.setId(detailInfoTexts[0]);
                    moveInfoDto.setMovieId(detailInfoTexts[1]);
                    moveInfoDto.setMovieName(detailInfoTexts[2]);
                    moveInfoDto.setCompany(detailInfoTexts[3]);
                    moveInfoDto.setArtName(detailInfoTexts[4]);
                    moveInfoDto.setResulet(detailInfoTexts[detailInfoTexts.length-2]);
                    moveInfoDto.setAttrbute(detailInfoTexts[detailInfoTexts.length-1]);

                   // System.out.println("eDetailInfo:"+detailInfoText);
                    String detailInfoOnclick =eDetailInfo.select("a").attr("onclick");
                    if("".equals(detailInfoOnclick)){
                        continue;
                    }
                    int s = detailInfoOnclick.indexOf("('");
                    int t = detailInfoOnclick.indexOf("', 'newwindow");
                    String detailInfoUrl = "http://dy.chinasarft.gov.cn"+detailInfoOnclick.substring(s+2,t);
                   // System.out.println("detailInfoUrl:"+detailInfoUrl);
                    Page DetailPage = RequestAndResponseTool.sendRequstAndGetResponse(detailInfoUrl);
                    Elements dpes= DetailPage.getDoc().select("span");
                    String detailtext= dpes.text();
                    //System.out.println("detailtext:"+detailtext);
                    moveInfoDto.setDescrip(detailtext);
                    moveInfoDtoList.add(moveInfoDto);
                }
                exportDto.setName(fenleiText);
                exportDto.setValueList(moveInfoDtoList);
                exportDtoList.add(exportDto);
            }

            //System.out.println("电影清单:"+exportDtoList);
            ExprtExcelUtil exprtExcelUtil = new ExprtExcelUtil();
            String path = "D:/";
            exprtExcelUtil.writeExcwl(exportDtoList,esheadingText,path);

            Long endTime = System.currentTimeMillis();


            System.err.println("线程名:"+Thread.currentThread().getName()+",执行时间:"+(endTime-startTime));






            //将保存文件
           // FileTool.saveToLocal(page);

            //将已经访问过的链接放入已访问的链接中；
            //Links.addVisitedUrlSet(visitUrl);

            //得到超链接
           /* Set<String> links = PageParserTool.getLinks(page,"img");
            for (String link : links) {
                Links.addUnvisitedUrlQueue(link);
                System.out.println("新增爬取路径: " + link);
            }*/

    }


    //main 方法入口
    public static void main(String[] args) {
        MyCrawler crawler = new MyCrawler();
        String url = "http://dy.chinasarft.gov.cn/shanty.deploy/catalog.nsp?id=0129dffcccb1015d402881cd29de91ec&pageIndex=";
        //crawler.getAllUrl(url,"2016年");
        crawler.getAllPage(url,"2016年",6,9);


       /* String[] url={"http://dy.chinasarft.gov.cn/shanty.deploy/blueprint.nsp?id=01535a32454826e9402881a65139fdb6&templateId=0129f8148f650065402881cd29f7df33"};
        crawler.crawling(url);
*/
    }


}
