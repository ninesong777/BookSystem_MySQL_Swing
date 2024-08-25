package Board;
import javax.swing.*;
import java.awt.*;

/*
编写时间：2024.5.28
作者：沈海鹏
*/

public class About extends JFrame {
    public About() {
        initComponents();
    }
    /*初始化组件，采用GridBagLayout布局*/
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        label7 = new JLabel();

        //======== this ========
        setTitle("About");
        setIconImage(new ImageIcon(getClass().getResource("/images/booklogo.png")).getImage());
        var contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {16, 352, 20, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {281, 29, 38, 0, 0, 22, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label3 ----
        label3.setIcon(new ImageIcon(getClass().getResource("/images/ncu.png")));
        contentPane.add(label3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- label4 ----
        label4.setText("\u5f00\u53d1\u8005\u59d3\u540d: \u6c88\u6d77\u9e4f  \u5b66\u53f7: 8008123144");
        label4.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 20));
        contentPane.add(label4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- label5 ----
        label5.setText("\u8f6f\u4ef6\u5b66\u9662\u8ba1\u7b97\u673a II \u7c7b2305\u73ed");
        label5.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 20));
        contentPane.add(label5, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- label6 ----
        label6.setText("@IntelliJ IDEA; MySQL 8.0.28; Navicat Premium 15");
        label6.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 15));
        contentPane.add(label6, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- label7 ----
        label7.setText("\u611f\u8c22\u60a8\u7684\u4f7f\u7528\uff01");
        label7.setFont(new Font("\u534e\u6587\u4eff\u5b8b", Font.BOLD | Font.ITALIC, 24));
        contentPane.add(label7, new GridBagConstraints(1, 4, 1, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(0, 0, 0, 0), 0, 0));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}