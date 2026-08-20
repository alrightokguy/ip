abstract class Task {

    private final String desc;
    private boolean status;

    public Task(String desc) {
        this.desc = desc;
        this.status = false;
    }

    public void mark() {
        this.status = true;
    }

    public void unmark() {
        this.status = false;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", this.status ? "X" : " ", this.desc);
    }
}