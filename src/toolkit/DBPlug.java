package toolkit;

import dao.NewsDAO;
import dao_implement.HibernateTemplate;
import dao_implement.NewsDAOImp;
import entity.NewsBriefModel;
import entity.NewsModel;
import entity.SettingModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.CoreService;
import service_implement.CoreServiceImpl;

import java.util.List;

/**
 * Created by shikee_app03 on 16/7/20.
 * 数据库插件,处理异常数据
 */
public class DBPlug extends HibernateTemplate{

    public void addNewsBriefData()
    {
        Session s=getSession();
        Query q=s.createQuery("from NewsModel as nm");
        List<NewsModel> newsList=q.list();
        s.close();
        Session s2=getSession();

        for (NewsModel newsModel:newsList){
            NewsBriefModel newsBriefModel=new NewsBriefModel(newsModel);
            Query q2=s2.createQuery("from NewsBriefModel as nbm where nId=?");
            q2.setParameter(0,newsModel.getId());
            List q2Result=q2.list();
            try {
                if(q2Result.size()<1){
                    Transaction t= s2.beginTransaction();
                    s2.save(newsBriefModel);
                    t.commit();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        s2.close();

    }

    public static void main(String[] args) {
       DBPlug plug=new DBPlug();
        plug.addNewsBriefData();
    }

}
