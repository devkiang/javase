import entity.SettingModel;
import service.CoreService;
import service_implement.CoreServiceImpl;

/**
 * Created by shikee_app03 on 16/7/13.
 */
public class Main {

    public static void main(String[] args) {
        CoreService service=new CoreServiceImpl();
        SettingModel setting=new SettingModel();
        setting.setThreadCount(5);//五条线程去抓数据
//        setting.setAutoSpeed(true);//自动控制速度
        setting.setSpeed(60*10);//初始化速度
        service.setSetting(setting);
        service.start();//开始抓取
    }
}
