package util;

import java.sql.Connection;
import java.sql.DriverManager;

/*数据库工具类，将与数据库建立的连接封装起来*/
public class ConnectionUtil {
    /*加载驱动，获取四个参数，驱动JDBC_DRIVER、URL、用户名、密码*/
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";//类名
    public static final String URL = "jdbc:mysql://localhost:3306/booksystem?serverTimezone=UTC";
//3306是MySQL默认主机端口名
    public static final String USERNAME = "root";
    public static final String PASSWORD = "123456";

    /*获取数据库连接，抛出异常*/
    public Connection getConnection() throws Exception {
        /*加载驱动*/
        Class.forName(JDBC_DRIVER);  //接受一个完整的类名，并动态的加载初始化它们
        /*创建连接*/
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);//自动输入账户密码和URL配置
        return conn;
    }

    /*关闭数据库连接，释放资源，抛出异常*/
    public void closeConnection(Connection connection) throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}