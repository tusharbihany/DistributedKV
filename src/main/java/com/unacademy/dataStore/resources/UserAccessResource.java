package com.unacademy.dataStore.resources;

import com.codahale.metrics.annotation.Timed;
import com.unacademy.dataStore.DAO.UserDataAccessManager;
import com.unacademy.dataStore.models.Response;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import static com.unacademy.dataStore.constants.DataStoreConstants.*;

@Path("/dataStore")
@Produces(MediaType.APPLICATION_JSON)
/*
   User Path APIs
 */
@Slf4j
public class UserAccessResource {

    private UserDataAccessManager userDataAccessManager;

    public UserAccessResource(UserDataAccessManager userDataAccessManager) {
        this.userDataAccessManager = userDataAccessManager;
    }

    @GET
    @Timed
    @Path(GET_API)
    public Response get(@QueryParam("key") String key) {
        try {
            return new Response(key, userDataAccessManager.get(key));
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(GET_API, e.getMessage());
        }
    }

    @GET
    @Timed
    @Path(SET_API)
    public Response set(@QueryParam("key") String key, @QueryParam("value") String value, @QueryParam("ttl") long ttl) {
        try {
            userDataAccessManager.set(key, value, ttl);
            return new Response(SET_API+"/"+key, SUCCESS);
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
            userDataAccessManager.flush(key);
            return new Response(FLUSH_API+"/"+key, SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(FLUSH_API, e.getMessage());
        }
    }
}
