package si.fri.rso.skupina3.billing.v1.resources;

import si.fri.rso.skupina3.billing.services.beans.BillBean;
import si.fri.rso.skupina3.lib.Bill;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/billing")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BillResource {

    private Logger log = Logger.getLogger(BillResource.class.getName());

    @Inject
    private BillBean billBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getBills() {

        log.info("getBills() - GET");
        List<Bill> bills = billBean.getBills(uriInfo);

        return Response.status(Response.Status.OK).entity(bills).build();
    }
}
