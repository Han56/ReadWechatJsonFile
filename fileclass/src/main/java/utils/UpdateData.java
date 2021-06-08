package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;

/**
 * @author https://github.com/Han56
 * @date 2021/6/7 15:08
 * 插入数据至数据库
 */
public class UpdateData {

    public void updateSql(String author,String title,String coverUrl,String digest,String contentUrl) throws SQLException {
        Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        preparedStatement = connection.prepareStatement("insert into articles(author,title,cover,digest,content_url,tip)"+"values(?,?,?,?,?,?)");
        preparedStatement.setString(1,author);
        preparedStatement.setString(2,title);
        preparedStatement.setString(3,coverUrl);
        preparedStatement.setString(4,digest);
        preparedStatement.setString(5,contentUrl);
        preparedStatement.setString(6,"IntelMining智能矿业");
        preparedStatement.executeUpdate();

        System.out.println("数据添加完成");
        //输出完毕，关闭连接
//        JDBCUtils.close(preparedStatement,connection);

    }

}
