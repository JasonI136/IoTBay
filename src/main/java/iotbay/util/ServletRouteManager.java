package iotbay.util;

import iotbay.servlets.interfaces.Route;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This class is used to manage the routes for the servlets.
 * It provides a modularised way to add routes to the servlets.
 */
public class ServletRouteManager {

    /**
     * Define a hashmap to store the routes.
     */
    private final Map<String, HashMap<String, Route>> routes = new HashMap<>();

    /**
     * Define a hashmap to store the precompiled patterns.
     * By pre-compiling the patterns, it allows for faster lookup of routes.
     */
    private final Map<String, Pattern> precompiledPatterns = new HashMap<>();

    public ServletRouteManager() {

        // Define the routes for each method.
        routes.put("GET", new HashMap<>());
        routes.put("POST", new HashMap<>());
        routes.put("DELETE", new HashMap<>());
        routes.put("PUT", new HashMap<>());
    }

    /**
     * Add a route,
     * @param method The HTTP method.
     * @param regex The regex for the route.
     * @param route The route to add.
     */
    public void addRoute(String method, String regex, Route route) {
        routes.get(method).put(regex, route);
        precompiledPatterns.put(regex, Pattern.compile(regex));
    }

    /**
     * Get the route for the given method and path.
     * @param method The HTTP method.
     * @param path The path to get the route for.
     * @return The route for the given method and path. Null if no route is found.
     */
    public Route getRoute(String method, String path) {
        HashMap<String, Route> methodRoutes = routes.get(method);

        // Loop through each route for the method.
        for (Map.Entry<String, Route> entry : methodRoutes.entrySet()) {
            String regex = entry.getKey();
            Route route = entry.getValue();

            // Check if the path matches the regex.
            if (precompiledPatterns.get(regex).matcher(path).matches()) {
                return route;
            }
        }

        return null;
    }
}
