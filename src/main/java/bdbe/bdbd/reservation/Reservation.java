package bdbe.bdbd.reservation;

import bdbe.bdbd.bay.Bay;
import bdbe.bdbd.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Column(nullable = false)
    private int price;

    @Column(name="start_time", nullable = false)
    private LocalDateTime startTime; // ex) 2023-10-11T12:34

    @Column(name="end_time", nullable = false)
    private LocalDateTime endTime;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY) //외래키
    @JoinColumn(name="b_id",  nullable = false)
    private Bay bay;

    @ManyToOne(fetch = FetchType.LAZY) //외래키
    @JoinColumn(name="u_id",  nullable = false)
    private User user;


    @Builder

    public Reservation(Long id, int price, LocalDateTime startTime, LocalDateTime endTime, Bay bay, User user) {
        this.id = id;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bay = bay;
        this.user = user;
    }
}