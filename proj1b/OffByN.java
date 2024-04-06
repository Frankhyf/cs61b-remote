public class OffByN implements CharacterComparator{

    private int off;
    public OffByN(int N) {
        this.off = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        if (x-y == off || y-x == off) {
            return true;
        } else {
            return false;
        }
    }
}
