/**
 * Created by Semen on 13-Dec-16.
 */
public class Printer extends Thread {


    private String name;
    private Task currentTask;

    private boolean isBusy;

    private static int count;

    public Printer() {
        currentTask = null;
        this.name = "Printer " + String.valueOf(count++);
    }

    public void addTask(Task task) {
        this.currentTask = task;
        this.isBusy = true;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public String getPrinterName() {
        return name;
    }

    public void run() {
        try {
            while (!Runner.isReady) {
                if (currentTask == null) {
                    do {
                        //System.out.println(this + "Ama free, waiting");
                        this.sleep(500);
                    } while (currentTask == null);
                }
                System.out.println(this + currentTask.getBuffer());
                sleep(currentTask.getTime());
                this.isBusy = false;
                this.currentTask = null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.name + " printing : ";
    }
}