package cn.codeaper.util;

import java.sql.*;

/**
 * @description: 增加数据至数据库
 * @author: qzl
 * @created: 2020/07/15 18:36
 */

public class JDBCOpt {
        private static Connection con = null;
        private static Statement statement = null;
        private static PreparedStatement pstat = null;
        //操作数据
    public static boolean DDL(int opt,String table, String values){
        try {
            con = JDBCInit.init();
            //定义sql
            String sql = null;
            if(opt == 1) sql = "insert into "+table+" values "+values+";";
            if(opt == 2) sql = "update "+table+" set "+values+";";
            if(opt == 3) sql = "delete from "+table+values;
            //获取执行sql对象
            Statement statement = con.createStatement();
            //执行sql
            return statement.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCInit.close(statement,con);
        }
        return false;
    }
    public static ResultSet select(String table, String condtion){
        ResultSet resultSet = null;
        try {
            con = JDBCInit.init();
            //定义sql
            String sql = "select * from login where card_id = ? and password = ?;";
            //获取数据库执行对象
            pstat = con.prepareStatement(sql);
            //给问号赋值
            pstat.setString(1,table);
            pstat.setString(2,condtion);
            //执行sql
            resultSet = pstat.executeQuery();
            resultSet.last();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {

            JDBCInit.close(resultSet,pstat,con);
        }
        return null;
    }

    public static void main(String[] args) {

    }
}
