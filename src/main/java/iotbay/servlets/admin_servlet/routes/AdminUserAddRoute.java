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

public class AdminUserAddRoute implements Route {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomHttpServletRequest req = (CustomHttpServletRequest) request;
        CustomHttpServletResponse res = (CustomHttpServletResponse) response;
        DatabaseManager db = (DatabaseManager) request.getServletContext().getAttribute("db");
        JsonObject json = req.getJsonBody();
        User user = new User(db);

        JsonElement username = json.get("username");
        if (username == null) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .error(true)
                    .message("Bad Request")
                    .data("Username is required")
                    .build());
            return;
        } else {
            user.setUsername(username.getAsString());
        }

        JsonElement password = json.get("password");
        if (password == null) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .error(true)
                    .message("Bad Request")
                    .data("Password is required")
                    .build());
            return;
        } else {
            user.setPassword(password.getAsString());
        }

        JsonElement firstName = json.get("firstName");
        if (firstName == null) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .error(true)
                    .message("Bad Request")
                    .data("First name is required")
                    .build());
            return;
        } else {
            user.setFirstName(firstName.getAsString());
        }

        JsonElement lastName = json.get("lastName");
        if (lastName == null) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .error(true)
                    .message("Bad Request")
                    .data("Last name is required")
                    .build());
            return;
        } else {
            user.setLastName(lastName.getAsString());
        }

        JsonElement phoneNumber = json.get("phoneNumber");
        if (phoneNumber == null) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .error(true)
                    .message("Bad Request")
                    .data("Phone number is required")
                    .build());
            return;
        } else {
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

            } catch (NumberParseException e) {
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
        }

        JsonElement emailAddress = json.get("emailAddress");
        if (emailAddress == null) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .error(true)
                    .message("Bad Request")
                    .data("Email address is required")
                    .build());
            return;
        } else {
            user.setEmail(emailAddress.getAsString());
        }

        JsonElement address = json.get("address");
        if (address == null) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .error(true)
                    .message("Bad Request")
                    .data("Address is required")
                    .build());
            return;
        } else {
            user.setAddress(address.getAsString());
        }

        JsonElement staffMember = json.get("staffMember");
        if (staffMember == null) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .error(true)
                    .message("Bad Request")
                    .data("Staff member is required")
                    .build());
            return;
        } else {
            user.setStaff(staffMember.getAsBoolean());
        }

        try {
            db.getUsers().registerUser(user);
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(500)
                    .error(true)
                    .message("Internal Server Error")
                    .data(e.getMessage())
                    .build());
            return;
        } catch (UserExistsException e) {
            res.sendJsonResponse(GenericApiResponse.<String>builder()
                    .statusCode(400)
                    .error(true)
                    .message("User Exists")
                    .data(e.getMessage())
                    .build());
            return;
        }

        res.sendJsonResponse(GenericApiResponse.<String>builder()
                .statusCode(200)
                .error(false)
                .message("User Created")
                .data("User with username " + user.getUsername() + " created successfully")
                .build());

    }
}
