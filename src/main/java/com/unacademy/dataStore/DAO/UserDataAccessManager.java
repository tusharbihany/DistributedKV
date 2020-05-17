package com.unacademy.dataStore.DAO;

import com.unacademy.dataStore.DataStoreConfiguration;
import com.unacademy.dataStore.clients.HTTPCommandExecutor;
import com.unacademy.dataStore.core.BaseDataStore;
import com.unacademy.dataStore.models.ClusterInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;

import static com.unacademy.dataStore.constants.DataStoreConstants.*;

/*
   Orchestrator between user actions and data layer
 */
@Slf4j
public class UserDataAccessManager extends DataAccessManager {

    public UserDataAccessManager(BaseDataStore baseDataStore, ClusterInfo nodeManager) {
        super(baseDataStore, nodeManager);
    }

    /*
      Sets the key in this node, and informs other cluster nodes
     */
    @Override
    public void set(String key, String value, long ttl) throws Exception {
        super.set(key, value, ttl);
        final URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.addParameter("key", key);
        uriBuilder.addParameter("value", value);
        uriBuilder.addParameter("ttl", String.valueOf(ttl));
        uriBuilder.setPath(REPLICA_API+SET_API);
        this.clusterInfo.getNodes().parallelStream().forEach(node -> {
            try {
                if(node.equals(DataStoreConfiguration.thisServer))
                    return;
                HTTPCommandExecutor.executeGetRequest(uriBuilder,node);
            } catch (Exception e) {
                return;
            }
        });
    }

    /*
      Flushes the key in this node, and informs other cluster nodes
     */
    @Override
    public void flush(String key) throws Exception {
        super.flush(key);
        final URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.addParameter("key", key);
        uriBuilder.setPath(REPLICA_API+FLUSH_API);
        this.clusterInfo.getNodes().parallelStream().forEach(node -> {
            try {
                if(node.equals(DataStoreConfiguration.thisServer))
                    return;
                HTTPCommandExecutor.executeGetRequest(uriBuilder,node);
            } catch (Exception e) {
                return;
            }
        });
    }

    /*
      Gets the key from this node
     */
    public String get(String key) throws Exception {
        log.info("get: " + key);
        return this.dataStore.get(key);
    }
}
