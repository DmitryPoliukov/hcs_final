package by.epamtc.poliukov.controller.listener;

import by.epamtc.poliukov.exception.ConnectionPoolListenerException;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.PoolService;
import by.epamtc.poliukov.service.ServiceFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ConnectionPoolListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(ConnectionPoolListener.class);


    public ConnectionPoolListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        try {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        PoolService poolService = serviceFactory.getPoolService();
        poolService.init();
        logger.log(Level.INFO, "Pool successfully initialized");
         } catch (ServiceException e) {
            try {
                throw new ConnectionPoolListenerException("Cannot init the pool", e);
            } catch (ConnectionPoolListenerException connectionPoolListenerException) {
                connectionPoolListenerException.printStackTrace();
            }
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            PoolService poolService = serviceFactory.getPoolService();
            poolService.destroy();
            logger.log(Level.INFO, "Pool successfully destroyed");
        } catch (ServiceException e) {
            try {
                throw new ConnectionPoolListenerException("Cannot destroy the pool", e);
            } catch (ConnectionPoolListenerException connectionPoolListenerException) {
                connectionPoolListenerException.printStackTrace();
            }
        }
    }
}
