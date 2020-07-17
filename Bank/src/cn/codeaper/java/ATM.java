package cn.codeaper.java;

import cn.codeaper.util.JDBCInit;
import cn.codeaper.util.JDBCOpt;

import javax.swing.text.Document;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * @description: 柜员机类
 * @author: qzl
 * @created: 2020/07/15 19:13
 */

public class ATM {
    private static boolean isLogin = false;
    private static String ATM_user = null;
    private static String ATM_pwd = null;

    //主菜单
    public static void display(){
        if(!isLogin){
            System.out.println("-----------------------欢迎使用ATM柜员机系统---------------------");
            System.out.println();
            System.out.println("                        1.插卡                                  ");
            System.out.println("                        2.注册新卡                              ");
            System.out.println();
            System.out.println();
            System.out.println("                        请选择功能[1-3]：                        ");
            System.out.println();
            System.out.println("-----------------------------------------------------------------");
            int choose_1;
            Scanner in = new Scanner(System.in);
            choose_1 = in.nextInt();
            switch (choose_1){
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;

            }
        }
        else {
            System.out.println("-----------------------欢迎使用ATM柜员机系统---------------------");
            System.out.println();
            System.out.println("                        1.存款                                  ");
            System.out.println("                        2.取款                                   ");
            System.out.println("                        3.余额查询                               ");
            System.out.println("                        4.转账                                   ");
            System.out.println("                        5.转账记录查询                            ");
            System.out.println("                        6.退卡                                  ");
            System.out.println();
            System.out.println("                        请选择功能[1-3]：                         ");
            System.out.println();
            System.out.println("-----------------------------------------------------------------");
            int choose_1;
            Scanner in = new Scanner(System.in);
            choose_1 = in.nextInt();
            switch (choose_1){
                case 1:
                    deposit();
                    break;
                case 2:
                    withdrawals();
                    break;
                case 3:
                    findMoney();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    findTransfer();
                    break;
                case 6:
                    System.out.println("退卡中...");
                    ATM_user = null;
                    ATM_pwd = null;
                    isLogin = false;
                    System.out.println("退卡成功，欢迎下次使用！");
                    break;
            }
        }
    }
    //登录
    public static void login(){
        System.out.println("请输入卡号：");
        Scanner in = new Scanner(System.in);
        String user = in.next();
        System.out.println("请输入密码：");
        String pwd = in.next();
        System.out.println("登录中...");
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            //获取sql执行对象
            con = JDBCInit.init();
            //定义sql语句
            String sql = "select * from login where card_id = ? and password = ?";
            //获取数据库执行对象
            statement = con.prepareStatement(sql);
            //替换？
            statement.setString(1,user);
            statement.setString(2,pwd);

            //执行sql，获取登录结果
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                System.out.println("登录成功！");
                ATM_user = user;
                ATM_pwd = pwd;
                isLogin = true;
            }
            else {
                System.out.println("登录失败！");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCInit.close(resultSet,statement,con);
            display();
        }

    }
    //注册
    public static void register(){
        Scanner in = new Scanner(System.in);
        User user = new User();
        System.out.println("请输入姓名：");
        user.setName(in.next());
        System.out.println("请输入性别：");
        user.setSex(in.next());
        System.out.println("请输入电话：");
        user.setPhone(in.next());
        System.out.println("请输入密码[6位]：");
        String pwd = in.next();
        user.setMoney(0);
        user.setStart_card(LocalDate.now());
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //获取数据库连接对象
            con = JDBCInit.init();

            //定义sql
            String sql_select = "select max(card_id) from login";
            statement = con.createStatement();
            //执行sql
            //查询银行卡号最大值
            resultSet = statement.executeQuery(sql_select);
            resultSet.next();
            int id = resultSet.getInt(1);
            user.setId(id+1);

            //注册卡号，写入数据库
            String sql_login = "insert into login values("+user.getId() +","+pwd+")";

            statement.executeUpdate(sql_login);

            String sql_acc = "insert into account values("
                    +user.getId()+",'"
                    + user.getName()+"','"
                    +user.getSex()+"','"
                    +user.getPhone()+"',"
                    +user.getMoney()+",+'"
                    +user.getStart_card()+"' )";
            statement.executeUpdate(sql_acc);

            System.out.println("尊敬的"+user.getName()+"(先生/女士),您的银行卡注册成功!");
            System.out.println("卡号: "+user.getId()+" ,开户日期："+user.getStart_card());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCInit.close(resultSet,statement,con);
        }
    }
    //存款
    public static void deposit(){
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Scanner in = new Scanner(System.in);
        while(true){
            System.out.println("请输入密码：");
            String pwd = in.next();
            if(!pwd.equals(ATM_pwd)){
                System.out.println("密码错误！");
            }
            else break;
        }
        //输入存款金额
        double money_add = 0;  //存款金额——d
        int data_money = 0;   //查询余额
        int money_last = 0;   //最终金额
        while(true){
            System.out.println("请输入存款金额.");
            money_add = in.nextDouble();
            if(money_add <= 0){
                System.out.println("金额不合法，请重新输入...");
            }else {
                //System.out.println("money_1 = "+money_1);
                //money_last = new Double(money_add_d).intValue();
                //System.out.println("money = "+money);
                break;
            }
        }
        try {
            //获取数据库连接对象
            con = JDBCInit.init();
            //定义sql查询余额
            String sql_select = "select money from account where id = ?";
            //获取数据库执行对象
            statement = con.prepareStatement(sql_select);
            //替代?
            statement.setString(1,ATM_user);
            //查询余额
            resultSet = statement.executeQuery();

            if(resultSet.next()){
                data_money = resultSet.getInt("money");
            }

            money_last = data_money + new Double(money_add).intValue()*100;

            //定义sql，存入余额
            String sql = "update account set money = ? where id = ?";
            //获取数据库执行对象
            statement = con.prepareStatement(sql);
            //替换?
            statement.setString(1,String.valueOf(money_last));
            statement.setString(2,ATM_user);
            //执行sql，更新余额
            int count = statement.executeUpdate();
            if(count > 0){
                System.out.println("存款成功！，存入金额额"+money_add+"元");
            }
            else {
                System.out.println("存款失败！");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCInit.close(resultSet,statement,con);
        }
    }
    //取款
    public static void  withdrawals(){
        Scanner in = new Scanner(System.in);
        while(true){
            System.out.println("请输入密码：");
            String pwd = in.next();
            if(!pwd.equals(ATM_pwd)){
                System.out.println("密码错误！");
            }
            else break;
        }
        double money_sub = 0;  //取款金额——d
        int data_money = 0;   //查询余额
        int money_last = 0;   //最终金额
        System.out.println("请输入取款金额：");
        money_sub = in.nextDouble();
        /*
        * 连接数据库，查询余额是否充足
        * */
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            //获取数据库连接对象
            con = JDBCInit.init();
            //定义sql查询余额
            String sql_select = "select money from account where id = ?";
            //获取数据库执行对象
            statement = con.prepareStatement(sql_select);
            //替代?
            statement.setString(1,ATM_user);
            //查询余额
            resultSet = statement.executeQuery();

            if(resultSet.next()){
                data_money = resultSet.getInt("money");
                if(money_sub*100 <= data_money){
                    System.out.println("取款中...");
                    money_last = data_money - new Double(money_sub).intValue()*100;
                    //定义取款sql
                    String sql = "update account set money = ? where id = ?";
                    //获取sql执行对象
                    statement = con.prepareStatement(sql);
                    //替换？
                    statement.setString(1,String.valueOf(money_last));
                    statement.setString(2,ATM_user);
                    //执行sql，修改余额
                    int count = statement.executeUpdate();
                    if(count > 0){
                        System.out.println("取款成功！金额"+money_sub+"元");
                    }
                    else {
                        System.out.println("取款失败！");
                    }
                }
                else {
                    System.out.println("余额不足，取款失败！");
                }
            }
            else {
                System.out.println("查询余额失败！");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCInit.close(resultSet,statement,con);
        }
    }

    //查询余额
    public static void findMoney(){
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            //获取数据库连接对象
            con = JDBCInit.init();

            //定义sql语句
            String sql = "select * from account where id = ?";
            //获取sql执行对象
            statement = con.prepareStatement(sql);
            //替换sql
            statement.setString(1,ATM_user);
            //获取结果
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                double money = resultSet.getInt("money");
                System.out.println("您的余额为"+money*0.01+"元");
            }
            else {
                System.out.println("查询余额失败！");
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCInit.close(resultSet,statement,con);
        }
    }

    //转账
    public static void transfer(){
        //输入密码
        Scanner in = new Scanner(System.in);
        while(true){
            System.out.println("请输入密码：");
            String pwd = in.next();
            if(!pwd.equals(ATM_pwd)){
                System.out.println("密码错误！");
            }else break;
        }
        //输入转账账号
        double money_sub = 0;  //转账金额——d
        int data_My_money = 0;   //查询我的余额
        int data_o_money = 0;   //查询对方余额
        int my_money_last = 0;   //我的最终金额
        int he_money_last = 0;  //对方最终余额
        System.out.println("请输对方账号");
        String other_id = in.next();
        while(true){
            System.out.println("请输入转账金额：");
            money_sub = in.nextDouble()*100;
            if(money_sub <= 0){
                System.out.println("输入不合法！");
            }
            else break;
        }
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            con = JDBCInit.init();
            //查询对方账号是否存在
            //定义sql
            String sql_id = "select * from account where id = ?";
            statement = con.prepareStatement(sql_id);
            statement.setString(1,other_id);
            resultSet = statement.executeQuery();
            if(!resultSet.next()){
                System.out.println("转账失败，对方账户不存在！");
            }else {
                //查询金额是否充足
                String sql_money = "select * from account where id = ";
                statement.setString(1,ATM_user);
                resultSet = statement.executeQuery();

                if(resultSet.next()){
                    data_My_money =resultSet.getInt("money");
                    if(money_sub > data_My_money){
                        System.out.println("转账失败，余额不足.");
                    }
                    else {
                        try{
                            //我方扣款
                            con.setAutoCommit(false);  //开启事务，关闭自动提交
                            String sql_sub = "update account set money = ? where id = ?";
                            statement = con.prepareStatement(sql_sub);
                            my_money_last = data_My_money - new Double(money_sub).intValue();
                            statement.setString(1,String.valueOf(my_money_last));
                            statement.setString(2,ATM_user);
                            int count = statement.executeUpdate();
                            if(count > 0){
                                //收款方收款
                                //查询对方余额
                                String sql_other = "select * from account where id = ?";
                                statement =con.prepareStatement(sql_other);
                                statement.setString(1,other_id);
                                resultSet = statement.executeQuery();
                                resultSet.next();
                                data_o_money = resultSet.getInt("money");

                                String sql_add = "update account set money = ? where id = ?";
                                statement = con.prepareStatement(sql_add);
                                he_money_last = data_o_money + new Double(money_sub).intValue();
                                statement.setString(1,String.valueOf(he_money_last));
                                statement.setString(2,other_id);
                                int count_2 = statement.executeUpdate();
                                if(count_2 > 0){
                                    System.out.println("转账成功，对方账户："+other_id+" ，我方账户："+ATM_user+" ,金额："+money_sub*0.01+"元.");

                                    //添加转账记录
                                    String sql_desc_1 = "insert into opt values (?,?,?,?,?)";
                                    statement = con.prepareStatement(sql_desc_1);
                                    java.sql.Date date = Date.valueOf(LocalDate.now());
                                    String desc1 = "转账给"+other_id+" "+money_sub*0.01+"元";
                                    statement.setString(1,ATM_user);
                                    statement.setString(2,other_id);
                                    statement.setString(3,String.valueOf(date));
                                    statement.setString(4,"-"+money_sub);
                                    statement.setString(5,desc1);
                                    statement.executeUpdate();

                                    String sql_desc_2 = "insert into opt values (?,?,?,?,?)";
                                    statement = con.prepareStatement(sql_desc_2);
                                    String desc2 = "收到来自"+ATM_user+" 的转账"+money_sub*0.01+"元";
                                    statement.setString(1,other_id);
                                    statement.setString(2,ATM_user);
                                    statement.setString(3,String.valueOf(date));
                                    statement.setString(4,"+"+money_sub);
                                    statement.setString(5,desc2);
                                    statement.executeUpdate();

                                    con.commit();  //提交事务
                                }else con.rollback();
                            }
                            else con.rollback();
                        }catch (Exception e){
                            //出现异常，事务回滚
                            con.rollback();
                            System.out.println("转账失败！");
                            e.printStackTrace();
                        }
                    }
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCInit.close(resultSet,statement,con);
        }

    }

    //转账记录查询
    public static void findTransfer(){
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            con = JDBCInit.init();
            String sql = "select * from opt where id = ? or other_id = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1,ATM_user);
            statement.setString(2,ATM_user);
            resultSet = statement.executeQuery();

            System.out.println("卡号  |  收款卡号  |  转账日期  |  转账金额  |          备注          |");
            System.out.println("-------------------------------------------------------------------");
            while(resultSet.next()){
                    System.out.println(resultSet.getInt("id")+" |  "
                            +resultSet.getInt("other_id")+"    | "
                            +resultSet.getString("tranfer_date")+"|   "
                            +resultSet.getString("money")+"    |    "
                            +resultSet.getString("desc_text"));
            }
            System.out.println("-------------------------------------------------------------------");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCInit.close(resultSet,statement,con);
        }

    }

    //字符串转int
    public static int stringToInt(String string){
        int j = 0;

        String str = string.substring(0, string.indexOf(".")) + string.substring(string.indexOf(".") + 1);

        int intgeo = Integer.parseInt(str);

        return intgeo;
    }
}
