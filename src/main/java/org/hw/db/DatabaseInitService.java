package org.hw.db;

import org.hw.reader.ReaderSQLFiles;
import org.hw.settings.Settings;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseInitService {
    public static void main(String[] args) {
        DataBase dataBase = DataBase.getInstance();
        Connection conn = dataBase.getConnection();
        String script = new ReaderSQLFiles().getScript(Settings.FILE_NAME_INIT_DB);
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(script);
            preparedStatement.execute();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
