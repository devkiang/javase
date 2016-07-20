package dao_implement;

import dao.NewsBriefDAO;
import entity.NewsBriefModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by shikee_app03 on 16/7/20.
 */
public class NewsBriefDAOImpl extends HibernateTemplate implements NewsBriefDAO{
    @Override
    public NewsBriefModel load(NewsBriefModel entity) {
        if(entity==null&&entity.getNbId()<1){
            return null;
        }
        try {
            getSession().load(entity,entity.getNbId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return entity;
    }

    @Override
    public NewsBriefModel findObjByNId(Long nId) {
        if(nId<1){
            return null;
        }
        Session s=getSession();
        Query q=s.createQuery("from NewsBriefModel as nbm where nbm.nId=?");
        q.setParameter(0,nId);
        List<NewsBriefModel> result=q.list();
        if(result.size()>0){
            return result.get(0);
        }
        return null;
    }


    @Override
    public Boolean save(NewsBriefModel entity) {
        final Session session = getSession();
        Transaction tran=session.beginTransaction();
        try {
            session.save(entity);
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
}
