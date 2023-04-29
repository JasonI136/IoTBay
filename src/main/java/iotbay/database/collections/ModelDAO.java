package iotbay.database.collections;

import java.sql.SQLException;
import java.util.List;

public interface ModelDAO<T> {
    List<T> get(int offset, int limit) throws SQLException;
    List<T> get(int offset, int limit, String searchTerm) throws SQLException;
    int count() throws SQLException;

    int count(String searchTerm) throws SQLException;
}
