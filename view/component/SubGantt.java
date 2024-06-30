package view.component;

public class SubGantt {

    public static int TYPE_USED = 0, TYPE_FREE = 1;

    private String name;   //在矩形上显示的名称
    private int size;      //可以理解成为权重
    private int startPoint;   //起点

    private String processName;   // 别名（甘特图对应的主题等等）

    public SubGantt(String name, int size, int startPoint, int type, String processName) {
        this.name = name;
        this.size = size;
        this.startPoint = startPoint;
        this.type = type;
        this.processName = processName;
    }

    private int type;    //种类

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(int startPoint) {
        this.startPoint = startPoint;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
}
