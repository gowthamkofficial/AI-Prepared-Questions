/**
 * JAVA SOLUTIONS FOR FREQUENTLY ASKED INTERVIEW PROBLEMS
 * All solutions include time and space complexity analysis
 */

import java.util.*;

public class InterviewSolutions {

    // ==================== STRING PROBLEMS ====================

    /**
     * 1. PALINDROME CHECK
     * Time: O(n) | Space: O(1)
     */
    public static boolean isPalindrome(String s) {
        String clean = s.toLowerCase().replaceAll("[^a-z0-9]", "");
        int left = 0, right = clean.length() - 1;
        
        while (left < right) {
            if (clean.charAt(left) != clean.charAt(right)) return false;
            left++;
            right--;
        }
        
        return true;
    }

    /**
     * 2. REVERSE STRING
     * Time: O(n) | Space: O(n)
     */
    public static String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public static void reverseStringInPlace(char[] chars) {
        int left = 0, right = chars.length - 1;
        
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
    }

    /**
     * 3. LONGEST SUBSTRING WITHOUT REPEATING CHARACTERS
     * Time: O(n) | Space: O(min(n, charset))
     */
    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> charMap = new HashMap<>();
        int maxLen = 0, start = 0;
        
        for (int end = 0; end < s.length(); end++) {
            char c = s.charAt(end);
            if (charMap.containsKey(c) && charMap.get(c) >= start) {
                start = charMap.get(c) + 1;
            }
            charMap.put(c, end);
            maxLen = Math.max(maxLen, end - start + 1);
        }
        
        return maxLen;
    }

    /**
     * 4. VALID PARENTHESES
     * Time: O(n) | Space: O(n)
     */
    public static boolean isValidParentheses(String s) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> pairs = new HashMap<>();
        pairs.put(')', '(');
        pairs.put(']', '[');
        pairs.put('}', '{');
        
        for (char c : s.toCharArray()) {
            if (pairs.containsKey(c)) {
                if (stack.isEmpty() || stack.pop() != pairs.get(c)) {
                    return false;
                }
            } else {
                stack.push(c);
            }
        }
        
        return stack.isEmpty();
    }

    /**
     * 5. LONGEST PALINDROMIC SUBSTRING
     * Time: O(n²) | Space: O(1)
     */
    public static String longestPalindrome(String s) {
        if (s.length() < 2) return s;
        
        int start = 0, maxLen = 1;
        
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            
            if (len > maxLen) {
                maxLen = len;
                start = i - (len - 1) / 2;
            }
        }
        
        return s.substring(start, start + maxLen);
    }

    private static int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    /**
     * 6. ANAGRAM CHECK
     * Time: O(n) | Space: O(1)
     */
    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
            count[t.charAt(i) - 'a']--;
        }
        
        for (int c : count) {
            if (c != 0) return false;
        }
        
        return true;
    }

    /**
     * 7. GROUP ANAGRAMS
     * Time: O(n * k log k) | Space: O(n * k)
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);
            map.computeIfAbsent(sorted, k -> new ArrayList<>()).add(str);
        }
        
        return new ArrayList<>(map.values());
    }

    /**
     * 8. LONGEST COMMON PREFIX
     * Time: O(S * n) | Space: O(1)
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i >= strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        
        return strs[0];
    }

    /**
     * 9. STRING COMPRESSION
     * Time: O(n) | Space: O(1) excluding output
     */
    public static String compressString(String s) {
        StringBuilder compressed = new StringBuilder();
        int count = 1;
        
        for (int i = 0; i < s.length(); i++) {
            if (i + 1 >= s.length() || s.charAt(i) != s.charAt(i + 1)) {
                compressed.append(s.charAt(i)).append(count);
                count = 1;
            } else {
                count++;
            }
        }
        
        return compressed.length() < s.length() ? compressed.toString() : s;
    }

    // ==================== ARRAY PROBLEMS ====================

    /**
     * 11. TWO SUM
     * Time: O(n) | Space: O(n)
     */
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        
        return new int[]{};
    }

    /**
     * 12. THREE SUM
     * Time: O(n²) | Space: O(1)
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) break;
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            
            int left = i + 1, right = nums.length - 1;
            int target = -nums[i];
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return result;
    }

    /**
     * 13. MERGE SORTED ARRAYS
     * Time: O(n + m) | Space: O(n + m)
     */
    public static int[] mergeSortedArrays(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;
        
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] <= arr2[j]) {
                result[k++] = arr1[i++];
            } else {
                result[k++] = arr2[j++];
            }
        }
        
        while (i < arr1.length) {
            result[k++] = arr1[i++];
        }
        
        while (j < arr2.length) {
            result[k++] = arr2[j++];
        }
        
        return result;
    }

    /**
     * 14. REMOVE DUPLICATES FROM SORTED ARRAY
     * Time: O(n) | Space: O(1)
     */
    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        
        int j = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[j]) {
                j++;
                nums[j] = nums[i];
            }
        }
        
        return j + 1;
    }

    /**
     * 15. ROTATE ARRAY
     * Time: O(n) | Space: O(1)
     */
    public static void rotateArray(int[] nums, int k) {
        k = k % nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    private static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    /**
     * 16. CONTAINER WITH MOST WATER
     * Time: O(n) | Space: O(1)
     */
    public static int maxArea(int[] height) {
        int maxA = 0;
        int left = 0, right = height.length - 1;
        
        while (left < right) {
            int width = right - left;
            int h = Math.min(height[left], height[right]);
            maxA = Math.max(maxA, width * h);
            
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return maxA;
    }

    /**
     * 17. BEST TIME TO BUY AND SELL STOCK
     * Time: O(n) | Space: O(1)
     */
    public static int maxProfit(int[] prices) {
        int minPrice = prices[0], maxProfit = 0;
        
        for (int i = 1; i < prices.length; i++) {
            int profit = prices[i] - minPrice;
            maxProfit = Math.max(maxProfit, profit);
            minPrice = Math.min(minPrice, prices[i]);
        }
        
        return maxProfit;
    }

    /**
     * 18. MAXIMUM SUBARRAY (KADANE'S ALGORITHM)
     * Time: O(n) | Space: O(1)
     */
    public static int maxSubArray(int[] nums) {
        int currentSum = nums[0], maxSum = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            maxSum = Math.max(maxSum, currentSum);
        }
        
        return maxSum;
    }

    /**
     * 19. NEXT PERMUTATION
     * Time: O(n) | Space: O(1)
     */
    public static void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        
        if (i >= 0) {
            int j = nums.length - 1;
            while (j > i && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        
        reverse(nums, i + 1, nums.length - 1);
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 20. SEARCH IN ROTATED SORTED ARRAY
     * Time: O(log n) | Space: O(1)
     */
    public static int searchInRotatedArray(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) return mid;
            
            if (nums[left] <= nums[mid]) {
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return -1;
    }

    /**
     * 21. FIRST MISSING POSITIVE
     * Time: O(n) | Space: O(1)
     */
    public static int firstMissingPositive(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] > 0 && nums[i] <= nums.length && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) return i + 1;
        }
        
        return nums.length + 1;
    }

    /**
     * 22. TRAPPING RAIN WATER
     * Time: O(n) | Space: O(1)
     */
    public static int trap(int[] height) {
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int water = 0;
        
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    water += leftMax - height[left];
                }
                left++;
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    water += rightMax - height[right];
                }
                right--;
            }
        }
        
        return water;
    }

    // ==================== LINKED LIST PROBLEMS ====================

    public static class ListNode {
        public int val;
        public ListNode next;
        
        public ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * 23. REVERSE LINKED LIST
     * Time: O(n) | Space: O(1)
     */
    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        
        return prev;
    }

    /**
     * 24. MERGE TWO SORTED LISTS
     * Time: O(n + m) | Space: O(1)
     */
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        
        current.next = list1 != null ? list1 : list2;
        
        return dummy.next;
    }

    /**
     * 25. LINKED LIST CYCLE DETECTION (FLOYD'S ALGORITHM)
     * Time: O(n) | Space: O(1)
     */
    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;
        
        ListNode slow = head, fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        
        return false;
    }

    /**
     * 26. MIDDLE OF LINKED LIST
     * Time: O(n) | Space: O(1)
     */
    public static ListNode middleOfList(ListNode head) {
        ListNode slow = head, fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }

    /**
     * 27. REMOVE NTH NODE FROM END
     * Time: O(n) | Space: O(1)
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy, second = dummy;
        
        for (int i = 0; i <= n; i++) {
            if (first != null) first = first.next;
        }
        
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        
        second.next = second.next.next;
        
        return dummy.next;
    }

    /**
     * 30. PALINDROME LINKED LIST
     * Time: O(n) | Space: O(1)
     */
    public static boolean isPalindromeList(ListNode head) {
        if (head == null || head.next == null) return true;
        
        // Find middle
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Reverse second half
        ListNode prev = null;
        ListNode curr = slow;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        
        // Compare
        ListNode left = head, right = prev;
        while (left != null && right != null) {
            if (left.val != right.val) return false;
            left = left.next;
            right = right.next;
        }
        
        return true;
    }

    // ==================== TREE PROBLEMS ====================

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
        
        public TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * 31. BINARY TREE TRAVERSALS
     */
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        traverse(root, result, "inorder");
        return result;
    }

    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        traverse(root, result, "preorder");
        return result;
    }

    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        traverse(root, result, "postorder");
        return result;
    }

    private static void traverse(TreeNode node, List<Integer> result, String order) {
        if (node == null) return;
        
        if ("preorder".equals(order)) result.add(node.val);
        
        traverse(node.left, result, order);
        
        if ("inorder".equals(order)) result.add(node.val);
        
        traverse(node.right, result, order);
        
        if ("postorder".equals(order)) result.add(node.val);
    }

    /**
     * 32. LEVEL ORDER TRAVERSAL (BFS)
     * Time: O(n) | Space: O(w)
     */
    public static List<List<Integer>> levelOrderTraversal(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            
            result.add(currentLevel);
        }
        
        return result;
    }

    /**
     * 33. MAXIMUM DEPTH OF BINARY TREE
     * Time: O(n) | Space: O(h)
     */
    public static int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    /**
     * 34. LOWEST COMMON ANCESTOR
     * Time: O(n) | Space: O(h)
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (root.val == p.val || root.val == q.val) return root;
        
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        
        if (left != null && right != null) return root;
        return left != null ? left : right;
    }

    /**
     * 36. VALIDATE BINARY SEARCH TREE
     * Time: O(n) | Space: O(h)
     */
    public static boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean validate(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        
        return validate(node.left, min, node.val) && validate(node.right, node.val, max);
    }

    /**
     * 37. PATH SUM
     * Time: O(n) | Space: O(h)
     */
    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        
        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }
        
        return hasPathSum(root.left, targetSum - root.val) ||
               hasPathSum(root.right, targetSum - root.val);
    }

    /**
     * 39. DIAMETER OF BINARY TREE
     * Time: O(n) | Space: O(h)
     */
    public static int diameterOfBinaryTree(TreeNode root) {
        int[] diameter = new int[1];
        dfs(root, diameter);
        return diameter[0];
    }

    private static int dfs(TreeNode node, int[] diameter) {
        if (node == null) return 0;
        
        int left = dfs(node.left, diameter);
        int right = dfs(node.right, diameter);
        
        diameter[0] = Math.max(diameter[0], left + right);
        
        return 1 + Math.max(left, right);
    }

    /**
     * 40. BALANCED BINARY TREE
     * Time: O(n) | Space: O(h)
     */
    public static boolean isBalanced(TreeNode root) {
        return checkBalance(root) != -1;
    }

    private static int checkBalance(TreeNode node) {
        if (node == null) return 0;
        
        int left = checkBalance(node.left);
        if (left == -1) return -1;
        
        int right = checkBalance(node.right);
        if (right == -1) return -1;
        
        if (Math.abs(left - right) > 1) return -1;
        
        return 1 + Math.max(left, right);
    }

    /**
     * 41. SYMMETRIC TREE
     * Time: O(n) | Space: O(h)
     */
    public static boolean isSymmetric(TreeNode root) {
        return isMirror(root, root);
    }

    private static boolean isMirror(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        
        return left.val == right.val &&
               isMirror(left.left, right.right) &&
               isMirror(left.right, right.left);
    }

    // ==================== DYNAMIC PROGRAMMING ====================

    /**
     * 51. FIBONACCI NUMBER
     * Time: O(n) | Space: O(n)
     */
    public static int fib(int n, Map<Integer, Integer> memo) {
        if (memo == null) memo = new HashMap<>();
        
        if (n <= 1) return n;
        if (memo.containsKey(n)) return memo.get(n);
        
        int result = fib(n - 1, memo) + fib(n - 2, memo);
        memo.put(n, result);
        
        return result;
    }

    /**
     * 52. CLIMBING STAIRS
     * Time: O(n) | Space: O(n)
     */
    public static int climbStairs(int n) {
        if (n <= 2) return n;
        
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }

    /**
     * 53. HOUSE ROBBER
     * Time: O(n) | Space: O(n)
     */
    public static int rob(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(nums[i] + dp[i - 2], dp[i - 1]);
        }
        
        return dp[nums.length - 1];
    }

    /**
     * 55. COIN CHANGE
     * Time: O(n * amount) | Space: O(amount)
     */
    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        for (int i = 1; i <= amount; i++) {
            dp[i] = amount + 1;
        }
        
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        
        return dp[amount] > amount ? -1 : dp[amount];
    }

    /**
     * 57. LONGEST INCREASING SUBSEQUENCE
     * Time: O(n log n) | Space: O(n)
     */
    public static int lengthOfLIS(int[] nums) {
        List<Integer> tails = new ArrayList<>();
        
        for (int num : nums) {
            int left = 0, right = tails.size();
            
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (tails.get(mid) < num) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            
            if (left == tails.size()) {
                tails.add(num);
            } else {
                tails.set(left, num);
            }
        }
        
        return tails.size();
    }

    /**
     * 58. EDIT DISTANCE
     * Time: O(m * n) | Space: O(m * n)
     */
    public static int editDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], 
                                     Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                }
            }
        }
        
        return dp[m][n];
    }

    /**
     * 60. UNIQUE PATHS
     * Time: O(m * n) | Space: O(m * n)
     */
    public static int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 1;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        
        return dp[m - 1][n - 1];
    }

    /**
     * 61. MAXIMUM PRODUCT SUBARRAY
     * Time: O(n) | Space: O(1)
     */
    public static int maxProduct(int[] nums) {
        int maxProd = nums[0], minProd = nums[0], result = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            int temp = maxProd;
            maxProd = Math.max(nums[i], Math.max(nums[i] * maxProd, nums[i] * minProd));
            minProd = Math.min(nums[i], Math.min(nums[i] * temp, nums[i] * minProd));
            result = Math.max(result, maxProd);
        }
        
        return result;
    }

    // ==================== SORTING ====================

    /**
     * 65. MERGE SORT
     * Time: O(n log n) | Space: O(n)
     */
    public static int[] mergeSort(int[] arr) {
        if (arr.length <= 1) return arr;
        
        int mid = arr.length / 2;
        int[] left = mergeSort(Arrays.copyOfRange(arr, 0, mid));
        int[] right = mergeSort(Arrays.copyOfRange(arr, mid, arr.length));
        
        return merge(left, right);
    }

    private static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0, j = 0, k = 0;
        
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
        
        while (i < left.length) result[k++] = left[i++];
        while (j < right.length) result[k++] = right[j++];
        
        return result;
    }

    /**
     * 66. QUICK SORT
     * Time: O(n log n) average, O(n²) worst | Space: O(log n)
     */
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
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

    /**
     * 68. BINARY SEARCH
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

    // ==================== HASH MAP PROBLEMS ====================

    /**
     * 82. MAJORITY ELEMENT
     * Time: O(n) | Space: O(1)
     */
    public static int majorityElement(int[] nums) {
        int count = 0, candidate = 0;
        
        for (int num : nums) {
            if (count == 0) candidate = num;
            count += num == candidate ? 1 : -1;
        }
        
        return candidate;
    }

    /**
     * 85. FIRST UNIQUE CHARACTER
     * Time: O(n) | Space: O(1)
     */
    public static int firstUniqueChar(String s) {
        Map<Character, Integer> count = new HashMap<>();
        
        for (char c : s.toCharArray()) {
            count.put(c, count.getOrDefault(c, 0) + 1);
        }
        
        for (int i = 0; i < s.length(); i++) {
            if (count.get(s.charAt(i)) == 1) return i;
        }
        
        return -1;
    }

    // ==================== BIT MANIPULATION ====================

    /**
     * 89. SINGLE NUMBER (XOR)
     * Time: O(n) | Space: O(1)
     */
    public static int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }

    /**
     * 91. NUMBER OF 1 BITS
     * Time: O(1) for 32-bit | Space: O(1)
     */
    public static int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            count += n & 1;
            n = n >>> 1;
        }
        return count;
    }

    /**
     * 92. POWER OF TWO
     * Time: O(1) | Space: O(1)
     */
    public static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    /**
     * 93. MISSING NUMBER
     * Time: O(n) | Space: O(1)
     */
    public static int missingNumber(int[] nums) {
        int xor = 0;
        for (int i = 0; i < nums.length; i++) {
            xor ^= i ^ nums[i];
        }
        return xor ^ nums.length;
    }

    /**
     * 96. HAMMING DISTANCE
     * Time: O(1) | Space: O(1)
     */
    public static int hammingDistance(int x, int y) {
        int xor = x ^ y;
        int count = 0;
        while (xor != 0) {
            count += xor & 1;
            xor = xor >>> 1;
        }
        return count;
    }

    // ==================== UTILITY ====================

    public static void main(String[] args) {
        // Test cases can be added here
        System.out.println("Interview Solutions Compiled Successfully!");
    }
}
