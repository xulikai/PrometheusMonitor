package prometheus.messages.db;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import prometheus.messages.entity.Alarm_url;
import prometheus.messages.util.Loger;


public class LoadDbConfig {
    private static Map<String, Alarm_url> mapUrl = new HashMap<String, Alarm_url>(); //告警方式，包括地址以及电话号码等

    public static void Load(){
        getTableInfo(Alarm_url.class, "");
    }

    /**
     * 根据文件名和特殊查询条件加载数据库中的配置表信息
     * @param c class<?>
     * @param where string
     * */
    private static void getTableInfo(Class<?> c , String where){
        List<Object[]> list = DbFunctions.select(c.getName(), where);
        List<Object> listObject = getEntity(c , list);				//根据class文件名对此类对象进行封装
        if(null == listObject || 0 == listObject.size()){
            return;
        }else{
            setEntity(c , listObject);								//根据class文件名对此类进行封装
        }
    }

    /**
     * 根据class文件名对此类对象进行封装
     * @param d Class<?>
     * @param list List<Object[]>
     * @return List<Object>
     * */
    private static List<Object> getEntity(Class<?> d , List<Object[]> list){
        if(null == list || list.size() == 0){
            return null;
        }
        Class<?> c = null;
        int index = 0 ;
        List<Object> listObject = new ArrayList<Object>();
        for(Object[] o : list){
            index = 0;
            Object k = null;

            try {
                c = Class.forName(d.getName());
                k = c.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            Field f[] = c.getDeclaredFields();
            Method met = null;
            String method = "";
            for(Field g : f){
                method = "set" + g.getName().substring(0, 1).toUpperCase() + g.getName().substring(1, g.getName().length());
                try {
                    if(("java.lang.String").equals(g.getType().getName())){
                        met = c.getMethod(method , String.class);
                        met.invoke(k , o[index].toString().trim());
                    }else{
                        met = c.getMethod(method , int.class);
                        met.invoke(k , Integer.parseInt(o[index].toString().trim()));
                    }
                    index ++;
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            listObject.add(k);
        }
        return listObject;
    }


    /**
     * 根据class文件名对此类进行封装
     * @param d Class<?>
     * @param list List<Object>
     * */
    private static void setEntity(Class<?> d , List<Object> list){
        if(null == list || 0 == list.size()){
            return;
        }
        if(list.get(0) instanceof Alarm_url){
            Alarm_url gwt = null;
            Map<String , Alarm_url> map = new HashMap<String , Alarm_url>();
            for(Object o : list){
                gwt = (Alarm_url)o;
                map.put(gwt.getName() + "-" + gwt.getType(), gwt);
            }
            mapUrl = map;
        } else{
            Loger.Info_log.info("[ERROR] 信息封装错误，错误类LoadDbConfig，错误方法setEntity");
        }
    }


    public static void setMapUrl(Map<String, Alarm_url> mapUrl) {
        LoadDbConfig.mapUrl = mapUrl;
    }

    public static Map<String, Alarm_url> getMapUrl() {
        return mapUrl;
    }

    public static void main(String[] args){
        Load();
    }
}
