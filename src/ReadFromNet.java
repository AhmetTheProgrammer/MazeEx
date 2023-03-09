import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadFromNet {
    static int i = 0;
    static int matrixLenght = 0;
    static int[][] matrix;
    public static int[][] readURL(){
        URL url;
        try {
            url = new URL("http://bilgisayar.kocaeli.edu.tr/prolab2/url1.txt");
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
        String line;
        while (true)
        {
            try {
                if ((line = read.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for(int j = 0; j < line.length() ; j++){
                matrixLenght++;
            }
            if(i == 0){
                matrix = new int[matrixLenght][matrixLenght];
            }
            for(int j = 0; j < line.length() ; j++){
                int value = Integer.parseInt(String.valueOf(line.charAt(j)));
                matrix[i][j] = value;
            }
            i++;
        }
        for(int i = 0;i < matrix.length;i++){
            for(int j = 0;j< matrix[0].length;j++){
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        try {
            read.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return matrix;
    }
}
