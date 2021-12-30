package si.fri.rso.skupina3.billing.models.converters;

import si.fri.rso.skupina3.billing.models.entities.RvBillEntity;
import si.fri.rso.skupina3.lib.RvBill;

public class RvBillConverter {

    public static RvBill toDto(RvBillEntity entity) {

        RvBill dto = new RvBill();
        dto.setBill_id(entity.getBill_id());
        dto.setPayer_id(entity.getPayer_id());
        dto.setReceiver_id(entity.getReceiver_id());
        dto.setReservation_id(entity.getReservation_id());
        dto.setRv_id(entity.getRv_id());
        dto.setPrice(entity.getPrice());

        return dto;
    }

    public static RvBillEntity toEntity(RvBill dto) {

        RvBillEntity entity = new RvBillEntity();
        entity.setBill_id(dto.getBill_id());
        entity.setPayer_id(dto.getPayer_id());
        entity.setReceiver_id(dto.getReceiver_id());
        entity.setReservation_id(dto.getReservation_id());
        entity.setRv_id(dto.getRv_id());
        entity.setPrice(dto.getPrice());

        return entity;
    }

}
