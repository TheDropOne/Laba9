/**
 * Created by Semen on 13-Dec-16.
 */
public class Task {
    private int time;
    private String buffer;

    public Task(String buffer) {
        this.buffer = buffer;
        this.time = buffer.length();
    }

    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public int getTime() {

        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
