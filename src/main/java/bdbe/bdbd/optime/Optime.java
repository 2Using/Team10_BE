package bdbe.bdbd.optime;

import bdbe.bdbd.carwash.Carwash;
import bdbe.bdbd.keyword.carwashKeyword.CarwashKeyword;
import bdbe.bdbd.reservation.Reservation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="optime")
public class Optime{ // 영업시간
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Enumerated(EnumType.STRING) // DB에 문자열 저장
    @Column(name="day_type", length = 10, nullable = false)
    private DayType dayType; // enum 요일명 (평일, 주말, 휴일)

    @Column(name="start_time", nullable = false)
    private LocalTime startTime; // ex)10:00

    @Column(name="end_time", nullable = false)
    private LocalTime endTime;
    @Builder
    public Optime(Long id, DayType dayType, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.dayType = dayType;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}