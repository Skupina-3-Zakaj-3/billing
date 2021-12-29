package si.fri.rso.skupina3.lib;

public class Bill {
    private Integer payer_id;
    private Integer receiver_id;
    private Integer rv_id;
    private Integer rv_reservation_id;
    private Float price;

    public Integer getPayer_id() {
        return payer_id;
    }

    public void setPayer_id(Integer payer_id) {
        this.payer_id = payer_id;
    }

    public Integer getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(Integer receiver_id) {
        this.receiver_id = receiver_id;
    }

    public Integer getRv_id() {
        return rv_id;
    }

    public void setRv_id(Integer rv_id) {
        this.rv_id = rv_id;
    }

    public Integer getRv_reservation_id() {
        return rv_reservation_id;
    }

    public void setRv_reservation_id(Integer rv_reservation_id) {
        this.rv_reservation_id = rv_reservation_id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
