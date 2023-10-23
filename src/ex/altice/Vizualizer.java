package ex.altice;

import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vizualizer {
        
    private Vizualizer() {
            throw new UnsupportedOperationException("Cannot instantiate utility class.");
            
    }
        
    //inserts an CDR in an ARRAYLIST by a timestamp
    public static void insertSortedCDR(List<CDR> records, CDR newRecord) {
        int index = 0;
        for (CDR record : records) {
            if (record.getTimeStamp().after(newRecord.getTimeStamp())) {
                break;
            }
            index++;
        }
        records.add(index, newRecord);
    }
    //inserts an useCost in an ARRAYLIST by a timestamp
    public static void insertSortedUseCost(List<UseCost> useCosts, UseCost newUseCost) {
        int index = 0;
        for (UseCost record : useCosts) {
            if (record.getTimeStamp().after(newUseCost.getTimeStamp())) {
                break;
            }
            index++;
        }
        useCosts.add(index, newUseCost);
    }

    public static void display(List<UseCost> useCosts)
    {
        JFrame frame = new JFrame("Use and Cost of Msisdn");
        JLabel label = new JLabel("msisdn:");
        JTextField textField = new JTextField();
        JButton button = new JButton("Procurar");
        String[] column = {"Timestamp" ,"MSISDN", "Granted Service Units" , "rate " , "FinalPrice"};
        

        DefaultTableModel model = new DefaultTableModel(column, 0);
        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 60, 500, 500);

        label.setBounds(20, 20, 70, 25);
        textField.setBounds(90, 20,100, 25);
        button.setBounds(210, 20, 120, 25);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                if(!input.matches("^[1-9][0-9]{10}$")) JOptionPane.showMessageDialog(frame, "Msisdn is invalid (valid example: 12345678910)", "Error", JOptionPane.INFORMATION_MESSAGE);
                else
                {
                    model.setRowCount(0);
                    for (UseCost use: useCosts) {
                        if(use.getMsisdn().equals(input)){
                            model.addRow(new Object[]{use.getTimeStamp(), use.getMsisdn(), use.getGsu(), use.getCost(), use.getFinalPrice()});
                        }
                    }
                }
            }
        });

        frame.add(label);
        frame.add(textField);
        frame.add(button);
        frame.add(scrollPane);

        frame.setSize(550, 600);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
