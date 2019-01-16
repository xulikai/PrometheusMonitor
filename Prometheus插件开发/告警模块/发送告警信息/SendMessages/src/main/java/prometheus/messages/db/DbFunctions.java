package prometheus.messages.db;


import java.lang.reflect.Field;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import prometheus.messages.util.Loger;

public class DbFunctions {

    public static List<Object[]> select(String className, String where){
        String sql = "";
        try {
            sql = "select " + getProperty(className) + " where 1=1 ";
            if("" != where && !"".equals(where)){
                sql = sql + " and " + where;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Loger.Info_log.info("[ERROR] sql语句拼接错误");
            return null;
        }

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        List<Object[]> result = new ArrayList<Object[]>();
        List<Object> list = new ArrayList<Object>();
        DBManagerConnection_Main DBManagerConnection = DBManagerConnection_Main.getInstance();
        try {
            conn = DBManagerConnection.getConnection();
            if (null == conn) {
                Loger.Info_log.info("[ERROR] 数据库连接失败");
                return null;
            }
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            rsmd = rs.getMetaData();
            while(rs.next()){
                for(int i = 1 ; i <= rsmd.getColumnCount(); i++){
                    Object a = new Object();
                    a = rs.getObject(i);
                    if(a == null|| "".equals(a)){
                        list.add((Object)"");
                    }else{
                        list.add(a);
                    }
                }
                if(list == null || list.size() == 0){
                    result = null;
                }else{
                    result.add(list.toArray());
                    list.clear();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DBManagerConnection_Main.closekind(rs , st , conn);
        }
        return result;
    }

    /**
     * 获取查询字段
     * 根据entity名获得entity中的属性，拼出select的查询条件
     * */
    private static String getProperty(String className){
        Class<?> c = null;
        try {
            c = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Field f[] = c.getDeclaredFields();
        StringBuffer sb = new StringBuffer();
        for(Field g : f){
            sb.append(g.getName()).append(",");
        }
        String result = sb.toString().substring(0, sb.toString().length()-1);
        String formName = className.split("\\.")[className.split("\\.").length-1];
        return result + " from " + formName;
    }


    /**
     * 更新已经获取的数据的flag标示
     * 获取到更新中间不能添加任何查询操作
     * @param form 操作表
     * @param set set语句
     * @param id List<String>
     * @return Boolean
     * */
    public static Boolean updateInfo(String form, String set, List<String> id){
        String sql="update " + form + " set " + set + " where id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        DBManagerConnection_Main DBManagerConnection = DBManagerConnection_Main.getInstance();
        Boolean flag = true;
        try {
            conn = DBManagerConnection.getConnection();
            if(null == conn){
                Loger.Info_log.info("[ERROR]数据库连接失败");
                return false;
            }
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(sql);
            for(String i : id){
                stmt.setString(1, i);
                stmt.addBatch();
            }
            stmt.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println(e);
            Loger.Info_log.info("[ERROR]数据更新失败");
            flag = false;
            e.printStackTrace();
        }finally{
            DBManagerConnection_Main.closekind(stmt , conn);
        }
        return flag;
    }

    public static Boolean savelog(List<Object[]> logs){
        String sql="INSERT INTO send_logs(id, create_time, send_time, application, content, job, extend, " +
                "key_word, monitortype, platform, ecode, rcode, receiver, status, value, explanation, pending_id) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        DBManagerConnection_Main DBManagerConnection = DBManagerConnection_Main.getInstance();
        Boolean flag = true;
        try {
            conn = DBManagerConnection.getConnection();
            if(null == conn){
                Loger.Info_log.info("[ERROR]数据库连接失败");
                return false;
            }
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(sql);
            for(Object[] o : logs){
                stmt.setString(1, o[0].toString());
                stmt.setTimestamp(2,Timestamp.valueOf(o[1].toString()));
                stmt.setTimestamp(3,Timestamp.valueOf(o[2].toString()));
                stmt.setString(4, o[3].toString());
                stmt.setString(5, o[4].toString());
                stmt.setString(6, o[5].toString());
                stmt.setString(7, o[6].toString());
                stmt.setString(8, o[7].toString());
                stmt.setString(9, o[8].toString());
                stmt.setString(10, o[9].toString());
                stmt.setInt(11, Integer.valueOf(o[10].toString()));
                stmt.setInt(12, Integer.valueOf(o[11].toString()));
                stmt.setString(13, o[12].toString());
                stmt.setInt(14, Integer.valueOf(o[13].toString()));
                stmt.setString(15, o[14].toString());
                stmt.setString(16, o[15].toString());
                stmt.setString(17, o[16].toString());
                stmt.addBatch();
//                }
            }
            stmt.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println(e);
            Loger.Info_log.info("[ERROR]数据插入失败");
            flag = false;
            e.printStackTrace();
        }finally{
            DBManagerConnection_Main.closekind(stmt , conn);
        }
        return flag;
    }

    public static void main(String[] arg){
        List<Object[]> l = select("prometheus.messages.entity.Alarm_url", "");
        for(Object[] o : l){
            System.out.println();
            for(Object o1 : o){
                System.out.println(o1);
            }
        }
    }
}
