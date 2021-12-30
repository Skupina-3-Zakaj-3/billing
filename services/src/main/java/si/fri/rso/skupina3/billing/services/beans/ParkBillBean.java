package si.fri.rso.skupina3.billing.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.skupina3.billing.models.converters.ParkBillConverter;
import si.fri.rso.skupina3.billing.models.entities.ParkBillEntity;
import si.fri.rso.skupina3.lib.ParkBill;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class ParkBillBean {

    private final Logger log = Logger.getLogger(ParkBillBean.class.getName());

    @Inject
    private EntityManager em;

    public List<ParkBill> getBills(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, ParkBillEntity.class, queryParameters).stream()
                .map(ParkBillConverter::toDto).collect(Collectors.toList());
    }


    public ParkBill getBill(Integer billId) {
        ParkBillEntity ParkBillEntity = em.find(ParkBillEntity.class, billId);

        if (ParkBillEntity == null) {
            throw new NotFoundException();
        }

        return ParkBillConverter.toDto(ParkBillEntity);
    }

    public ParkBill createBill(ParkBill ParkBill) {

        ParkBillEntity ParkBillEntity = ParkBillConverter.toEntity(ParkBill);

        try {
            beginTx();
            em.persist(ParkBillEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (ParkBillEntity.getBill_id() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return ParkBillConverter.toDto(ParkBillEntity);
    }

    public ParkBill updateBill(Integer billId, ParkBill ParkBill) {

        ParkBillEntity ParkBillEntity = em.find(ParkBillEntity.class, billId);

        if (ParkBillEntity == null) {
            return null;
        }

        ParkBillEntity updatedParkBillEntity = ParkBillConverter.toEntity(ParkBill);

        try {
            beginTx();
            updatedParkBillEntity.setBill_id(ParkBill.getBill_id());
            updatedParkBillEntity = em.merge(updatedParkBillEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return ParkBillConverter.toDto(updatedParkBillEntity);
    }

    public boolean deleteBill(Integer billId) {

        ParkBillEntity ParkBillEntity = em.find(ParkBillEntity.class, billId);

        if (ParkBillEntity != null) {
            try {
                beginTx();
                em.remove(ParkBillEntity);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }


    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
