package com.mygdx.game;

import java.util.ArrayList;

public class MultipleBlock extends BlockCommand {
    private ArrayList<BlockCommand> blockList = new ArrayList<>();
    private int times;
    private int curBlock;
    private int curTime;

    private boolean ifCondition;
    public MultipleBlock(String name, int times) {
        this.name = name;
        this.times = times;
        this.curBlock = 0;
        this.curTime = 0;
    }
    public MultipleBlock(String name, boolean ifCondition) {
        this.name = name;
        this.curBlock = 0;
        this.curTime = 0;
        this.times = 1;
        this.ifCondition = ifCondition;
    }
    public void setBlockList(ArrayList<BlockCommand> blockList) {
        this.blockList = blockList;
    }

    public int getTimes() {
        return times;
    }

    public int getCurBlock() {
        return curBlock;
    }

    public void increaseCurBlock() {
        this.curBlock++;
    }

    public void resetCurBlock() {
        this.curBlock = 0;
    }

    public int getCurTime() {
        return curTime;
    }

    public void resetCurTime() {
        this.curTime = 0;
    }

    public void increaseCurTime() {
        this.curTime++;
    }

    public ArrayList<BlockCommand> getBlockList() {
        return blockList;
    }

    public int getBlockListSize(){ return  blockList.size(); }

    public BlockCommand getBlock() {
        return blockList.get(curBlock);
    }

    @Override
    public void addBlockCommand(BlockCommand blockCommand) {
        this.blockList.add(blockCommand);
    }

    public boolean getIfCondition() {
        return ifCondition;
    }
}

