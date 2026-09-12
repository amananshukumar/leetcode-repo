import java.util.*;

class Solution {

    static class Interval {
        int l, r, w, idx;

        Interval(int l, int r, int w, int idx) {
            this.l = l;
            this.r = r;
            this.w = w;
            this.idx = idx;
        }
    }

    static class State {
        long score;
        List<Integer> indices;

        State(long score, List<Integer> indices) {
            this.score = score;
            this.indices = indices;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        Interval[] arr = new Interval[n];

        for (int i = 0; i < n; i++) {
            arr[i] = new Interval(
                intervals.get(i).get(0),
                intervals.get(i).get(1),
                intervals.get(i).get(2),
                i
            );
        }

        Arrays.sort(arr, (a, b) -> {
            if (a.r != b.r)
                return Integer.compare(a.r, b.r);
            return Integer.compare(a.l, b.l);
        });

        // previous[i] = last interval whose right < arr[i].left
        int[] previous = new int[n];

        for (int i = 0; i < n; i++) {
            int low = 0;
            int high = i - 1;
            int ans = -1;

            while (low <= high) {
                int mid = low + (high - low) / 2;

                if (arr[mid].r < arr[i].l) {
                    ans = mid;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            previous[i] = ans;
        }

        State[][] dp = new State[n][5];

        for (int i = 0; i < n; i++) {

            for (int k = 0; k <= 4; k++) {

                State skip = (i > 0) ? dp[i - 1][k] : new State(0, new ArrayList<>());

                State take = null;

                if (k > 0) {

                    State prev;

                    if (previous[i] == -1) {
                        prev = new State(0, new ArrayList<>());
                    } else {
                        prev = dp[previous[i]][k - 1];
                    }

                    List<Integer> list = new ArrayList<>(prev.indices);
                    list.add(arr[i].idx);

                    take = new State(
                        prev.score + arr[i].w,
                        list
                    );
                }

                if (take == null) {
                    dp[i][k] = skip;
                } else {
                    dp[i][k] = better(skip, take);
                }
            }
        }

        State answer = dp[n - 1][4];

        int[] result = new int[answer.indices.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = answer.indices.get(i);
        }

        Arrays.sort(result);

        return result;
    }

    static State better(State a, State b) {

        if (a.score != b.score) {
            return a.score > b.score ? a : b;
        }

        List<Integer> x = new ArrayList<>(a.indices);
        List<Integer> y = new ArrayList<>(b.indices);

        Collections.sort(x);
        Collections.sort(y);

        int len = Math.min(x.size(), y.size());

        for (int i = 0; i < len; i++) {
            if (!x.get(i).equals(y.get(i))) {
                return x.get(i) < y.get(i) ? a : b;
            }
        }

        return x.size() <= y.size() ? a : b;
    }
}