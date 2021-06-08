package utils;

import com.sun.org.apache.xml.internal.resolver.readers.SAXCatalogReader;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.sql.*;

/**
 * @author https://github.com/Han56
 * @date 2021/6/7 10:31
 * Mysql数据库 操作工具类
 */
public class JDBCUtils {
    private JDBCUtils() {}
    private static Connection connection;

    static {

        try {
            //初始化Mysql的驱动类
            Class.forName("com.mysql.cj.jdbc.Driver");
            //链接数据库
            String url="jdbc:mysql://localhost:3306/ks?serverTimezone=UTC&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8";
            String user = "root";
            String password = "123456";
            connection = DriverManager.getConnection(url,user,password);
        }catch (Exception e){
            throw new RuntimeException(e+"数据库连接失败");
        }
    }

    public static Connection getConnection(){
        return connection;
    }

    public static void close(Statement statement,Connection connection){
        if (statement!=null){
            try{
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (connection!=null){
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static void close(Statement statement, Connection connection, ResultSet resultSet){
        if (statement!=null){
            try{
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (connection!=null){
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (resultSet!=null){
            try {
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }



}
