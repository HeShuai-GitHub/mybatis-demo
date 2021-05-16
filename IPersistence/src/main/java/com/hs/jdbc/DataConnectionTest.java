package com.hs.jdbc;

import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author heshuai
 * @title: DataConnectionTest
 * @description: TODO
 * @date 2021年05月12日 22:17
 */
public class DataConnectionTest {
    String url = "jdbc:mysql://localhost:3307/mybatis-test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
    String user = "root";
    String password = "123";
    @Test
    public void DBConnectionOneTest()throws SQLException {
        // 获取加载器实例
        Driver driver = new com.mysql.cj.jdbc.Driver();
        // 配置数据库参数
        Properties properties = new Properties();
        properties.setProperty("user",user);
        properties.setProperty("password",password);
        String sql = "SELECT * FROM `user` WHERE id = ?;";
        ResultSet resultSet = null;
        try (Connection connect = driver.connect(url, properties);
             /**
              *  创建一个无参sql声明对象， 用来向数据库发送sql，这里需要注意的是，如果该sql声明被使用多次，建议使用PreparedStatement，但是如果只使用一次，
              *  那么使用PreparedStatement反而会使用更多的资源
              */
//        connect.createStatement();
             /**
              * prepareStatement 创建一个有参或无参的预编译声明对象，向数据库发送sql声明，当该sql被执行多次时，
              * prepareStatement比createStatement效率更高，并且可以自动转义特殊字符来起到防止SQL注入的目的
              * 这个方法具有多个重载方法，这里对它多个方法所涉及的参数进行介绍
              * sql：SQL声明，可包含？表示参数
              * resultSetType：结果集类型
              *      ResultSet.TYPE_FORWARD_ONLY：默认，当结果集类型设置为该属性时，光标只能向前移动
              *      ResultSet.TYPE_SCROLL_INSENSITIVE 光标可以滚动，也就是可以使用previous()、absolute(int row)、relative(int rows)、first()等，可以修改表内容
              *      ResultSet.TYPE_SCROLL_SENSITIVE 同上，不可以修改表内容
              * resultSetConcurrency：结果集并发模式
              *      ResultSet.CONCUR_READ_ONLY：并发模式下不可以更新ResultSet对象
              *      ResultSet.CONCUR_UPDATABLE：并发模式下可以更新ResultSet对象
              * resultSetHoldability：常规来讲，当statement执行另外一个查询时，第一个查询的ResultSet将会被关闭，也就是说所有的Statement查询对应的结果集只有一个，
              * 调用Connection.commit()也会关闭结果集，如果想要在这种情况下继续保留ResultSet结果集的话，那么就需要设置这个属性了
              *      ResultSet.HOLD_CURSORS_OVER_COMMIT：发生上面情况依然保留结果集
              *      ResultSet.CLOSE_CURSORS_AT_COMMIT：默认，发生上面情况不保留结果集
              * autoGeneratedKeys：是否返回自增id
              *      Statement.RETURN_GENERATED_KEYS：自增ID将会返回
              *      Statement.NO_GENERATED_KEYS：自增ID不会返回
              */
             PreparedStatement preparedStatement = connect.prepareStatement(sql);
             ){
            /**
             * 设置sqlStatement参数
             * parameterIndex：参数下标，第一个参数下标为1
             * x：参数值，setInt对应数据库字段类型为Integer
             */
            preparedStatement.setInt(1,1);
            /**
             * 清除参数值
             * preparedStatement.clearParameters();
             */
            /**
             * preparedStatement.setFetchSize(); 设置一次性查询并封装到ResultSet中的数据行数，默认是0，不限制，也就是说将查询到的所有数据都封装到ResultSet中，
             * 如果数据量特别大的时候会造成内存崩掉，这个时候可以使用setFetchSize方法设置一次性读取行数，当光标移动到一次性读取行数之外，那么将再次查询下一批数据，这样就防止了一次性读取太多数据导致内存崩盘。
             */
            resultSet = preparedStatement.executeQuery();
            /**
             * next:设置结果集的光标，当前默认光标位置是在第一行之前，第一次执行next方法光标将到第一行数据位置，以此每次调用next方法都将移动光标
             * 当光标位置处于最后一行数据的后面，那么next将返回false，否则返回true；
             */
            while (resultSet.next()) {
                // get***方法可以获取光标所在行的字段值
                String username = resultSet.getString("username");
                System.out.println(username);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @Test
    public void ConnectionForName() throws Exception {
        Class<?> aClass = Class.forName("com.mysql.cj.jdbc.Driver");
        // 配置数据库参数
        Properties properties = new Properties();
        properties.setProperty("user",user);
        properties.setProperty("password",password);
        String sql = "SELECT * FROM `user` WHERE id = ?;";
        Driver driver = (Driver)aClass.getDeclaredConstructor().newInstance();
        Connection connect = driver.connect(url, properties);
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1,2);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        System.out.println(resultSet.getString(2));
    }

    @Test
    public void ConnectionManager() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 配置数据库参数
        Properties properties = new Properties();
        properties.setProperty("user",user);
        properties.setProperty("password",password);
        String sql = "SELECT * FROM `user` WHERE id = ?;";
        Connection connection = DriverManager.getConnection(url, properties);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,2);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        System.out.println(resultSet.getString(2));
    }
    @Test
    public void ConnectionManagerByOutProperty() throws Exception {
        // 获取properties文件文件流
        InputStream resource = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        // 通过一个文件流来添加一些key-value
        properties.load(resource);
        String sql = "SELECT * FROM `user` WHERE id = ?;";
        // 加载驱动，在com.mysql.cj.jdbc.Driver类有一个static代码块，将自己注册到DriverManager中，然后就可以通过DriverManager的静态方法getConnection来获取连接了
        Class.forName(properties.getProperty("driverClass"));
        // 通过DriverManager来获取连接
        Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,2);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        System.out.println(resultSet.getString(2));
    }
}