package com.unacademy.dataStore.nodeDiscovery;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unacademy.dataStore.DataStoreConfiguration;
import com.unacademy.dataStore.clients.HTTPCommandExecutor;
import com.unacademy.dataStore.models.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;

import static com.unacademy.dataStore.constants.DataStoreConstants.*;

/*
   Registers and Bootstraps a new node
 */
@Slf4j
public class NodeRegistrar{

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void registerAndBootstrap() throws Exception {
        final URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setPath(NODE_REGISTER_API);
        Response response = HTTPCommandExecutor.executePostRequest(uriBuilder, DataStoreConfiguration.nodeDiscoveryService, objectMapper.writeValueAsString(DataStoreConfiguration.thisServer));
        if(response != null && response.getValue().equals(SUCCESS))
            log.info("Node registered successfully");
        else
            throw new Exception("Couldn't register node");
    }

}
