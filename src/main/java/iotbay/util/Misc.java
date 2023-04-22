package iotbay.util;

import com.google.gson.Gson;
import iotbay.models.httpResponses.GenericApiResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Misc {

    public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        try {
            rs.findColumn(columnName);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Sends a JSON response to the client.
     * @param response The response object.
     * @param json The JSON object to send.
     * @throws IOException
     */
    public static void sendJsonResponse(HttpServletResponse response, GenericApiResponse<?> json) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(json.getStatusCode());
        response.getWriter().write(new Gson().toJson(json));
    }
}
