import java.util.*;

public class PriorityPreemptive {
    public void execute(List<Task> taskList) {
        Gantt gantt = new Gantt("Priority Preemptive");
        Collections.sort(taskList, new Comparator<Task>() {
            @Override
            public int compare(Task t0, Task t1) {
                if (t0.getArrivalTime() == t1.getArrivalTime())
                    return Integer.compare(t0.getPriority(), t1.getPriority());
                return Integer.compare(t0.getArrivalTime(), t1.getArrivalTime());
            }
        });

        Queue<Task> queue = new PriorityQueue<>(new Comparator<Task>() {
            @Override
            public int compare(Task t0, Task t1) {
                if (t0.getPriority() != t1.getPriority())
                    return Integer.compare(t0.getPriority(), t1.getPriority());
                if (t0.getArrivalTime() != t1.getArrivalTime())
                    return Integer.compare(t0.getArrivalTime(), t1.getArrivalTime());
                return Integer.compare(t0.getRemainingTime(), t1.getRemainingTime());
            }
        });

        int curTime = 0;
        for (int i = 0; i < taskList.size(); ++i) {
            while (curTime < taskList.get(i).getArrivalTime()) {
                if (queue.isEmpty()) {
                    int startTime = curTime;
                    curTime = taskList.get(i).getArrivalTime();
                    int stopTime = curTime;
                    gantt.add(new TimeRange(-1, startTime, stopTime));
                    break;
                }
                Task task = queue.poll();
                int startTime = curTime;
                curTime = Math.min(curTime + task.getRemainingTime(), taskList.get(i).getArrivalTime());
                int stopTime = curTime;
                if (startTime < stopTime) {
                    gantt.add(new TimeRange(task.getId(), startTime, stopTime));
                    task.setFirstRunTime(startTime);
                }
                if (curTime < startTime + task.getRemainingTime()) {
                    task.setRemainingTime(task.getRemainingTime() - (stopTime - startTime));
                    queue.add(task);
                } else {
                    task.setDoneTime(stopTime);
                }
            }
            queue.add(taskList.get(i));
        }
        while (!queue.isEmpty()) {
            Task task = queue.poll();
            int startTime = curTime;
            task.setFirstRunTime(startTime);
            curTime += task.getRemainingTime();
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
