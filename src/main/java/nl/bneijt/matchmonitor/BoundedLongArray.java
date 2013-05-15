package nl.bneijt.matchmonitor;

public class BoundedLongArray {
    public Long[] data;
    private int writeIndex = 0;

    public BoundedLongArray(int maxSize) {
        data = new Long[maxSize];
    }

    public BoundedLongArray(BoundedLongArray other) {
        data = new Long[other.data.length];
        System.arraycopy(other.data, 0, data, 0, other.data.length);
        writeIndex = other.writeIndex;
    }

    public void add(Long value) {
        writeIndex += 1;
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
        if(size == 0l) {
            return sum;
        }
        return sum / size;
    }
    public Long last() {
        return data[writeIndex];
    }

}
