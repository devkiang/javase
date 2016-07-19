package dao_implement;

import dao.NewsDAO;
import entity.NewsModel;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import toolkit.CrawlerException;

import java.util.List;

/**
 * Created by shikee_app03 on 16/7/12.
 */
public class NewsDAOImp extends HibernateTemplate implements NewsDAO{

    @Override
    public Boolean save(NewsModel obj) {
        final Session session = getSession();
        Transaction tran=session.beginTransaction();
        try {
            session.save(obj);
            tran.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally
        {

            session.close();
        }


        return true;
    }

    @Override
    public Boolean delete(NewsModel obj) {
        final Session session = getSession();
        Transaction tran=session.beginTransaction();
        try {
            session.delete(obj);
            tran.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

            session.close();
        }
        return true;
    }


    public boolean checkIsExist(NewsModel obj) throws CrawlerException {
        if(obj==null||obj.getUrl()==null||obj.getUrl().length()<1){
           throw new CrawlerException("传来的对象有问题");
        }
        List<NewsModel> result=null;
        try {
            Session s=getSession();
            Query query=getSession().createQuery("from NewsModel as news where news.title=? and news.url=?");
            query.setParameter(0,obj.getTitle());
            query.setParameter(1,obj.getUrl());
            result=query.list();
            if(result.size()>0){
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public NewsModel load(NewsModel obj) {
        if(obj==null||obj.getId()<1){
            return null;
        }

        try {
            getSession().load(obj,obj.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }
    @Override
    public List allList() {
       return null;
    }

    @Override
    public List list(int page) {
        return null;
    }

    @Override
    public NewsModel update(NewsModel model) {
        return null;
    }
}
