import java.util.ArrayList;
import java.util.List;


class TimeRange {
    private int processId;
    private int startTime;
    private int stopTime;
    public TimeRange(int processId, int startTime, int stopTime) {
        this.startTime = startTime;
        this.processId = processId;
        this.stopTime = stopTime;
    }
    int getProcessId() { return processId; }
    int getStartTime() { return startTime; }
    int getStopTime() { return stopTime; }
}

public class Gantt {
    private boolean canContinue = true;
    private String name;
    private List<TimeRange> timeRangeList = new ArrayList<>();
    public Gantt(String name) {
        this.name = name;
    }
    public void add(TimeRange timeRange) {
        timeRangeList.add(timeRange);
    }
    private final String GAP = " ";

    public void setCanContinue(boolean canContinue) {
        this.canContinue = canContinue;
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(name + " Gantt Chart\n");
        StringBuilder table = new StringBuilder();
        List<Integer> timeList = new ArrayList<>();
        timeList.add(0);
        for (int x = 0; x < timeRangeList.size();) {
            TimeRange timeRange = timeRangeList.get(x);
            int start = timeRange.getStartTime();

            while (canContinue && x < timeRangeList.size() && timeRange.getProcessId() != -1 && timeRange.getProcessId() == timeRangeList.get(x).getProcessId()) {
                timeRange = timeRangeList.get(x);
                ++x;
            }
            while (x < timeRangeList.size() && timeRange.getProcessId() == -1 && timeRangeList.get(x).getProcessId() == -1) {
                timeRange = timeRangeList.get(x);
                ++x;
            }

            int stop = timeRange.getStopTime();

            table.append("|");

            timeList.add(timeRange.getStopTime());
            for (int i = 0; i < stop - start; ++i) {
                if (timeRange.getProcessId() != -1 && i == (stop - start) / 2)
                    table.append("P" + timeRange.getProcessId());
                table.append(GAP);
            }
            if (!canContinue) ++x;
        }
        table.append("|");
        for (int i = 0; i < table.length(); ++i) {
            string.append("-");
        }
        string.append("\n");
        string.append(table);
        string.append("\n");
        for (int i = 0; i < table.length(); ++i) {
            string.append("-");
        }
        string.append("\n");
        int iter = 0;
        for (int i = 0; i < table.length(); ++i) {
            if (table.charAt(i) == '|') {
                i += timeList.get(iter).toString().length() - 1;
                string.append(timeList.get(iter++));
            } else
                string.append(" ");
        }
        string.append("\n");
        return string.toString();
    }
}
