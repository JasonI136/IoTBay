package iotbay.models;

import lombok.Getter;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public class Log implements Serializable {

    /**
     * The log id.
     * <br>
     * <br>
     * <b>TABLE:</b> EVENT_LOG.id
     */
    private final int id;

    /**
     * The log timestamp.
     * <br>
     * <br>
     * <b>TABLE:</b> EVENT_LOG.timestamp
     */
    private final String timestamp;

    /**
     * The log level.
     * <br>
     * <br>
     * <b>TABLE:</b> EVENT_LOG.level
     */
    private final String level;

    /**
     * The log message.
     * <br>
     * <br>
     * <b>TABLE:</b> EVENT_LOG.message
     */
    private final String message;

    /**
     * Initialise a new log object from a result set.
     * @param rs The result set.
     * @throws SQLException If there is an error with the SQL query.
     */
    public Log(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.timestamp = rs.getString("timestamp");
        this.level = rs.getString("level");
        this.message = rs.getString("message");
    }
}
