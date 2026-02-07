package kz.aitu.endtermapi.patterns.singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static DBManager instance;

    private final String url;
    private final String user;
    private final String password;

    private DBManager(){
        this.url = "jdbc:mysql://localhost:3306/library_db?useSSL=false&serverTimezone=UTC";
        this.user = "root";
        this.password = "";
    }

    public static DBManager getInstance(){
        if (instance == null){
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url,user,password);
    }

}
