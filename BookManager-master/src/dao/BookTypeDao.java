package dao;

import Datathings.Booktype;
import util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class BookTypeDao {
    /*添加图书类别*/
    public static int add(Connection connection, Booktype booktype) throws SQLException {
        /*
        预编译sql语句，插入图书类型数据
        */
        String sql = "insert into table_booktype values(null,?,?)";//？占位符
        PreparedStatement psmt = connection.prepareStatement(sql);
        psmt.setString(1, booktype.getBooktypename());
        psmt.setString(2, booktype.getBooktypedesc());
        /*
        更新数据库并返回值，成功则返回1
         */
        return psmt.executeUpdate();
    }

    /*
      查询图书类别集合
     */
    public static ResultSet list(Connection connection, Booktype booktype) throws SQLException {
        /*
        预处理sql语句，查询图书类型数据
         */
        StringBuffer sb = new StringBuffer("select * from table_booktype");
        if (!StringUtil.isEmpty(booktype.getBooktypename())) {
            /*
            使用mysql中的like语句，只需要输入需查找内容的部分字段即可进行查找
             */
            sb.append(" and bookTypeName like '%" + booktype.getBooktypename() + "%'");
        }
        PreparedStatement psmt = connection.prepareStatement(sb.toString().replaceFirst("and", "where"));
        return psmt.executeQuery();//查询
    }

    /*
      删除图书类别
     */
    public static int delete(Connection connection, String id) throws SQLException {
        String sql = "delete from table_booktype where id=?";//预编译sql语句
        PreparedStatement psmt = connection.prepareStatement(sql);
        psmt.setString(1, id);
        return psmt.executeUpdate();//更新
    }

    /*
      修改图书类别
     */
    public static int update(Connection connection, Booktype booktype) throws SQLException {
        /*
        预编译sql语句
         */
        String sql = "update table_booktype set bookTypeName=? ,bookTypeDesc=? where id=?";
        /*
        声明PreparedStatement
         */
        PreparedStatement psmt = connection.prepareStatement(sql);
        psmt.setString(1, booktype.getBooktypename());
        psmt.setString(2, booktype.getBooktypedesc());
        psmt.setInt(3, booktype.getId());
        return psmt.executeUpdate();//更新
    }

    /*
    判断指定类别下是否有图书
     */
    public static boolean existBook(Connection conn, String id) throws SQLException {
        /*
        预编译sql语句
         */
        String sql = "select * from book where bookTypeid=?";
        /*
        声明PreparedStatement
         */
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, id);
        ResultSet rs = psmt.executeQuery();//查询
        /*
        遍历，若存在，则返回true
         */
        return rs.next();
    }
}
