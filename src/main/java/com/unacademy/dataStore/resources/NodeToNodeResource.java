package com.unacademy.dataStore.resources;

import com.codahale.metrics.annotation.Timed;
import com.unacademy.dataStore.DAO.NodeToNodeDataAccessManager;
import com.unacademy.dataStore.models.DataModel;
import com.unacademy.dataStore.models.Response;
import com.unacademy.dataStore.models.Server;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.Set;

import static com.unacademy.dataStore.constants.DataStoreConstants.*;

@Path("/dataStore/replica")
@Produces(MediaType.APPLICATION_JSON)
/*
   Node to Node communication APIs
 */
@Slf4j
public class NodeToNodeResource {

    private NodeToNodeDataAccessManager nodeToNodeDataAccessManager;

    public NodeToNodeResource(NodeToNodeDataAccessManager nodeToNodeDataAccessManager) {
        this.nodeToNodeDataAccessManager = nodeToNodeDataAccessManager;
    }

    @GET
    @Timed
    @Path(SET_API)
    public Response set(@QueryParam("key") String key, @QueryParam("value") String value, @QueryParam("ttl") long ttl) {
        try {
            nodeToNodeDataAccessManager.set(key, value, ttl);
            return new Response(SET_API + "/" + key, SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(SET_API, e.getMessage());
        }
    }

    @GET
    @Timed
    @Path(FLUSH_API)
    public Response flush(@QueryParam("key") String key) {
        try {
            nodeToNodeDataAccessManager.flush(key);
            return new Response(FLUSH_API + "/" + key, SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(FLUSH_API, e.getMessage());
        }
    }

    @POST
    @Timed
    @Path(ONBAORD_API)
    public Response onboard(Server server) {
        try {
            return nodeToNodeDataAccessManager.onboard(server);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(ONBAORD_API, e.getMessage());
        }
    }

    @POST
    @Timed
    @Path(BOOTSTRAP_API)
    public Response bootstrap(DataModel dataModel) {
        try {
            nodeToNodeDataAccessManager.bootstrap(dataModel);
            return new Response(BOOTSTRAP_API, SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(BOOTSTRAP_API, e.getMessage());
        }
    }

    @POST
    @Timed
    @Path(UPDATE_NODES_API)
    public Response updateNodes(Set<Server> nodes) {
        nodeToNodeDataAccessManager.updateNodes(nodes);
        return new Response(UPDATE_NODES_API, SUCCESS);
    }

    @POST
    @Timed
    @Path(DEREGISTER_API)
    public Response deregister(Server server) {
        nodeToNodeDataAccessManager.deregister(server);
        return new Response(DEREGISTER_API, SUCCESS);
    }
}
