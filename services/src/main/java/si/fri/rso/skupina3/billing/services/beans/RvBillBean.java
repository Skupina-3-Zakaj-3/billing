package si.fri.rso.skupina3.billing.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.skupina3.billing.models.converters.RvBillConverter;
import si.fri.rso.skupina3.billing.models.entities.RvBillEntity;
import si.fri.rso.skupina3.lib.RvBill;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class RvBillBean {

    private final Logger log = Logger.getLogger(RvBillBean.class.getName());

    @Inject
    private EntityManager em;

    public List<RvBill> getBills(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, RvBillEntity.class, queryParameters).stream()
                .map(RvBillConverter::toDto).collect(Collectors.toList());
    }


    public RvBill getBill(Integer billId) {
        RvBillEntity rvBillEntity = em.find(RvBillEntity.class, billId);

        if (rvBillEntity == null) {
            throw new NotFoundException();
        }

        return RvBillConverter.toDto(rvBillEntity);
    }

    public RvBill createBill(RvBill rvBill) {

        RvBillEntity rvBillEntity = RvBillConverter.toEntity(rvBill);

        try {
            beginTx();
            em.persist(rvBillEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (rvBillEntity.getBill_id() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return RvBillConverter.toDto(rvBillEntity);
    }

    public RvBill updateBill(Integer billId, RvBill rvBill) {

        RvBillEntity rvBillEntity = em.find(RvBillEntity.class, billId);

        if (rvBillEntity == null) {
            return null;
        }

        RvBillEntity updatedRvBillEntity = RvBillConverter.toEntity(rvBill);

        try {
            beginTx();
            updatedRvBillEntity.setBill_id(rvBill.getBill_id());
            updatedRvBillEntity = em.merge(updatedRvBillEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return RvBillConverter.toDto(updatedRvBillEntity);
    }

    public boolean deleteBill(Integer billId) {

        RvBillEntity rvBillEntity = em.find(RvBillEntity.class, billId);

        if (rvBillEntity != null) {
            try {
                beginTx();
                em.remove(rvBillEntity);
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
