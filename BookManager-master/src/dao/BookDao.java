package dao;

import Datathings.Book;
import util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDao {
    /*添加图书*/
    public static int add(Connection connection, Book book) throws SQLException {
        /*
        预编译Sql语句，？为占位符
         */
        String sql = "insert into book values(null,?,?,?,?,?,?)";
        PreparedStatement psmt = connection.prepareStatement(sql);
        psmt.setString(1, book.getBookname());
        psmt.setString(2, book.getAuthor());
        psmt.setString(3, book.getSex());
        psmt.setInt(4, book.getBooktypeid());
        psmt.setString(5, book.getBookdesc());
        psmt.setFloat(6, book.getPrice());
        /*
        更新并返回结果，成功则返回1
         */
        return psmt.executeUpdate();
    }

    /*列出图书*/
    public static ResultSet list(Connection conn, Book book) throws SQLException {
        /*预编译sql语句，进行查找*/
        StringBuffer sb = new StringBuffer("select * from book b,table_booktype bt where b.bookTypeId=bt.id");
        /*
        可以根据书名或作者名或书类型名进行查找
        使用使用mysql中的like条件查询，实现输入部分需查找内容部分字段即可完成查询
         */
        if (!StringUtil.isEmpty(book.getBookname())) {
            sb.append(" and b.bookName like '%" + book.getBookname() + "%'");
        }
        if (!StringUtil.isEmpty(book.getAuthor())) {  //通配符
            sb.append(" and b.author like '%" + book.getAuthor() + "%'");
        }
        if (book.getBooktypeid() != null && book.getBooktypeid() != -1) {
            sb.append(" and b.bookTypeId=" + book.getBooktypeid());
        }
        /*
        声明PreparedStatement语句
         */
        PreparedStatement psmt = conn.prepareStatement(sb.toString());
        /*
        查询，并返回结果集
         */
        return psmt.executeQuery();
    }

    /*
      删除图书信息
     */
    public static int delete(Connection conn, String id) throws SQLException {
        /*
        预编译sql语句，根据id删除图书
         */
        String sql = "delete from book where id=?";
        /*
        声明PreparedStatement语句
         */
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, id);
        /*
        更新，并返回整型值，删除成功则返回1
         */
        return psmt.executeUpdate();
    }

    /*
      修改图书信息
     */
    public static int update(Connection conn, Book book) throws SQLException {
        /*
        预编译sql语句，？为占位符
         */
        String sql = "update book set bookName=?,author=?,sex=?,price=?,bookTypeid=?,bookDesc=? where id=?";
        /*
        声明PreparedStatement语句
         */
        PreparedStatement psmt = conn.prepareStatement(sql);
        /*
        更改各项值
         */
        psmt.setString(1, book.getBookname());
        psmt.setString(2, book.getAuthor());
        psmt.setString(3, book.getSex());
        psmt.setFloat(4, book.getPrice());
        psmt.setInt(5, book.getBooktypeid());
        psmt.setString(6, book.getBookdesc());
        psmt.setInt(7, book.getId());
        /*
        更新，成功则返回1
         */
        return psmt.executeUpdate();
    }

}
