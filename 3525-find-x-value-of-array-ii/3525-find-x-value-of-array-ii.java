class Solution {

    static class Node {
        long product;
        long[] prefix;

        Node(int k) {
            this.prefix = new long[k];
        }
    }

    private int n, k;
    private int[] nums;
    private Node[] tree;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.n = nums.length;
        this.k = k;
        this.nums = nums;

        this.tree = new Node[4 * n];
        build(1, 0, n - 1);

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            this.nums[index] = value;
            update(1, 0, n - 1, index, value);

            Node res = query(1, 0, n - 1, start, n - 1);
            result[i] = (int) res.prefix[x];
        }

        return result;
    }

    // ---------------- BUILD ----------------

    private void build(int node, int l, int r) {
        if (l == r) {
            tree[node] = makeLeaf(nums[l]);
            return;
        }

        int mid = l + (r - l) / 2;
        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    // ---------------- LEAF ----------------

    private Node makeLeaf(int value) {
        Node res = new Node(k);
        int rem = (int) (((long) value % k + k) % k);

        res.product = rem;
        res.prefix[rem] = 1;

        return res;
    }

    // ---------------- MERGE ----------------

    private Node merge(Node left, Node right) {
        if (left == null) return right;
        if (right == null) return left;

        Node res = new Node(k);
        res.product = (left.product * right.product) % k;

        // Prefixes entirely within left segment
        for (int r = 0; r < k; r++) {
            res.prefix[r] += left.prefix[r];
        }

        // Prefixes covering all of left segment + a prefix of right segment
        for (int r = 0; r < k; r++) {
            if (right.prefix[r] > 0) {
                int rem = (int) ((left.product * r) % k);
                res.prefix[rem] += right.prefix[r];
            }
        }

        return res;
    }

    // ---------------- UPDATE ----------------

    private void update(int node, int l, int r, int index, int value) {
        if (l == r) {
            tree[node] = makeLeaf(value);
            return;
        }

        int mid = l + (r - l) / 2;
        if (index <= mid) {
            update(node * 2, l, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, r, index, value);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    // ---------------- QUERY ----------------

    private Node query(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            return tree[node];
        }

        int mid = l + (r - l) / 2;

        if (qr <= mid) {
            return query(node * 2, l, mid, ql, qr);
        }
        if (ql > mid) {
            return query(node * 2 + 1, mid + 1, r, ql, qr);
        }

        Node left = query(node * 2, l, mid, ql, qr);
        Node right = query(node * 2 + 1, mid + 1, r, ql, qr);

        return merge(left, right);
    }
}