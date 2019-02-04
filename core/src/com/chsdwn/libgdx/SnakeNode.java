package com.chsdwn.libgdx;

public class SnakeNode {

    SnakeNode nextSnakeNode;

    public int x;
    public int y;

    public SnakeNode(int x, int y){
        this.nextSnakeNode = null;

        this.x = x;
        this.y = y;
    }

    public void addNextNode(SnakeNode nextSnakeNode){
        this.nextSnakeNode = nextSnakeNode;
    }

    public void move(int x, int y){
        if(nextSnakeNode != null){
            nextSnakeNode.move(this.x, this.y);
        }

        this.x = x;
        this.y = y;
    }
}
