package prometheus.messages.threads;

import prometheus.messages.db.LoadDbConfig;
import prometheus.messages.entity.Pending_messages;
import prometheus.messages.threads.functions.SendMessages;
import prometheus.messages.threads.functions.impl.SendDingMessages;
import prometheus.messages.threads.functions.impl.SendTelMessages;
import prometheus.messages.util.ConfigContainer;
import prometheus.messages.util.GetSendMessage;
import prometheus.messages.util.Loger;


public class SendThread implements Runnable  {

    @Override
    public void run() {
        Pending_messages[] fetch = GetSendMessage.getPending_messages(ConfigContainer.getSendCount());
        if(null != fetch || fetch.length != 0){
            for(Pending_messages f : fetch ){
                analyse(f) ;
            }
        }
    }

    private void analyse(Pending_messages pm){
        String level = pm.getLevel();
        String platform = pm.getPlatform();
        String status;
        if(null == level || "".equals(level) || null == platform || "".equals(platform) ){
            Loger.Info_log.info("[error] " + pm.toString());
        }

        SendMessages sm = SendMessagesFactory(level, platform);

        if(pm.getStatus() == 0){
            status = "恢复";
        }else{
            status = "告警";
        }
        try {
            sm.send(status,pm.getKey_word(),pm.getCreate_time(),pm.getInstance(),pm.getPlatform(),pm.getContent(),
                    pm.getValue(),pm.getExtend(),pm.getApplication(),pm.getJob(),pm.getMonitortype(),pm.getId(),pm.getStatus());
        } catch (Exception e) {
            Loger.Info_log.info("[error] " + pm.toString());
            e.printStackTrace();
        }

        if("disaster".equals(level)){
            SendMessages sm1 = SendMessagesFactory("average", platform);

            if(pm.getStatus() == 0){
                status = "恢复";
            }else{
                status = "告警";
            }
            try {
                sm1.send(status,pm.getKey_word(),pm.getCreate_time(),pm.getInstance(),pm.getPlatform(),pm.getContent(),
                        pm.getValue(),pm.getExtend(),pm.getApplication(),pm.getJob(),pm.getMonitortype(),pm.getId(),pm.getStatus());
            } catch (Exception e) {
                Loger.Info_log.info("[error] " + pm.toString());
                e.printStackTrace();
            }
        }

    }

    private SendMessages SendMessagesFactory(String level, String platform){
        String reciver = "";
        if("disaster".equals(level)){
            reciver = LoadDbConfig.getMapUrl().get(platform + "-Tel").getUrl();
            return new SendTelMessages(reciver);
        }else if("average".equals(level)){
            reciver = LoadDbConfig.getMapUrl().get(platform + "-Ding").getUrl();
            return new SendDingMessages(reciver);
        }else{
            return new SendDingMessages(reciver);
        }
    }

}
