package Board;

import dao.BookDao;
import dao.BookTypeDao;
import Datathings.Book;
import Datathings.Booktype;
import util.ConnectionUtil;
import util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookAddBoard extends JFrame {
    public BookAddBoard() {
        initComponents();
    }

    /*
      添加图书事件处理
     */
    private void button1ActionPerformed(ActionEvent e) {
        String bookName = this.booknameTXT.getText();
        String author = this.bookAuthorTXT.getText();
        String price = this.bookPriceTXT.getText();
        String bookDesc = this.bookDescTXT.getText();
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
            float pricef = Float.parseFloat(price);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(null, "图书价格应为数字");
            return;
        }
        String sex = "";
        if (this.man.isSelected()) {
            sex = "男";
        } else {
            sex = "女";
        }
        Booktype booktype = (Booktype) this.booktypecb.getSelectedItem();
        assert booktype != null;
        int booktypeId = booktype.getId();
        Book book = new Book(bookName, author, sex, Float.parseFloat(price), booktypeId, bookDesc);
        Connection conn = null;
        try {
            conn = dbUtil.getConnection();
            int addNum = BookDao.add(conn, book);
            if (addNum == 1) {
                JOptionPane.showMessageDialog(null, "添加成功");
                resetValue();
            } else {
                JOptionPane.showMessageDialog(null, "添加失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "添加失败");
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /*重置表单*/
    private void resetValue() {
        this.booknameTXT.setText("");
        this.bookAuthorTXT.setText("");
        this.bookDescTXT.setText("");
        this.bookPriceTXT.setText("");
        this.man.setSelected(true);
        if (this.booktypecb.getItemCount() > 0) {
            this.booktypecb.setSelectedIndex(0);
        }
    }
    //建立数据库连接
    private ConnectionUtil dbUtil = new ConnectionUtil();
    /*填充图书类别下拉框*/
    private void fillBookTypeCB() {
        Booktype booktype = null;
        Connection conn = null;
        try {
            conn = dbUtil.getConnection();
            ResultSet rs = BookTypeDao.list(conn, new Booktype());
            while (rs.next()) {
                booktype = new Booktype();
                booktype.setId(rs.getInt("id"));
                booktype.setBooktypename(rs.getString("bookTypeName"));
                this.booktypecb.addItem(booktype);
            }
        } catch (Exception e) {

        } finally {

        }
    }

    private void button2ActionPerformed(ActionEvent e) {
        this.resetValue();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        booknameTXT = new JTextField();
        label2 = new JLabel();
        bookAuthorTXT = new JTextField();
        label3 = new JLabel();
        man = new JRadioButton();
        woman = new JRadioButton();
        label7 = new JLabel();
        label8 = new JLabel();
        bookPriceTXT = new JTextField();
        scrollPane1 = new JScrollPane();
        bookDescTXT = new JTextArea();
        label9 = new JLabel();
        button1 = new JButton();
        button2 = new JButton();
        booktypecb = new JComboBox();
        label4 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        label10 = new JLabel();
        label11 = new JLabel();
        label12 = new JLabel();
        label13 = new JLabel();
        label14 = new JLabel();
        label15 = new JLabel();
        label16 = new JLabel();
        label17 = new JLabel();
        label18 = new JLabel();

        //======== this ========
        setTitle("Book Add");
        setIconImage(new ImageIcon(getClass().getResource("/images/booklogo.png")).getImage());
        setBackground(new Color(0x93b5cf));
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("\u56fe\u4e66\u540d\u79f0");
        label1.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(30, 30), label1.getPreferredSize()));

        //---- booknameTXT ----
        booknameTXT.setFont(new Font("\u534e\u6587\u884c\u6977", Font.PLAIN, 16));
        contentPane.add(booknameTXT);
        booknameTXT.setBounds(105, 25, 100, booknameTXT.getPreferredSize().height);

        //---- label2 ----
        label2.setText("\u56fe\u4e66\u4f5c\u8005");
        label2.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
        contentPane.add(label2);
        label2.setBounds(230, 30, 80, label2.getPreferredSize().height);

        //---- bookAuthorTXT ----
        bookAuthorTXT.setFont(new Font("\u534e\u6587\u884c\u6977", Font.PLAIN, 16));
        contentPane.add(bookAuthorTXT);
        bookAuthorTXT.setBounds(310, 25, 130, bookAuthorTXT.getPreferredSize().height);

        //---- label3 ----
        label3.setText("\u4f5c\u8005\u6027\u522b");
        label3.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
        contentPane.add(label3);
        label3.setBounds(new Rectangle(new Point(30, 75), label3.getPreferredSize()));

        //---- man ----
        man.setText("\u7537");
        man.setFont(new Font("\u6977\u4f53", Font.PLAIN, 16));
        contentPane.add(man);
        man.setBounds(105, 75, man.getPreferredSize().width, 20);

        //---- woman ----
        woman.setText("\u5973");
        woman.setFont(new Font("\u6977\u4f53", Font.PLAIN, 16));
        contentPane.add(woman);
        woman.setBounds(150, 75, woman.getPreferredSize().width, 20);

        //---- label7 ----
        label7.setText("\u56fe\u4e66\u7c7b\u522b");
        label7.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
        contentPane.add(label7);
        label7.setBounds(new Rectangle(new Point(30, 125), label7.getPreferredSize()));

        //---- label8 ----
        label8.setText("\u56fe\u4e66\u4ef7\u683c");
        label8.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
        contentPane.add(label8);
        label8.setBounds(new Rectangle(new Point(230, 80), label8.getPreferredSize()));

        //---- bookPriceTXT ----
        bookPriceTXT.setFont(new Font("\u534e\u6587\u884c\u6977", Font.PLAIN, 16));
        contentPane.add(bookPriceTXT);
        bookPriceTXT.setBounds(310, 75, 130, bookPriceTXT.getPreferredSize().height);

        //======== scrollPane1 ========
        {

            //---- bookDescTXT ----
            bookDescTXT.setLineWrap(true);
            bookDescTXT.setFont(new Font("\u534e\u6587\u884c\u6977", Font.PLAIN, 16));
            scrollPane1.setViewportView(bookDescTXT);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(90, 175, 350, 145);

        //---- label9 ----
        label9.setText("\u7b80\u4ecb");
        label9.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
        contentPane.add(label9);
        label9.setBounds(new Rectangle(new Point(35, 185), label9.getPreferredSize()));

        //---- button1 ----
        button1.setText("\u6dfb\u52a0");
        button1.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 20));
        button1.addActionListener(e -> button1ActionPerformed(e));
        contentPane.add(button1);
        button1.setBounds(155, 335, 85, 35);

        //---- button2 ----
        button2.setText("\u91cd\u7f6e");
        button2.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 20));
        button2.addActionListener(e -> button2ActionPerformed(e));
        contentPane.add(button2);
        button2.setBounds(275, 335, 85, 35);

        //---- booktypecb ----
        booktypecb.setFont(new Font("\u534e\u6587\u884c\u6977", Font.PLAIN, 16));
        contentPane.add(booktypecb);
        booktypecb.setBounds(105, 120, 335, booktypecb.getPreferredSize().height);

        //---- label4 ----
        label4.setIcon(new ImageIcon(getClass().getResource("/images/description4.png")));
        contentPane.add(label4);
        label4.setBounds(new Rectangle(new Point(30, 210), label4.getPreferredSize()));

        //---- label5 ----
        label5.setIcon(new ImageIcon(getClass().getResource("/images/Watermelon_Monochromatic.png")));
        contentPane.add(label5);
        label5.setBounds(0, 320, 125, 120);

        //---- label6 ----
        label6.setIcon(new ImageIcon(getClass().getResource("/images/watermelon.png")));
        contentPane.add(label6);
        label6.setBounds(new Rectangle(new Point(415, 365), label6.getPreferredSize()));

        //---- label10 ----
        label10.setIcon(new ImageIcon(getClass().getResource("/images/drink.png")));
        contentPane.add(label10);
        label10.setBounds(new Rectangle(new Point(345, 365), label10.getPreferredSize()));

        //---- label11 ----
        label11.setIcon(new ImageIcon(getClass().getResource("/images/airconditioner.png")));
        contentPane.add(label11);
        label11.setBounds(435, 0, 45, label11.getPreferredSize().height);

        //---- label12 ----
        label12.setIcon(new ImageIcon(getClass().getResource("/images/sun.png")));
        contentPane.add(label12);
        label12.setBounds(new Rectangle(new Point(0, 270), label12.getPreferredSize()));

        //---- label13 ----
        label13.setIcon(new ImageIcon(getClass().getResource("/images/sunglass (2).png")));
        contentPane.add(label13);
        label13.setBounds(new Rectangle(new Point(130, 380), label13.getPreferredSize()));

        //---- label14 ----
        label14.setIcon(new ImageIcon(getClass().getResource("/images/icecream.png")));
        contentPane.add(label14);
        label14.setBounds(new Rectangle(new Point(195, 375), label14.getPreferredSize()));

        //---- label15 ----
        label15.setIcon(new ImageIcon(getClass().getResource("/images/cocunut.png")));
        contentPane.add(label15);
        label15.setBounds(265, 375, 45, 48);

        //---- label16 ----
        label16.setIcon(new ImageIcon(getClass().getResource("/images/lotus (2).png")));
        contentPane.add(label16);
        label16.setBounds(new Rectangle(new Point(445, 195), label16.getPreferredSize()));

        //---- label17 ----
        label17.setIcon(new ImageIcon(getClass().getResource("/images/leaf.png")));
        contentPane.add(label17);
        label17.setBounds(new Rectangle(new Point(445, 260), label17.getPreferredSize()));

        //---- label18 ----
        label18.setIcon(new ImageIcon(getClass().getResource("/images/tree.png")));
        contentPane.add(label18);
        label18.setBounds(new Rectangle(new Point(445, 325), label18.getPreferredSize()));

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
        setSize(490, 460);
        setLocationRelativeTo(getOwner());

        //---- buttonGroup1 ----
        var buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(man);
        buttonGroup1.add(woman);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        fillBookTypeCB();
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JTextField booknameTXT;
    private JLabel label2;
    private JTextField bookAuthorTXT;
    private JLabel label3;
    private JRadioButton man;
    private JRadioButton woman;
    private JLabel label7;
    private JLabel label8;
    private JTextField bookPriceTXT;
    private JScrollPane scrollPane1;
    private JTextArea bookDescTXT;
    private JLabel label9;
    private JButton button1;
    private JButton button2;
    private JComboBox booktypecb;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label10;
    private JLabel label11;
    private JLabel label12;
    private JLabel label13;
    private JLabel label14;
    private JLabel label15;
    private JLabel label16;
    private JLabel label17;
    private JLabel label18;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
