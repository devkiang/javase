package service_implement;

import service.CLog;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shikee_app03 on 16/7/14.
 */
public class CLogImpl implements CLog {
    final String path="CrawlerLog.txt"; // 文件和该类在同个目录下
    @Override
    public void info(String msg) {
        try {
            msg=formatMsg("info",msg);
            writeToFile(msg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.print(msg);
        }
    }

    @Override
    public void waring(String msg) {
        try {
            msg=formatMsg("waring",msg);
            writeToFile(msg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.print(msg);
        }
    }

    @Override
    public void error(String msg) {
        try {
            msg=formatMsg("error",msg);
            writeToFile(msg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.print(msg);
        }
    }

    private  String formatMsg(String type,String msg)
    {
        String fMsg="===========\n\n";
        if(type.endsWith("info")){
            fMsg+="类型:【信息】";
        }else if(type.endsWith("waring")){
            fMsg+="类型:【警告】";
        }else if(type.endsWith("error")){
            fMsg+="类型:【错误】";
        }else {
            fMsg+="类型:【信息】";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date date=new Date();
        fMsg+="时间:"+df.format(date)+"\n";
        fMsg+=msg;
        fMsg+="\n\n===========\n\n";
        return fMsg;
    }

    private void writeToFile(String msg) throws IOException {
        try{
            File file =new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWritter = new FileWriter(file.getName(),true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(msg);
            bufferWritter.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String readTxtFile() throws Exception {

        BufferedReader reader = null;
        StringBuffer result=new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            String str = null;
            while ((str = reader.readLine()) != null) {
                result.append(str+"\r\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }
}
