package dao_implement;

import dao.NewsDAO;
import entity.NewsModel;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by shikee_app03 on 16/7/12.
 */
public class NewsDAOImp extends HibernateDaoSupport implements NewsDAO{
    @Override
    public Boolean save(NewsModel obj) {
        try {
            this.getHibernateTemplate().save(obj);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean delete(NewsModel obj) {
        try {
            this.getHibernateTemplate().delete(obj);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public NewsModel load(NewsModel obj) {
        if(obj==null||obj.getId()<1){
            return null;
        }
        try {
           getHibernateTemplate().load(obj,obj.getId());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }
    @Override
    public List allList() {
        List result=null;
        try {
           result=getHibernateTemplate().loadAll(NewsModel.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
        return result;
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
