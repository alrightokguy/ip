public class Task {
    private final String desc;
    private boolean done;

    public Task(String desc) {
        this.desc = desc;
        this.done = false;
    }

    public void mark() {
        this.done = true;
    }

    public void unmark() {
        this.done = false;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", this.done ? "X" : " ", this.desc);
    }
}