package entity;

/**
 * Created by shikee_app03 on 16/7/13.
 */
public class SettingModel {
    public int getLog_level() {
        return log_level;
    }

    public void setLog_level(int log_level) {
        this.log_level = log_level;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    private int log_level;//日志等级
    private int speed;//爬取间隔
    private int threadCount;//线程数量

    public boolean isAutoSpeed() {
        return autoSpeed;
    }

    public void setAutoSpeed(boolean autoSpeed) {
        this.autoSpeed = autoSpeed;
    }

    private boolean autoSpeed;//自动控制速度
}
