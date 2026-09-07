class Solution {
    public int distinctSubseqII(String s) {
        int MOD = 1_000_000_007;
        
        // endsWith[i] stores the number of distinct subsequences ending with character ('a' + i)
        long[] endsWith = new long[26];
        
        for (int i = 0; i < s.length(); i++) {
            int charIdx = s.charAt(i) - 'a';
            
            // Sum all subsequences formed so far across all characters
            long sum = 0;
            for (int j = 0; j < 26; j++) {
                sum = (sum + endsWith[j]) % MOD;
            }
            
            // New subsequences ending with current character:
            // 1 (the single character itself) + all existing subsequences with this char appended
            endsWith[charIdx] = (sum + 1) % MOD;
        }
        
        // Total distinct non-empty subsequences is the sum of endsWith across all characters
        long result = 0;
        for (int j = 0; j < 26; j++) {
            result = (result + endsWith[j]) % MOD;
        }
        
        return (int) result;
    }
}