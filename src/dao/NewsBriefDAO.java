package dao;

import entity.NewsBriefModel;

/**
 * Created by shikee_app03 on 16/7/20.
 */
public interface NewsBriefDAO {
    public NewsBriefModel load(NewsBriefModel entity);
    public NewsBriefModel findObjByNId(Long nId);//通过新闻ID获取新闻摘要
    public Boolean save(NewsBriefModel entity);
}
