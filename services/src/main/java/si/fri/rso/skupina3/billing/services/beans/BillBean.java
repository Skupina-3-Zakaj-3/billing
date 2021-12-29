package si.fri.rso.skupina3.billing.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.skupina3.billing.models.converters.BillConverter;
import si.fri.rso.skupina3.billing.models.entities.BillEntity;
import si.fri.rso.skupina3.lib.Bill;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class BillBean {

    private final Logger log = Logger.getLogger(BillBean.class.getName());

    @Inject
    private EntityManager em;

    public List<Bill> getBills(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, BillEntity.class, queryParameters).stream()
                .map(BillConverter::toDto).collect(Collectors.toList());
    }


    public Bill getBill(Integer billId) {
        BillEntity billEntity = em.find(BillEntity.class, billId);

        if (billEntity == null) {
            throw new NotFoundException();
        }

        return BillConverter.toDto(billEntity);
    }

    public Bill createBill(Bill bill) {

        BillEntity billEntity = BillConverter.toEntity(bill);

        try {
            beginTx();
            em.persist(billEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (billEntity.getRv_id() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return BillConverter.toDto(billEntity);
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
