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

    private final Logger log = Logger.getLogger(BillResource.class.getName());

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

    @GET
    @Path("{id}")
    public Response getBill(@PathParam("id") Integer id) {

        log.info("getBill() - GET");

        Bill bill = billBean.getBill(id);

        if(bill == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(bill).build();
    }

    @POST
    public Response createBill(Bill bill) {

        log.info("createBill() - POST");

        // TODO: check for needed parameters and send back bad request if they are not present
        if (bill == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            bill = billBean.createBill(bill);
        }

        return Response.status(Response.Status.OK).entity(bill).build();
    }

    @PUT
    @Path("{billId}")
    public Response putRv(@PathParam("billId") Integer billId, Bill bill) {

        log.info("putBill() - PUT");
        //TODO: preveri, da so ustrezna polja
        bill = billBean.updateBill(billId, bill);

        if (bill == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(bill).build();

    }

    @DELETE
    @Path("{billId}")
    public Response deleteRv(@PathParam("billId") Integer billId) {

        log.info("deleteBill() - DELETE");

        boolean deleted = billBean.deleteBill(billId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
