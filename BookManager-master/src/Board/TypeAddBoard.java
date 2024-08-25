package Board;

import dao.BookTypeDao;
import Datathings.Booktype;
import util.ConnectionUtil;
import util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

public class TypeAddBoard extends JFrame {
    public TypeAddBoard() {
        initComponents();
    }

    private void button2ActionPerformed(ActionEvent e) {
        this.resetValue();
    }

    /*清空输入框功能*/
    private void resetValue() {
        this.bookTypeNameTxt.setText("");
        this.bookTypeDescTxt.setText("");
    }

    /*图书类别添加事件处理*/
    private void button1ActionPerformed(ActionEvent e) {
        String bookTypeName = this.bookTypeNameTxt.getText();
        String bookTypeDesc = this.bookTypeDescTxt.getText();
        if (StringUtil.isEmpty(bookTypeName)) {
            JOptionPane.showMessageDialog(null, "图书类别名称不能为空");
            return;
        }
        Booktype booktype = new Booktype(bookTypeName, bookTypeDesc);
        Connection conn = null;
        try {
            //建立数据库连接
            conn = dbUtil.getConnection();
            /*调用BookTypeDao中的add方法，添加图书类型数据*/
            int n = BookTypeDao.add(conn, booktype);
            if (n == 1) {
                JOptionPane.showMessageDialog(null, "图书类别添加成功");
                this.resetValue();
            } else {
                JOptionPane.showMessageDialog(null, "图书类别添加失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "图书类别添加失败");
        } finally {
            try {
                //关闭数据库连接
                dbUtil.closeConnection(conn);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    ConnectionUtil dbUtil = new ConnectionUtil();

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label3 = new JLabel();
        label4 = new JLabel();
        bookTypeNameTxt = new JTextField();
        scrollPane1 = new JScrollPane();
        bookTypeDescTxt = new JTextArea();
        button1 = new JButton();
        button2 = new JButton();
        label1 = new JLabel();
        label2 = new JLabel();
        label6 = new JLabel();
        label7 = new JLabel();
        label8 = new JLabel();
        label9 = new JLabel();
        label10 = new JLabel();

        //======== this ========
        setTitle("Type Add");
        setBackground(new Color(0x93b5cf));
        setFont(new Font("\u9ed8\u964c\u6708\u82bd\u4f53", Font.PLAIN, 14));
        setIconImage(new ImageIcon(getClass().getResource("/images/addbooklogo.png")).getImage());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label3 ----
        label3.setText("\u56fe\u4e66\u7c7b\u540d");
        label3.setFont(new Font("\u6977\u4f53", Font.PLAIN, 20));
        contentPane.add(label3);
        label3.setBounds(45, 50, 105, 25);

        //---- label4 ----
        label4.setText("\u7c7b\u522b\u7b80\u4ecb");
        label4.setFont(new Font("\u6977\u4f53", Font.PLAIN, 20));
        contentPane.add(label4);
        label4.setBounds(45, 95, 85, label4.getPreferredSize().height);

        //---- bookTypeNameTxt ----
        bookTypeNameTxt.setFont(new Font("\u534e\u6587\u884c\u6977", Font.PLAIN, 16));
        contentPane.add(bookTypeNameTxt);
        bookTypeNameTxt.setBounds(140, 50, 160, bookTypeNameTxt.getPreferredSize().height);

        //======== scrollPane1 ========
        {

            //---- bookTypeDescTxt ----
            bookTypeDescTxt.setFont(new Font("\u534e\u6587\u884c\u6977", Font.PLAIN, 16));
            scrollPane1.setViewportView(bookTypeDescTxt);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(140, 85, 160, 130);

        //---- button1 ----
        button1.setText("\u6dfb\u52a0");
        button1.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 16));
        button1.setBackground(new Color(0x93b5cf));
        button1.addActionListener(e -> button1ActionPerformed(e));
        contentPane.add(button1);
        button1.setBounds(100, 235, 70, button1.getPreferredSize().height);

        //---- button2 ----
        button2.setText("\u91cd\u7f6e");
        button2.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 16));
        button2.setBackground(new Color(0x93b5cf));
        button2.addActionListener(e -> button2ActionPerformed(e));
        contentPane.add(button2);
        button2.setBounds(190, 235, 70, button2.getPreferredSize().height);

        //---- label1 ----
        label1.setIcon(new ImageIcon(getClass().getResource("/images/descriptionlogo3.png")));
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(55, 125), label1.getPreferredSize()));

        //---- label2 ----
        label2.setIcon(new ImageIcon(getClass().getResource("/images/bouquet.png")));
        contentPane.add(label2);
        label2.setBounds(new Rectangle(new Point(285, 0), label2.getPreferredSize()));

        //---- label6 ----
        label6.setIcon(new ImageIcon(getClass().getResource("/images/longmao.png")));
        contentPane.add(label6);
        label6.setBounds(new Rectangle(new Point(0, -5), label6.getPreferredSize()));

        //---- label7 ----
        label7.setIcon(new ImageIcon(getClass().getResource("/images/cat.png")));
        contentPane.add(label7);
        label7.setBounds(new Rectangle(new Point(25, 240), label7.getPreferredSize()));

        //---- label8 ----
        label8.setIcon(new ImageIcon(getClass().getResource("/images/craw.png")));
        contentPane.add(label8);
        label8.setBounds(new Rectangle(new Point(280, 240), label8.getPreferredSize()));

        //---- label9 ----
        label9.setText("=");
        label9.setIcon(new ImageIcon(getClass().getResource("/images/wind.png")));
        contentPane.add(label9);
        label9.setBounds(95, 0, 85, label9.getPreferredSize().height);

        //---- label10 ----
        label10.setIcon(new ImageIcon(getClass().getResource("/images/cloud (2).png")));
        contentPane.add(label10);
        label10.setBounds(new Rectangle(new Point(200, 0), label10.getPreferredSize()));

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
        setSize(350, 345);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private void thisWindowClosing(WindowEvent e) {

    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label3;
    private JLabel label4;
    private JTextField bookTypeNameTxt;
    private JScrollPane scrollPane1;
    private JTextArea bookTypeDescTxt;
    private JButton button1;
    private JButton button2;
    private JLabel label1;
    private JLabel label2;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JLabel label10;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
