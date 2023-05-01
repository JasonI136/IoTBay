package iotbay.servlets.admin_servlet;

import iotbay.database.DatabaseManager;
import iotbay.servlets.admin_servlet.routes.*;
import iotbay.servlets.interfaces.Route;
import iotbay.util.CustomHttpServletRequest;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.HttpMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {

    public DatabaseManager db;

    // Map<Method, Map<Path, Handler>>
    private static final Map<String, HashMap<String, Route>> ROUTES = new HashMap<>();

    static {
        HashMap<String, Route> getRoutes = new HashMap<>();
        getRoutes.put("/", new AdminIndexRoute());
        getRoutes.put("/inventory", new AdminInventoryRoute());
        getRoutes.put("/users", new AdminUsersRoute());
        getRoutes.put("/user/\\d+", new AdminGetUserRoute());
        getRoutes.put("/orders", new AdminOrdersRoute());
        getRoutes.put("/logs", new AdminLogsRoute());
        ROUTES.put(HttpMethod.GET, getRoutes);

        HashMap<String, Route> postRoutes = new HashMap<>();
        postRoutes.put("/product/\\d+", new AdminProductUpdateRoute());
        postRoutes.put("/orders/\\d+", new AdminOrderUpdateRoute());
        postRoutes.put("/categories/add", new AdminCategoryAddRoute());
        postRoutes.put("/products/add", new AdminProductAddRoute());
        postRoutes.put("/users/add", new AdminUserAddRoute());
        postRoutes.put("/user/\\d+", new AdminUserUpdateRoute());
        ROUTES.put(HttpMethod.POST, postRoutes);

        HashMap<String, Route> deleteRoutes = new HashMap<>();
        deleteRoutes.put("/product/\\d+", new AdminProductDeleteRoute());
        deleteRoutes.put("/category/\\d+", new AdminCategoryDeleteRoute());
        deleteRoutes.put("/user/\\d+", new AdminUserDeleteRoute());
        ROUTES.put(HttpMethod.DELETE, deleteRoutes);
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomHttpServletRequest req = (CustomHttpServletRequest) request;

        // regex match the path
        for (String path : ROUTES.get(req.getMethod()).keySet()) {
            if (req.getPath().matches(path)) {
                ROUTES.get(req.getMethod()).get(path).handle(request, response);
                return;
            }
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        this.db = (DatabaseManager) getServletContext().getAttribute("db");
    }

}
