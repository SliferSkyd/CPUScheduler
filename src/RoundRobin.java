import java.util.*;

public class RoundRobin {
    public void execute(List<Task> taskList, int Q) {
        Gantt gantt = new Gantt("Round Robin");
        Collections.sort(taskList, new Comparator<Task>() {
            @Override
            public int compare(Task t0, Task t1) {
                if (t0.getArrivalTime() != t1.getArrivalTime())
                    return Integer.compare(t0.getArrivalTime(), t1.getArrivalTime());
                return Integer.compare(t0.getPriority(), t1.getPriority());
            }
        });
        Queue<Task> queue = new LinkedList<>();
        int curTime = 0;
        int i = 0;
        while (queue.size() > 0 || i < taskList.size()) {
            if (queue.size() == 0) {
                int startTime = curTime;
                curTime = taskList.get(i).getArrivalTime();
                int stopTime = curTime;
                if (startTime < stopTime) {
                    gantt.add(new TimeRange(-1, startTime, stopTime));
                }
                queue.add(taskList.get(i++));
                continue;
            }
            Task task = queue.poll();
            int startTime = curTime;
            task.setFirstRunTime(startTime);
            curTime += Math.min(Q, task.getRemainingTime());
            int stopTime = curTime;
            gantt.add(new TimeRange(task.getId(), startTime, stopTime));

            while (i < taskList.size() && taskList.get(i).getArrivalTime() <= curTime) {
                queue.add(taskList.get(i++));
            }

            if (task.getRemainingTime() > Q) {
                task.setRemainingTime(task.getRemainingTime() - Q);
                queue.add(task);
            } else {
                task.setDoneTime(stopTime);
            }
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
