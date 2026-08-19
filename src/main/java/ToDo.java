public class ToDo extends Task {
    private String type;

    public ToDo(String desc) {
        super(desc);
        this.type = "T";
    }

    @Override
    public String toString() {
        return String.format("[%s]%s", this.type, super.toString());
    }
}
