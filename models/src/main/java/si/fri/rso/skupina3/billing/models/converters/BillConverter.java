package si.fri.rso.skupina3.billing.models.converters;

import si.fri.rso.skupina3.billing.models.entities.BillEntity;
import si.fri.rso.skupina3.lib.Bill;

public class BillConverter {

    public static Bill toDto(BillEntity entity) {

        Bill dto = new Bill();
        dto.setPayer_id(entity.getPayer_id());
        dto.setReceiver_id(entity.getReceiver_id());
        dto.setRv_reservation_id(entity.getRv_reservation_id());
        dto.setRv_id(entity.getRv_id());
        dto.setPrice(entity.getPrice());

        return dto;
    }

    public static BillEntity toEntity(Bill dto) {

        BillEntity entity = new BillEntity();
        entity.setPayer_id(dto.getPayer_id());
        entity.setReceiver_id(dto.getReceiver_id());
        entity.setRv_reservation_id(dto.getRv_reservation_id());
        entity.setRv_id(dto.getRv_id());
        entity.setPrice(dto.getPrice());

        return entity;
    }
}
