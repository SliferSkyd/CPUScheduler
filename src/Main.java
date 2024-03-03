import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Task> list = new ArrayList<>();
    private static int Q = 0;
    public static void readInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        Q = scanner.nextInt();
        int curId = 0;
        while (scanner.hasNext()) {
            int burstTime = scanner.nextInt();
            int priority = scanner.nextInt();
            int arrivalTime = scanner.nextInt();
            list.add(new Task(++curId, arrivalTime, burstTime, priority));
        }
    }

    public static List<Task> getList() {
        List<Task> newList = new ArrayList<>();
        for (Task task: list) {
            newList.add(new Task(task.getId(), task.getArrivalTime(), task.getBurstTime(), task.getPriority()));
        }
        return newList;
    }

    public static void main(String[] args) throws FileNotFoundException {
        readInput();
        FCFS fcfs = new FCFS();
        fcfs.execute(getList());

        SJFPreemptive sjfPreemptive = new SJFPreemptive();
        sjfPreemptive.execute(getList());

        SJFNonPreemptive sjfNonPreemptive = new SJFNonPreemptive();
        sjfNonPreemptive.execute(getList());

        RoundRobin roundRobin = new RoundRobin();
        roundRobin.execute(getList(), Q);

        PriorityPreemptive priorityPreemptive = new PriorityPreemptive();
        priorityPreemptive.execute(getList());

        PriorityNonPreemptive priorityNonPreemptive = new PriorityNonPreemptive();
        priorityNonPreemptive.execute(getList());
    }
}
