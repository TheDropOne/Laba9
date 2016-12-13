import java.util.ArrayList;
import java.util.List;

/**
 * Created by Semen on 13-Dec-16.
 */
public class Printer {
    private List<Task> taskList;

    public Printer() {
        this.taskList = new ArrayList<>();
    }

    public void addTask(Task task) {
        this.taskList.add(task);
    }

    public int calculateQueue() {
        int sum = 0;
        for (Task task : taskList) {
            sum += task.getTime();
        }
        return sum;
    }
}