import java.util.List;

public class Worker {

    public String name;
    public String position;
    public final int id;
    public List<String> permissions;

    public Worker(String name, String position, int id, List<String> permissions) {
        this.name = name;
        this.position = position;
        this.id = id;
        this.permissions = permissions;
    }

    public String workerDetails() {

        String pers = "";

        for (String permission : permissions) {
            pers += permission + ", ";
        }

        return name + " on position " + position +
                " has id " + id +
                " and permissions " + pers;
    }

    public void changeName(String name) {
        this.name = name;
    }
}