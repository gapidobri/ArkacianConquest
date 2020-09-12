package ga.arkacia.conquest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private Connection connection;
    private Statement statement;
    private String host, database, username, password;
    private int port;

    public Database(String host, String database, String username, String password, int port) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            statement = connection.createStatement();
        }
    }

    public void setSomething() {
        try {
            statement.executeUpdate("CREATE TABLE NationData (id varchar(255), display_name varchar(255))");
            statement.executeUpdate("INSERT INTO NationData (ID, DISPLAY_NAME) VALUES ('test_nation', 'Test Nation')");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
