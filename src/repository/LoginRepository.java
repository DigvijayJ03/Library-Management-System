package src.repository;

import src.model.LoginUser;
import src.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginRepository {

    public LoginUser authenticate(
            String username,
            String password) {

        String sql =
                """
                SELECT *
                FROM login_users
                WHERE username = ?
                AND password = ?
                """;

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                return new LoginUser(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}