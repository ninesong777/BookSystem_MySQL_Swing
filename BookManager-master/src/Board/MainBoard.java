package Board;

import javax.swing.border.*;
import dao.BookDao;
import dao.BookTypeDao;
import Datathings.Book;
import Datathings.Booktype;
import util.ConnectionUtil;
import util.StringUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class MainBoard extends JFrame {
    public MainBoard() {
        initComponents();
    }

    private void thisWindowClosing(WindowEvent e) {
        System.exit(1);
    }
    //创建”关于“事件处理
    private void menuItem1ActionPerformed(ActionEvent e) {
        About about = new About();
        about.setVisible(true);
    }

    /*
      填充下拉框
     */
    private void fillerItem(String type) {
        Connection conn = null;
        Booktype booktype = null;
        try {
            //建立数据库连接
            conn = dbUtil.getConnection();
            //遍历数据库的book_type表，将图书类型名形成结果集
            ResultSet rs = BookTypeDao.list(conn, new Booktype());
            if ("search".equals(type)) {
                booktype = new Booktype();
                booktype.setBooktypename("请选择...");
                booktype.setId(-1);
                this.booktypeCB.addItem(booktype);
            }
            while (rs.next()) {
                booktype = new Booktype();
                booktype.setBooktypename(rs.getString("bookTypeName"));
                booktype.setId(rs.getInt("id"));
                if ("search".equals(type)) {
                    this.booktypeCB.addItem(booktype);
                } else if ("modify".equals(type)) {
                    this.typeCB.addItem(booktype);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭数据库连接
                dbUtil.closeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*安全退出事件处理*/
    private void menuItem2ActionPerformed(ActionEvent e) {
        //弹出对话框，有三个选项，是、否、取消
        int result = JOptionPane.showConfirmDialog(null, "是否退出系统");
        System.out.println(result);
        if (result == 0) {
            System.exit(1);
        }
    }


    /*图书类别管理事件处理*/
    private void menuItem5ActionPerformed(ActionEvent e) {
    //dispose();
        TypeBoard bookTypeManagerFrm = new TypeBoard();
        bookTypeManagerFrm.setVisible(true);
    }

    /*图书添加事件处理*/
    private void menuItem3ActionPerformed(ActionEvent e) {
        BookAddBoard bookAddFrm = new BookAddBoard();
        bookAddFrm.setVisible(true);
        //this.table.add(bookAddFrm);
    }
    //查询事件处理
    private void button1ActionPerformed(ActionEvent e) {
        String name = this.bookNameTXT.getText();
        String author = this.authorTXT.getText();
        Booktype booktype = (Booktype) this.booktypeCB.getSelectedItem();
        //填充表格
        this.fillTable(new Book(name, author, booktype.getId()));
    }

    /*表格行点击事件处理*/
    private void table1MouseClicked(MouseEvent e) {
        int row = this.table1.getSelectedRow();
        this.idTXT.setText(table1.getValueAt(row, 0).toString());
        this.BookNameTXT.setText(table1.getValueAt(row, 1).toString());
        this.bookauthorTXT.setText(table1.getValueAt(row, 2).toString());
        String sex = table1.getValueAt(row, 3).toString();
        if ("男".equals(sex)) {
            this.man.setSelected(true);
        } else if ("女".equals(sex)) {
            this.woman.setSelected(true);
        }
        this.bookDescTXT.setText(table1.getValueAt(row, 5).toString());
        this.priceTXT.setText(table1.getValueAt(row, 6).toString());
        String bookbype = table1.getValueAt(row, 4).toString();
        int n = this.typeCB.getItemCount();
        for (int i = 0; i < n; i++) {
            Booktype item = (Booktype) this.typeCB.getItemAt(i);
            if (item.getBooktypename().equals(bookbype)) {
                this.typeCB.setSelectedIndex(i);
            }
        }
    }

    /*图书修改事件处理*/
    private void updateButtonActionPerformed(ActionEvent e) {
        String id = idTXT.getText();
        if (StringUtil.isEmpty(id)) {
            //弹出对话框
            JOptionPane.showMessageDialog(null, "请选择要修改的记录");
            return;
        }
        //获取文本框中的信息
        String bookName = this.BookNameTXT.getText();
        String author = this.bookauthorTXT.getText();
        String price = this.priceTXT.getText();
        String bookDesc = this.bookDescTXT.getText();
        /*判断图书名称、作者名、图书价格是否为空*/
        if (StringUtil.isEmpty(bookName)) {
            JOptionPane.showMessageDialog(null, "图书名称不能为空");
            return;
        }
        if (StringUtil.isEmpty(author)) {
            JOptionPane.showMessageDialog(null, "图书作者不能为空");
            return;
        }
        if (StringUtil.isEmpty(price)) {
            JOptionPane.showMessageDialog(null, "图书价格不能为空");
            return;
        }
        try {
            //将price转为float类型
            float pricef = Float.parseFloat(price);
            /*异常处理，判断图书价格是否为数字*/
        } catch (NumberFormatException numberFormatException) {
            //若图书价格不是数字，则弹出提示对话框
            JOptionPane.showMessageDialog(null, "图书价格应为数字");
            return;
        }
        String sex = "";
        if (this.man.isSelected()) {
            sex = "男";
        } else {
            sex = "女";
        }
        Booktype booktype = (Booktype) this.typeCB.getSelectedItem();
        assert booktype != null;
        int booktypeId = booktype.getId();
        //创建book对象
        Book book = new Book(Integer.parseInt(id), bookName, author, sex, Float.parseFloat(price), booktypeId, bookDesc);
        //建立连接
        Connection conn = null;
        try {
            conn = dbUtil.getConnection();
            //调用BookDao类中的update方法修改数据库中图书数据，成功则返回1
            int addNum = BookDao.update(conn, book);
            if (addNum == 1) {
                JOptionPane.showMessageDialog(null, "修改成功");
                resetValue();
                //填充表格
                fillTable(new Book());
            } else {
                JOptionPane.showMessageDialog(null, "修改失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "修改失败");
        } finally {
            try {
                //关闭数据库连接
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /*图书删除事件处理*/
    private void delButtonActionPerformed(ActionEvent e) {
        String id = idTXT.getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "请选择要删除的记录");
            return;
        }
        int n = JOptionPane.showConfirmDialog(null, "确定要删除记录吗？");
        if (n == 0) {
            //建立数据库连接
            Connection connection = null;
            try {
                connection = dbUtil.getConnection();
                //调用BookDao类中的delete方法，删除数据库中的图书数据，成功则返回1
                int deleteNum = BookDao.delete(connection, id);
                if (deleteNum == 1) {
                    JOptionPane.showMessageDialog(null, "删除成功");
                    this.resetValue();
                    //重新填充表格
                    this.fillTable(new Book());
                } else {
                    JOptionPane.showMessageDialog(null, "删除失败");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "删除失败");
            } finally {
                try {
                    //关闭数据库连接
                    dbUtil.closeConnection(connection);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    /*重置方法，将各文本框清空*/
    private void resetValue() {
        this.BookNameTXT.setText("");
        this.bookauthorTXT.setText("");
        this.bookDescTXT.setText("");
        this.priceTXT.setText("");
        this.idTXT.setText("");
        this.idTXT.setText("");
        this.man.setSelected(true);
        this.typeCB.setSelectedIndex(0);
    }

    ConnectionUtil dbUtil = new ConnectionUtil();
    /*组装组件，形成页面*/
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem5 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menu5 = new JMenu();
        menuItem3 = new JMenuItem();
        menu2 = new JMenu();
        menuItem1 = new JMenuItem();
        panel1 = new JPanel();
        label1 = new JLabel();
        bookNameTXT = new JTextField();
        label2 = new JLabel();
        authorTXT = new JTextField();
        label3 = new JLabel();
        booktypeCB = new JComboBox();
        button1 = new JButton();
        label14 = new JLabel();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        panel2 = new JPanel();
        label4 = new JLabel();
        idTXT = new JTextField();
        label5 = new JLabel();
        BookNameTXT = new JTextField();
        label6 = new JLabel();
        priceTXT = new JTextField();
        label7 = new JLabel();
        bookauthorTXT = new JTextField();
        label8 = new JLabel();
        man = new JRadioButton();
        woman = new JRadioButton();
        label9 = new JLabel();
        typeCB = new JComboBox();
        label10 = new JLabel();
        scrollPane2 = new JScrollPane();
        bookDescTXT = new JTextArea();
        updateButton = new JButton();
        delButton = new JButton();
        label11 = new JLabel();
        label13 = new JLabel();
        label12 = new JLabel();

        //======== this ========
        setTitle("Book Manager System");
        setIconImage(new ImageIcon(getClass().getResource("/images/booklogo.png")).getImage());
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setBackground(new Color(0xccccff));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setForeground(new Color(0x9999ff));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== menuBar1 ========
        {
            menuBar1.setBackground(new Color(0xdce2f1));

            //======== menu1 ========
            {
                menu1.setText("\u56fe\u4e66\u7c7b\u522b\u64cd\u4f5c");
                menu1.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));
                menu1.setBackground(new Color(0xe3edcd));
                menu1.setToolTipText("operation");

                //---- menuItem5 ----
                menuItem5.setText("\u56fe\u4e66\u7c7b\u522b\u7ba1\u7406");
                menuItem5.setToolTipText("classification");
                menuItem5.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));
                menuItem5.setBackground(new Color(0xe3edcd));
                menuItem5.addActionListener(e -> menuItem5ActionPerformed(e));
                menu1.add(menuItem5);

                //---- menuItem2 ----
                menuItem2.setText("\u5b89\u5168\u9000\u51fa");
                menuItem2.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));
                menuItem2.setBackground(new Color(0xe3edcd));
                menuItem2.setToolTipText("exit");
                menuItem2.addActionListener(e -> menuItem2ActionPerformed(e));
                menu1.add(menuItem2);
            }
            menuBar1.add(menu1);

            //======== menu5 ========
            {
                menu5.setText("\u56fe\u4e66\u7ba1\u7406");
                menu5.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));
                menu5.setBackground(new Color(0xe3edcd));
                menu5.setToolTipText("manage");

                //---- menuItem3 ----
                menuItem3.setText("\u56fe\u4e66\u6dfb\u52a0");
                menuItem3.setBackground(new Color(0xe3edcd));
                menuItem3.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));
                menuItem3.setToolTipText("addbook");
                menuItem3.addActionListener(e -> menuItem3ActionPerformed(e));
                menu5.add(menuItem3);
            }
            menuBar1.add(menu5);

            //======== menu2 ========
            {
                menu2.setText("\u5173\u4e8e");
                menu2.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));
                menu2.setBackground(new Color(0xe3edcd));

                //---- menuItem1 ----
                menuItem1.setText("\u5f00\u53d1\u8005\u4fe1\u606f");
                menuItem1.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));
                menuItem1.setBackground(new Color(0xe3edcd));
                menuItem1.addActionListener(e -> menuItem1ActionPerformed(e));
                menu2.add(menuItem1);
            }
            menuBar1.add(menu2);
        }
        setJMenuBar(menuBar1);

        //======== panel1 ========
        {
            panel1.setBorder(new TitledBorder("Panda"));
            panel1.setBackground(new Color(0xe2e2f1));
            panel1.setLayout(null);

            //---- label1 ----
            label1.setText("\u4e66\u540d");
            label1.setFont(new Font("\u6977\u4f53", Font.ITALIC, 16));
            label1.setToolTipText("bookname");
            panel1.add(label1);
            label1.setBounds(90, 45, 40, label1.getPreferredSize().height);

            //---- bookNameTXT ----
            bookNameTXT.setFont(new Font("\u534e\u6587\u884c\u6977", Font.PLAIN, 14));
            bookNameTXT.setBackground(new Color(0xdceaf1));
            panel1.add(bookNameTXT);
            bookNameTXT.setBounds(130, 40, 110, bookNameTXT.getPreferredSize().height);

            //---- label2 ----
            label2.setText("\u4f5c\u8005");
            label2.setFont(new Font("\u6977\u4f53", Font.ITALIC, 16));
            label2.setToolTipText("author");
            panel1.add(label2);
            label2.setBounds(270, 45, 40, label2.getPreferredSize().height);

            //---- authorTXT ----
            authorTXT.setFont(new Font("\u534e\u6587\u884c\u6977", Font.PLAIN, 14));
            authorTXT.setBackground(new Color(0xdceaf1));
            panel1.add(authorTXT);
            authorTXT.setBounds(315, 40, 100, 30);

            //---- label3 ----
            label3.setText("\u56fe\u4e66\u7c7b\u522b");
            label3.setFont(new Font("\u6977\u4f53", Font.ITALIC, 16));
            label3.setToolTipText("type");
            panel1.add(label3);
            label3.setBounds(430, 45, 70, 20);

            //---- booktypeCB ----
            booktypeCB.setFont(new Font("\u9ed8\u964c\u6708\u82bd\u4f53", Font.PLAIN, 14));
            booktypeCB.setBackground(new Color(0xdceaf1));
            panel1.add(booktypeCB);
            booktypeCB.setBounds(510, 40, 95, booktypeCB.getPreferredSize().height);

            //---- button1 ----
            button1.setText("\u67e5\u8be2");
            button1.setBackground(new Color(0xccccff));
            button1.setFont(new Font("\u9ed8\u964c\u6708\u82bd\u4f53", Font.ITALIC, 16));
            button1.setToolTipText("inquire");
            button1.setIcon(new ImageIcon(getClass().getResource("/images/inquirelogo.png")));
            button1.addActionListener(e -> button1ActionPerformed(e));
            panel1.add(button1);
            button1.setBounds(645, 30, 105, 38);

            //---- label14 ----
            label14.setIcon(new ImageIcon(getClass().getResource("/images/panda.png")));
            panel1.add(label14);
            label14.setBounds(new Rectangle(new Point(20, 30), label14.getPreferredSize()));

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel1.getComponentCount(); i++) {
                    Rectangle bounds = panel1.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel1.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel1.setMinimumSize(preferredSize);
                panel1.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(panel1);
        panel1.setBounds(0, 0, 830, 110);

        //======== scrollPane1 ========
        {
            scrollPane1.setFont(new Font("\u9ed8\u964c\u6708\u82bd\u4f53", Font.ITALIC, 16));

            //---- table1 ----
            table1.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "\u7f16\u53f7", "\u4e66\u540d", "\u4f5c\u8005", "\u4f5c\u8005\u6027\u522b", "\u56fe\u4e66\u7c7b\u522b", "\u7b80\u4ecb", "\u4ef7\u683c"
                }
            ));
            table1.setBackground(new Color(0xdce2f1));
            table1.setSelectionForeground(new Color(0x000099));
            table1.setFont(new Font("\u534e\u6587\u884c\u6977", Font.PLAIN, 14));
            table1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    table1MouseClicked(e);
                    table1MouseClicked(e);
                }
            });
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(0, 110, 820, 180);

        //======== panel2 ========
        {
            panel2.setBackground(new Color(0xdce2f1));
            panel2.setBorder(new TitledBorder("Book"));
            panel2.setLayout(null);

            //---- label4 ----
            label4.setText("\u7f16\u53f7");
            label4.setFont(new Font("\u6977\u4f53", Font.ITALIC, 16));
            label4.setToolTipText("number");
            panel2.add(label4);
            label4.setBounds(40, 30, 40, 20);

            //---- idTXT ----
            idTXT.setEditable(false);
            idTXT.setBackground(new Color(0xdceaf1));
            idTXT.setFont(new Font("\u9ed8\u964c\u6708\u82bd\u4f53", Font.PLAIN, 14));
            panel2.add(idTXT);
            idTXT.setBounds(90, 25, 100, idTXT.getPreferredSize().height);

            //---- label5 ----
            label5.setText("\u4e66\u540d");
            label5.setFont(new Font("\u6977\u4f53", Font.ITALIC, 16));
            label5.setToolTipText("bookname");
            panel2.add(label5);
            label5.setBounds(280, 30, 40, 20);

            //---- BookNameTXT ----
            BookNameTXT.setFont(new Font("\u534e\u6587\u884c\u6977", Font.PLAIN, 14));
            BookNameTXT.setBackground(new Color(0xdceaf1));
            panel2.add(BookNameTXT);
            BookNameTXT.setBounds(320, 25, 130, BookNameTXT.getPreferredSize().height);

            //---- label6 ----
            label6.setText("\u4ef7\u683c");
            label6.setFont(new Font("\u6977\u4f53", Font.ITALIC, 16));
            label6.setToolTipText("price");
            panel2.add(label6);
            label6.setBounds(545, 30, 40, 20);

            //---- priceTXT ----
            priceTXT.setFont(new Font("\u534e\u6587\u884c\u6977", Font.PLAIN, 14));
            priceTXT.setBackground(new Color(0xdceaf1));
            panel2.add(priceTXT);
            priceTXT.setBounds(585, 25, 140, priceTXT.getPreferredSize().height);

            //---- label7 ----
            label7.setText("\u4f5c\u8005");
            label7.setFont(new Font("\u6977\u4f53", Font.ITALIC, 16));
            label7.setToolTipText("author");
            panel2.add(label7);
            label7.setBounds(40, 80, 37, 20);

            //---- bookauthorTXT ----
            bookauthorTXT.setFont(new Font("\u534e\u6587\u884c\u6977", Font.PLAIN, 14));
            bookauthorTXT.setBackground(new Color(0xdceaf1));
            panel2.add(bookauthorTXT);
            bookauthorTXT.setBounds(90, 75, 100, bookauthorTXT.getPreferredSize().height);

            //---- label8 ----
            label8.setText("\u4f5c\u8005\u6027\u522b");
            label8.setFont(new Font("\u6977\u4f53", Font.ITALIC, 16));
            label8.setToolTipText("sex");
            panel2.add(label8);
            label8.setBounds(270, 80, 70, label8.getPreferredSize().height);

            //---- man ----
            man.setText("\u7537");
            man.setFont(new Font("\u6977\u4f53", Font.ITALIC, 14));
            man.setBackground(new Color(0xdceaf1));
            man.setToolTipText("male");
            panel2.add(man);
            man.setBounds(355, 80, 40, man.getPreferredSize().height);

            //---- woman ----
            woman.setText("\u5973");
            woman.setFont(new Font("\u6977\u4f53", Font.ITALIC, 14));
            woman.setBackground(new Color(0xdceaf1));
            woman.setToolTipText("female");
            panel2.add(woman);
            woman.setBounds(new Rectangle(new Point(400, 80), woman.getPreferredSize()));

            //---- label9 ----
            label9.setText("\u56fe\u4e66\u7c7b\u522b");
            label9.setFont(new Font("\u6977\u4f53", Font.ITALIC, 16));
            label9.setToolTipText("type");
            panel2.add(label9);
            label9.setBounds(525, 75, 74, 20);

            //---- typeCB ----
            typeCB.setFont(new Font("\u9ed8\u964c\u6708\u82bd\u4f53", Font.ITALIC, 14));
            typeCB.setBackground(new Color(0xdceaf1));
            panel2.add(typeCB);
            typeCB.setBounds(600, 70, 115, typeCB.getPreferredSize().height);

            //---- label10 ----
            label10.setText("\u7b80\u4ecb");
            label10.setFont(new Font("\u6977\u4f53", Font.ITALIC, 16));
            label10.setToolTipText("description");
            panel2.add(label10);
            label10.setBounds(60, 160, 35, label10.getPreferredSize().height);

            //======== scrollPane2 ========
            {

                //---- bookDescTXT ----
                bookDescTXT.setLineWrap(true);
                bookDescTXT.setBackground(new Color(0xdce6f1));
                bookDescTXT.setFont(new Font("\u534e\u6587\u884c\u6977", Font.PLAIN, 14));
                scrollPane2.setViewportView(bookDescTXT);
            }
            panel2.add(scrollPane2);
            scrollPane2.setBounds(120, 130, 605, 150);

            //---- updateButton ----
            updateButton.setText("\u4fee\u6539");
            updateButton.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 16));
            updateButton.setBackground(new Color(0xdceaf1));
            updateButton.setToolTipText("modify");
            updateButton.addActionListener(e -> updateButtonActionPerformed(e));
            panel2.add(updateButton);
            updateButton.setBounds(205, 300, 135, updateButton.getPreferredSize().height);

            //---- delButton ----
            delButton.setText("\u5220\u9664");
            delButton.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 16));
            delButton.setBackground(new Color(0xdceaf1));
            delButton.setToolTipText("delete");
            delButton.addActionListener(e -> delButtonActionPerformed(e));
            panel2.add(delButton);
            delButton.setBounds(470, 300, 135, 30);

            //---- label11 ----
            label11.setIcon(new ImageIcon(getClass().getResource("/images/descriptionlogo2.png")));
            label11.setBackground(new Color(0xdce2f1));
            panel2.add(label11);
            label11.setBounds(55, 185, 55, label11.getPreferredSize().height);

            //---- label13 ----
            label13.setIcon(new ImageIcon(getClass().getResource("/images/church.png")));
            panel2.add(label13);
            label13.setBounds(730, 290, 110, 110);

            //---- label12 ----
            label12.setIcon(new ImageIcon(getClass().getResource("/images/tower.png")));
            panel2.add(label12);
            label12.setBounds(new Rectangle(new Point(-5, 295), label12.getPreferredSize()));

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel2.getComponentCount(); i++) {
                    Rectangle bounds = panel2.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel2.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel2.setMinimumSize(preferredSize);
                panel2.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(panel2);
        panel2.setBounds(0, 290, 820, 390);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        setSize(830, 735);
        setLocationRelativeTo(getOwner());

        //---- buttonGroup1 ----
        var buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(man);
        buttonGroup1.add(woman);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        fillerItem("search");
        fillerItem("modify");
        fillTable(new Book());
        this.man.setSelected(true);
    }

    /*填充表格*/
    private void fillTable(Book book) {
        //建立model模型(创建和操作表格数据的类型，外行内列
        DefaultTableModel dtm = (DefaultTableModel) table1.getModel();
        //初始设置行数为0
        dtm.setRowCount(0);
        //建立数据库连接
        Connection conn = null;
        try {
            conn = dbUtil.getConnection();  //dbutil是connectionUtil类
            ResultSet resultSet = BookDao.list(conn, book);  //自己编写的查找方法，返回查找的数据
            while (resultSet.next()) {
                //建立vector集合，将图书各信息存入其中
                Vector all = new Vector();
                all.add(resultSet.getString("id"));
                all.add(resultSet.getString("bookName"));
                all.add(resultSet.getString("author"));
                all.add(resultSet.getString("sex"));
                all.add(resultSet.getString("bookTypeName"));
                all.add(resultSet.getString("bookDesc"));
                all.add(resultSet.getFloat("price"));
                //利用vector集合来给tablemodel添加行
                dtm.addRow(all);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭数据库连接
                dbUtil.closeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem5;
    private JMenuItem menuItem2;
    private JMenu menu5;
    private JMenuItem menuItem3;
    private JMenu menu2;
    private JMenuItem menuItem1;
    private JPanel panel1;
    private JLabel label1;
    private JTextField bookNameTXT;
    private JLabel label2;
    private JTextField authorTXT;
    private JLabel label3;
    private JComboBox booktypeCB;
    private JButton button1;
    private JLabel label14;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JPanel panel2;
    private JLabel label4;
    private JTextField idTXT;
    private JLabel label5;
    private JTextField BookNameTXT;
    private JLabel label6;
    private JTextField priceTXT;
    private JLabel label7;
    private JTextField bookauthorTXT;
    private JLabel label8;
    private JRadioButton man;
    private JRadioButton woman;
    private JLabel label9;
    private JComboBox typeCB;
    private JLabel label10;
    private JScrollPane scrollPane2;
    private JTextArea bookDescTXT;
    private JButton updateButton;
    private JButton delButton;
    private JLabel label11;
    private JLabel label13;
    private JLabel label12;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}