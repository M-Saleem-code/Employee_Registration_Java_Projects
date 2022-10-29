import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;


public class Employee1 {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtSalary;
    private JTextField txtMobile;
    private JButton Save;
    private JButton updateButton;
    private JTable table1;
    private JButton deleteButton;
    private JButton reloadButton;
    private JTextField textField1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee1");
        frame.setContentPane(new Employee1().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mscompany", "root", "");
            System.out.println("Success");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    void table_load(){
        try{
            pst=con.prepareStatement("Select * from employee1");
            ResultSet ms=pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(ms));
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    void delete_record(String name){
        try{
            pst=con.prepareStatement("Delete from employee1 where empname='"+name+"'" +
                    "");
            pst.executeUpdate();
            table_load();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    void update_record(String name,double salary,int mobile_number){
        try{
            pst=con.prepareStatement("update  employee1 set salary="+salary+", mobile="+mobile_number+" where empname='"+name+"'");
            pst.executeUpdate();
            table_load();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }



    public Employee1() {
        connect();
        //table_load();
        reloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table_load();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empname;

                empname = txtName.getText();

                delete_record(empname);
                JOptionPane.showMessageDialog(null,"Record Deleted.");
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empname;
                double salary;
                int mobile;

                empname = txtName.getText();
                salary = Double.parseDouble(txtSalary.getText());
                mobile = Integer.parseInt(txtMobile.getText());

                update_record(empname,salary,mobile);
                table_load();
                JOptionPane.showMessageDialog(null,"Record updated.");

            }
        });
        Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empname, salary, mobile;

                empname = txtName.getText();
                salary = txtSalary.getText();
                mobile = txtMobile.getText();
                try {
                    pst = con.prepareStatement("insert into employee1(empname,salary,mobile)value(?,?,?)");
                    pst.setString(1, empname);
                    pst.setString(2, salary);
                    pst.setString(3, mobile);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data saved!");

                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, e1);

                }
            }
        });


    }
}



