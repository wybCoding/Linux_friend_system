package dataStructure;

// 最小块是4k，默认规定单位是kb

public class BlockOfMemory {

    private int size;   //单位默认kb
    private boolean isAssignment;
    private int start;
    private Task task;

    public BlockOfMemory(int size, boolean isAssignment, int start, Task task) {
        this.size = size;
        this.isAssignment = isAssignment;
        this.start = start;
        this.task = task;
    }

    public BlockOfMemory(int start, int size, boolean isAssignment){
        this.size = size;
        this.isAssignment = isAssignment;
        this.start = start;
        this.task = null;
    }

    public BlockOfMemory[] divide(){   //分成两块,返回两份
        BlockOfMemory[] newBlock = {new BlockOfMemory(this.size/2, false, this.start, task),new BlockOfMemory(this.size/2, false, this.start + size/2, task)};
        return newBlock;
    }
    public static BlockOfMemory conquer(BlockOfMemory b1, BlockOfMemory b2){   //合成1块
        return new BlockOfMemory(Math.min(b1.getStart(), b2.getStart()), b1.size + b2.size, false);

    }
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }




    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isAssignment() {
        return isAssignment;
    }

    public void setAssignment(boolean assignment) {
        isAssignment = assignment;
    }

}
