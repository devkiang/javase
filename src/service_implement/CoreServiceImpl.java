package service_implement;

import java.io.*;

import dao.NewsDAO;
import service.CoreService;
import entity.NewsModel;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;

/**
 * Created by shikee_app03 on 16/7/12.
 */

public class CoreServiceImpl implements CoreService{
    static String urlDomain="http://3g.chinabreed.com/";
    public NewsDAO getNewDAO() {
        return newDAO;
    }
    JTextArea _input;
    public void setNewDAO(NewsDAO newDAO) {
        this.newDAO = newDAO;
    }

    private NewsDAO newDAO;
    @Override
    public void start(JTextArea input) {
        _input=input;
        String url ="http://3g.chinabreed.com/list.php?fid=48";
        RequestClient client=new RequestClient();
        String htmlContent=client.getWebCon(url);
        System.out.println("=============\n\n");
        List<NewsModel> result=this.formatContent(htmlContent);
        this.saveData2DB(null);
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    private List<NewsModel> formatContent(String content)
    {
        return formatContent(content,false);
    }

    private List<NewsModel> formatContent(String content, boolean isContentFlag)
    {
        log("=内容解析开始=\n","");
        List<NewsModel> result=new ArrayList<NewsModel>();
        Document doc = Jsoup.parse(content);
        if(isContentFlag){
            NewsModel model=new NewsModel();
            model.setContent(doc.body().getElementsByClass("article-container").html());
            result.add(model);
        }else{
            Elements newsList=doc.body().getElementsByClass("ListMoreSort").first().getElementsByClass("cont").first().getElementsByTag("ul").first().getElementsByTag("li");
            if(newsList==null){
                log("sys====>","newsList为空");
            }
            for(Element element:newsList){
                log("li", element.text());
                String url=urlDomain+element.getElementsByTag("a").first().attr("href");
                log("开始加载url",url);
                NewsModel newsModel=new NewsModel();
                newsModel.setTitle(element.text());
                newsModel.setUrl(url);
                RequestClient request=new RequestClient();
                NewsModel contentModel=formatContent(request.getWebCon(url),true).get(0);
                newsModel.setContent(contentModel.getContent());
//                log("加载完毕,解析到内容",newsModel.getContent());
                result.add(newsModel);
            }
        }
//        log("body",doc.body().getElementsByClass("ListMoreSort").first().getElementsByClass("cont").first().getElementsByTag("ul").text());
        log("=内容解析结束=\n","log");
        return result;
    }

    private boolean saveData2DB(NewsModel obj){
        log("=数据保存开始=\n","");
        log("=数据保存结束=\n","");
        return true;
    }

    private void log(String title,String msg){
        _input.append("\n"+title+":"+msg+"============");
        _input.paintImmediately(_input.getBounds());
//        _input.repaint();
    }
}

class RequestClient{

    public String getWebCon(String domain) {
        System.out.println("开始读取内容...("+domain+")");
        StringBuffer sb = new StringBuffer();
        try {
            java.net.URL url = new java.net.URL(domain);
            BufferedReader in = new BufferedReader(new InputStreamReader(url
                    .openStream(),"gb2312"));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
        } catch (Exception e) {
            sb.append(e.toString());
            System.err.println(e);
            System.err.println("Usage:   java   HttpClient   <URL>   [<filename>]");
        }
        return sb.toString();
    }
}
