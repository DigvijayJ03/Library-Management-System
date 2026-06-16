package src.repository;

import src.model.User;
import src.util.DBConnection;
import src.util.FileUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserRepository {

    private static final String USER_FILE = "users.dat";

    public void saveUsers(ArrayList<User> users) {

        FileUtil.saveObject(users, USER_FILE);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<User> loadUsers() {

        Object obj = FileUtil.loadObject(USER_FILE);

        if(obj != null) {
            return (ArrayList<User>) obj;
        }

        return new ArrayList<>();
    }

    public void addUser(User user) {

        String sql =
                "INSERT INTO users(user_id,name) VALUES(?,?)";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)) {

            ps.setInt(1, user.getUserId());
            ps.setString(2, user.getName());

            ps.executeUpdate();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    public ArrayList<User> getAllUsers() {

        ArrayList<User> users =
                new ArrayList<>();

        String sql =
                "SELECT * FROM users";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()) {

            while(rs.next()) {

                users.add(
                        new User(
                                rs.getInt("user_id"),
                                rs.getString("name")
                        )
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return users;
    }

    public User findUserById(int id) {

        String sql =
                "SELECT * FROM users WHERE user_id=?";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                return new User(
                        rs.getInt("user_id"),
                        rs.getString("name")
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public int totalUsers() {

        String sql =
                "SELECT COUNT(*) FROM users";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()) {

            if(rs.next()) {
                return rs.getInt(1);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


}