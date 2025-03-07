package Datathings;

import java.io.Serializable;

/*定义书籍类*/
public class Book implements Serializable {
    /*显性声明一个序列化ID*/
    private static final long serialVersionUID = 920269858498853741L;
    
    private Integer id;
    
    private String bookname;
    
    private String author;
    
    private String sex;
    
    private Float price;
    
    private Integer booktypeid;
    
    private String bookdesc;

    public Book(String bookname, String author, String sex, float price, Integer booktypeid, String bookdesc) {
        this.bookname = bookname;
        this.author = author;
        this.sex = sex;
        this.price = price;
        this.booktypeid = booktypeid;
        this.bookdesc = bookdesc;
    }

    public Book(String bookname, String author, Integer booktypeid) {
        this.bookname = bookname;
        this.author = author;
        this.booktypeid = booktypeid;
    }

    public Book(Integer id, String bookname, String author, String sex, Float price, Integer booktypeid, String bookdesc) {
        this.id = id;
        this.bookname = bookname;
        this.author = author;
        this.sex = sex;
        this.price = price;
        this.booktypeid = booktypeid;
        this.bookdesc = bookdesc;
    }

    public Book() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getBooktypeid() {
        return booktypeid;
    }

    public void setBooktypeid(Integer booktypeid) {
        this.booktypeid = booktypeid;
    }

    public String getBookdesc() {
        return bookdesc;
    }

    public void setBookdesc(String bookdesc) {
        this.bookdesc = bookdesc;
    }

}