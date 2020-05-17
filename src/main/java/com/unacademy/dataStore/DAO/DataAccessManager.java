package com.unacademy.dataStore.DAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.unacademy.dataStore.core.BaseDataStore;
import com.unacademy.dataStore.models.ClusterInfo;
import lombok.extern.log4j.Log4j;

/*
  Orchestrator between resource layer and data layer
 */
@Log4j
public class DataAccessManager {

    protected static final ObjectMapper objectMapper = new ObjectMapper();

    protected volatile BaseDataStore dataStore;
    protected volatile ClusterInfo clusterInfo;

    @Inject
    public DataAccessManager(BaseDataStore baseDataStore, ClusterInfo clusterInfo) {
        this.dataStore = baseDataStore;
        this.clusterInfo = clusterInfo;
    }

    /*
    Sets the key in this node's data store
     */
    public void set(String key, String value, long ttl) throws Exception {
        log.info("set: " + key+" "+value + " "+ttl);
        this.dataStore.set(key, value, ttl);
    }

    /*
    Flushes the key in this node's data store
     */
    public void flush(String key) throws Exception {
        log.info("flush: " + key);
        this.dataStore.flush(key);
    }
}
