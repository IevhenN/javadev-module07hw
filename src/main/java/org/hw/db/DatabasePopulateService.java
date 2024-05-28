package org.hw.db;

import org.hw.reader.ReaderPopulate;
import org.hw.reader.ReaderSQLFiles;
import org.hw.settings.Settings;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DatabasePopulateService {
    public static void main(String[] args) {
        DataBase dataBase = DataBase.getInstance();
        Connection conn = dataBase.getConnection();
        String scripts = new ReaderSQLFiles().getScript(Settings.FILE_NAME_POPULATE_DB);
        String[] script = scripts.split(";");

        populateTableWorker(conn, script[0]);
        populateTableClient(conn, script[1]);
        populateTableProject(conn, script[2]);
        populateTableProjectWorker(conn, script[3]);

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void populateTableWorker(Connection connection, String script) {
        List<Map<String, String>> workers = new ReaderPopulate().getValuesToFill("worker");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(script);

            for (Map<String, String> worker : workers) {
                preparedStatement.setString(1, worker.get("name"));
                preparedStatement.setDate(2, Date.valueOf(worker.get("birthday")));
                preparedStatement.setString(3, worker.get("level"));
                preparedStatement.setInt(4, Integer.parseInt(worker.get("salary")));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void populateTableClient(Connection connection, String script) {
        List<Map<String, String>> clients = new ReaderPopulate().getValuesToFill("client");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(script);

            for (Map<String, String> client : clients) {
                preparedStatement.setString(1, client.get("name"));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void populateTableProject(Connection connection, String script) {
        List<Map<String, String>> projects = new ReaderPopulate().getValuesToFill("project");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(script);

            for (Map<String, String> project : projects) {
                preparedStatement.setLong(1, Long.parseLong(project.get("client_id")));
                preparedStatement.setDate(2, Date.valueOf(project.get("start_date")));
                preparedStatement.setDate(3, Date.valueOf(project.get("finish_date")));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void populateTableProjectWorker(Connection connection, String script) {
        List<Map<String, String>> projectWorkers = new ReaderPopulate().getValuesToFill("project_worker");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(script);

            for (Map<String, String> projectWorker : projectWorkers) {
                preparedStatement.setLong(1, Long.parseLong(projectWorker.get("project_id")));
                preparedStatement.setLong(2, Long.parseLong(projectWorker.get("worker_id")));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
