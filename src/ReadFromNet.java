import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadFromNet {

    static int matrixLenght = 0;
    static int[][] matrix;
    public static int[][] readURL(){
        URL url = null;

        String url1 = ("http://bilgisayar.kocaeli.edu.tr/prolab2/url1.txt");
        String url2 = ("http://bilgisayar.kocaeli.edu.tr/prolab2/url2.txt");
        if(Main.urlCount %2 == 0){
            try {
                url = new URL(url1);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                url = new URL(url2);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }

        BufferedReader read;
        try {
            read = new BufferedReader(
                    new InputStreamReader(url.openStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String line;
        int i = 0 ;
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
        try {
            read.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return matrix;
    }
}
