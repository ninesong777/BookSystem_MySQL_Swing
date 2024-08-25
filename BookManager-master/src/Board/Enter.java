package Board;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import dao.UserDao;
import Datathings.User;
import util.ConnectionUtil;
import util.StringUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
public class Enter {
    /*初始化登录界面*/
    private JFrame frame;
    private JPanel panel1;
    private JTextField userNameTxt;
    private JTextField passwordTxt;
    private JButton loginButton;
    private JButton resetButton;
    private ConnectionUtil dbUtil = new ConnectionUtil();
    private UserDao userDao = new UserDao();
    public Enter() {
        /*设置窗口*/
        this.frame = new JFrame("登录入口");
        frame.setContentPane(this.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        /*设置”重置“按钮的监听器*/
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetbuttonAction(e);
            }
        });
        /*设置”登录“按钮的监听器*/
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButtonAction(e);
            }
        });
        this.frame.setLocationRelativeTo(null);//将窗口居中
    }

    /*登录事件处理*/
    private void loginButtonAction(ActionEvent evt) {
        /*获取文本域中产生的用户名和密码字符串*/
        String userName = this.userNameTxt.getText();
        String password = this.passwordTxt.getText();
        /*调用StringUtil类中的isEmpty方法，判断用户名和密码是否为空*/
        if (StringUtil.isEmpty(userName)) {
            JOptionPane.showMessageDialog(null, "用户名不能为空");
        }
        if (StringUtil.isEmpty(password)) {
            JOptionPane.showMessageDialog(null, "密码不能为空");
        }
        /*创建一个User对象，并创建一个数据库的连接，初始值设为null*/
        User user = new User(userName, password);
        Connection conn = null;
        /*异常处理*/
        try {
            conn = dbUtil.getConnection();
            /*调用userDao类中的login方法，传入数据库连接和user对象，返回值为User对象，判断用户名和密码是否正确*/
            User currentUser = userDao.login(conn, user);
            /*正确则进入主界面*/
            if (currentUser != null) {
                this.frame.setVisible(false);//将登录页面隐藏
                new MainBoard().setVisible(true);//展示主界面
            } else {
                /*错误则出现异常，弹出对话框显示用户名或密码错误*/
                JOptionPane.showMessageDialog(null, "用户名或密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                /*finally中关闭产生的连接*/
                dbUtil.closeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*重置事件处理，将用户名和密码对应的文本域清空*/
    private void resetbuttonAction(ActionEvent evt) {
        this.userNameTxt.setText("");
        this.passwordTxt.setText("");
    }
    /*主方法，入口，先判断数据库是否连接成功*/
    public static void main(String[] args) throws Exception {
        ConnectionUtil dbUtil = new ConnectionUtil();
        try {
            dbUtil.getConnection();
            System.out.println("数据库连接成功");
        } catch (CommunicationsException e) {
            System.out.println("数据库连接失败");
            JOptionPane.showMessageDialog(null,"数据库连接失败");//弹出对话框
            return;
        }
        /*进入登录界面*/
        MusicPlay musicPlay=new MusicPlay();
        new Thread(musicPlay).start();
        new Enter();
    }
}