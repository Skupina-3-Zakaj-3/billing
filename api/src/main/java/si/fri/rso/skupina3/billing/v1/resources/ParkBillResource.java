package si.fri.rso.skupina3.billing.v1.resources;

import si.fri.rso.skupina3.billing.services.beans.ParkBillBean;
import si.fri.rso.skupina3.lib.ParkBill;

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
@Path("/park_bills")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParkBillResource {

    private final Logger log = Logger.getLogger(RvBillResource.class.getName());

    @Inject
    private ParkBillBean parkBillBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getBills() {

        log.info("getBills() - GET");
        List<ParkBill> parkBills = parkBillBean.getBills(uriInfo);

        return Response.status(Response.Status.OK).entity(parkBills).build();
    }

    @GET
    @Path("{id}")
    public Response getBill(@PathParam("id") Integer id) {

        log.info("getBill() - GET");

        ParkBill parkBill = parkBillBean.getBill(id);

        if(parkBill == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(parkBill).build();
    }

    @POST
    public Response createBill(ParkBill parkBill) {

        log.info("createBill() - POST");

        if (parkBill == null || parkBill.getPayer_id() == null || parkBill.getReceiver_id() == null ||
                parkBill.getPark_id() == null || parkBill.getPrice() == null || parkBill.getReservation_id() == null
        ) {
            log.info("Some needed values are missing!");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        else {
            parkBill = parkBillBean.createBill(parkBill);
        }

        return Response.status(Response.Status.OK).entity(parkBill).build();
    }

    @PUT
    @Path("{billId}")
    public Response putBill(@PathParam("billId") Integer billId, ParkBill parkBill) {

        log.info("putBill() - PUT");
        //TODO: preveri, da so ustrezna polja
        parkBill.setBill_id(billId);
        parkBill = parkBillBean.updateBill(billId, parkBill);

        if (parkBill == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(parkBill).build();

    }

    @DELETE
    @Path("{billId}")
    public Response deleteBill(@PathParam("billId") Integer billId) {

        log.info("deleteBill() - DELETE");

        boolean deleted = parkBillBean.deleteBill(billId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
