import java.awt.*;
import java.util.Random;

public class Grid extends PhysicMember{
    private Block[][]blocks;
    public Block[][] getBlocks() {
        return blocks;
    }

    public void setBlocks(Block[][] blocks) {
        this.blocks = blocks;
    }

    public Grid(int[][]matrix) {
        Random random = new Random();
        blocks = new Block[matrix.length][matrix.length];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix.length; j++){
                blocks[i][j] = new Block(i, j, (j * (Block.BLOCK_WIDHT + 1) + 1), 1 + (i * (Block.BLOCK_WIDHT + 1)),
                        Block.BLOCK_WIDHT, Block.BLOCK_WIDHT, matrix[i][j]);//Point i-Point j-Konum-Konum-Genişlik-Yükseklik-State
                if(blocks[i][j].state != 0 && blocks[i][j].state != 1){
                    int rand = random.nextInt(0,100);
                    if(rand > 75){
                        blocks[i][j].state = 0;
                        matrix[i][j] = 0;
                    }
                }
            }
        }
    }
    public Block setStartLocation(Block[][] blocks){
        Random rand = new Random();
        int i;
        int j;
        do{
            i = rand.nextInt(blocks.length);
            j = rand.nextInt(blocks.length);
        }
        while (blocks[i][j].state != 0);

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
