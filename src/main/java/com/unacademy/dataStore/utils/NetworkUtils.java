package com.unacademy.dataStore.utils;

import com.unacademy.dataStore.DataStoreConfiguration;
import io.dropwizard.jetty.ConnectorFactory;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.server.DefaultServerFactory;

public class NetworkUtils {

    public static int getPort(DataStoreConfiguration configuration) {
        int httpPort = 0;
        DefaultServerFactory serverFactory = (DefaultServerFactory) configuration.getServerFactory();
        for (ConnectorFactory connector : serverFactory.getApplicationConnectors()) {
            if (connector.getClass().isAssignableFrom(HttpConnectorFactory.class)) {
                httpPort = ((HttpConnectorFactory) connector).getPort();
                break;
            }
        }
        return httpPort;
    }
}
