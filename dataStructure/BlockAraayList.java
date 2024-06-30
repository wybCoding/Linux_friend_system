package dataStructure;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.ceil;
import static java.lang.Math.pow;
import static util.Log.log;

// 单位默认是kb
public class BlockAraayList implements MemoryConst,BlockAraayListFunction{
    public static int MEMORY_BLOCK_TYPE_LENGTH = (int)log(2, MEMORY_SIZE) - (int)log(2, MINI_SIZE);
    public static int LAST_BLOCKARRAYLIST_ELEMENT = MEMORY_BLOCK_TYPE_LENGTH;   //表示线性表最后一个元素位置，因此索引左闭右闭
    private final Logger log;
    private List<BlockAraay> freeBlockAraayList;
    private List<BlockAraay> usedBlockAraayList;


    public BlockAraayList(){

        log = Logger.getLogger(BlockAraayList.class);
        PropertyConfigurator.configure("log4j.properties");       //配置日志（输出到控制台）

        freeBlockAraayList = new ArrayList<BlockAraay>();
        usedBlockAraayList = new ArrayList<BlockAraay>();

        for (int i = 0; i <=MEMORY_BLOCK_TYPE_LENGTH; i++) {
            this.freeBlockAraayList.add(new BlockAraay((int)Math.pow(2, i) * MINI_SIZE));   //相当于对空闲内存表设置初始链表
            this.usedBlockAraayList.add(new BlockAraay((int)Math.pow(2, i)* MINI_SIZE));
        }
        this.freeBlockAraayList.get(freeBlockAraayList.size() - 1).add(0, false);

    }

    public boolean findName(String name){   //按名称遍历寻求某元素
        boolean find_flag = false;
        for(int i = 0; i <= LAST_BLOCKARRAYLIST_ELEMENT ; i++){
            BlockAraay temp1 =usedBlockAraayList.get(i);
            for (int j = 0 ; j < temp1.getLength();j++){
                if(temp1.get(j).getTask().getName().equals(name)) find_flag =true;
            }
        }
        return find_flag;
    }
    public int[] findNameLocation(String name){   //按名称遍历寻求某元素并返回位置i，j
        for(int i = 0; i <= LAST_BLOCKARRAYLIST_ELEMENT ; i++){
            BlockAraay temp1 =usedBlockAraayList.get(i);
            for (int j = 0 ; j < temp1.getLength();j++){
                if(temp1.get(j).getTask().getName() .equals(name)) {
                    int []temp = {i, j};
                    return temp;
                }
            }
        }
        return null;
    }
    public boolean _assignTarget(Task task, int i){ //尝试直接分配指定位置内存块给某进程，然后挂到被使用链表中
        if(freeBlockAraayList.get(i).getLength() == 0)  return false;       //找不到一个内存块，返回失败
        else{
            BlockOfMemory block = freeBlockAraayList.get(i).remove();
            block.setTask(task);                    //注意要分配任务!!!
            usedBlockAraayList.get(i).add(block);
            return true;
        }
    }
    public int _findFitI(int i_now){   //找到合适的i
        while (i_now <= LAST_BLOCKARRAYLIST_ELEMENT){
            if(freeBlockAraayList.get(i_now).getLength() > 0 )  break;
            i_now++;
        }
        return i_now;
    }

    @Override
    public boolean assign(Task task) {  //分配算法

        log.debug("start to assign");

        if(findName(task.getName())){
            log.debug("name repeat");
            return false;   //表明分配失败
        }
        int locate = (int)ceil(log(2, task.getSize())) - 2;      //locate表示当前所需要的最佳内存块的位置
        return _recursionAssign(locate, locate,task,false);    //蕴含分配结果
    }

    @Override
    public boolean _recursionAssign(int locate, int i,Task task, boolean cannotAssignFlag) {  //函数：将i处的任务争取分配给locate
        if(cannotAssignFlag) return false;
        if(i > LAST_BLOCKARRAYLIST_ELEMENT){      //base-case
            cannotAssignFlag = true ; return false;
        }
        if(locate == i){   //能否直接分配
            if(_assignTarget(task, i)) return true;   //尝试直接分配,此时成功！
            else return _recursionAssign(locate, i+1, task, cannotAssignFlag);  //不能直接分配，进一层
        }
        else {     //先拆解再分配
            i = _findFitI(i);   //找到一个合适的第i层：至少含有一个内存块
            if(i <= LAST_BLOCKARRAYLIST_ELEMENT){    //至少有一块，可以分解
                BlockOfMemory block = freeBlockAraayList.get(i).remove();
                BlockOfMemory []newBlocks = block.divide();
                freeBlockAraayList.get(i - 1).add(newBlocks[0]);
                freeBlockAraayList.get(i - 1).add(newBlocks[1]);
                return _recursionAssign(locate, i-1, task, cannotAssignFlag);
            }//return false可能有问题
            else {
                cannotAssignFlag = true ; return false;
            }
        }
    }

    @Override
    public boolean delete(String name) {

        log.debug("start to delete");

        int[] result = findNameLocation(name);
        if(result == null) {
            System.out.println("failed to find");
            return false;
        }
        int i = result[0]; int j = result[1];
        //获取待删除的内存块
        BlockOfMemory block = usedBlockAraayList.get(i).remove(j);
        block.setTask(null);      //相当于清除进程（停止引用）；
        freeBlockAraayList.get(i).add(block);
        conquer();
        return true;
    }

    @Override
    public void conquer() {
        int bottom = LAST_BLOCKARRAYLIST_ELEMENT;  //获取最后一个元素位置的索引
        for(int i = 0; i <= bottom ;i++){
            sibconquer(i);    //实现子链合并
        }
    }

    private void sibconquer(int i) {
        BlockAraay blockAraayThisState = freeBlockAraayList.get(i);  //获取本层的连续表
        boolean all_finish = false;
        while (!all_finish){

            all_finish = true;

            for (int j = 0; j<blockAraayThisState.length; j++){
                for(int k = j+1;k<blockAraayThisState.length; k++){
                    BlockOfMemory b1 =blockAraayThisState.get(j);
                    BlockOfMemory b2 =blockAraayThisState.get(k);
                    if(Math.abs(b1.getStart()  -  b2.getStart()) == (int)pow(2, i + 2)){   //比较差值是否符合(数学关系)
                        int min = Math.min(b1.getStart(),b2.getStart());   //获取到最小值
                        if(min % (2 * (int)pow(2, i + 2)) == 0){   //可合并
                            BlockOfMemory new_b = BlockOfMemory.conquer(b1, b2);
                            freeBlockAraayList.get(i).remove(Math.min(j,k));
                            freeBlockAraayList.get(i).remove(Math.max(j, k) - 1);            //删除对应两元素 , 要注意元素位置的变化！
                            freeBlockAraayList.get(i+1).add(new_b);
                            all_finish = false;
                        }
                    }
                }
            }
        }


    }
    public List<BlockOfMemory> traversal(){     //遍历函数
        List<BlockOfMemory> list = new ArrayList<BlockOfMemory>();
        for(int i=0; i<=LAST_BLOCKARRAYLIST_ELEMENT; i++){
            BlockAraay blockAraayFree = freeBlockAraayList.get(i);
            BlockAraay blockAraayUsed = usedBlockAraayList.get(i);
            for(int j=0; j<blockAraayFree.length;j++){
                list.add(blockAraayFree.get(j));
            }
            for(int j=0; j<blockAraayUsed.length;j++){
                list.add(blockAraayUsed.get(j));
            }
        }
        return list;
    }
}
