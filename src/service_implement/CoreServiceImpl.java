package service_implement;

import java.io.*;

import dao.NewsBriefDAO;
import dao.NewsDAO;
import dao_implement.NewsBriefDAOImpl;
import dao_implement.NewsDAOImp;
import entity.NewsBriefModel;
import entity.SettingModel;
import service.CLog;
import service.CoreService;
import entity.NewsModel;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import toolkit.CrawlerCallback;
import toolkit.CrawlerException;
import toolkit.CrawlerThread;


/**
 * Created by shikee_app03 on 16/7/12.
 */

public class CoreServiceImpl implements CoreService{
    int _dataCount=0;
    int _failCount=0;
    Timer _timer;
    int ___repeatDataCount=0;
    int _repeatDataCount=0;
    boolean _isFinish=true;//任务执行完毕
    int _taskCount;
    static String urlDomain="http://3g.chinabreed.com/";
    private SettingModel _settingModel;
    public NewsDAO getNewDAO() {
        return newDAO;
    }
    public void setNewDAO(NewsDAO newDAO) {
        this.newDAO = newDAO;
    }
    private CLog cLog=new CLogImpl();
    private NewsDAO newDAO=new NewsDAOImp();
    private NewsBriefDAO newsBriefDAO=new NewsBriefDAOImpl();

    @Override
    public void setSetting(SettingModel setting) {
        _settingModel=setting;
    }

    @Override
    public void start() {

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 12); // 控制时
//        calendar.set(Calendar.MINUTE, 0);       // 控制分
//        calendar.set(Calendar.SECOND, 0);       // 控制秒
//
//        Date time = calendar.getTime();         // 得出执行任务的时间,此处为今天的12：00：00

//        Timer timer = new Timer();
        dataInit();
        if(_timer!=null){
            _timer.cancel();
            _timer=null;
        }
        _timer = new Timer();
        _timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(_isFinish==false){
                    _failCount++;
                    if(_failCount>5){//如果任务有五次超时的话,则视为异常处理,重置所有数据重新请求
                        dataInit();
                    }
                    return;
                }
                _isFinish=false;
                final String url = "http://3g.chinabreed.com/list.php?fid=48";
                final RequestClient client = new RequestClient();
                client.getWebCon(url, new RequestClientCallback() {
                    public void handleAction(String webContent) {
                        if (_settingModel == null) {
                            _settingModel = new SettingModel();
                            _settingModel.setThreadCount(3);
                        }
                        ExecutorService threadPool = Executors.newFixedThreadPool(_settingModel.getThreadCount());
                        final List<NewsModel> url_title_array = getContentURLAndTitleArray(webContent);
                        _taskCount=url_title_array.size();
                        for (final NewsModel model : url_title_array) {
                            try {
                                threadPool.execute(new Runnable() {
                                    public void run() {
                                        client.getWebCon(model.getUrl(), new RequestClientCallback() {
                                            public void handleAction(String webContent) {
                                                model.setContent(getPageHtmlCode(webContent));
                                                model.setTime(getTime(webContent));
                                                saveData2DB(model);
                                                if(_dataCount==url_title_array.size()-1){
                                                    System.out.println("task was finish!");
                                                    _isFinish=true;
                                                    _dataCount=0;
                                                    _repeatDataCount=0;
                                                }else{
                                                    _dataCount++;
                                                    System.out.println("task is runing!  "+_dataCount);
                                                }

                                            }
                                        });
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                threadPool.shutdown();
                            }

                        }
                        threadPool.shutdown();

                    }
                });
            }
        }, 0, 1000 * _settingModel.getSpeed());// 这里设定将延时每天固定执行
    }

    @Override
    public void pause() {
        _timer.cancel();
    }

    @Override
    public void stop() {
        _timer.cancel();
        _timer=null;
        dataInit();
    }

    private void dataInit()
    {
        _dataCount=0;
        _failCount=0;
        _repeatDataCount=0;
        _taskCount=0;
        ___repeatDataCount=0;
        _isFinish=true;
    }

/**
 *
 * 根据页面读取列表标题和列表URL
 *
 * */
    private List<NewsModel> getContentURLAndTitleArray(String htmlCode){
        Document doc = Jsoup.parse(htmlCode);
        Elements newsList=doc.body().getElementsByClass("ListMoreSort").first().getElementsByClass("cont").first().getElementsByTag("ul").first().getElementsByTag("li");
        if(newsList==null){
            log("sys====>","newsList为空");
        }
        log("读取到新闻列表");
        log("");
        log("");
        log("");
        List<NewsModel> modelList=new ArrayList<NewsModel>();
        for(final Element element:newsList){
            log(element.text());
            String url=urlDomain+element.getElementsByTag("a").first().attr("href");
            final NewsModel newsModel=new NewsModel();
            newsModel.setTitle(element.text());
            newsModel.setUrl(url);
            modelList.add(newsModel);
        }

        log("新闻列表获取结束");
        log("");
        log("");
        log("");
        return modelList;
    }


    /**
     *
     * 获取文章内容html代码
     * */
    private String getPageHtmlCode(String content){
        Document doc = Jsoup.parse(content);
        String result=doc.body().getElementsByClass("article-container").get(0).getElementsByTag("section").html();
        return result;
    }

    private String getTime(String content)
    {
        Document doc = Jsoup.parse(content);
        String result=doc.body().getElementsByClass("article-container").get(0).getElementsByTag("time").text();
        System.out.print("tiem:\n"+result+"\n");
        return result;
    }

    private NewsBriefModel getBriefContent(NewsModel newsModel){
        NewsBriefModel briefModel=new NewsBriefModel(newsModel);
        return briefModel;
    }

    private boolean saveBriefData2DB(NewsModel obj){
        try {
            if(newsBriefDAO.findObjByNId(obj.getId())==null){
                NewsBriefModel newsBriefModel=getBriefContent(obj);
                newsBriefDAO.save(newsBriefModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            cLog.error("摘要数据保存失败:\n"+e.getMessage());
            return false;
        }

        return true;
    }

    private boolean saveData2DB(NewsModel obj){



        try {
            NewsModel newsModel=newDAO.checkIsExist(obj);
            if(newsModel==null){
                obj=newDAO.save(obj);
                cLog.info("爬到新数据:"+obj.getTitle());
                saveBriefData2DB(obj);
            }else{
                saveBriefData2DB(newsModel);
                if(_taskCount-1 == _repeatDataCount){
                    ___repeatDataCount++;
                }
                if(___repeatDataCount==5){//五次都搜不到新数据
                    if(_settingModel.isAutoSpeed()){
                        cLog.waring(___repeatDataCount+"次都扒不到新数据,自动降低搜索速度为30分钟");
                        _settingModel.setSpeed(60*30);
                        stop();
                        start();
                    }else {
                        cLog.waring(___repeatDataCount+"次都扒不到新数据");
                    }

                }else if(___repeatDataCount==10){//10次都搜不到新数据
                    if(_settingModel.isAutoSpeed()){
                        cLog.waring(___repeatDataCount+"次都扒不到新数据,自动降低搜索速度为60分钟");
                        _settingModel.setSpeed(60*60);
                        stop();
                        start();
                    }else {
                        cLog.waring(___repeatDataCount+"次都扒不到新数据");
                    }
                }
                _repeatDataCount++;
            }
        } catch (CrawlerException e) {
            e.printStackTrace();
            cLog.error("数据保存失败("+obj.getTitle()+")");
            return false;
        }
        return true;
    }





    private void log(String msg)
    {
        log("",msg);
    }

    private void log(String title,String msg){
        if(title==null||title.length()<1){
            System.out.println("\n=========== "+msg+" ============");
        }else{
            System.out.println("\n=========== "+title+":"+msg+" ============");
        }
    }

}











interface RequestClientCallback{
    public void handleAction(String webContent);
}

class RequestClient{
    public void getWebCon(String domain,RequestClientCallback callback) {
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
        }
        callback.handleAction(sb.toString());
    }
}
