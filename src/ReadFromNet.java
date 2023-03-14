import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReadFromNet {
    public static int[][] readURL(){
        URL url ;
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

        matrixChar = new char[row][];
        matrix = new int[row][row];
        for(int i = 0; i < row; i++){
            matrixChar[i] = lines.get(i).toCharArray();
        }
        for(int i = 0; i < row; i++ ){
            for(int j = 0; j < row; j++){
                matrix[i][j] = (matrixChar[i][j]) - 48;
            }
        }
        lines.clear();
        try {
            read.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return matrix;
    }
    public static int[][] readURL2(){
        URL url ;
        try {
            url = new URL("http://bilgisayar.kocaeli.edu.tr/prolab2/url2.txt");
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

        matrixChar = new char[row][];
        matrix = new int[row][row];
        for(int i = 0; i < row; i++){
            matrixChar[i] = lines.get(i).toCharArray();
        }
        for(int i = 0; i < row; i++ ){
            for(int j = 0; j < row; j++){
                matrix[i][j] = (matrixChar[i][j]) - 48;
            }
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
