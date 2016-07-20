package dao;



import java.util.List;
import entity.NewsModel;
import toolkit.CrawlerException;

/**
 * Created by shikee_app03 on 16/7/12.
 */
public interface NewsDAO {
    public NewsModel save(NewsModel obj);
    public Boolean delete(NewsModel obj);
    public NewsModel load(NewsModel obj);
    public List<NewsModel> allList();
    public List<NewsModel> list(int page);
    public NewsModel update(NewsModel model);
    public NewsModel checkIsExist(NewsModel obj) throws CrawlerException;
}
