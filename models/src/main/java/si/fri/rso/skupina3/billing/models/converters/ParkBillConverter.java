package si.fri.rso.skupina3.billing.models.converters;

import si.fri.rso.skupina3.billing.models.entities.ParkBillEntity;
import si.fri.rso.skupina3.lib.ParkBill;

public class ParkBillConverter {

    public static ParkBill toDto(ParkBillEntity entity) {

        ParkBill dto = new ParkBill();
        dto.setBill_id(entity.getBill_id());
        dto.setPayer_id(entity.getPayer_id());
        dto.setReceiver_id(entity.getReceiver_id());
        dto.setReservation_id(entity.getReservation_id());
        dto.setPark_id(entity.getPark_id());
        dto.setPrice(entity.getPrice());

        return dto;
    }

    public static ParkBillEntity toEntity(ParkBill dto) {

        ParkBillEntity entity = new ParkBillEntity();
        entity.setBill_id(dto.getBill_id());
        entity.setPayer_id(dto.getPayer_id());
        entity.setReceiver_id(dto.getReceiver_id());
        entity.setReservation_id(dto.getReservation_id());
        entity.setPark_id(dto.getPark_id());
        entity.setPrice(dto.getPrice());

        return entity;
    }
}
