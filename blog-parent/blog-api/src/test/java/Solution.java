import java.util.*;

class Solution {
    public int busyStudent(int[] startTime, int[] endTime, int queryTime) {
        List<int[]> times = new ArrayList<>();
        int n = startTime.length;
        for (int i = 0; i < n; i++) {
            times.add(new int[]{startTime[i], endTime[i]});
        }
        Collections.sort(times, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[0], o2[0]);
            }
        });
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int[] timeInterval = times.get(i);
            if (timeInterval[0] > queryTime) break;
            if (timeInterval[1] >=queryTime) {
                // [0]<=queryTime
                // [1]>=queryTime
                ans++;
            }
        }
        return ans;
    }
}