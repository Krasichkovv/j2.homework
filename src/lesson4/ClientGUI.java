package lesson4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ClientGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAddress = new JTextField("192.168.1.46");
    private final JTextField tfPort = new JTextField("23131");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top", true);
    private final JTextField tfLogin = new JTextField("Vasily");
    private final JPasswordField tfPassword = new JPasswordField("665");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("<html><b>Disconnect</b></html>");
    private final JTextField tfMessage = new JTextField();
    private final JButton btbSend = new JButton("Send");

    private final JList<String> userList = new JList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI();
            }
        });


    }

    ClientGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setAlwaysOnTop(true);
        userList.setListData(new String[]{"user1", "user2", "user3", "user4", "user5",
                "user6", "user7", "user8", "user9", "user042342424234234234"});
        JScrollPane scrUser = new JScrollPane(userList);
        JScrollPane scrLog = new JScrollPane(log);
        scrUser.setPreferredSize(new Dimension(100, 0));
        log.setLineWrap(true);
        log.setWrapStyleWord(true);
        log.setEditable(false);
        cbAlwaysOnTop.addActionListener(this);
        tfMessage.addActionListener(this);
        btbSend.addActionListener(this);
        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                }

            }
        });


        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btbSend, BorderLayout.EAST);

        add(scrUser, BorderLayout.EAST);
        add(scrLog, BorderLayout.CENTER);
        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void sendMsgLog() throws FileNotFoundException {
        PrintStream writeText = new PrintStream("log.txt");
        String msgLog = tfMessage.getText();
        log.append(msgLog + "\n");
        writeText.append(msgLog).append("\n");
        tfMessage.setText("");


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if(src == cbAlwaysOnTop){
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        }else if (src == btbSend || src == tfMessage){
            try {
                sendMsgLog();

            } catch (FileNotFoundException a) {
                a.printStackTrace();
            }

        }else {
            throw new RuntimeException("Unknown source " + src);
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        msg = String.format("Exception in \"%s\" %s: %s\n\tat %s ",
                t.getName(), e.getClass().getCanonicalName(), e.getMessage(), ste[0]);
        JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

}
