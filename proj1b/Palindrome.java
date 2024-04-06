public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    /*
    public boolean isPalindrome(String word) {
        String copy = word;
        String reversed = new StringBuilder(copy).reverse().toString();
        return reversed.equals(word);
    }

     */

    private boolean helper(Deque<Character> D) {
        //base case 1
        if (D.size() == 0 || D.size() == 1) {
            return true;
        } else if (!D.removeFirst().equals(D.removeLast())) {
            return false;
        } else {
            return helper(D);
        }
    }

    private boolean helper(Deque<Character> D, CharacterComparator cc) {
        //base case 1
        if (D.size() == 0 || D.size() == 1) {
            return true;
        } else if (!cc.equalChars(D.removeFirst(),D.removeLast())) {
            return false;
        } else {
            return helper(D, cc);
        }
    }
    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        return helper(deque);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = wordToDeque(word);
        return  helper(deque, cc);
    }

}
