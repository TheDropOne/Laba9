import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Semen on 13-Dec-16.
 */
/*
Имеется два принтера(два побочных потока) и несколько заданий с определенным временем исполнения,
использующих их для печати. Когда заданию необходимо вывести данные на печать, оно ожидает, пока
какой–либо из принтеров не освободится. После использования принтера заданием он становится
свободен и может быть использован любым другим процессом. Составить приложение,  моделирующее
работу принтеров. Для формирования  очереди заданий и времени печати использовать датчик
случайных чисел(максимальное время печати задания 5 сек). В выходной файл записать информацию
о том, какой принтер какое задание печатал.


Чтение задач может быть реализовано:
4-5 б. - чтение с консоли
5-6 б. - чтение из файла (однократное)
7-8 б. - чтение из файла с возможностью добавления задач в файл во время исполнения
программы + обработка ситуации, когда время выполнения задания выше 5 сек.
9-10 б. - мониторинг файлов на изменение + обработка ситуации, когда время выполнения задания выше 5 сек
 */
public class Runner {

    public static boolean isReady = false;
    private static final String INPUT_FILENAME = "input.txt";

    public static void main(String[] args) {
        File inputFile = new File(INPUT_FILENAME);
        File outputFile = new File("output.txt");
        List<Printer> printers = new ArrayList<>();
        printers.addAll(Arrays.asList(new Printer(), new Printer()));

        try {
            FileWriter fw = new FileWriter(outputFile);
            List<Task> taskList = readListOfStrings(inputFile);

            Path inputPath = Paths.get(inputFile.getAbsolutePath().replace(File.separator + INPUT_FILENAME, ""));
            WatchService watchService = FileSystems.getDefault().newWatchService();
            inputPath.register(watchService,
                    StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey key = watchService.take();
            for (int i = 0; i < printers.size(); i++) {
                printers.get(i).start();
            }
            int taskListSize = taskList.size();
            for (int i = 0; i < taskListSize; i++) {
                boolean taskAdded = false;
                taskListSize = taskList.size();
                do {
                    for (int j = 0; j < printers.size(); j++) {
                        if (!printers.get(j).isBusy()) {
                            printers.get(j).addTask(taskList.get(i));
                            fw.write(printers.get(j).getPrinterName() + " was printing task #" + String.valueOf(i) + System.lineSeparator());
                            taskAdded = true;
                            break;
                        }
                    }
                } while (!taskAdded);
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();
                    if (kind == StandardWatchEventKinds.ENTRY_MODIFY && fileName.toString().equals(INPUT_FILENAME)) {
                        List<Task> tempList = readListOfStrings(inputFile);
                        //i -= (tempList.size() - taskList.size());
                        taskList.clear();
                        taskList.addAll(tempList);
                        System.out.println("Input file has changed!!!");
                    }
                }
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
            fw.close();
            isReady = true;
            for (int i = 0; i < printers.size(); i++) {
                printers.get(i).join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("success!");
    }

    private static List<Task> readListOfStrings(File file) {
        BufferedReader br = null;
        List<Task> listOfTasks = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(file));
            String tempString = br.readLine();
            while (tempString != null) {
                listOfTasks.add(new Task(tempString));
                tempString = br.readLine();
            }
        } catch (IOException ex) {
            System.out.println("Reading from file successfully failed. IOException");
        } finally {
            try {
                br.close();
            } catch (IOException | NullPointerException ex) {
                System.out.println("Stream didn't closed or not exists. Reading from file failed. ");
            }
        }
        return listOfTasks;
    }
}
