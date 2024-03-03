public class Task {
    private int id;
    private int arrivalTime;
    private int burstTime;
    private int priority;
    private int doneTime;
    private int remainingTime;
    private int firstRunTime;
    public Task(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
        this.doneTime = -1;
        this.firstRunTime = -1;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(int doneTime) {
        this.doneTime = doneTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getFirstRunTime() {
        return firstRunTime;
    }

    public void setFirstRunTime(int firstRunTime) {
        if (this.firstRunTime == -1)
            this.firstRunTime = firstRunTime;
    }
}
