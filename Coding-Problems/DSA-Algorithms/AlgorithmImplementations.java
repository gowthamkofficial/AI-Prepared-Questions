/**
 * DSA ALGORITHMS - COMPLETE JAVA IMPLEMENTATIONS
 */

import java.util.*;

public class AlgorithmImplementations {

    // ==================== SORTING ALGORITHMS ====================

    /**
     * MERGE SORT
     * Time: O(n log n) | Space: O(n)
     */
    public static void mergeSort(int[] arr) {
        if (arr.length <= 1) return;
        mergeSortHelper(arr, 0, arr.length - 1);
    }

    private static void mergeSortHelper(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortHelper(arr, left, mid);
            mergeSortHelper(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        
        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];
        
        System.arraycopy(temp, 0, arr, left, temp.length);
    }

    /**
     * QUICK SORT
     * Time: O(n log n) avg | O(n²) worst | Space: O(log n)
     */
    public static void quickSort(int[] arr) {
        if (arr.length == 0) return;
        quickSortHelper(arr, 0, arr.length - 1);
    }

    private static void quickSortHelper(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSortHelper(arr, low, pi - 1);
            quickSortHelper(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * HEAP SORT
     * Time: O(n log n) | Space: O(1)
     */
    public static void heapSort(int[] arr) {
        int n = arr.length;
        
        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        
        // Extract elements from heap
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }
        
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    /**
     * INSERTION SORT
     * Time: O(n²) | Space: O(1)
     */
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    /**
     * COUNTING SORT
     * Time: O(n + k) | Space: O(k)
     */
    public static int[] countingSort(int[] arr, int max) {
        int[] count = new int[max + 1];
        int[] result = new int[arr.length];
        
        for (int num : arr) {
            count[num]++;
        }
        
        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }
        
        for (int i = arr.length - 1; i >= 0; i--) {
            result[count[arr[i]] - 1] = arr[i];
            count[arr[i]]--;
        }
        
        return result;
    }

    /**
     * RADIX SORT
     * Time: O(d * (n + k)) | Space: O(n + k)
     */
    public static void radixSort(int[] arr) {
        if (arr.length == 0) return;
        
        int max = Arrays.stream(arr).max().orElse(0);
        
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp);
        }
    }

    private static void countingSortByDigit(int[] arr, int exp) {
        int[] count = new int[10];
        int[] output = new int[arr.length];
        
        for (int num : arr) {
            count[(num / exp) % 10]++;
        }
        
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        for (int i = arr.length - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
        
        System.arraycopy(output, 0, arr, 0, arr.length);
    }

    // ==================== SEARCHING ALGORITHMS ====================

    /**
     * BINARY SEARCH
     * Time: O(log n) | Space: O(1)
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) return mid;
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1;
    }

    /**
     * BINARY SEARCH - FIRST OCCURRENCE
     */
    public static int binarySearchFirst(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                result = mid;
                right = mid - 1;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }

    /**
     * BINARY SEARCH - LAST OCCURRENCE
     */
    public static int binarySearchLast(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                result = mid;
                left = mid + 1;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }

    // ==================== GRAPH ALGORITHMS ====================

    /**
     * DFS - RECURSIVE
     * Time: O(V + E) | Space: O(V)
     */
    public static List<Integer> dfs(Map<Integer, List<Integer>> graph, int start) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        dfsHelper(graph, start, visited, result);
        return result;
    }

    private static void dfsHelper(Map<Integer, List<Integer>> graph, int node, 
                                  Set<Integer> visited, List<Integer> result) {
        visited.add(node);
        result.add(node);
        
        for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                dfsHelper(graph, neighbor, visited, result);
            }
        }
    }

    /**
     * DFS - ITERATIVE
     */
    public static List<Integer> dfsIterative(Map<Integer, List<Integer>> graph, int start) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        
        stack.push(start);
        
        while (!stack.isEmpty()) {
            int node = stack.pop();
            
            if (!visited.contains(node)) {
                visited.add(node);
                result.add(node);
                
                for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        
        return result;
    }

    /**
     * BFS
     * Time: O(V + E) | Space: O(V)
     */
    public static List<Integer> bfs(Map<Integer, List<Integer>> graph, int start) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        
        visited.add(start);
        queue.add(start);
        
        while (!queue.isEmpty()) {
            int node = queue.poll();
            result.add(node);
            
            for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        
        return result;
    }

    /**
     * DIJKSTRA'S ALGORITHM
     * Time: O((V + E) log V) | Space: O(V)
     */
    public static Map<Integer, Integer> dijkstra(
            Map<Integer, List<int[]>> graph, int start) {
        Map<Integer, Integer> distances = new HashMap<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[1], b[1])
        );
        
        // Initialize
        for (int node : graph.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.offer(new int[]{start, 0});
        
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int dist = current[1];
            
            if (dist > distances.get(node)) continue;
            
            for (int[] edge : graph.getOrDefault(node, new ArrayList<>())) {
                int neighbor = edge[0];
                int weight = edge[1];
                int newDist = dist + weight;
                
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    pq.offer(new int[]{neighbor, newDist});
                }
            }
        }
        
        return distances;
    }

    /**
     * TOPOLOGICAL SORT - DFS
     * Time: O(V + E) | Space: O(V)
     */
    public static List<Integer> topologicalSort(Map<Integer, List<Integer>> graph) {
        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        
        for (int node : graph.keySet()) {
            if (!visited.contains(node)) {
                topologicalDFS(graph, node, visited, stack);
            }
        }
        
        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        
        return result;
    }

    private static void topologicalDFS(Map<Integer, List<Integer>> graph, int node,
                                      Set<Integer> visited, Stack<Integer> stack) {
        visited.add(node);
        
        for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                topologicalDFS(graph, neighbor, visited, stack);
            }
        }
        
        stack.push(node);
    }

    /**
     * TOPOLOGICAL SORT - KAHN'S ALGORITHM
     */
    public static List<Integer> topologicalSortKahn(Map<Integer, List<Integer>> graph) {
        Map<Integer, Integer> inDegree = new HashMap<>();
        Set<Integer> allNodes = new HashSet<>();
        
        for (int node : graph.keySet()) {
            inDegree.put(node, 0);
            allNodes.add(node);
        }
        
        for (int node : graph.keySet()) {
            for (int neighbor : graph.get(node)) {
                inDegree.put(neighbor, inDegree.getOrDefault(neighbor, 0) + 1);
                allNodes.add(neighbor);
            }
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (int node : allNodes) {
            if (inDegree.getOrDefault(node, 0) == 0) {
                queue.add(node);
            }
        }
        
        List<Integer> result = new ArrayList<>();
        
        while (!queue.isEmpty()) {
            int node = queue.poll();
            result.add(node);
            
            for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }
        
        return result.size() == allNodes.size() ? result : new ArrayList<>();
    }

    // ==================== STRING ALGORITHMS ====================

    /**
     * KMP STRING MATCHING
     * Time: O(n + m) | Space: O(m)
     */
    public static int kmpSearch(String text, String pattern) {
        int[] lps = buildLPS(pattern);
        int i = 0, j = 0;
        
        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            
            if (j == pattern.length()) {
                return i - j;
            } else if (i < text.length() && pattern.charAt(j) != text.charAt(i)) {
                j = j != 0 ? lps[j - 1] : 0;
                if (j < pattern.length() && pattern.charAt(j) == text.charAt(i)) {
                    i++;
                    j++;
                }
            }
        }
        
        return -1;
    }

    private static int[] buildLPS(String pattern) {
        int[] lps = new int[pattern.length()];
        int len = 0, i = 1;
        
        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        
        return lps;
    }

    /**
     * RABIN-KARP STRING MATCHING
     * Time: O(n + m) average | Space: O(1)
     */
    public static int rabinKarp(String text, String pattern) {
        final int BASE = 256;
        final int PRIME = 101;
        
        int patternHash = hashFunction(pattern, BASE, PRIME);
        
        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            int windowHash = hashFunction(text.substring(i, i + pattern.length()), BASE, PRIME);
            
            if (patternHash == windowHash) {
                if (text.substring(i, i + pattern.length()).equals(pattern)) {
                    return i;
                }
            }
        }
        
        return -1;
    }

    private static int hashFunction(String str, int base, int prime) {
        int hash = 0;
        int pow = 1;
        
        for (int i = str.length() - 1; i >= 0; i--) {
            hash = (hash + str.charAt(i) * pow) % prime;
            pow = (pow * base) % prime;
        }
        
        return hash;
    }

    /**
     * LONGEST COMMON SUBSEQUENCE
     * Time: O(m * n) | Space: O(m * n)
     */
    public static String lcs(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        // Reconstruct LCS
        StringBuilder result = new StringBuilder();
        int i = m, j = n;
        
        while (i > 0 && j > 0) {
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                result.insert(0, s1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        
        return result.toString();
    }

    /**
     * EDIT DISTANCE (LEVENSHTEIN)
     * Time: O(m * n) | Space: O(m * n)
     */
    public static int editDistance(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j],
                                    Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                }
            }
        }
        
        return dp[m][n];
    }

    // ==================== UTILITY DATA STRUCTURES ====================

    /**
     * UNION-FIND WITH PATH COMPRESSION AND UNION BY RANK
     */
    public static class UnionFind {
        int[] parent;
        int[] rank;
        
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }
        
        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX == rootY) return false;
            
            // Union by rank
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            
            return true;
        }
        
        public boolean connected(int x, int y) {
            return find(x) == find(y);
        }
    }

    /**
     * TRIE (PREFIX TREE)
     */
    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord = false;
    }

    public static class Trie {
        TrieNode root = new TrieNode();
        
        public void insert(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                node.children.putIfAbsent(c, new TrieNode());
                node = node.children.get(c);
            }
            node.isEndOfWord = true;
        }
        
        public boolean search(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                if (!node.children.containsKey(c)) return false;
                node = node.children.get(c);
            }
            return node.isEndOfWord;
        }
        
        public boolean startsWith(String prefix) {
            TrieNode node = root;
            for (char c : prefix.toCharArray()) {
                if (!node.children.containsKey(c)) return false;
                node = node.children.get(c);
            }
            return true;
        }
    }

    /**
     * SEGMENT TREE FOR RANGE SUM QUERIES
     */
    public static class SegmentTree {
        int[] tree;
        int n;
        
        public SegmentTree(int[] arr) {
            n = arr.length;
            tree = new int[4 * n];
            build(arr, 0, 0, n - 1);
        }
        
        private void build(int[] arr, int node, int start, int end) {
            if (start == end) {
                tree[node] = arr[start];
            } else {
                int mid = (start + end) / 2;
                build(arr, 2 * node + 1, start, mid);
                build(arr, 2 * node + 2, mid + 1, end);
                tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
            }
        }
        
        public int rangeSum(int l, int r) {
            return query(0, 0, n - 1, l, r);
        }
        
        private int query(int node, int start, int end, int l, int r) {
            if (r < start || end < l) return 0;
            if (l <= start && end <= r) return tree[node];
            
            int mid = (start + end) / 2;
            return query(2 * node + 1, start, mid, l, r) +
                   query(2 * node + 2, mid + 1, end, l, r);
        }
    }

    public static void main(String[] args) {
        System.out.println("Algorithm Implementations Compiled Successfully!");
    }
}
