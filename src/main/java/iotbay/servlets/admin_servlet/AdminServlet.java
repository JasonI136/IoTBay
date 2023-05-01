package iotbay.servlets.admin_servlet;

import iotbay.database.DatabaseManager;
import iotbay.servlets.admin_servlet.routes.*;
import iotbay.servlets.interfaces.Route;
import iotbay.util.CustomHttpServletRequest;
import iotbay.util.ServletRouteManager;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.HttpMethod;

import java.io.IOException;

public class AdminServlet extends HttpServlet {

    public DatabaseManager db;

    private static final ServletRouteManager routeManager = new ServletRouteManager();

    static {
        routeManager.addRoute(HttpMethod.GET, "/", new AdminIndexRoute());
        routeManager.addRoute(HttpMethod.GET, "/inventory", new AdminInventoryRoute());
        routeManager.addRoute(HttpMethod.GET, "/users", new AdminUsersRoute());
        routeManager.addRoute(HttpMethod.GET, "/user/\\d+", new AdminGetUserRoute());
        routeManager.addRoute(HttpMethod.GET, "/orders", new AdminOrdersRoute());
        routeManager.addRoute(HttpMethod.GET, "/logs", new AdminLogsRoute());

        routeManager.addRoute(HttpMethod.POST, "/product/\\d+", new AdminProductUpdateRoute());
        routeManager.addRoute(HttpMethod.POST, "/orders/\\d+", new AdminOrderUpdateRoute());
        routeManager.addRoute(HttpMethod.POST, "/categories/add", new AdminCategoryAddRoute());
        routeManager.addRoute(HttpMethod.POST, "/products/add", new AdminProductAddRoute());
        routeManager.addRoute(HttpMethod.POST, "/users/add", new AdminUserAddRoute());
        routeManager.addRoute(HttpMethod.POST, "/user/\\d+", new AdminUserUpdateRoute());

        routeManager.addRoute(HttpMethod.DELETE, "/product/\\d+", new AdminProductDeleteRoute());
        routeManager.addRoute(HttpMethod.DELETE, "/category/\\d+", new AdminCategoryDeleteRoute());
        routeManager.addRoute(HttpMethod.DELETE, "/user/\\d+", new AdminUserDeleteRoute());
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomHttpServletRequest req = (CustomHttpServletRequest) request;

        Route route = routeManager.getRoute(req.getMethod(), req.getPath());
        if (route != null) {
            route.handle(req, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        this.db = (DatabaseManager) getServletContext().getAttribute("db");
    }

}
