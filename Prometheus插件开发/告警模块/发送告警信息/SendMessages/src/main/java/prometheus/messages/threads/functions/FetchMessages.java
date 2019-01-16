package prometheus.messages.threads.functions;

import prometheus.messages.db.DbFunctions;
import prometheus.messages.entity.Pending_messages;
import prometheus.messages.util.ConfigContainer;
import prometheus.messages.util.Loger;

import java.util.ArrayList;
import java.util.List;


/***
 * 获取待发告警信息
 */
public class FetchMessages {
    private static volatile FetchMessages instance = null; // 唯一实例

    //单例模式控制对象唯一
    public static FetchMessages getInstance() {
        if (instance == null) {
            synchronized (FetchMessages.class) {
                if (instance == null) {
                    instance = new FetchMessages();
                }
            }
        }
        return instance;
    }

    private FetchMessages(){}


    /**
     * 加锁防止多线程同时操作数据重复
     * 获取需要发送的数据
     * 获得消息后更新数据状态
     * 获取和更新必须先后进行不能在获取和修改中添加其它查询更新操作
     * @return List<Pending_messages> 需要发送的信息
     * */
    public synchronized List<Pending_messages> getMessage(){
        List<Pending_messages> result;   //返回的结果集
        try {
            //获取需要发送的数据
            List<Object[]>  list = DbFunctions.select("prometheus.messages.entity.Pending_messages"," flag = -1 limit " + ConfigContainer.getFetchCount());
            if(null == list || list.size()<= 0){
                return null;    //获取数据为空时，返回空
            }

            //将获取的object[]的list转为Pending_messages对象list
            result = changeInfo(list);

            //需要更新的数据id
            List<String> id_list = new ArrayList<String>();
            for(Pending_messages pm : result){
                pm.toString();
                id_list.add(pm.getId());
            }

            //更新告警数据状态flag=-1 改为flag=0
            Boolean flag = DbFunctions.updateInfo("pending_messages", " flag=0 ",id_list);
            if(!flag){
                Loger.Info_log.info("[ERROR] 数据状态update失败");
                return null;
            }
        } catch (Exception e) {
            Loger.Info_log.info("[ERROR] 数据fetch异常");
            return null;
        }
        return result;
    }


    /**
     * 将object数组转为Pending_messages类中的数据
     * @param result List<Object[]>
     * @return List<Pending_messages>
     * */
    private List<Pending_messages> changeInfo(List<Object[]> result ){
        //传入参数为空时，返回null
        if(null == result || result.size() <= 0){
            return null;
        }

        List<Pending_messages> list = new ArrayList<Pending_messages>();        //结果集
        Pending_messages pm;
        for(Object[] o : result){
            if(null == o){
                continue;
            }
            pm = new Pending_messages();
            pm.setId(o[0].toString().trim());
            pm.setApplication(o[1].toString().trim());
            pm.setContent(o[2].toString().trim());
            pm.setJob(o[3].toString().trim());
            pm.setExtend(o[4].toString().trim());
            pm.setKey_word(o[5].toString().trim());
            pm.setMonitortype(o[6].toString().trim());
            pm.setPlatform(o[7].toString().trim());
            pm.setFlag((Integer)o[8]);
            pm.setCreate_time(o[9].toString().trim());
            pm.setStatus((Integer)o[10]);
            pm.setValue(o[11].toString().trim());
            pm.setLevel(o[12].toString().trim());
            pm.setInstance(o[13].toString().trim());
            list.add(pm);
        }
        return list;
    }


    public static void main(String[] args){
        FetchMessages fm = FetchMessages.getInstance();
        List<Pending_messages> l = fm.getMessage();
        for(Pending_messages pm : l){
            System.out.println(pm.toString());
        }
    }
}
