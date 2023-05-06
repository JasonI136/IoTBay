package iotbay.servlets.admin_servlet.routes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import iotbay.database.DatabaseManager;
import iotbay.exceptions.UserExistsException;
import iotbay.models.User;
import iotbay.models.httpResponses.GenericApiResponse;
import iotbay.servlets.interfaces.Route;
import iotbay.util.CustomHttpServletRequest;
import iotbay.util.CustomHttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class AdminUserUpdateRoute implements Route {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomHttpServletRequest req = (CustomHttpServletRequest) request;
        CustomHttpServletResponse res = (CustomHttpServletResponse) response;
        DatabaseManager db = (DatabaseManager) request.getServletContext().getAttribute("db");
        JsonObject json = req.getJsonBody();

        String path = req.getPathInfo();
        String[] pathParts = path.split("/");
        // path is in the format of /user/{id}
        if (pathParts.length != 3) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .message("Invalid user id.")
                    .data("Invalid user id or user id not provided.")
                    .error(true)
                    .build());
            return;
        }

        int id;
        try {
            id = Integer.parseInt(pathParts[2]);
        } catch (NumberFormatException e) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .message("Invalid user id.")
                    .data("Invalid user id or user id not provided.")
                    .error(true)
                    .build());
            return;
        }

        User user;
        try {
            user = db.getUsers().getUser(id);
        } catch (SQLException e) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(500)
                    .message("Internal server error.")
                    .data(e.getMessage())
                    .error(true)
                    .build());
            return;
        }

        if (user == null) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(404)
                    .message("User not found.")
                    .data("User not found.")
                    .error(true)
                    .build());
            return;
        }

        JsonElement username = json.get("username");
        if (username != null) {
            user.setUsername(username.getAsString());
        }
        JsonElement password = json.get("password");
        if (password != null) {
            String passwordValue = password.getAsString();
            if (passwordValue.isEmpty()) {
                res.sendJsonResponse(GenericApiResponse.<String>builder()
                        .statusCode(400)
                        .error(true)
                        .message("Bad Request")
                        .data("Password cannot be empty")
                        .build());
                return;
            } else {
                user.setPassword(passwordValue);
            }
        }

        JsonElement firstName = json.get("firstName");
        if (firstName != null) {
            user.setFirstName(firstName.getAsString());
        }

        JsonElement lastName = json.get("lastName");
        if (lastName != null) {
            user.setLastName(lastName.getAsString());
        }

        JsonElement phoneNumber = json.get("phoneNumber");
        if (phoneNumber != null) {
            try {
                PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
                Phonenumber.PhoneNumber phoneNumber2;
                phoneNumber2 = phoneUtil.parse(phoneNumber.getAsString(), "AU");
                if (!phoneUtil.isValidNumber(phoneNumber2)) {
                    res.sendJsonResponse(
                            GenericApiResponse.<String>builder()
                                    .statusCode(400)
                                    .message("Error")
                                    .data("Invalid Australian phone number")
                                    .error(true)
                                    .build()
                    );
                    return;
                }

            user.setPhoneNumber(phoneUtil.format(phoneNumber2, PhoneNumberUtil.PhoneNumberFormat.E164));
        } catch(NumberParseException e){
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .error(true)
                    .message("Bad Request")
                    .data("Invalid phone number")
                    .build());
            return;
        }
    }

    JsonElement emailAddress = json.get("emailAddress");
        if(emailAddress !=null)

    {
        user.setEmail(emailAddress.getAsString());
    }

    JsonElement address = json.get("address");
        if(address !=null)

    {
        user.setAddress(address.getAsString());
    }

    JsonElement staffMember = json.get("staffMember");
        if(staffMember !=null)

    {
        user.setStaff(staffMember.getAsBoolean());
    }

        try

    {
        db.getUsers().updateUser(user);
    } catch(SQLException |NoSuchAlgorithmException |
    InvalidKeySpecException e)

    {
        res.sendJsonResponse(GenericApiResponse.<String>builder()
                .statusCode(500)
                .message("Internal server error.")
                .data(e.getMessage())
                .error(true)
                .build());
        return;
    } catch(
    UserExistsException e)

    {
        res.sendJsonResponse(GenericApiResponse.<String>builder()
                .statusCode(400)
                .message("Bad Request")
                .data(e.getMessage())
                .error(true)
                .build());
        return;
    }

        res.sendJsonResponse(GenericApiResponse .

    <String> builder()
                .

    statusCode(200)
                .

    message("User updated successfully.")
                .

    data("User updated successfully.")
                .

    error(false)
                .

    build());
}
}
