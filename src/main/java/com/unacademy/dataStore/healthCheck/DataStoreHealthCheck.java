package com.unacademy.dataStore.healthCheck;

import com.unacademy.dataStore.constants.DataStoreConstants;
import com.unacademy.dataStore.models.Response;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import static com.unacademy.dataStore.constants.DataStoreConstants.HEALTHCHECK_API;

@Path("/dataStore")
@Produces(MediaType.APPLICATION_JSON)
public class DataStoreHealthCheck {

    @GET
    @Path(HEALTHCHECK_API)
    public Response getHealthCheckStatus(@Context HttpServletRequest httpServletRequest) {
        return new Response(HEALTHCHECK_API,DataStoreConstants.SUCCESS);
    }
}
