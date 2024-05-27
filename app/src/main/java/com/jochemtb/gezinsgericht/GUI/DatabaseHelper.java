package com.jochemtb.gezinsgericht.GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private static final String URL = "jdbc:mysql://your_database_url:3306/your_database_name";
    private static final String USER = "your_database_user";
    private static final String PASSWORD = "your_database_password";

    public List<String[]> getAnswers() {
        List<String[]> answers = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String query = "SELECT question, answer FROM your_table_name";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String question = resultSet.getString("question");
                String answer = resultSet.getString("answer");
                answers.add(new String[]{question, answer});
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answers;
    }
}
