package dao;

import Datathings.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*用户登录类*/
public class UserDao {
    public User login(Connection connection, User user) throws SQLException {
        //将数据库连接和用户类对象传入方法login，抛出数据库相关异常
        //返回一个user类对象的作用是判定这个对象是否为空，从而确定账号密码是否匹配
        //user是用户登录时输入的User类
        User resultUser = null;

        /*预编译sql语句*/

        String sql = "select * from table_user where userName=? and password=?";//？表示占位符

        //返回PreparedStatement的实例

        PreparedStatement psmt = connection.prepareStatement(sql);
        /*填充实例中的占位符*/
        psmt.setString(1, user.getUserName());//user是传进方法参数（用户输入的账户密码）
        psmt.setString(2, user.getPassword());
        /*查询数据库，返回结果集（一行表格）*/
        ResultSet resultSet = psmt.executeQuery();
        /*
        遍历数据库，若查询成功(存在结果集)，则进入if语句内部，将resultUser对象赋值，否则为null
        next()方法本用于滚动指针，这里只滚了一次，也就是结果集是否为空的意思
         */
        if (resultSet.next()) {
            resultUser = new User();
            resultUser.setId(resultSet.getInt("id"));
            resultUser.setUserName(resultSet.getString("userName"));
            resultUser.setPassword(resultSet.getString("password"));
        }
        return resultUser;   //Enter中会通过检查resultuser是否为空来确定登录是否成功
    }
}
