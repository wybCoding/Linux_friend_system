package viewModel;

import dataStructure.BlockAraay;
import dataStructure.BlockAraayList;
import dataStructure.BlockOfMemory;
import dataStructure.Task;
import view.GUI;
import view.component.SubGantt;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import static util.StringToIntChecker.isInteger;

public class ViewModel implements ActionListener {    //介于view和model层的中间层，相当于模拟进程在内存中分配的管理者
    GUI gui;

    public BlockAraayList blockAraayList = new BlockAraayList();   // 内存块的记录
    public ViewModel(GUI gui){
        this.gui = gui;

//        //对内存显示的ui进行初始化的设置（获取初始空内存块）
//        List<SubGantt> list = FromMemoryToGantt();   //获取到list
//        gui.gantt.refreshWithNewList(list);          //上述两句话相当于获取当前内存块分配情况并转化为甘特图可以使用的数据结构
    }

    // 处理按钮事件
    @Override
    public void actionPerformed(ActionEvent e) {        //响应按钮事件，模拟进程分配和删除事件
        if(e.getSource() == gui.btn_1){  //add事件

            //先判定用户输入是否合法
            if( "".equals(gui.tf_1.getText()) || !isInteger(gui.tf_2.getText())){
                JOptionPane.showMessageDialog(null, "输入非法");
                return;
            }

            int time = 1;
            String selectedItem = gui.cb.getSelectedItem().toString();
            if ("KB".equals(selectedItem)) time = 1;
            else if ("MB".equals(selectedItem)) time = 1024;
            if ("GB".equals(selectedItem)) time = 1024 * 1024;

            Task task = new Task(Integer.parseInt(gui.tf_2.getText()) * time, gui.tf_1.getText());
            if(blockAraayList.assign(task)) {   //执行任务
                gui.model.addRow(new String[]{task.getName(), String.valueOf(task.getSize()) + "KB"});  //设置任务在表中可见

                List<SubGantt> list = FromMemoryToGantt();   //获取到list
                gui.gantt.refreshWithNewList(list);          //上述两句话相当于获取当前内存块分配情况并转化为甘特图可以使用的数据结构
            }
        }
        else if (e.getSource() == gui.btn_2) {  //删除进程
            int row = gui.table.getSelectedRow();
            if(row >= 0) {
                if(blockAraayList.delete((String) gui.table.getValueAt(row, 0))) {

                    gui.model.removeRow(row);
                    List<SubGantt> list = FromMemoryToGantt();   //获取到list
                    gui.gantt.refreshWithNewList(list);
                }

            }
        }
    }

    //这是一个辅助方法，用来将数据层的内存块转化为甘特图能接受的图形块
    public List<SubGantt>  FromMemoryToGantt(){
        List<SubGantt> list = new ArrayList<SubGantt>();
        List<BlockOfMemory> oldList = blockAraayList.traversal();
        for (int i=0;i<oldList.size();i++){
            BlockOfMemory old = oldList.get(i);
            SubGantt New;
            if(old.getTask() == null) {
                New = new SubGantt("",old.getSize(),old.getStart() , SubGantt.TYPE_FREE, "");
            }else {
                New = new SubGantt(old.getTask().getName(),old.getSize(),old.getStart() , SubGantt.TYPE_USED,"process:"+old.getTask().getName() + old.getTask().getSize());
            }
            list.add(New);    //相当于转化成功的某一项
        }
        return list;
    }

}
// 进一步优化思路：设计为类似MVVM的构造，将进程存储一个链表中，让表格的数据源头基于该链表，不过课设的重点不在这里，因此可以不设计
