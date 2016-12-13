import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Semen on 13-Dec-16.
 */
public class Runner {
    public static void main(String[] args) {
        File inputFile = new File("input.txt");
        File inputOnceFile = new File("inputOnce.txt");
        File outputFile = new File("output.txt");
        Printer mFirstPrinter = new Printer();
        Printer mSecondPrinter = new Printer();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String command = br.readLine();
            while (!command.equals("exit")){
                mFirstPrinter.addTask(new Task("Hello world!"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
