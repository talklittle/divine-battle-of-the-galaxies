/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aseproject;

import entity.accountInfo;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import session.accountInfoFacadeRemote;

/**
 * Administrator panel with functionality to modify accounts.
 * @author Janessa
 */
public class adminPanel extends JPanel implements ActionListener {
    BorderLayout layout;
    JLabel userPanelTitle;
    JTable acctTable;
    JScrollPane scrollPane;

    JPanel editPane;
    JPanel subEditPane;
    JButton editButton;
    JButton deleteButton;
    JButton refreshButton;
    JButton saveButton;
    JButton cancelButton;
    JButton logoutButton;
    JTextField editPswField;
    JLabel userLabel;
    JLabel userDisp;
    JLabel pwdLabel;

    String selectUser;
    String selectPwd;
    int rowIndex;

    private accountInfoFacadeRemote AcctSession;
    private Main parent;

    /**
     * constructor. sets up panes and buttons
     * @param m instance of standalone-client/Main
     */
    public adminPanel(Main m) {
        parent = m;
        AcctSession = Lookup.lookupaccountInfoFacadeRemote();
        
        layout = new BorderLayout();
        this.setLayout(layout);
        setBackground(new java.awt.Color(255, 255, 204));

        userPanelTitle = new JLabel();
        userPanelTitle.setFont(new java.awt.Font("Algerian", 0, 24));
        userPanelTitle.setText("MANAGE USER ACCOUNTS");
        add(userPanelTitle, BorderLayout.NORTH);

        loadTable();
        //add(scrollPane, BorderLayout.CENTER);

        logoutButton = new JButton();
        logoutButton.setFont(new java.awt.Font("Algerian", 1, 12));
        logoutButton.setText("LOGOUT ADMIN CONSOLE");
        logoutButton.addActionListener(this);
        add(logoutButton, BorderLayout.SOUTH);

        //edit panel
        editPane = new JPanel();
        BoxLayout editLayout = new BoxLayout(editPane,
                                             BoxLayout.Y_AXIS);
        editPane.setLayout(editLayout);
        editPane.setPreferredSize(new Dimension(300, 500));

        editButton = new JButton();
        editButton.setFont(new java.awt.Font("Algerian", 1, 12));
        editButton.setText("EDIT USER");
        editButton.addActionListener(this);
        editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editPane.add(editButton);

        deleteButton = new JButton();
        deleteButton.setFont(new java.awt.Font("Algerian", 1, 12));
        deleteButton.setText("DELETE USER");
        deleteButton.addActionListener(this);
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editPane.add(deleteButton);

        refreshButton = new JButton();
        refreshButton.setFont(new java.awt.Font("Algerian", 1, 12));
        refreshButton.setText("REFRESH");
        refreshButton.addActionListener(this);
        refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editPane.add(refreshButton);

        subEditPane = new JPanel();
        BoxLayout subLayout = new BoxLayout(subEditPane,
                                            BoxLayout.Y_AXIS);
        subEditPane.setLayout(subLayout);
        subEditPane.setBorder(
                BorderFactory.createEmptyBorder(70, 30, 30, 30));
        editPane.add(subEditPane);

        add(editPane, BorderLayout.EAST);

        userLabel = new JLabel();
        userLabel.setFont(new java.awt.Font("Algerian", 0, 14));
        userLabel.setText("USERNAME:");
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        userDisp = new JLabel();
        userDisp.setAlignmentX(Component.CENTER_ALIGNMENT);

        pwdLabel = new JLabel();
        pwdLabel.setFont(new java.awt.Font("Algerian", 0, 14));
        pwdLabel.setText("RESET PASSWORD:");
        pwdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        editPswField = new JTextField(20);
        editPswField.setAlignmentX(Component.CENTER_ALIGNMENT);
        editPswField.setAlignmentY(Component.CENTER_ALIGNMENT);
        //editPswField.addActionListener(this);
        editPswField.setMaximumSize(editPswField.getPreferredSize());
        editPswField.setAlignmentX(Component.CENTER_ALIGNMENT);
        editPswField.setDocument(new JTextFieldLimit(20));

        saveButton = new JButton();
        saveButton.setFont(new java.awt.Font("Algerian", 1, 12));
        saveButton.setText("SAVE USER INFO");
        saveButton.addActionListener(this);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        cancelButton = new JButton();
        cancelButton.setFont(new java.awt.Font("Algerian", 1, 12));
        cancelButton.setText("CANCEL");
        cancelButton.addActionListener(this);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    }

    /**
     * TableModel to handle display of usernames/passwords
     */
    class myTableModel extends AbstractTableModel {

        String[] columnNames = {"Username", "Password"};
        ArrayList<Object[]> data;
        Object[] row;
        accountInfo acct;
        int index = 0;

        public myTableModel() {
            List users = AcctSession.findAll();
            data = new ArrayList<Object[]>();
            Iterator iter = users.iterator();
            while (iter.hasNext()) {
                row = new Object[columnNames.length];
                acct = (accountInfo) iter.next();
                row[0] = acct.getId();
                row[1] = acct.getPsw();
                data.add(row);
                index += 1;
            }
        }

        public String getColumnName(int col) {
            return columnNames[col].toString();
        }

        public int getRowCount() {
            return data.size();
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public Object getValueAt(int row, int col) {
            try {
                return data.get(row)[col];
            } catch (Exception e) {
                //catch null upon delete
                return null;
            }
        }

        public boolean isCellEditable(int row, int col) {
            return false;
        }

        public void setValueAt(Object value, int row, int col) {
            data.get(row)[col] = value;
            fireTableCellUpdated(row, col);
        }

        public void removeRow(int rowIndex) {
            data.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }
    
    private void loadTable() {
        acctTable = new JTable(new myTableModel());
        acctTable.removeEditor();
        acctTable.setPreferredScrollableViewportSize(
                  new Dimension(500, 200));
        acctTable.setFillsViewportHeight(true);
        acctTable.getSelectionModel().addListSelectionListener(
                                      new RowListener());
        acctTable.setSelectionMode(
                  ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane(acctTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Listener to help with rows changing
     */
    private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            TableModel model = acctTable.getModel();
            rowIndex = acctTable.getSelectedRow();
            selectUser = (String)model.getValueAt(rowIndex, 0);
            selectPwd = (String)model.getValueAt(rowIndex, 1);
        }
    }

    /**
     * helper method to perform MD5 hashing
     * @param pass
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String MD5(String pass) throws NoSuchAlgorithmException {
	MessageDigest m = MessageDigest.getInstance("MD5");
	byte[] data = pass.getBytes();
	m.update(data, 0, data.length);
	BigInteger i = new BigInteger(1, m.digest());
	return String.format("%1$032X", i);
    }

    /**
     * handler for button clicks
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        subEditPane.removeAll();
        Object src = e.getSource();

        if (src == editButton && selectPwd != null
                              && selectUser != null) {
            userDisp.setText(selectUser);
            editPswField.setText("");

            subEditPane.add(userLabel);
            subEditPane.add(userDisp);
            subEditPane.add(pwdLabel);
            subEditPane.add(editPswField);
            subEditPane.add(saveButton);
            subEditPane.add(cancelButton);
        } else if (src == saveButton) {
            accountInfo user = (accountInfo)
                               AcctSession.find(selectUser);
            int passwordLength = editPswField.getText().length();
            // Check for characters and numbers only
            Pattern p = Pattern.compile("[^a-zA-Z_0-9]");
            Matcher m = p.matcher(editPswField.getText());
            if (passwordLength < 6 || passwordLength > 20) {
                JOptionPane.showMessageDialog(null,
                            "Invalid password: length must be between"
                            + " 6 and 20 (inclusive).");
            }
            else if(m.find()) {
                JOptionPane.showMessageDialog(null,
                            "Invalid password: Must contain only "
                            + "letters and numbers");
            }

            else {
                String password = "";
                try {
                    password = MD5(editPswField.getText());
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                user.setPsw(password);
                AcctSession.edit(user);
                JLabel editMsg = new JLabel();
                editMsg.setText("User [" + selectUser + "]"
                                + "\n updated!");
                editMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
                subEditPane.add(editMsg);
                acctTable.setValueAt(password, rowIndex, 1);
            }
        }

        else if(src == deleteButton) {
            accountInfo user = (accountInfo)AcctSession.find(selectUser);
            AcctSession.remove(user);
            int confirm = JOptionPane.showConfirmDialog(editPane,
                          "Are you sure you want to\n"
                          + "delete user [" + selectUser + "]?",
                          "Delete User?",
                          JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                JLabel editMsg = new JLabel();
                editMsg.setText("User [" + selectUser + "]\n"
                                + "deleted!");
                editMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
                subEditPane.add(editMsg);
                myTableModel model = (myTableModel)
                                     acctTable.getModel();
                model.removeRow(rowIndex);
            }
        }

        else if(src == refreshButton) {
            acctTable.setModel(new myTableModel());
            acctTable.invalidate();
        }

        else if(src == logoutButton) {
            parent.nFlag_admin = false;
            parent.window.remove(this);
            parent.window.add(parent.regPanel);
            parent.window.validate();
            parent.window.repaint();
        }

        revalidate();
        repaint();
    }
}
