import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    static OffByOne offByOne = new OffByOne();
    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {

        // 测试单个字符的字符串，应该是回文
        assertTrue(palindrome.isPalindrome("a"));

        // 测试空字符串，应该是回文
        assertTrue(palindrome.isPalindrome(""));

        // 测试普通回文字符串
        assertTrue(palindrome.isPalindrome("racecar"));

        // 测试包含大写和小写字符的字符串，按照题目要求，不考虑大小写差异
        // 如果你的实现考虑了大小写差异，则这个测试可能需要调整
        assertFalse(palindrome.isPalindrome("Racecar"));

        // 测试非回文字符串
        assertFalse(palindrome.isPalindrome("cat"));

        // 测试边界情况，比如只有两个字符的字符串
        assertTrue(palindrome.isPalindrome("aa"));
        assertFalse(palindrome.isPalindrome("ab"));

        // 测试较长的回文和非回文字符串
        assertTrue(palindrome.isPalindrome("anavolimilovana"));
        assertFalse(palindrome.isPalindrome("anavolimilovanb"));

        // 特殊字符测试，考虑到回文的定义，特殊字符也应被正常处理
        assertTrue(palindrome.isPalindrome("!!"));
        assertTrue(palindrome.isPalindrome("!.!"));
    }

    @Test
    public void testIsPalindromeOffByOne() {
        // 这些字符串应该被认为是回文，因为相邻字符的差值为1
        assertTrue(palindrome.isPalindrome("ab", offByOne));
        assertTrue(palindrome.isPalindrome("acb", offByOne));
        assertTrue(palindrome.isPalindrome("flake", offByOne));

        // 这些情况应该返回false
        assertFalse(palindrome.isPalindrome("aa", offByOne)); // 相同字符不是Off by one
        assertFalse(palindrome.isPalindrome("xyz", offByOne)); // x和z的差值不为1
        assertFalse(palindrome.isPalindrome("aba", offByOne)); // a和b的差值是1，但aba不符合Off by one回文的定义

        // 测试包含非字母字符的字符串
        assertTrue(palindrome.isPalindrome("&%", offByOne));
        assertTrue(palindrome.isPalindrome("%&", offByOne)); // %和&的顺序不满足Off by one

        // 测试空字符串和单字符字符串，按照定义它们应该被认为是回文
        assertTrue(palindrome.isPalindrome("", offByOne));
        assertTrue(palindrome.isPalindrome("a", offByOne));
    }
}
