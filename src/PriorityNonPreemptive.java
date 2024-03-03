import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PriorityNonPreemptive {
    public void execute(List<Task> taskList) {
        Gantt gantt = new Gantt("Priority Non Preemptive");
        Collections.sort(taskList, new Comparator<Task>() {
            @Override
            public int compare(Task t0, Task t1) {
                if (t0.getArrivalTime() != t1.getArrivalTime())
                    return Integer.compare(t0.getArrivalTime(), t1.getArrivalTime());
                return Integer.compare(t0.getPriority(), t1.getPriority());
            }
        });
        int curTime = 0;
        for (Task task: taskList) {
            if (curTime < task.getArrivalTime()) {
                gantt.add(new TimeRange(-1, curTime, task.getArrivalTime()));
            }
            curTime = Math.max(curTime, task.getArrivalTime());
            task.setFirstRunTime(curTime);
            int startTime = curTime;
            curTime += task.getBurstTime();
            int stopTime = curTime;
            task.setDoneTime(stopTime);
            gantt.add(new TimeRange(task.getId(), startTime, stopTime));
        }
        System.out.println(gantt);

        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;
        int totalResponseTime = 0;

        for (Task task: taskList) {
            totalTurnaroundTime += task.getDoneTime() - task.getArrivalTime();
            totalWaitingTime += task.getDoneTime() - task.getArrivalTime() - task.getBurstTime();
            totalResponseTime += task.getFirstRunTime() - task.getArrivalTime();
        }

        System.out.println("Average Turnaround Time: " + (double) totalTurnaroundTime / taskList.size());
        System.out.println("Average Waiting Time: " + (double) totalWaitingTime / taskList.size());
        System.out.println("Average Response Time: " + (double) totalResponseTime / taskList.size());

        System.out.println("\n");
    }
}
