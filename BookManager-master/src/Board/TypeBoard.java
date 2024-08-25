package Board;

import dao.BookTypeDao;
import Datathings.Booktype;
import util.ConnectionUtil;
import util.StringUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TypeBoard extends JFrame {
    public TypeBoard() {
        initComponents();
    }

    private void button1ActionPerformed(ActionEvent e) {
        bookTypeQuery(e);
    }

    /*图书查询事件处理*/
    private void bookTypeQuery(ActionEvent ex) {
        String name = this.bookTypeNameTxt.getText();
        Booktype booktype = new Booktype();
        booktype.setBooktypename(name);
        //填充表格
        this.fillTable(booktype);
    }

    private void table1MousePressed(MouseEvent e) {
        bookTypeTableMousePressed(e);
    }

    /*选中表格数据事件处理*/
    private void bookTypeTableMousePressed(MouseEvent ex) {
        //获取行号
        int row = this.table1.getSelectedRow();
        this.idTxt.setText(this.table1.getValueAt(row, 0).toString());
        this.nameTxt.setText(this.table1.getValueAt(row, 1).toString());
        this.descTxt.setText(this.table1.getValueAt(row, 2).toString());
    }

    private void button2ActionPerformed(ActionEvent e) {
        bookTypeupdate(e);
    }

    /*修改图书类别信息*/
    private void bookTypeupdate(ActionEvent ex) {
        String id = idTxt.getText();
        String name = nameTxt.getText();
        String desc = descTxt.getText();
        if (StringUtil.isEmpty(id)) {
            JOptionPane.showMessageDialog(null, "请选择要修改的记录");
            return;
        }
        Booktype booktype = new Booktype(Integer.parseInt(id), name, desc);
        //建立数据库连接
        Connection conn = null;
        try {
            conn = dbUtil.getConnection();
            //更新，成功则返回1
            int modify = BookTypeDao.update(conn, booktype);
            if (modify == 1) {
                JOptionPane.showMessageDialog(null, "修改成功");
                //重置值
                this.resetValue();
                //重新填充表格
                this.fillTable(new Booktype());
            } else {
                JOptionPane.showMessageDialog(null, "修改失败");
            }
            /*发生异常，修改失败*/
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "修改失败");
        } finally {
            try {
                //关闭数据库连接
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /*清空输入框信息*/
    private void resetValue() {
        this.idTxt.setText("");
        this.nameTxt.setText("");
        this.descTxt.setText("");
    }

    /*删除按钮事件处理*/
    private void button3ActionPerformed(ActionEvent e) {
        String id = idTxt.getText();
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
                boolean flag = BookTypeDao.existBook(connection, id);
                if (flag) {
                    JOptionPane.showMessageDialog(null, "当前类别下有图书，不能删除该类别");
                    return;
                }
                int deleteNum = BookTypeDao.delete(connection, id);
                if (deleteNum == 1) {
                    JOptionPane.showMessageDialog(null, "删除成功");
                    this.resetValue();
                    this.fillTable(new Booktype());
                } else {
                    JOptionPane.showMessageDialog(null, "删除失败");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "删除失败");
            } finally {
                try {
                    dbUtil.closeConnection(connection);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /*添加按钮事件处理*/
    private void button4ActionPerformed(ActionEvent e) {
        new TypeAddBoard().setVisible(true);
    }


    private void initComponents() {
        //先释放资源
        dispose();
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        label1 = new JLabel();
        bookTypeNameTxt = new JTextField();
        button1 = new JButton();
        panel1 = new JPanel();
        label2 = new JLabel();
        idTxt = new JTextField();
        label3 = new JLabel();
        nameTxt = new JTextField();
        label4 = new JLabel();
        scrollPane2 = new JScrollPane();
        descTxt = new JTextArea();
        button2 = new JButton();
        button3 = new JButton();
        button4 = new JButton();
        label5 = new JLabel();
        label6 = new JLabel();
        label8 = new JLabel();
        label9 = new JLabel();

        //======== this ========
        setTitle("Book Type");
        setIconImage(new ImageIcon(getClass().getResource("/images/booklogo.png")).getImage());
        setFont(new Font("Segoe UI", Font.PLAIN, 12));
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {

            //---- table1 ----
            table1.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "\u7f16\u53f7", "\u7c7b\u578b", "\u7b80\u4ecb"
                }
            ));
            table1.setFont(new Font("\u534e\u6587\u884c\u6977", Font.PLAIN, 15));
            table1.setBackground(new Color(0xbaccd9));
            table1.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    table1MousePressed(e);
                }
            });
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(0, 35, 445, 155);

        //---- label1 ----
        label1.setText("\u56fe\u4e66\u7c7b\u522b\u67e5\u8be2");
        label1.setFont(new Font("\u6977\u4f53", Font.PLAIN, 16));
        label1.setBackground(new Color(0xfff2e2));
        label1.setToolTipText("type inquire");
        contentPane.add(label1);
        label1.setBounds(10, 5, 105, 22);

        //---- bookTypeNameTxt ----
        bookTypeNameTxt.setFont(new Font("\u534e\u6587\u884c\u6977", Font.PLAIN, 15));
        bookTypeNameTxt.setBackground(new Color(0xc4cbcf));
        contentPane.add(bookTypeNameTxt);
        bookTypeNameTxt.setBounds(110, 5, 210, 27);

        //---- button1 ----
        button1.setText("\u67e5\u8be2");
        button1.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 16));
        button1.setBackground(new Color(0xdce2f1));
        button1.setToolTipText("inquire");
        button1.setIcon(new ImageIcon(getClass().getResource("/images/inquirelogo2.png")));
        button1.addActionListener(e -> button1ActionPerformed(e));
        contentPane.add(button1);
        button1.setBounds(330, 5, 100, 30);

        //======== panel1 ========
        {
            panel1.setBorder(new TitledBorder("--Operation--"));
            panel1.setBackground(new Color(0xbaccd9));
            panel1.setLayout(null);

            //---- label2 ----
            label2.setText("\u7f16\u53f7");
            label2.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
            label2.setToolTipText("number");
            panel1.add(label2);
            label2.setBounds(25, 40, 40, label2.getPreferredSize().height);

            //---- idTxt ----
            idTxt.setEditable(false);
            idTxt.setFont(new Font("\u9ed8\u964c\u6708\u82bd\u4f53", Font.PLAIN, 14));
            idTxt.setBackground(new Color(0xc4cbcf));
            panel1.add(idTxt);
            idTxt.setBounds(70, 35, 75, idTxt.getPreferredSize().height);

            //---- label3 ----
            label3.setText("\u56fe\u4e66\u7c7b\u522b\u540d\u79f0");
            label3.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
            label3.setToolTipText("type name");
            panel1.add(label3);
            label3.setBounds(170, 40, 110, label3.getPreferredSize().height);

            //---- nameTxt ----
            nameTxt.setFont(new Font("\u9ed8\u964c\u6708\u82bd\u4f53", Font.PLAIN, 14));
            nameTxt.setBackground(new Color(0xc4cbcf));
            panel1.add(nameTxt);
            nameTxt.setBounds(285, 35, 120, nameTxt.getPreferredSize().height);

            //---- label4 ----
            label4.setText("\u7b80\u4ecb");
            label4.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
            label4.setToolTipText("description");
            panel1.add(label4);
            label4.setBounds(25, 85, 37, 25);

            //======== scrollPane2 ========
            {

                //---- descTxt ----
                descTxt.setLineWrap(true);
                descTxt.setFont(new Font("\u534e\u6587\u884c\u6977", Font.PLAIN, 15));
                descTxt.setBackground(new Color(0xc4cbcf));
                scrollPane2.setViewportView(descTxt);
            }
            panel1.add(scrollPane2);
            scrollPane2.setBounds(65, 75, 340, 110);

            //---- button2 ----
            button2.setText("\u4fee\u6539");
            button2.setBackground(new Color(0xdce2f1));
            button2.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 20));
            button2.setToolTipText("modify");
            button2.addActionListener(e -> button2ActionPerformed(e));
            panel1.add(button2);
            button2.setBounds(185, 200, 85, 35);

            //---- button3 ----
            button3.setText("\u5220\u9664");
            button3.setBackground(new Color(0xdce2f1));
            button3.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 20));
            button3.setToolTipText("delete");
            button3.addActionListener(e -> button3ActionPerformed(e));
            panel1.add(button3);
            button3.setBounds(310, 200, 90, 35);

            //---- button4 ----
            button4.setText("\u6dfb\u52a0");
            button4.setBackground(new Color(0xdce2f1));
            button4.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 20));
            button4.setToolTipText("add");
            button4.addActionListener(e -> button4ActionPerformed(e));
            panel1.add(button4);
            button4.setBounds(60, 200, 85, 35);

            //---- label5 ----
            label5.setIcon(new ImageIcon(getClass().getResource("/images/descriptionlogo.png")));
            panel1.add(label5);
            label5.setBounds(25, 115, 37, 35);

            //---- label6 ----
            label6.setIcon(new ImageIcon(getClass().getResource("/images/dog.png")));
            panel1.add(label6);
            label6.setBounds(new Rectangle(new Point(385, 235), label6.getPreferredSize()));

            //---- label8 ----
            label8.setIcon(new ImageIcon(getClass().getResource("/images/cat.png")));
            panel1.add(label8);
            label8.setBounds(new Rectangle(new Point(10, 240), label8.getPreferredSize()));

            //---- label9 ----
            label9.setIcon(new ImageIcon(getClass().getResource("/images/bird (2).png")));
            panel1.add(label9);
            label9.setBounds(new Rectangle(new Point(415, 35), label9.getPreferredSize()));

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
        panel1.setBounds(0, 190, 460, 295);

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
        setSize(460, 515);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        this.fillTable(new Booktype());
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane scrollPane1;
    private JTable table1;
    private JLabel label1;
    private JTextField bookTypeNameTxt;
    private JButton button1;
    private JPanel panel1;
    private JLabel label2;
    private JTextField idTxt;
    private JLabel label3;
    private JTextField nameTxt;
    private JLabel label4;
    private JScrollPane scrollPane2;
    private JTextArea descTxt;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label8;
    private JLabel label9;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    ConnectionUtil dbUtil = new ConnectionUtil();

    /*填充表格*/
    private void fillTable(Booktype booktype) {
        //建立tablemodel模型
        DefaultTableModel dtm = (DefaultTableModel) table1.getModel();
        dtm.setRowCount(0);
        Connection conn = null;
        try {
            conn = dbUtil.getConnection();
            ResultSet resultSet = BookTypeDao.list(conn, booktype);
            while (resultSet.next()) {
                //创建vector集合
                Vector all = new Vector();
                all.add(resultSet.getString("id"));
                all.add(resultSet.getString("bookTypeName"));
                all.add(resultSet.getString("bookTypeDesc"));
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
}
