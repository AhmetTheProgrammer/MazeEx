import java.awt.*;
import java.util.Random;

public class Grid extends PhysicMember{
    private static Block[][]blocks;
    public Block[][] getBlocks() {
        return blocks;
    }

    public void setBlocks(Block[][] blocks) {
        Grid.blocks = blocks;
    }

    public Grid(int[][]matrix) {
        blocks = new Block[matrix.length][matrix.length];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix.length; j++){
                blocks[i][j] = new Block(50 + (j * Block.BLOCK_WIDHT), 50 + (i * Block.BLOCK_WIDHT ),
                        Block.BLOCK_WIDHT, Block.BLOCK_WIDHT, matrix[i][j]);//Konum-Konum-Genişlik-Yükseklik-State
            }
        }
    }
    public Block setStartLocation(Block[][] blocks){
        Random rand = new Random();
        int i = rand.nextInt(20);
        int j = rand.nextInt(20);
        if(blocks[i][j].getState() == 0){
            return blocks[i][j];
        }
        else{
            setStartLocation(blocks);
        }
        return blocks[i][j];
    }
    public void drawtoScreen(Graphics g){
        for(int i = 0; i < blocks.length; i++){
            for(int j = 0; j < blocks.length; j++){
                blocks[i][j].drawtoScreen(g);
            }
        }
    }
}
