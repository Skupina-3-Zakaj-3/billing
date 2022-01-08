package si.fri.rso.skupina3.billing.v1.resources;

import com.kumuluz.ee.logs.cdi.Log;
import si.fri.rso.skupina3.billing.services.beans.RvBillBean;
import si.fri.rso.skupina3.lib.RvBill;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@Log
@ApplicationScoped
@Path("/rv_bills")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RvBillResource {

    private final Logger log = Logger.getLogger(RvBillResource.class.getName());

    @Inject
    private RvBillBean rvBillBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getBills() {

        log.info("getBills() - GET");
        List<RvBill> rvBills = rvBillBean.getBills(uriInfo);

        return Response.status(Response.Status.OK).entity(rvBills).build();
    }

    @GET
    @Path("{id}")
    public Response getBill(@PathParam("id") Integer id) {

        log.info("getBill() - GET");

        RvBill rvBill = rvBillBean.getBill(id);

        if(rvBill == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(rvBill).build();
    }

    @POST
    public Response createBill(RvBill rvBill) {

        log.info("createBill() - POST");

        if (rvBill == null || rvBill.getPayer_id() == null || rvBill.getReceiver_id() == null ||
                rvBill.getRv_id() == null || rvBill.getPrice() == null || rvBill.getReservation_id() == null
        ) {
            log.info("Some needed values are missing!");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            rvBill = rvBillBean.createBill(rvBill);
        }

        return Response.status(Response.Status.OK).entity(rvBill).build();
    }

    @PUT
    @Path("{billId}")
    public Response putBill(@PathParam("billId") Integer billId, RvBill rvBill) {

        log.info("putBill() - PUT");
        //TODO: preveri, da so ustrezna polja
        rvBill.setBill_id(billId);
        rvBill = rvBillBean.updateBill(billId, rvBill);

        if (rvBill == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(rvBill).build();

    }

    @DELETE
    @Path("{billId}")
    public Response deleteBill(@PathParam("billId") Integer billId) {

        log.info("deleteBill() - DELETE");

        boolean deleted = rvBillBean.deleteBill(billId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
