package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by shikee_app03 on 16/7/12.
 */
public class MainView {

    JFrame _mainFrame;

    public static void main(String[] args) {
        MainView mainView=new MainView();
        mainView.initLayout();
    }

    public void initLayout() {
        _mainFrame = new JFrame();
        _mainFrame.setTitle("爬虫控制台");
        _mainFrame.setSize(400,400);
        MenuBar menuBar = new MenuBar();
        Menu item1 = new Menu();
        item1.add("文件");
        item1.add("退出");
        menuBar.add(item1);
        _mainFrame.setMenuBar(menuBar);
        _mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _mainFrame.setVisible(true);
    }
}
