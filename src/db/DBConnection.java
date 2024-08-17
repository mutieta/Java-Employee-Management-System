package db;

import Constants.CommonConstants;

import java.sql.*;

public class DBConnection{
    private static Connection connection;
    private static Connection getConnection(){
        if(connection==null){
            try {
                connection = DriverManager.getConnection(CommonConstants.DB_URL,CommonConstants.DB_USERNAME,CommonConstants.DB_PASSWORD);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return connection;
    }
    public static boolean validatedLogin(String username, String password){
        Connection con = getConnection();
        try{
            PreparedStatement validateUser = con.prepareStatement("SELECT * FROM "+CommonConstants.DB_USER_TABLE_NAME+" WHERE username= ? AND password=?");
            validateUser.setString(1,username);
            validateUser.setString(2,password);
            ResultSet resultSet = validateUser.executeQuery();
            if(!resultSet.isBeforeFirst()){
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
}
