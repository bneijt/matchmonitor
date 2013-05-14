package nl.bneijt.matchmonitor;

public class BoundedLongArray {
    public Long[] data;
    private int writeIndex = 0;

    public BoundedLongArray(int maxSize) {
        data = new Long[maxSize];
    }

    public void add(Long value) {
        ++writeIndex;
        writeIndex = writeIndex % data.length;
        data[writeIndex] = value;
    }

    public Long mean() {
        Long sum = 0l;
        Long size = 0l;
        for (Long d : data) {
            if (d != null) {
                sum += d;
                size += 1;
            }
        }
        return sum / size;
    }
    public Long last() {
        int lastIndex = writeIndex -1;
        lastIndex = lastIndex % data.length;
        return data[lastIndex];
    }

}
