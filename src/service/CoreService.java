package service;


import entity.SettingModel;

/**
 * Created by shikee_app03 on 16/7/12.
 */
public interface CoreService {
    public void setSetting(SettingModel setting);
    public void start();
    public void pause();
    public void stop();
}
