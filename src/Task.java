import java.time.LocalDate;

public class Task {
    int id; //Номер задачи
    LocalDate creation_date; //Дата создания задачи
    LocalDate realization_date; //Дата выполнения задачи
    String name; //Название задачи
    String description; //Описание Задачи
    boolean realization_status; //Статус выполнения задачи (вып/невып)

    public Task(String name, String description, int days) {
        this.name = name;
        this.description = description;
        creation_date = LocalDate.now();
        realization_date = LocalDate.now().plusDays(days);
        realization_status = false;
    }

    public void changeRealization() {
        realization_status = (!realization_status);
    } //Изменяет статус выполнения задачи
}
