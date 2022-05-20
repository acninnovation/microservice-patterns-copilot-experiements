package no.acntech.reservation.model;

import java.util.UUID;

public class ReservationEvent {

    private UUID reservationId;

    public UUID getReservationId() {
        return reservationId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private UUID reservationId;

        private Builder() {
        }

        public Builder reservationId(UUID reservationId) {
            this.reservationId = reservationId;
            return this;
        }

        public ReservationEvent build() {
            final var target = new ReservationEvent();
            target.reservationId = this.reservationId;
            return target;
        }
    }
}
