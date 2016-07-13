import entity.SettingModel;
import service.CoreService;
import service_implement.CoreServiceImpl;

/**
 * Created by shikee_app03 on 16/7/13.
 */
public class App {

    public static void main(String[] args) {
        CoreService service=new CoreServiceImpl();
        SettingModel setting=new SettingModel();
        setting.setThreadCount(3);
        setting.setSpeed(1000*5);
        service.setSetting(setting);
        service.start();
    }
}
