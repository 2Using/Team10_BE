package bdbe.bdbd.reservation;

import bdbe.bdbd.bay.Bay;
import bdbe.bdbd.carwash.Carwash;
import bdbe.bdbd.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ReservationRequest {

    @Getter
    @Setter
    @ToString
    public static class SaveDTO {

        private Long bayId;

        private LocalDateTime startTime;
        private LocalDateTime endTime;

        public Reservation toReservationEntity(Carwash carwash, Bay bay, User user) {
            int perPrice = carwash.getPrice();
            LocalDateTime startTime = this.startTime;
            LocalDateTime endTime = this.endTime;

            int minutesDifference = (int) ChronoUnit.MINUTES.between(startTime, endTime); //시간 차 계산
            int blocksOf30Minutes = minutesDifference / 30; //30분 단위로 계산
            int price = perPrice * blocksOf30Minutes;

            return Reservation.builder()
                    .startTime(startTime)
                    .endTime(endTime)
                    .price(price)
                    .bay(bay)
                    .user(user)
                    .build();
        }
    }
}