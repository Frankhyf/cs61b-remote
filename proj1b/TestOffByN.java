import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    @Test
    public void testEqualChars() {
        CharacterComparator offBy5 = new OffByN(5);

        // 测试字符相差5的情况
        assertTrue(offBy5.equalChars('a', 'f'));  // 应该返回true
        assertTrue(offBy5.equalChars('f', 'a'));  // 应该返回true

        // 测试字符相差非5的情况
        assertFalse(offBy5.equalChars('f', 'h'));  // 应该返回false

        // 测试其他情况
        CharacterComparator offBy2 = new OffByN(2);
        assertTrue(offBy2.equalChars('c', 'e'));  // 应该返回true
        assertTrue(offBy2.equalChars('c', 'a'));  // 应该返回false
        assertFalse(offBy2.equalChars('c', 'c'));  // 相同字符，应该返回false

        // 测试包含非字母字符的情况
        assertTrue(offBy5.equalChars('&', '+'));  // ASCII值相差5，应该返回true
        assertFalse(offBy5.equalChars('&', '%'));  // ASCII值不相差5，应该返回false
    }

}
