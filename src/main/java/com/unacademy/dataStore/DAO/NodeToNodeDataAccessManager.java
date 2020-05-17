package com.unacademy.dataStore.DAO;

import com.unacademy.dataStore.clients.HTTPCommandExecutor;
import com.unacademy.dataStore.core.BaseDataStore;
import com.unacademy.dataStore.models.DataModel;
import com.unacademy.dataStore.models.Response;
import com.unacademy.dataStore.models.Server;
import com.unacademy.dataStore.models.ClusterInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;

import java.util.Set;

import static com.unacademy.dataStore.constants.DataStoreConstants.*;

/*
   Orchestrator between node to node communication and data layer
 */
@Slf4j
public class NodeToNodeDataAccessManager extends DataAccessManager{

    public NodeToNodeDataAccessManager(BaseDataStore baseDataStore, ClusterInfo nodeManager) {
        super(baseDataStore, nodeManager);
    }

    /*
    Onboards a server, by sending the data dump to it
     */
    public Response onboard(Server server) throws Exception {
        log.info("onboard: " + server);
        final URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setPath(REPLICA_API+BOOTSTRAP_API);
        return HTTPCommandExecutor.executePostRequest(uriBuilder, server, objectMapper.writeValueAsString(this.dataStore.getDump()));
    }

    /*
    Bootstrap this node using dataModel
     */
    public void bootstrap(DataModel dataModel) throws Exception {
        log.info("bootstrap: " + dataModel);
        this.dataStore.bootStrap(dataModel);
    }

    /*
    Updates the cluster nodes
     */
    public void updateNodes(Set<Server> nodes) {
        log.info("updateNodes: " + nodes);
        this.clusterInfo.setNodes(nodes);
    }

    /*
    Removes the unhealthy node from the cluster nodes
     */
    public void deregister(Server server) {
        log.info("deregister: " + server);
        this.clusterInfo.getNodes().remove(server);
    }
}
