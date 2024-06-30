package dataStructure;

public interface BlockAraayListFunction {
    public boolean assign(Task task);
    public boolean _recursionAssign(int locate, int i, Task task, boolean cannotAssignFlag);   //locate表示需要分配的最小内存块的位置，i表示当前所在的内存块位置,最后一项必须写false
    public boolean delete(String name);  //删除函数
    public void conquer();  //合并函数
}
