import static org.junit.Assert.*;
import org.junit.Test;

public class Fliktest {
    @Test
    public void testIsSameNumber() {
        assertTrue(Flik.isSameNumber(100, 100)); // 在缓存范围内测试
        assertTrue(Flik.isSameNumber(128, 128)); // 超出缓存范围测试
        assertFalse(Flik.isSameNumber(500, 400)); // 不同数字测试
    }
}
