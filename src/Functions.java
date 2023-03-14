public class Functions {
    public static int[][] generateSolutionMatrix(int [][]matrix,int[][]solutionMatrix){
        for(int i = 0; i < matrix.length ; i ++){
            for(int j = 0; j < matrix.length; j++){
                if(matrix[i][j] == 1){
                    solutionMatrix[i][j] = 0;
                }
                else if(matrix[i][j] == 2){
                    solutionMatrix[i][j] = 0;
                }
                else if(matrix[i][j] == 3){
                    solutionMatrix[i][j] = 0;
                }
                else{
                    solutionMatrix[i][j] = 1;
                }
            }
        }
        return solutionMatrix;
    }
}
