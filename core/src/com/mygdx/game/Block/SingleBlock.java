package com.mygdx.game.Block;

public class SingleBlock extends BlockCommand {
    private int step;

    public SingleBlock(String name, int step) {
        this.name = name;
        this.step = step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getStep(){
        return step;
    }

    @Override
    public void addBlockCommand(BlockCommand blockCommand) {

    }
}
