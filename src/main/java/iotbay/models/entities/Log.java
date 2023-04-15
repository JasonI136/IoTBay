package iotbay.models.entities;

import lombok.Getter;

import java.io.Serializable;
import java.sql.ResultSet;

@Getter
public class Log implements Serializable {
    private final int id;
    private final String timestamp;
    private final String level;
    private final String message;

    public Log(ResultSet rs) throws Exception {
        this.id = rs.getInt("id");
        this.timestamp = rs.getString("timestamp");
        this.level = rs.getString("level");
        this.message = rs.getString("message");
    }
}
