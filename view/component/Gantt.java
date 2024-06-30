package view.component;

import dataStructure.BlockOfMemory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Gantt extends JComponent {    //修改数据结构，修改索引方式（start+数组）
    private List<SubGantt> ganttList = new ArrayList<SubGantt>();    //每个块的数组,是可以为空的!
    public static int totalLength;    //总长度（按比例）
    public Gantt(List<SubGantt> ganttList, int totalLength){
        this.ganttList = ganttList;
        this.totalLength = totalLength;           //赋值totalLength
    }

    @Override
   public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(ganttList != null) {
            drawList(g, this.getWidth(), this.getY());
        }
    }



    public void drawList(Graphics g, int width, int y){
        Iterator<SubGantt> iterator = ganttList.iterator();
        while(iterator.hasNext()) {
            //使用size_of_memory变量记录迭代器对应实数值
            SubGantt node = iterator.next();
            int size_of_memory = node.getSize();
            int x = node.getStartPoint();                                             //获取起点
            double proportion = (double)size_of_memory / (double)totalLength ;        //求解比例
            //颜色变化用于区分
            Color color = getSubGanttColor(node);
            g.setColor(color);
            g.fillRect((int)((x * ((double)getWidth())/(double)totalLength)), 0, (int) (getWidth() * proportion), getHeight());
            //设置文字
            g.setColor(Color.RED);
            g.drawString(String.valueOf(node.getSize()), (int)((x * ((double)getWidth())/(double)totalLength)), getHeight()/2);
            // 获取文本的高度
            FontMetrics fontMetrics = g.getFontMetrics();
            int height = fontMetrics.getHeight();
            // 在y坐标上加上文本的高度，使第二行文本显示在第一行文本的下方
            g.drawString(String.valueOf(node.getProcessName()), (int)((x * ((double)getWidth())/(double)totalLength)), getHeight()/2 + height);
            // 为矩形设置黑色边框
            g.setColor(Color.BLACK);
            g.drawRect((int)((x * ((double)getWidth())/(double)totalLength)), 0, (int) (getWidth() * proportion), getHeight());
        }
    }
    public Color getSubGanttColor(SubGantt node){
        if (node.getType() == SubGantt.TYPE_FREE) return new Color(224,224,224);
        else return  new Color(255,255,153);
    }
    public void refreshWithNewList(List<SubGantt> ganttList){
        this.ganttList = ganttList;
        repaint();
    }
}
