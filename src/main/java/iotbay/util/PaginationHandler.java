package iotbay.util;

import iotbay.database.collections.ModelDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public class PaginationHandler<T> implements Serializable {
    public int currentPage;
    public int pageSize;
    public int totalItems;
    public int totalPages;
    public List<T> items;

    private final transient ModelDAO<T> modelDAO;

    public PaginationHandler(ModelDAO<T> modelDAO, int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.modelDAO = modelDAO;
    }

    public List<T> getItems() {
        return items;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setPage(int page) {
        this.currentPage = page;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
        this.totalPages = (int) Math.ceil((double) totalItems / pageSize);
    }

    public void loadItems() throws SQLException {
        int offset = (currentPage - 1) * pageSize;
        items = modelDAO.get(offset, pageSize);
        this.setTotalItems(modelDAO.count());
    }

    public void loadItems(String searchTerm) throws SQLException {
        int offset = (currentPage - 1) * pageSize;
        items = modelDAO.get(offset, pageSize, searchTerm);
        this.setTotalItems(modelDAO.count(searchTerm));
    }
}

