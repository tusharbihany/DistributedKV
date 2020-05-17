package com.unacademy.dataStore;

import com.unacademy.dataStore.models.Server;
import io.dropwizard.Configuration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/*
   Configuration class, maintains all configs, supposed to be integrated with config service
 */
public class DataStoreConfiguration extends Configuration {
    public static Server thisServer;
    public static Server nodeDiscoveryService = new Server("localhost",25380);
}
