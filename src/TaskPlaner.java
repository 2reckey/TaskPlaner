import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TaskPlaner {
    ArrayList<Task> task_array = new ArrayList<>(); //Список всех задач

    public TaskPlaner() throws InterruptedException {
        while (true) {
            Thread.sleep(1000);
            PlanerInterface();
        }
    }

    public void PlanerInterface() { //Интерфейс взаимодействия с пользователем
        if (task_array.size() == 0) {
            System.out.println("-----------------------");
            System.out.println("Необходимо добавить задание!");
            AddTask();
        } else {
            System.out.println("-----------------------");
            System.out.println("1) Добавить задание");
            System.out.println("2) Удалить задание");
            System.out.println("3) Изменить задание");
            System.out.println("4) Список всех заданий");
            System.out.println("5) Список всех выполненых заданий");
            System.out.println("6) Список всех невыполненых заданий");
            System.out.println("7) Посмотреть детальную информацию о задании");
            System.out.println("8) Поиск по дате");
            int chose = ScanInt();
            if (chose == 1) AddTask();
            if (chose == 2) DeleteTask();
            if (chose == 3) EditTask();
            if (chose == 4) ShowAll(task_array);
            if (chose == 5) ShowTrueRealization(task_array);
            if (chose == 6) ShowFalseRealization(task_array);
            if (chose == 7) ShowDetails();
            if (chose == 8) DateSearch();
        }
    }

    public void ShowTask(Task task) { //Показывает основные параметны задачи
        System.out.println(task.id + ") | Название задачи: " + task.name + " | Дата Выполнения задачи: " + task.realization_date + (task.realization_status ? " | " : " | НЕ") + "ВЫПОЛНЕНО");
    }

    public void TaskDetails(Task task) { //Детали задачи
        System.out.println("Задача Номер:  " + task.id);
        System.out.println("Название:  " + task.name);
        System.out.println("Дата создания:  " + task.creation_date);
        System.out.println("Дата выполнения:  " + task.realization_date);
        System.out.println("Статус:  " + (task.realization_status ? "" : "НЕ") + "ВЫПОЛНЕНО");
        System.out.println("Описание задачи:");
        System.out.println(task.description);
    }

    public void ShowDetails() { //Показывает детали задач
        ShowAll(task_array);
        System.out.println("Введите номер задачи для которой хотите узнать подробности:");
        int id = ScanID();
        TaskDetails(task_array.get(id));
    }

    public void ShowAll(ArrayList<Task> task_array) { //Показывает все задачи
        task_array.forEach(task -> ShowTask(task));
        if (task_array.size()==0) System.out.println("Задачи отсутствуют!");
    }

    public void ShowTrueRealization(ArrayList<Task> task_array) { //Показывает выполненые задачи
        ArrayList<Task> true_task_array=task_array.stream().filter(task -> task.realization_status)
                .collect(Collectors.toCollection(ArrayList::new));
        ShowAll(true_task_array);
    }

    public void ShowFalseRealization(ArrayList<Task> task_array) { //Показывает невыполненые задачи
        ArrayList<Task> false_task_array=task_array.stream().filter(task -> !task.realization_status)
                .collect(Collectors.toCollection(ArrayList::new));
        ShowAll(false_task_array);
    }

    public void DateSearch(){ //поиск по дате
        System.out.println("1) Поиск по дате создания");
        System.out.println("2) Поиск по дате выполнения");
        boolean bool_status=(ScanInt() == 2);
        LocalDate user_date=ScanDate();
        ArrayList<Task> date_tasks=task_array.stream()
                .filter(task -> (user_date.equals(task.creation_date)&&!bool_status)
                        ||((user_date.equals(task.realization_date))&&bool_status))
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("1) Список всех заданий");
        System.out.println("2) Список всех выполненых заданий");
        System.out.println("3) Список всех невыполненых заданий");
        int chose=ScanInt();
        if (chose == 1) ShowAll(date_tasks);
        if (chose == 2) ShowTrueRealization(date_tasks);
        if (chose == 3) ShowFalseRealization(date_tasks);
    }

    public void AddTask() { //Создает новую задачу
        System.out.println("Введите название задачи:");
        String name = ScanStr();
        System.out.println("Введите описание задачи:");
        String description = ScanStr();
        System.out.println("Введите срок выполнения задачи в днях:");
        int days = ScanInt();
        Task task = new Task(name, description, days);
        task_array.add(task);
        task.id = task_array.size();
        ShowTask(task);
        System.out.println("Задание добавлено!");
    }

    public void DeleteTask() { //Удаляет задачу
        ShowAll(task_array);
        System.out.println("Введите номер задачи которую хотите удалить:");
        int id = ScanID();
        task_array.remove(id);
        System.out.println("Задача удалена!");
        for (int i = id; i < task_array.size(); ++i) task_array.get(i).id = i + 1;
    }

    public void EditTask() { //Изменяет задачу
        ShowAll(task_array);
        System.out.println("Введите номер задачи которую хотите изменить:");
        int id = ScanID();
        System.out.println("1) Изменить название задачи");
        System.out.println("2) Изменить описание задачи");
        System.out.println("3) Изменить статус выполнения задачи задачи");
        int chose = ScanInt();
        if ((chose > 0) && (chose < 4)) {
            Task task = task_array.get(id);
            if (chose == 1) {
                System.out.println("Введите новое название задачи:");
                task.name = ScanStr();
            }
            if (chose == 2) {
                System.out.println("Введите новое описание задачи:");
                task.description = ScanStr();
            }
            if (chose == 3) task.changeRealization();
            ShowTask(task);
            System.out.println("Изменено!");
        }
    }

    public int ScanInt() {  //Ввод целых чисел
        try {
            Scanner scan = new Scanner(System.in);
            return scan.nextInt();
        } catch (Exception exception) {
            System.out.println("Неверный ввод данных, попробуйте еще раз!");
            return ScanInt();
        }
    }

    public String ScanStr() { //Ввод строки
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    public int ScanID() { //Ввод ID
        int id = ScanInt() - 1;
        if ((id >= 0) && (id < task_array.size())) return id;
        else {
            System.out.println("Неверный ввод данных, попробуйте еще раз!");
            return ScanID();
        }
    }

    public LocalDate ScanDate(){ //Ввод даты
        try{
        System.out.println("Введите год:");
        int year=ScanInt();
        System.out.println("Введите месяц в числовом формате:");
        int mouth=ScanInt();
        System.out.println("Введите день:");
        int day=ScanInt();
        return LocalDate.of(year,mouth,day);
        }
        catch (Exception exception){
            System.out.println("Неверно введена дата, попробуйте еще раз!");
            return ScanDate();
        }
    }
}
