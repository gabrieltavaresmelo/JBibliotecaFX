package br.com.lisa.libfx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Conexao {

    private static Connection conexao = null;

    public static Connection getConnection()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NamingException {

        String url = "jdbc:mysql://localhost:3306/escola";
        String user = "root";
        String password = "";
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        conexao = DriverManager.getConnection(url, user, password);
        return conexao;
    }

    public static void close() {
        try {
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
