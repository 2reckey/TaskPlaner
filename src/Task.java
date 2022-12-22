import java.time.LocalDate;

public class Task {
    int id;
    LocalDate creation_date;
    LocalDate realization_date;
    String name;
    String description;
    boolean realization_status;

    public Task(String name, String description, int days){
        this.name=name;
        this.description=description;
        creation_date=LocalDate.now();
        realization_date=LocalDate.now().plusDays(days);
        realization_status=false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void changeRealization(){
        realization_status=(!realization_status);
    }
}
