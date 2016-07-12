package ui;

import service.CoreService;
import service_implement.CoreServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by shikee_app03 on 16/7/12.
 */
public class MainView {

    JFrame _mainFrame;
    CoreService _service;
    JTextArea jTextArea1;
    public static void main(String[] args) {
        MainView mainView=new MainView();
        mainView.initCfg();
        mainView.initLayout();
    }

    public void initCfg()
    {
        _service=new CoreServiceImpl();
    }

    public void initLayout() {
        _mainFrame = new JFrame();
        _mainFrame.setTitle("爬虫控制台");
        _mainFrame.setSize(800,500);
        _mainFrame.setResizable(false);

        JPanel panel=new JPanel();
        _mainFrame.setContentPane(panel);

        MenuBar menuBar = new MenuBar();
        Menu item1 = new Menu("update");
        Menu item2 = new Menu("config");
        menuBar.add(item1);
        menuBar.add(item2);
        _mainFrame.setMenuBar(menuBar);
        jTextArea1 = new JTextArea("");//需要在换行的地方加入\n
        jTextArea1.setSize(790, 400);
        jTextArea1.setEnabled(true);
        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        panel.add(jTextArea1);


        JButton startBtn=new JButton("start");
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    handleAction(e);
            }
        });
        startBtn.setBounds(0,0,200,200);
        panel.add(startBtn);

        _mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _mainFrame.setVisible(true);



    }

    private void handleAction(ActionEvent e)
    {
        if(e.getActionCommand().equals("start")){
             _service.start(jTextArea1);
        }else{
            System.out.print("other\n");
        }


    }
}
