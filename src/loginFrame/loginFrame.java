package loginFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;

import data.manager;
import managerFrame.managerFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class loginFrame extends JFrame implements ActionListener {
	private final Font maxFont = new Font("楷体",Font.BOLD,35);
	private final Font midFont = new Font("楷体",Font.BOLD,20) ;
	private final Font minFont = new Font("宋体",Font.BOLD,17) ;
	private JPanel allPanel;
	private final JPanel loginPanel = new JPanel(new GridLayout(3, 2));
	private final JPanel buttonPanel = new JPanel();
	private final JLabel tip = new JLabel("欢 迎 登 录", JLabel.CENTER);
	private final JLabel userName = new JLabel("用户名", JLabel.CENTER);
	private final JLabel userPassword = new JLabel("密码", JLabel.CENTER);
	private final JTextField nameText = new JTextField();
	private final JTextField passwordText = new JTextField();
	private final JButton login = new JButton("登录");
	private final JButton register = new JButton("注册");

	public loginFrame() {
		super("停车场管理系统");

		setUp();

		login.addActionListener(this);
		register.addActionListener(this);
		nameText.setEditable(true);
		passwordText.setEditable(true);

		setSize(500, 350);
		setLocationRelativeTo(null);
		setVisible(true);
		addWindowListener(new WindowCloser());
	}

	private void setUp() {
		try {
            allPanel = new photoPanel(ImageIO.read(new File("src/photo/login.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }

		tip.setFont(maxFont);
		tip.setForeground(Color.YELLOW);
		// 设置透明
		tip.setOpaque(false);
		userName.setFont(midFont);
		userName.setForeground(Color.YELLOW);
		userPassword.setFont(midFont);
		userPassword.setForeground(Color.YELLOW);
		login.setFont(minFont);
		register.setFont(minFont);
		
		// 设置透明
		loginPanel.setOpaque(false);
		loginPanel.add(userName);
		loginPanel.add(nameText);
		loginPanel.add(userPassword);
		loginPanel.add(passwordText);
		loginPanel.setBackground(new Color(170,210,230));

		// 设置透明
	    buttonPanel.setOpaque(false);
		buttonPanel.add(login);
		buttonPanel.add(register);
		buttonPanel.setBackground(new Color(170,210,230));

		allPanel.setLayout(new GridLayout(3, 1));
		allPanel.add(tip);
		allPanel.add(loginPanel);
		allPanel.add(buttonPanel);
		
		add(allPanel);
		
	}

	public void actionPerformed(ActionEvent e) {
		// 跳转管理界面
		if (e.getSource() == login) {
			// 检测用户是否已经注册
			try {
				FileInputStream fis = new FileInputStream("src/data/manager.txt");
				ObjectInputStream ois = new ObjectInputStream(fis);
				int judge = 0;
				manager inManager;
				while (fis.available() > 0) {
					inManager = (manager) ois.readObject();
					// 避免多对象文件读取头文件报错
					fis.skip(4);
					if (nameText.getText().equals(inManager.getName())
							&& passwordText.getText().equals(inManager.getPassword())) {
						// 判断用户已经注册
						judge = 1;
						break;
					}
				}
				fis.close();
				if ((nameText.getText().isEmpty()) || (passwordText.getText().isEmpty())) {
					new registerDialog(this, "格 式 错 误");
				} else {
					if (judge == 1) {
						// 登录成功
						new managerFrame();
						loginFrame.this.dispose();
					} else {
						new registerDialog(this, "输 入 错 误");

					}
				}
			} catch (IOException | ClassNotFoundException ioe) {
				System.err.println(ioe);

			}

        }
		// 跳转注册界面
		else {
			new registerFrame();
		}
	}

	public static void main(String[] args) {
		new loginFrame();
	}
}

class WindowCloser extends WindowAdapter {
	public void windowClosing(WindowEvent we) {
		System.exit(0);
	}
}

class photoPanel extends JPanel {
    private final Image image;

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    public photoPanel(Image bgImage) {
        this.image = bgImage;
    }
}