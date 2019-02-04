package com.chsdwn.libgdx;

import javax.swing.border.EmptyBorder;

public class Snake {

    /* Variables */
    SnakeNode snakeHead;
    SnakeNode snakeTail;

    final int MOVE_UP = 0;
    final int MOVE_DOWN = 1;
    final int MOVE_LEFT = 2;
    final int MOVE_RIGHT = 3;

    final int EMPTY_SQUARE = 0;
    final int WALL_SQUARE = 1;
    final int POINT_SQUARE = 2;
    final int SNAKE_SQUARE = 3;

    boolean snakeGrow = false;

    int[][] field;

    int mod = 0;
    int x,y;
    /* --------- */

    public Snake(int x, int y, int[][] field){
        snakeHead = new SnakeNode(x, y);
        snakeTail = snakeHead;

        this.field = field;

        this.x = x;
        this.y = y;
    }

    public void addNode(){
        int x = snakeHead.x;
        int y = snakeHead.y;

        move();

        SnakeNode node = new SnakeNode(snakeHead.x, snakeHead.y);

        snakeHead.x = x;
        snakeHead.y = y;

        node.addNextNode(snakeHead);
        snakeHead = node;
    }

    public boolean move(){
        int moveX = snakeHead.x;
        int moveY = snakeHead.y;

        if(mod == MOVE_UP){
            moveY++;
        } else if(mod == MOVE_DOWN){
            moveY--;
        } else if(mod == MOVE_LEFT){
            moveX--;
        } else if(mod == MOVE_RIGHT){
            moveX++;
        }

        if(snakeGrow){
            if(field[moveX][moveY] == EMPTY_SQUARE || field[moveX][moveY] == POINT_SQUARE){
                snakeGrow = false;

                if(field[moveX][moveY] == POINT_SQUARE){
                    snakeGrow = true;
                }

                SnakeNode previousHead = snakeHead;
                snakeHead = new SnakeNode(moveX, moveY);
                snakeHead.nextSnakeNode = previousHead;
                field[snakeHead.x][snakeHead.y] = SNAKE_SQUARE;

                return true;
            } else{
                return false;
            }
        }

        // field check
        if(field[moveX][moveY] == EMPTY_SQUARE){
            field[snakeTail.x][snakeTail.y] = EMPTY_SQUARE;

            snakeHead.move(moveX, moveY);

            // update field
            field[snakeHead.x][snakeHead.y] = SNAKE_SQUARE;

            return true;
        } else if(field[moveX][moveY] == POINT_SQUARE){
            field[snakeTail.x][snakeTail.y] = EMPTY_SQUARE;

            snakeHead.move(moveX, moveY);

            // update field
            field[snakeHead.x][snakeHead.y] = SNAKE_SQUARE;

            if(!snakeGrow){
                snakeGrow = true;
            }

            return true;
        } else if(field[moveX][moveY] == WALL_SQUARE){
            return false;
        }

        return false;
    }

    public void setMod(int mod){
        if(mod < 4 && mod >= 0){

            // block to move opposite direction
            if (this.mod == MOVE_UP && mod == MOVE_DOWN) {
            } else if(this.mod == MOVE_DOWN && mod == MOVE_UP){
            } else if(this.mod == MOVE_LEFT && mod == MOVE_RIGHT){
            } else if(this.mod == MOVE_RIGHT && mod == MOVE_LEFT){
            } else{
                this.mod = mod;
            }
        }
    }
}
