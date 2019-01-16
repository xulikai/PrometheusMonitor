package prometheus.messages.threads.functions.impl;

import net.sf.json.JSONObject;
import prometheus.messages.db.DbFunctions;
import prometheus.messages.threads.functions.SendMessages;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SendTelMessages implements SendMessages {
    private String reciver;

    public SendTelMessages(String reciver){
        this.reciver = reciver;
    }

    @Override
    public void send(String status, String keyword, String time, String instance, String platform, String message, String value, String extend, String application, String job, String monitortype, String id, int state) {
        //恢复信息发送电话
        if(0 == state){
            return;
        }
        String str = "";
        String URL = "http://api.clink.cn/interface/entrance/OpenInterfaceEntrance";
        for(String tel : reciver.split(",")){
            if("".equals(tel) || null == tel){
                continue;
            }
            str = "interfaceType=webCall&enterpriseId=3001822&userName=alert&pwd=287178f04d1be58dd1f30422933b5744&customerNumber=" + tel + "&remoteIp=127.0.0.1&cookie=1&sync=0&paramTypes=2&ivrId=17781&paramNames=alert_content&alert_content=" + message;
            String response = postURL(str, URL);
            JSONObject json = JSONObject.fromObject(response);
            int ecode = Integer.valueOf(json.getString("result"));
            String explanation = json.getString("description");

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            Random rand =new Random();

            Object[] info = new Object[17];
            info[0] = "TEL" + df1.format(new Date()) + String.valueOf(rand.nextInt(1000000) + Thread.currentThread().getId());
            info[1] = time;
            info[2] = df.format(new Date());
            info[3] = application;
            info[4] = message;
            info[5] = job;
            info[6] = extend;
            info[7] = keyword;
            info[8] = monitortype;
            info[9] = platform;
            info[10] = ecode;
            info[11] = -1;
            info[12] = tel;
            info[13] = state;
            info[14] = value;
            info[15] = explanation;
            info[16] = id;
            List<Object[]> l = new ArrayList<Object[]>();
            l.add(info);
            DbFunctions.savelog(l);
        }
    }

    /**发送url串 */
    /**
     * @param commString 需要发送的url参数串
     * @param address 需要发送的url地址
     * @return rec_string 国都返回的自定义格式的串
     * @catch Exception
     */
    public static String postURL(String commString, String address) {
        String rec_string = "";
        URL url = null;
        HttpURLConnection urlConn = null;
        try {
            /* 得到url地址的URL类 */
            url = new URL(address);
            /* 获得打开需要发送的url连接 */
            urlConn = (HttpURLConnection) url.openConnection();
            /* 设置连接超时时间 */
            urlConn.setConnectTimeout(30000);
            /* 设置读取响应超时时间 */
            urlConn.setReadTimeout(30000);
            /* 设置post发送方式 */
            urlConn.setRequestMethod("GET");
            /* 发送commString */
            urlConn.setDoOutput(true);
            OutputStream out = urlConn.getOutputStream();
            out.write(commString.getBytes());
            out.flush();
            out.close();
            /* 发送完毕 获取返回流，解析流数据 */
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    urlConn.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            int ch;
            while ((ch = rd.read()) > -1) {
                sb.append((char) ch);
            }
            rec_string = sb.toString().trim();
            /* 解析完毕关闭输入流 */
            rd.close();
        } catch (Exception e) {
            /* 异常处理 */
            rec_string = "-107";
        } finally {
            /* 关闭URL连接 */
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
        /* 返回响应内容 */
        return rec_string;
    }


    @Override
    public Boolean save() {
        return null;
    }

    public static void main(String[] args){
        new SendTelMessages("13439678247,18311251176,17801016223,15117956265,").send(
                "告警","TEST","2017-01-01 11:11:11","10.10.10.10:8089",
                "local-234","只是测试而已","30","没有啊","","","", "asdfsdaf1", 0);
    }
}
