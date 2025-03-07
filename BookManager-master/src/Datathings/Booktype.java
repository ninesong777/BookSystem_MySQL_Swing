package Datathings;

import java.io.Serial;
import java.io.Serializable;
/*定义书籍类型类*/
public class Booktype implements Serializable {
    /*
    显性声明一个序列化ID
     */
    @Serial
    private static final long serialVersionUID = -43707012852599825L;

    private Integer id;

    private String booktypename;

    @Override
    public String toString() {
        return this.booktypename;
    }

    private String booktypedesc;

    public Booktype(String booktypename, String booktypedesc) {
        this.booktypename = booktypename;
        this.booktypedesc = booktypedesc;
    }

    public Booktype() {
        super();
    }

    public Booktype(Integer id, String booktypename, String booktypedesc) {
        this(booktypename, booktypedesc);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBooktypename() {
        return booktypename;
    }

    public void setBooktypename(String booktypename) {
        this.booktypename = booktypename;
    }

    public String getBooktypedesc() {
        return booktypedesc;
    }

    public void setBooktypedesc(String booktypedesc) {
        this.booktypedesc = booktypedesc;
    }

}