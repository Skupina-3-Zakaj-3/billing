package si.fri.rso.skupina3.billing.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.skupina3.billing.models.converters.BillConverter;
import si.fri.rso.skupina3.billing.models.entities.BillEntity;
import si.fri.rso.skupina3.lib.Bill;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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
}
