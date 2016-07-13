package toolkit;

/**
 * Created by shikee_app03 on 16/7/13.
 */
public class CrawlerThread extends Thread {

    CrawlerCallback _callback;
    @Override
    public void run() {
        super.run();
        _callback.handleAction();
    }

    public void runWithCallback(CrawlerCallback callback)
    {
        _callback=callback;
        start();
    }
}
