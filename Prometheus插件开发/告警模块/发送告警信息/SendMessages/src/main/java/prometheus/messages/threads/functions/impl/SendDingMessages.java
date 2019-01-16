package prometheus.messages.threads.functions.impl;

import net.sf.json.JSONObject;
import org.apache.http.client.config.RequestConfig;
import prometheus.messages.db.DbFunctions;
import prometheus.messages.threads.functions.SendMessages;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import prometheus.messages.util.Loger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class SendDingMessages implements SendMessages {
    private String reciver;

    public SendDingMessages(String reciver){
        this.reciver = reciver;
    }

    @Override
    public void send(String status, String keyword, String time, String instance, String platform, String message, String value, String extend, String application, String job, String monitortype, String id, int state){

            HttpClient httpclient = HttpClients.createDefault();

            HttpPost httppost = new HttpPost(this.reciver);
            //设置超时时间为15秒
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000).build();//设置请求和传输超时时间
            httppost.setConfig(requestConfig);
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");
            String content = String.format("> 状态：**%s** \\n\\n 告警key：%s \\n\\n 时间：%s \\n\\n 实例：%s \\n\\n 平台：%s \\n\\n 告警信息：%s \\n\\n 告警值：%s \\n\\n 详情：%s",
                    status, keyword, time, instance, platform, message, value, extend);
            String textMsg = "{ \"msgtype\": \"markdown\", \"markdown\": {\"title\": \""+status+"\", \"text\": \""+ content  +"\"}}";
            StringEntity se = new StringEntity(textMsg, "utf-8");
            httppost.setEntity(se);

        try {
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
//                {"errmsg":"ok","errcode":0}
                JSONObject json = JSONObject.fromObject(result);
                int ecode = Integer.valueOf(json.getString("errcode"));
                String explanation = json.getString("errmsg");

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
                Random rand =new Random();

                Object[] info = new Object[17];
                info[0] = "DING" + df1.format(new Date()) + String.valueOf(rand.nextInt(1000000) + Thread.currentThread().getId());
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
                info[12] = platform;
                info[13] = state;
                info[14] = value;
                info[15] = explanation;
                info[16] = id;
                List<Object[]> l = new ArrayList<Object[]>();
                l.add(info);
                DbFunctions.savelog(l);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Loger.Info_log.info("[error] " + time + " " + instance + " " + platform + " " + message + " " + extend);
        }
    }


    @Override
    public Boolean save() {
        return null;
    }

    public static void main(String[] args){
        new SendDingMessages("https://oapi.dingtalk.com/robot/send?access_token=0caa9b4117912da5e1cda8a4f14882884e80fb9dd66403d58ece04d218d8cd7c").send(
                "告警","TEST","2017-01-01 11:11:11","10.10.10.10:8089",
                "local-234","就是测试而已","30","[\"没有啊\"]","","","", "asdfsdaf1", 0);
    }
}
