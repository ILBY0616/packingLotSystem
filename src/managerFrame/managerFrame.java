package managerFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import data.owner;

public class managerFrame extends JFrame implements ActionListener {
	private final Font maxFont = new Font("宋体", Font.BOLD, 35);
	private final Font midFont = new Font("宋体", Font.BOLD, 15);
	private final MenuBar menubar = new MenuBar();
	private final Menu seekLocation = new Menu("查询车位");
	private final Menu enterManage = new Menu("进站管理");
	private final Menu outManage = new Menu("出站管理");
	private final Menu messageLook = new Menu("信息总览");
	private final MenuItem seekEmpty = new MenuItem("查询空位");
	private final MenuItem enterRegister = new MenuItem("进站登记");
	private final MenuItem outRegister = new MenuItem("出站登记");
	private final MenuItem message = new MenuItem("信息库");
	private final String[] cardName = { "0", "1", "2", "3" };
	private final CardLayout card = new CardLayout();
	private final Panel display = new Panel(card);
	private final JPanel firstCard = new JPanel(new GridLayout(3,1));
	private final JPanel firstDown = new JPanel();
	private final JLabel fempty = new JLabel(" 可 用 车 位 号", JTextField.CENTER);
	private final JTextField femptyText = new JTextField();
	private final JButton seek = new JButton("查询空车位");
	private final JPanel secondCard = new JPanel(new GridLayout(3, 1));
	private final JPanel secondUp = new JPanel(new GridLayout(1, 2));
	private final JPanel secondCenter = new JPanel(new GridLayout(2, 2));
	private final JPanel secondDown = new JPanel();
	private final JLabel sempty = new JLabel("车位号", JTextField.CENTER);
	private final JTextField semptyText = new JTextField();
	private final JLabel number = new JLabel("车牌号", JLabel.CENTER);
	private final JTextField numberText = new JTextField();
	private final Calendar cal = Calendar.getInstance();
	private final int year = cal.get(Calendar.YEAR);
	private final int month = cal.get(Calendar.MONTH) + 1;
	private final int day = cal.get(Calendar.DAY_OF_MONTH);
	private final int hour = cal.get(Calendar.HOUR_OF_DAY);
	private final int minute = cal.get(Calendar.MINUTE);
	private final JLabel startTime = new JLabel("进站时间", JLabel.CENTER);
	private final String timeString = year + "-" + month + "-" + day + "-" + hour + "-" + minute;
	private final JTextField startText = new JTextField(timeString);
	private final JButton register = new JButton("登记进站");
	private final int n = 50;
	private final Object[][] data = new Object[1][5];
	private final Object[][] totalData = new Object[n][5];
	private final String[] tableName = { "车位", "车牌号", "进站时间", "出站时间", "收费" };
	private final JPanel thirdCard = new JPanel(new BorderLayout());
	private final JTable table = new JTable(data, tableName);
	private final JScrollPane thirdTable = new JScrollPane(table);
	private final JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
	private final JButton query = new JButton("查询车牌号");
	private final JPanel queryPanel = new JPanel(new BorderLayout());
	private final JTextField queryText = new JTextField();
	private final JButton endTime = new JButton("登记出站");
	private final JButton delete = new JButton("删除记录");
	private final JPanel lastCard = new JPanel(new BorderLayout());
	private final JTable totalTable = new JTable(totalData, tableName);
	private final JScrollPane lastTable = new JScrollPane(totalTable);

	public managerFrame() {
		super("停车场管理系统");

		setUp();

		// 弹出卡片
		enterRegister.addActionListener(this);
		outRegister.addActionListener(this);
		message.addActionListener(this);
		seekEmpty.addActionListener(this);

		// 按button处理
		register.addActionListener(this);
		query.addActionListener(this);
		endTime.addActionListener(this);
		delete.addActionListener(this);
		seek.addActionListener(this);

		femptyText.setEditable(false);
		startText.setEditable(false);

		setSize(600, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		addWindowListener(new WindowCloser());
	}

	// 卡片布局总体框架
	private void setUp() {
		seekEmpty.setFont(midFont);
		seekLocation.setFont(midFont);
		seekLocation.add(seekEmpty);
		seekEmpty.setEnabled(true);
		
		enterRegister.setFont(midFont);
		enterManage.setFont(midFont);
		enterManage.add(enterRegister);
		enterRegister.setEnabled(true);

		outRegister.setFont(midFont);
		outManage.setFont(midFont);
		outManage.add(outRegister);
		outRegister.setEnabled(true);

		message.setFont(midFont);
		messageLook.setFont(midFont);
		messageLook.add(message);
		message.setEnabled(true);

		menubar.add(seekLocation);
		menubar.add(enterManage);
		menubar.add(outManage);
		menubar.add(messageLook);

		setMenuBar(menubar);
		setUpDisplay();
		add(display);

	}

	// 卡片布局具体框架
	private void setUpDisplay() {
		seek.setFont(midFont);
		firstDown.add(seek);
		fempty.setFont(midFont);
		femptyText.setFont(maxFont);
		firstCard.add(fempty);
		firstCard.add(femptyText);
		firstCard.add(firstDown);

		sempty.setFont(midFont);
		secondUp.add(sempty);
		secondUp.add(semptyText);
		number.setFont(midFont);
		secondCenter.add(number);
		secondCenter.add(numberText);
		startTime.setFont(midFont);
		secondCenter.add(startTime);
		secondCenter.add(startText);
		register.setFont(midFont);
		secondDown.add(register);
		secondCard.add(secondUp);
		secondCard.add(secondCenter);
		secondCard.add(secondDown);

		table.getTableHeader().setFont(midFont);
		table.setRowHeight(20);
		query.setFont(midFont);
		buttonPanel.add(query);
		queryPanel.add(queryText);
		buttonPanel.add(queryPanel);
		endTime.setFont(midFont);
		buttonPanel.add(endTime);
		delete.setFont(midFont);
		buttonPanel.add(delete);
		thirdCard.add(buttonPanel, BorderLayout.NORTH);
		thirdCard.add(thirdTable, BorderLayout.CENTER);

		totalTable.getTableHeader().setFont(midFont);
		totalTable.setRowHeight(20);
		lastCard.add(lastTable, BorderLayout.CENTER);

		display.add("0", firstCard);
		display.add("1", secondCard);
		display.add("2", thirdCard);
		display.add("3", lastCard);
	}

	public void actionPerformed(ActionEvent e) {
		// 卡片转换
		if (e.getSource() == seekEmpty) {
			card.show(display, cardName[0]);
		}
		if (e.getSource() == enterRegister) {
			card.show(display, cardName[1]);
		}
		if (e.getSource() == outRegister) {
			card.show(display, cardName[2]);
		}
		if (e.getSource() == message) {
			card.show(display, cardName[3]);
		}
		// display响应
		// 封装进站出站管理功能
		seekFunction(e);
		inFunction(e);
		outFunction(e);
		lookFunction(e);
	}

	public void seekFunction(ActionEvent e) {
		if (e.getSource() == seek) {
			try {
				for (int i = 0; i < n; i++) {
					RandomAccessFile raf = new RandomAccessFile("src/data/owner.txt", "r");
					// 计数文件中正在停车车辆的总个数
					int count = 0;
					// 当前读文件指针的位置
					long point = 0;
					// 这个循环只是为了获得当前文件中正在停车车辆的总个数
					while (point < raf.length()) {
						raf.seek(point);
						raf.readInt();
						raf.readUTF();
						raf.readUTF();
						raf.readUTF();
						raf.readDouble();
						// 读取一个owner对象的文件即正在停车的车辆就加1
						count++;
						if (count == 9) {
							new managerDialog(this, "车 位 已 满");
						}
						point = raf.getFilePointer();
					}
					// 计数文件中正在停车车辆的个数
					int judge = 0;
					// 重置读文件指针的位置
					point = 0;
					while (point < raf.length()) {
						raf.seek(point);
						int location = raf.readInt();
						// 如果一个数字属于(0,9)没有被所有的车辆等于即意味着该数是空车位
						if (i != location) {
							// 读取一个owner对象的文件即正在停车的车辆就加1
							judge++;
						}
						raf.readUTF();
						raf.readUTF();
						raf.readUTF();
						raf.readDouble();
						point = raf.getFilePointer();
					}
					raf.close();
					if (judge == count) {
						femptyText.setText(String.valueOf(i));
						break;
					}
				}
			} catch (IOException ioe) {
				System.err.println(ioe);
			}
		}
	}

	public void inFunction(ActionEvent e) {
		if (e.getSource() == register) {
			if (semptyText.getText().isEmpty() || numberText.getText().isEmpty()) {
				new managerDialog(this, "输 入 错 误");
			} else {
				// 有车位号和车牌号输入
				// 当填写的车牌号车辆已经进站正在停车时flag=1
				int flag = 0;
				try {
					RandomAccessFile raf = new RandomAccessFile("src/data/owner.txt", "r");
					// 当前读文件指针的位置
					long point = 0;
					owner rafOwner = new owner();
					while (point < raf.length()) {
						raf.seek(point);
						raf.readInt();
						// 读取正在停车车辆的车牌号
						rafOwner.setNumber(raf.readUTF());
						raf.readUTF();
						// 读取正在停车车辆的停车状态
						rafOwner.setEndTime(raf.readUTF());
						if (rafOwner.getNumber().equals(numberText.getText()) && rafOwner.getEndTime().equals("停车中")) {
							flag = 1;
						}
						raf.readDouble();
						point = raf.getFilePointer();
					}
					raf.close();
				} catch (IOException ioe) {
					System.err.println(ioe);
				}
				if (flag == 1) {
					new managerDialog(this, "该 车 辆 已 进 站");
				} else {
					try {
						RandomAccessFile raf = new RandomAccessFile("src/data/owner.txt", "rw");
						owner Owner = new owner();
						raf.seek(raf.length());
						int n = Integer.parseInt(semptyText.getText());
						raf.writeInt(n);
						raf.writeUTF(numberText.getText());
						raf.writeUTF(startText.getText());
						raf.writeUTF(Owner.getEndTime());
						raf.writeDouble(Owner.getPay());
						new managerDialog(this, "登 记 成 功");
						raf.close();
					} catch (IOException I) {
						I.printStackTrace();
					}
				}
			}
		}
	}

	public void outFunction(ActionEvent e) {
		if (e.getSource() == query) {
			if (queryText.getText().isEmpty()) {
				new managerDialog(this, "请先输入车牌号！");
			} else {
				try {
					RandomAccessFile raf = new RandomAccessFile("src/data/owner.txt", "r");
					// flag=0表示不存在此车牌号
					int flag = 0;
					long point = 0;
					owner rafOwner = new owner();
					while (point <= raf.length()) {
						raf.seek(point);
						rafOwner.setLocation(raf.readInt());
						rafOwner.setNumber(raf.readUTF());
						rafOwner.setStartTime(raf.readUTF());
						rafOwner.setEndTime(raf.readUTF());
						rafOwner.setPay(raf.readDouble());
						point = raf.getFilePointer();
						if (rafOwner.getNumber().equals(queryText.getText())) {
							flag = 1;
							data[0][0] = rafOwner.getLocation();
							data[0][1] = rafOwner.getNumber();
							data[0][2] = rafOwner.getStartTime();
							data[0][3] = rafOwner.getEndTime();
							data[0][4] = rafOwner.getPay();
							raf.close();
							break;
						}
					}
					if (flag == 0) {
						new managerDialog(this, "不存在此车牌号！");
					}
				} catch (IOException I) {
					I.printStackTrace();
				}
			}
		}
		if (e.getSource() == endTime) {
			double pay;
			if (queryText.getText().isEmpty()) {
				new managerDialog(this, "请先输入车牌号！");
			} else {
				try {
					RandomAccessFile raf = new RandomAccessFile("src/data/owner.txt", "rw");
					int i = 0;
					int count = 0;
					long point = 0;
					owner rafOwner = new owner();
					owner[] ownerData = new owner[n];
					for (int l = 0; l < n; l++) {
						ownerData[l] = new owner();
					}
					while (point < raf.length()) {
						raf.seek(point);
						rafOwner.setLocation(raf.readInt());
						rafOwner.setNumber(raf.readUTF());
						rafOwner.setStartTime(raf.readUTF());
						rafOwner.setEndTime(raf.readUTF());
						rafOwner.setPay(raf.readDouble());
						point = raf.getFilePointer();
						// 修改表格的数据
						if (rafOwner.getNumber().equals(queryText.getText())) {
							rafOwner.setEndTime(timeString);
							int[] startInt = new int[5];
							int[] endInt = new int[5];
							String[] startString;
							String[] endString;
							startString = rafOwner.getStartTime().split("-", 5);
							endString = rafOwner.getEndTime().split("-", 5);
							for (int j = 0; j < 5; j++) {
								startInt[j] = Integer.parseInt(startString[j]);
								endInt[j] = Integer.parseInt(endString[j]);
							}
							int year = endInt[0] - startInt[0];
							int month = endInt[1] - startInt[1];
							int day = endInt[2] - startInt[2];
							int hour = endInt[3] - startInt[3];
							int minute = endInt[4] - startInt[4];
							long time = year * 525600 + month * 43804 + day * 1440 + hour * 60 + minute;
							if (time < 30) {
								pay = 0.0;
							} else if (time < 60) {
								pay = 5.0;
							} else {
								pay = 5.0 + ((time - 60) / 30) * 5.0;
							}
							rafOwner.setPay(pay);
							data[0][0] = rafOwner.getLocation();
							data[0][1] = rafOwner.getNumber();
							data[0][2] = rafOwner.getStartTime();
							data[0][3] = rafOwner.getEndTime();
							data[0][4] = rafOwner.getPay();
						}
						ownerData[i].setLocation(rafOwner.getLocation());
						ownerData[i].setNumber(rafOwner.getNumber());
						ownerData[i].setStartTime(rafOwner.getStartTime());
						ownerData[i].setEndTime(rafOwner.getEndTime());
						ownerData[i].setPay(rafOwner.getPay());
						count++;
						i++;
					}
					// 重写修改好的数据
					point = 0;
					for (int j = 0; j < count; j++) {
						raf.seek(point);
						raf.writeInt(ownerData[j].getLocation());
						raf.writeUTF(ownerData[j].getNumber());
						raf.writeUTF(ownerData[j].getStartTime());
						raf.writeUTF(ownerData[j].getEndTime());
						raf.writeDouble(ownerData[j].getPay());
						point = raf.getFilePointer();
					}
					raf.close();
				} catch (IOException I) {
					I.printStackTrace();
				}
			}
		}
		if (e.getSource() == delete) {
			new managerDialog(this, "确认删除");
			if (queryText.getText().isEmpty()) {
				new managerDialog(this, "请先输入车牌号！");
			} else {
				try {
					RandomAccessFile raf = new RandomAccessFile("src/data/owner.txt", "rw");
					int i = 0;
					int count = 0;
					long point = 0;
					owner rafOwner = new owner();
					owner[] ownerData = new owner[n];
					for (int l = 0; l < n; l++) {
						ownerData[l] = new owner();
					}
					while (point < raf.length()) {
						raf.seek(point);
						rafOwner.setLocation(raf.readInt());
						rafOwner.setNumber(raf.readUTF());
						rafOwner.setStartTime(raf.readUTF());
						rafOwner.setEndTime(raf.readUTF());
						rafOwner.setPay(raf.readDouble());
						point = raf.getFilePointer();
						// 删除表格的数据
						if (rafOwner.getNumber().equals(queryText.getText())) {
							data[0][0] = "";
							data[0][1] = "";
							data[0][2] = "";
							data[0][3] = "";
							data[0][4] = "";
						} else {
							ownerData[i].setLocation(rafOwner.getLocation());
							ownerData[i].setNumber(rafOwner.getNumber());
							ownerData[i].setStartTime(rafOwner.getStartTime());
							ownerData[i].setEndTime(rafOwner.getEndTime());
							ownerData[i].setPay(rafOwner.getPay());
							count++;
							i++;
						}
					}
					// 清空文件所有数据
					try {
						FileWriter fileWriter = new FileWriter("src/data/owner.txt");
						fileWriter.write("");
						fileWriter.flush();
						fileWriter.close();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
					// 重写修改好的数据
					point = 0;
					for (int j = 0; j < count; j++) {
						raf.seek(point);
						raf.writeInt(ownerData[j].getLocation());
						raf.writeUTF(ownerData[j].getNumber());
						raf.writeUTF(ownerData[j].getStartTime());
						raf.writeUTF(ownerData[j].getEndTime());
						raf.writeDouble(ownerData[j].getPay());
						totalData[j][0] = ownerData[j].getLocation();
						totalData[j][1] = ownerData[j].getNumber();
						totalData[j][2] = ownerData[j].getStartTime();
						totalData[j][3] = ownerData[j].getEndTime();
						totalData[j][4] = ownerData[j].getPay();
						if (j == count - 1) {
							totalData[j + 1][0] = "";
							totalData[j + 1][1] = "";
							totalData[j + 1][2] = "";
							totalData[j + 1][3] = "";
							totalData[j + 1][4] = "";
						}
						point = raf.getFilePointer();
					}
					raf.close();
				} catch (IOException I) {
					I.printStackTrace();
				}
			}
		}
	}

	public void lookFunction(ActionEvent e) {
		if (e.getSource() == message) {
			try {
				long point = 0;
				owner owner = new owner();
				for (int i = 0; i < n; i++) {
					RandomAccessFile raf = new RandomAccessFile("src/data/owner.txt", "r");
					while (point < raf.length()) {
						raf.seek(point);
						owner.setLocation(raf.readInt());
						owner.setNumber(raf.readUTF());
						owner.setStartTime(raf.readUTF());
						owner.setEndTime(raf.readUTF());
						owner.setPay(raf.readDouble());
						totalData[i][0] = owner.getLocation();
						totalData[i][1] = owner.getNumber();
						totalData[i][2] = owner.getStartTime();
						totalData[i][3] = owner.getEndTime();
						totalData[i][4] = owner.getPay();
						point = raf.getFilePointer();
						break;
					}
					raf.close();
				}

			} catch (IOException I) {
				I.printStackTrace();
			}
		}
	}

	public class WindowCloser extends WindowAdapter {
		public void windowClosing(WindowEvent we) {
			managerFrame.this.dispose();
		}
	}
}

//编写提示对话框类
class managerDialog extends Dialog implements ActionListener {
	JButton confirm = new JButton("确认");

	public managerDialog(Frame parent, String tip) {
		super(parent, "提示");
		setUp(tip);
		confirm.addActionListener(this);
		setVisible(true);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		addWindowListener(new WindowCloser());
	}

	private void setUp(String tip) {
		JLabel tipLabel = new JLabel(tip, JLabel.CENTER);
		Panel tipPanel = new Panel(new GridLayout(2, 1));
		tipPanel.add(tipLabel);
		tipPanel.add(confirm);
		add(tipPanel);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == confirm) {
			managerDialog.this.dispose();
		}

	}

	class WindowCloser extends WindowAdapter {
		public void windowClosing(WindowEvent we) {
			managerDialog.this.dispose();
		}
	}
}