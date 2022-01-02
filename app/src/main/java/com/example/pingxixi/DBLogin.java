package com.example.pingxixi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBLogin {
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://39.108.61.227:3306/android";
    private static final String user = "android";
    private static final String pwd = "androidroot";
    private static String name;
    private static String newPwd;


    private static Connection conn = null;
    private static int checkConnection = 0; //无数据

//    static void Conn(){
//        try{
//            Class.forName(driver);
//            System.out.println("驱动加载失败！");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        try{
//            conn = DriverManager.getConnection(url,user,pwd);
//            System.out.println("数据库连接成功！！");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }

    public static void queryLogin(String username, String ppwd){
        try{
            Class.forName(driver);
            System.out.println("驱动加载失败！");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        try{
            conn = DriverManager.getConnection(url,user,pwd);
            System.out.println("数据库连接成功！！");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        try{
            String sql = "select * from user where username='" + username + "' and password='"+ppwd+"'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println(username+ppwd);
            if (rs.next()){
                checkConnection = 1;
                name = rs.getString("username");
                newPwd = rs.getString("password");
                System.out.print(newPwd);
            }else{
                checkConnection = -1;
            }
            System.out.println(checkConnection);
            rs.close();
            ps.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("数据库连接"+name+newPwd);
        if (conn != null){
            try{
                conn.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    public static int getFlag(){
        return checkConnection;
    }

    public static String getUserName(){
        return name;
    }

    public static String getPwd(){
        return newPwd;
    }
}
