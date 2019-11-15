package com.Dryft.gui;

import com.Dryft.DAOs.UserDAO;
import com.Dryft.exceptions.UserSideException;
import com.Dryft.models.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignUp extends JFrame {

    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JPanel contentPane;
    private JTextField textField;
//    private JTextField userField;
    private JTextField mailField;
    private JPasswordField passwordField;
    private JPasswordField passwordField_1;

    /**
     * Create the frame.
     */
    public SignUp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 612, 466);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setBackground(Color.RED);

        JLabel lblSignUpHere = new JLabel("SIGN UP HERE");
        lblSignUpHere.setFont(new Font("Century", Font.PLAIN, 22));
        lblSignUpHere.setBounds(212, 11, 223, 33);
        contentPane.add(lblSignUpHere);

        JLabel lblFullName = new JLabel("FULL NAME *");
        lblFullName.setFont(new Font("Dialog", Font.PLAIN, 13));
        lblFullName.setBounds(77, 74, 107, 14);
        contentPane.add(lblFullName);

//        JLabel lblUserName = new JLabel("USER NAME *");
//        lblUserName.setFont(new Font("Dialog", Font.PLAIN, 13));
//        lblUserName.setBounds(77, 120, 107, 14);
//        contentPane.add(lblUserName);
        JLabel lblEmailId = new JLabel("EMAIL ID *");
        lblEmailId.setFont(new Font("Dialog", Font.PLAIN, 13));
        lblEmailId.setBounds(77, 166, 107, 14);
        contentPane.add(lblEmailId);

        JLabel lblPassword = new JLabel("PASSWORD *");
        lblPassword.setFont(new Font("Dialog", Font.PLAIN, 13));
        lblPassword.setBounds(77, 210, 107, 14);
        contentPane.add(lblPassword);

        JLabel lblConfirmPassword = new JLabel("CONFIRM PASSWORD *");
        lblConfirmPassword.setFont(new Font("Dialog", Font.PLAIN, 13));
        lblConfirmPassword.setBounds(77, 256, 170, 14);
        contentPane.add(lblConfirmPassword);

        final JRadioButton maleButton = new JRadioButton("MALE");
        maleButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        buttonGroup.add(maleButton);
        maleButton.setBounds(231, 301, 66, 23);
        contentPane.add(maleButton);

        final JRadioButton femaleButton = new JRadioButton("FEMALE");
        femaleButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        buttonGroup.add(femaleButton);
        femaleButton.setBounds(326, 301, 109, 23);
        contentPane.add(femaleButton);

        final JRadioButton defaultButton = new JRadioButton("New radio button");
        buttonGroup.add(defaultButton);
        defaultButton.setBounds(503, 301, 109, 23);
        contentPane.add(defaultButton);
        defaultButton.setVisible(false);

        JLabel lblGender = new JLabel("GENDER *");
        lblGender.setFont(new Font("Dialog", Font.PLAIN, 13));
        lblGender.setBounds(77, 305, 107, 14);
        contentPane.add(lblGender);

        textField = new JTextField();
        textField.setBounds(257, 74, 138, 20);
        contentPane.add(textField);
        textField.setColumns(10);
//
//        userField = new JTextField();
//        userField.setBounds(257, 120, 134, 20);
//        contentPane.add(userField);
//        userField.setColumns(10);

        mailField = new JTextField();
        mailField.setBounds(257, 166, 134, 20);
        contentPane.add(mailField);
        mailField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setToolTipText("Choose a strong password");
        passwordField.setBounds(257, 210, 134, 20);
        contentPane.add(passwordField);

        passwordField_1 = new JPasswordField();
        passwordField_1.setBounds(257, 256, 134, 20);
        contentPane.add(passwordField_1);

        JButton btnNewButton = new JButton("SIGN UP TO DRYFT !");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = textField.getText();
//                String userName = userField.getText();
                String email_id = mailField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String confirmPassword = String.valueOf(passwordField_1.getPassword());
                String gender = "F";
                if (maleButton.isSelected() == true) {
                    gender = "M";
                }
                if (!password.contentEquals(confirmPassword) || password.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Password confirmation failed. Try again.");
                } else if (email_id.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Please enter the email.");
                } else if (maleButton.isSelected() == false && femaleButton.isSelected() == false) {
                    JOptionPane.showMessageDialog(null, "Please select a gender");
                } else {
                    User user = new User(name, email_id, password, gender.charAt(0), 0);
                    try {
                        UserDAO.createUser(user);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "You could not be signed up at this moment. Inconvenience caused is regretted.");
                        Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    } catch (UserSideException ex) {
                        switch (ex.getErrorCode()) {
                            case EmailAlreadyExists:
                                JOptionPane.showMessageDialog(null, "This email is taken.");
                                mailField.setText("");
                                return;
                            default:
                                JOptionPane.showMessageDialog(null, "Some error occured. Inconvenience caused is regretted.");
                                return;
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Account successfully created!");
                    new SignIn().setVisible(true);
                    dispose();
                }
            }
        });
        btnNewButton.setBounds(100, 357, 167, 33);
        contentPane.add(btnNewButton);

        JButton btnClear = new JButton("CLEAR");
        btnClear.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
//                userField.setText("");
                mailField.setText("");
                passwordField.setText("");
                passwordField_1.setText("");
                defaultButton.setSelected(true);

            }
        });
        btnClear.setBounds(320, 357, 102, 33);
        contentPane.add(btnClear);

        JButton btnClose = new JButton("CLOSE");
        btnClose.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnClose.setForeground(Color.RED);
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnClose.setBounds(472, 357, 89, 33);
        contentPane.add(btnClose);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SignUp frame = new SignUp();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
