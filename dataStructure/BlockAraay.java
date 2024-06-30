package dataStructure;

import java.util.ArrayList;
import java.util.List;

public class BlockAraay {
    List<BlockOfMemory> memorylist;

    protected int size;
    protected int length;
    public BlockAraay(int size){
        this.memorylist = new ArrayList<BlockOfMemory>();
        this.length = 0;
        this.size = size;
    }
    public void add(int start,boolean isAssigned){   //添加一个内存块
        memorylist.add(new BlockOfMemory(start, size ,isAssigned));
        this.length++;
    }
    public void add(BlockOfMemory blockOfMemory){   //添加一个内存块
        memorylist.add(blockOfMemory);
        this.length++;
    }
    public int getLength(){
        return this.length;
    }
    public BlockOfMemory remove(){
        if(length == 0) return null;
        int t = (int)(Math.random()*length);  //产生随机数
        BlockOfMemory block = this.memorylist.get(t);  //获取对应元素
        memorylist.remove(t);
        this.length--;
        return block;
    }
    public BlockOfMemory remove(int i){
        if(length == 0) return null;
        BlockOfMemory block = this.memorylist.get(i);  //获取对应元素
        memorylist.remove(i);
        this.length--;
        return block;
    }
    public BlockOfMemory get(int i){
        return this.memorylist.get(i);
    }
}
