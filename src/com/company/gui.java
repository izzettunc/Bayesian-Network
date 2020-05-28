package com.company;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.text.AttributedCharacterIterator;

public class gui {

    private JFrame window_main;

    public void createMainWindow()
    {
        window_main = new JFrame("Bayesian Network");
        window_main.setPreferredSize(new Dimension(600,300));
        window_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel(null);

        //Creating data_preprocess UI

        JButton data_ofd = new JButton("Select Train Data");
        data_ofd.setBounds(10,30,200,30);

        JLabel data_label = new JLabel("File Name : Not selected");
        data_label.setBounds(220,30,370,30);

        JButton network_ofd = new JButton("Select Network Data");
        network_ofd.setBounds(10,70,200,30);

        JLabel network_label = new JLabel("File Name : Not selected");
        network_label.setBounds(220,70,370,30);

        JButton test_ofd = new JButton("Select Test Data");
        test_ofd.setBounds(10,110,200,30);

        JLabel test_label = new JLabel("File Name : Not selected");
        test_label.setBounds(220,110,370,30);

        JCheckBox cpt_cbox = new JCheckBox("Print CPT");
        cpt_cbox.setBounds(7,150,80,20);

        JCheckBox test_acc_cbox = new JCheckBox("Print Test Accuracy Metrics");
        test_acc_cbox.setBounds(7,180,180,20);

        JCheckBox train_acc_cbox = new JCheckBox("Print Train Accuracy Metrics");
        train_acc_cbox.setBounds(7,210,185,20);

        JLabel target_feature_label = new JLabel("Target Feature");
        target_feature_label.setBounds(225,150,150,30);

        JTextField target_feature_textfield = new JTextField("class");
        target_feature_textfield.setBounds(225,180,150,20);

        JButton run_button = new JButton("Run");
        run_button.setBounds(440,180,125,50);

        //Listeners

        data_ofd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    JFileChooser data_fc = new JFileChooser();
                    data_fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
                    int result = data_fc.showOpenDialog(main);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = data_fc.getSelectedFile();
                        data_label.setText("File Name : " + selectedFile.getName());
                        data_label.setToolTipText(selectedFile.getAbsolutePath());
                        settings.train_path=selectedFile.getAbsolutePath();
                    }
            }
        });

        network_ofd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser type_fc = new JFileChooser();
                type_fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int result = type_fc.showOpenDialog(main);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = type_fc.getSelectedFile();
                    network_label.setText("File Name : " + selectedFile.getName());
                    network_label.setToolTipText(selectedFile.getAbsolutePath());
                    settings.network_path=selectedFile.getAbsolutePath();
                }
            }
        });

        test_ofd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser ordinal_fc = new JFileChooser();
                ordinal_fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int result = ordinal_fc.showOpenDialog(main);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = ordinal_fc.getSelectedFile();
                    test_label.setText("File Name : " + selectedFile.getName());
                    test_label.setToolTipText(selectedFile.getAbsolutePath());
                    settings.test_path=selectedFile.getAbsolutePath();
                }
            }
        });




        main.add(target_feature_label);
        main.add(target_feature_textfield);
        main.add(data_ofd);
        main.add(data_label);
        main.add(network_label);
        main.add(network_ofd);
        main.add(test_ofd);
        main.add(test_label);
        main.add(cpt_cbox);
        main.add(train_acc_cbox);
        main.add(test_acc_cbox);
        main.add(run_button);



        cpt_cbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                settings.print_cpt=!settings.print_cpt;
            }
        });

        train_acc_cbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                settings.print_train_acc=!settings.print_train_acc;
            }
        });

        test_acc_cbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                settings.print_test_acc=!settings.print_test_acc;
            }
        });

        run_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean all_inputs_are_valid=true;

                if(settings.train_path==null)
                {
                    JOptionPane.showMessageDialog(main,"Please select a train file","Error : Null data error",JOptionPane.ERROR_MESSAGE);
                    all_inputs_are_valid=false;
                }
                if(settings.network_path==null)
                {
                    JOptionPane.showMessageDialog(main,"Please select a network file","Error : Null data type error",JOptionPane.ERROR_MESSAGE);
                    all_inputs_are_valid=false;
                }
                if(settings.test_path==null)
                {
                    JOptionPane.showMessageDialog(main,"Please select a test file","Error : Null data type error",JOptionPane.ERROR_MESSAGE);
                    all_inputs_are_valid=false;
                }
                String tempVal = target_feature_textfield.getText();
                if(tempVal.equals(""))
                {
                    JOptionPane.showMessageDialog(main,"Target feature cannot be left empty","Error : Invalid value error",JOptionPane.ERROR_MESSAGE);
                    all_inputs_are_valid=false;
                }
                if(all_inputs_are_valid)
                {
                    settings.target_feature = target_feature_textfield.getText();
                    settings.run();
                }
            }
        });

        window_main.add(main);

        window_main.setVisible(true);
        window_main.setResizable(false);
        window_main.pack();
        window_main.setLocationRelativeTo(null);
    }

    static void show_error_message(String message,String title)
    {
        JOptionPane.showMessageDialog(null,message,"Error : "+title,JOptionPane.ERROR_MESSAGE);
    }

    static void show_message(String message,String title)
    {
        JOptionPane.showMessageDialog(null,message,title,JOptionPane.INFORMATION_MESSAGE);
    }
}
