package view;

import dataStructure.MemoryConst;
import viewModel.ViewModel;
import view.component.Gantt;
//import view.component.Gantt;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GUI {
    public final Gantt gantt;
    private final JFrame jFrame;
    private final JPanel jPanel_1;
    public final JButton btn_1;
    private final JPanel jPanel_2;
    public final JButton btn_2;
    private final JPanel jPanel_3;
    private final JScrollPane scrollPane;
    private final JLabel label_1;
    private final JLabel label_2;
    private final JLabel label_3;
    public final JTextField tf_1;
    public final JTable table;
    public final DefaultTableModel model;
    private final JPanel jPanel_1_sib;
    private final JLabel label_1_2;
    private final JLabel label_1_1;
    private final JLabel label_1_3;
    public final JTextField tf_2;
    public final JComboBox<String> cb;


    public GUI(){

        //new
        ViewModel viewModel = new ViewModel(this);
        jFrame = new JFrame("Linux伙伴系统模拟器");
        jPanel_1 = new JPanel();
        jPanel_1_sib = new JPanel();
        jPanel_2 = new JPanel();
        jPanel_3 = new JPanel();
        label_1 = new JLabel("设置进程的大小:");
        label_1_1 = new JLabel("进程名称");
        label_1_2 = new JLabel("进程大小");
        label_1_3 = new JLabel("单位设置");
        label_2 = new JLabel("进程列表");
        label_3 = new JLabel(String.format("进程图示(黄色表示分配，灰色为未分配)(当前内存最大值为%d MB)",MemoryConst.MEMORY_SIZE/1024));
        btn_1 = new JButton("添加");
        btn_2 = new JButton("删除");
        tf_1 = new JTextField();
        tf_2 = new JTextField();
        gantt = new Gantt(null, MemoryConst.MEMORY_SIZE);

        String[] options_1 = {"KB","MB","GB"};
        cb = new JComboBox<>(options_1);
        String[] columnNames = {"name", "size"};
        model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);
        //设置Panel1_sib布局
        jPanel_1_sib.setLayout(new GridLayout(3, 2));
        jPanel_1_sib.add(label_1_1);
        jPanel_1_sib.add(tf_1);
        jPanel_1_sib.add(label_1_2);
        jPanel_1_sib.add(tf_2);
        jPanel_1_sib.add(label_1_3);
        jPanel_1_sib.add(cb);
        // 设置Panel1的布局及其组件
        jPanel_1.setLayout(new BorderLayout());
        jPanel_1.add(label_1, BorderLayout.NORTH);
        jPanel_1.add(jPanel_1_sib, BorderLayout.CENTER);
        jPanel_1.add(btn_1, BorderLayout.SOUTH);
        //设置Panel2的布局和组件
        jPanel_2.setLayout(new BorderLayout());
        jPanel_2.add(label_2, BorderLayout.NORTH);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane(table);
        jPanel_2.add(scrollPane, BorderLayout.CENTER);
        jPanel_2.add(btn_2, BorderLayout.SOUTH);
        //设置Panel3的布局和控件
        jPanel_3.setLayout(new BorderLayout());
        jPanel_3.add(label_3, BorderLayout.NORTH);
        jPanel_3.add(gantt, BorderLayout.CENTER);
        //frame设置
        jFrame.setSize(1200, 600);
        jFrame.setLayout(new GridLayout(3, 1));
        jFrame.add(jPanel_1);
        jFrame.add(jPanel_2);
        jFrame.add(jPanel_3);

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);

        //Event
        btn_1.addActionListener(viewModel);
        btn_2.addActionListener(viewModel);
    }

}
