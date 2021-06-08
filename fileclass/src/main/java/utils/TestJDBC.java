package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author https://github.com/Han56
 * @date 2021/6/7 10:45
 * JDBC测试类
 */
public class TestJDBC {

    public static void main(String[] args) throws SQLException {
        Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        preparedStatement = connection.prepareStatement("insert into articles(author,title,cover,digest,content_url)"+"values(?,?,?,?,?)");
        preparedStatement.setString(1,"gihubHan56");
        preparedStatement.setString(2,"【经验与坑】使用Hbuilder封装VueApp");
        preparedStatement.setString(3,"http://www.baidu.com");
        preparedStatement.setString(4,"根据项目需求");
        preparedStatement.setString(5,"http://www.baidu.com");
        preparedStatement.executeUpdate();

        System.out.println("数据添加完成");
        //输出完毕，关闭连接
        JDBCUtils.close(preparedStatement,connection);
    }

}
