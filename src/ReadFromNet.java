import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReadFromNet {
    public static int[][] readURL(String urlS){
        URL url ;
        try {
            url = new URL(urlS);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        BufferedReader read;
        try {
            read = new BufferedReader(
                    new InputStreamReader(url.openStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> lines = new ArrayList<>();
        int row = 0 ;
        int [][] matrix;
        char[][] matrixChar;
        do{
            try {
                lines.add(read.readLine());
                row++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }while(!(lines.get(row - 1) == null));

        row--;//do-whiledan kaynaklı

        matrixChar = new char[row+1][];
        matrix = new int[row+2][row+2];
        int k=0;
        for(int i = 1; i < row+1; i++){
            matrixChar[i] = lines.get(k).toCharArray();
            k++;
        }

        for(int i = 0; i < row+2; i++ ){
            matrix[0][i] =1;
            matrix[row+1][i] =1;

        }
        for(int i = 1; i < row+1; i++ ){
            matrix[i][0] =1;

            for(int j = 1; j < row; j++){
                matrix[i][j] = (matrixChar[i][j]) - 48;

            }
            matrix[i][row+1] =1;
        }
        lines.clear();
        try {
            read.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return matrix;
    }
}