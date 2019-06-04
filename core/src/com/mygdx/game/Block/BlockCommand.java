package com.mygdx.game.Block;

public abstract class BlockCommand {
    protected String name;
    public BlockCommand() {}
    public BlockCommand(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public abstract void addBlockCommand(BlockCommand blockCommand);
}
