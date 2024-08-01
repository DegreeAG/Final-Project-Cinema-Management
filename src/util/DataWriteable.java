package util;

import java.util.List;

public interface DataWriteable<T> {
    void writeDataToFile(List<T> data, String fileName);
}
