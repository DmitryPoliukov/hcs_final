package by.epamtc.poliukov.dao.pool;

import by.epamtc.poliukov.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;



public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/hcs2508";
    private static final String USER = "root";
    private static final String PASSWORD = "112121";

    private static final int POOL_SIZE = 10;

    private static BlockingQueue<Connection> freeConnections;
    private static BlockingQueue<Connection> usedConnections;

    private volatile boolean isInit = false;
    private static final ConnectionPool instance = new ConnectionPool();

    public ConnectionPool() {
    }

    public void init() throws ConnectionPoolException {
        if (!isInit) {
            try {
                freeConnections = new ArrayBlockingQueue<>(POOL_SIZE);
                usedConnections = new ArrayBlockingQueue<>(POOL_SIZE);
                Class.forName(DRIVER);
                Connection connection;
                for (int i = 0; i < POOL_SIZE; i++) {
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    freeConnections.add(connection);
                }
                isInit = true;
                logger.info("Success connection pool init");
            } catch (ClassNotFoundException | SQLException e) {
                logger.catching(e);
                System.out.println("Unsuccess connection pool init");
            }
        } else {
          throw new ConnectionPoolException("You must init pool at once");
        }
    }

    public void destroy() throws ConnectionPoolException {
        if (isInit) {
            try {
                for (Connection connection : freeConnections) {
                    connection.close();
                }
                freeConnections.clear();
                for (Connection connection : usedConnections) {
                    connection.close();
                }
                usedConnections.clear();
                isInit = false;
            } catch (SQLException e) {
                throw new ConnectionPoolException("Can not destroy pool", e);
            }
        } else {
            throw new ConnectionPoolException("You can't destroy pool, that didn't init");
        }
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            if (freeConnections == null) {
                throw new ConnectionPoolException("Pool doesn't exist");
            }
            connection = freeConnections.take();
            usedConnections.put(connection);
            logger.info("Success pool takeConnection");
            return connection;
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Couldn't take connection from pool", e);
        }
    }

    public void returnConnection(Connection connection) throws ConnectionPoolException {
        try {
            if (connection == null || connection.isClosed()) {
                throw new ConnectionPoolException("Can't return closed connection");
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("SQL exception in returnConnection");
        }
        try {
            usedConnections.remove(connection);
            freeConnections.put(connection);
        } catch (InterruptedException e) {
            throw  new ConnectionPoolException("Exception while return connections to queues", e);
        }
    }

    public static void closeResource(Connection con, PreparedStatement st, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, e + "Exception while closing result set");
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, e + "Exception while closing statement");
            }
        }
        try {
            instance.returnConnection(con);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, e + "Exception while returning connection");
        }
    }
    public static void closeResource(Connection con, PreparedStatement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, e + "Exception while closing statement");
            }
        }
        try {
            instance.returnConnection(con);
        } catch (ConnectionPoolException e) {
           logger.log(Level.ERROR, e + "Exception while returning connection");
        }
    }

    public static void closeResource(Connection con, Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, e + "Exception while closing statement");
            }
        }
        try {
            instance.returnConnection(con);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, e + "Exception while returning connection");
        }
    }


}
