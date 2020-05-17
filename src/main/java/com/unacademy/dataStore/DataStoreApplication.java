package com.unacademy.dataStore;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.unacademy.dataStore.DAO.NodeToNodeDataAccessManager;
import com.unacademy.dataStore.DAO.UserDataAccessManager;
import com.unacademy.dataStore.core.MapDataStore;
import com.unacademy.dataStore.guice.GuiceModule;
import com.unacademy.dataStore.healthCheck.DataStoreHealthCheck;
import com.unacademy.dataStore.models.Server;
import com.unacademy.dataStore.nodeDiscovery.NodeRegistrar;
import com.unacademy.dataStore.models.ClusterInfo;
import com.unacademy.dataStore.resources.UserAccessResource;
import com.unacademy.dataStore.resources.NodeToNodeResource;
import com.unacademy.dataStore.utils.NetworkUtils;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import static com.unacademy.dataStore.constants.DataStoreConstants.LOCALHOST;

/*
   Application class
 */
public class DataStoreApplication extends Application<DataStoreConfiguration> {
    public static void main(String[] args) throws Exception {
        new DataStoreApplication().run(args);
        NodeRegistrar.registerAndBootstrap();
    }

    @Override
    public void initialize(Bootstrap<DataStoreConfiguration> bootstrap) {
        GuiceBundle<Configuration> guiceBundle = GuiceBundle.<Configuration>newBuilder()
                .addModule(new GuiceModule())
                .build(Stage.DEVELOPMENT);
        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(DataStoreConfiguration configuration, Environment environment) {
        Injector injector = Guice.createInjector(new GuiceModule());
        MapDataStore dataStore = injector.getInstance(MapDataStore.class);
        ClusterInfo nodeManager = injector.getInstance(ClusterInfo.class);
        final UserAccessResource userAccessResource = new UserAccessResource(new UserDataAccessManager(dataStore, nodeManager));
        final NodeToNodeResource nodeToNodeResource = new NodeToNodeResource(new NodeToNodeDataAccessManager(dataStore, nodeManager));
        final DataStoreHealthCheck healthCheck = new DataStoreHealthCheck();
        environment.jersey().register(userAccessResource);
        environment.jersey().register(nodeToNodeResource);
        environment.jersey().register(healthCheck);
        DataStoreConfiguration.thisServer = new Server(LOCALHOST, NetworkUtils.getPort(configuration));
        System.out.println("DataStoreApplication Server started");
    }

}
