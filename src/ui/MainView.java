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
        _mainFrame.setLayout(new BorderLayout());
        _mainFrame.setTitle("爬虫控制台");
        _mainFrame.setSize(800,500);
        _mainFrame.setResizable(false);
        _mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        addMenuUI();
        addCtrlView();
        addOperationBtns();


        _mainFrame.setVisible(true);

//        jTextArea1 = new JTextArea("");//需要在换行的地方加入\n
//        jTextArea1.setSize(790, 400);
//        jTextArea1.setEnabled(true);
//        jTextArea1.setEditable(false);
//        jTextArea1.setColumns(20);
//        jTextArea1.setRows(5);
//        _mainFrame.add(jTextArea1);

    }

    private void addSettingView(JPanel view)
    {
        JLabel speed=new JLabel("爬取间隔(s):");
        JTextField speedField=new JTextField("1");
        JLabel threadCount=new JLabel("线程数量(1-20):");
        JTextField threadCountField=new JTextField("4");
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
        view.add(panel);
    }

    private void addOperationBtns()
    {
        JPanel bottomPanel=new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.X_AXIS));
        _mainFrame.add(bottomPanel,BorderLayout.PAGE_START);
        addSettingView(bottomPanel);

        JButton startBtn=new JButton("start");
        JButton stopBtn=new JButton("stop");
        JButton checkNetworkBtn=new JButton("check");
        bottomPanel.add(startBtn);
        bottomPanel.add(stopBtn);
        bottomPanel.add(checkNetworkBtn);

    }

    private void addCtrlView()
    {
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
        _mainFrame.add(panel,BorderLayout.PAGE_END);
        JTextArea jta = new JTextArea(18, 15);
        jta.setTabSize(4);
        jta.setForeground(Color.white);
        jta.setFont(new Font("标楷体", Font.BOLD, 14));
        jta.setLineWrap(true);// 激活自动换行功能
        jta.setWrapStyleWord(true);// 激活断行不断字功能
        jta.setBackground(Color.black);
        panel.add(jta);

        JScrollPane scr = new JScrollPane(jta,
             JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
             JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.add(scr);
    }


    private void addMenuUI()
    {
        MenuBar menuBar = new MenuBar();
        Menu item1 = new Menu("update");
        Menu item2 = new Menu("config");
        menuBar.add(item1);
        menuBar.add(item2);
        _mainFrame.setMenuBar(menuBar);
    }

    private void handleAction(ActionEvent e)
    {
        if(e.getActionCommand().equals("start")){
             _service.start();
        }else{
            System.out.print("other\n");
        }
    }
}
