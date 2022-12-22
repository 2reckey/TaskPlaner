import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskPlaner {
    ArrayList<Task> task_array= new ArrayList<>();



    public void ShowAll(){
        for (Task task: task_array){
            System.out.println(task.id+" "+task.name);
        }
    }

    public void ShowTrueRealization(){
        for (Task task: task_array){
            if (task.realization_status) System.out.println(task.id+" "+task.name);
        }
    }

    public void ShowFalseRealization(){
        for (Task task: task_array){
            if (!task.realization_status) System.out.println(task.id+" "+task.name);
        }
    }

    public void AddTask(){
        String name=ScanStr();
        String description=ScanStr();
        int days=ScanInt();
        Task task=new Task(name,description,days);
        task_array.add(task);
        task.id=task_array.size();
    }

    public void DeleteTask(){
        ShowAll();
        int id=ScanInt()-1;
        task_array.remove(id);
        for (int i=id; i<task_array.size();++i) task_array.get(i).id=i+1;
    }

    public void EditTask(int id){
        int chose=ScanInt();
        Task task=task_array.get(id-1);
        if (chose==1){
            task.name=ScanStr();
        }
        if (chose==2){
            task.description=ScanStr();
        }
        if (chose==3){
            int i= ScanInt();
            if (i==2) task.changeRealization();
        }
    }

    public int ScanInt(){
        Scanner scan=new Scanner(System.in);
        return scan.nextInt();
    }

    public String ScanStr(){
        Scanner scan=new Scanner(System.in);
        return scan.nextLine();
    }
}
