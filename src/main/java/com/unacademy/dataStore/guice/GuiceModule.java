package com.unacademy.dataStore.guice;

import com.google.inject.*;
import com.unacademy.dataStore.DAO.DataAccessManager;
import com.unacademy.dataStore.core.BaseDataStore;
import com.unacademy.dataStore.core.MapDataStore;
import com.unacademy.dataStore.models.DataModel;
import com.unacademy.dataStore.models.ClusterInfo;

import java.util.HashSet;

public class GuiceModule extends AbstractModule {

    @Override
    public void configure() {
        bind(DataAccessManager.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    public BaseDataStore provideMapDataStore() {
        return new MapDataStore(new DataModel());
    }

    @Provides
    @Singleton
    public ClusterInfo provideClusterInfo() {
        return new ClusterInfo(new HashSet<>());
    }

}
