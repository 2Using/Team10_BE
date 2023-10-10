package bdbe.bdbd.review;

import bdbe.bdbd.reservation.Reservation;
import bdbe.bdbd.rkeyword.reviewKeyword.ReviewKeyword;
import bdbe.bdbd.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewResponse {

    @Getter
    @Setter
    public static class getReviewById{
        private Long id;
        private Long uId;
        private Long cId;
        private Reservation reservation; //1:1, 참조용(read-only)
        private String comment;
        private double rate;

        public getReviewById(Review review) {
            this.id = review.getId();
            this.uId = review.getUser().getId();
            this.cId = review.getCarwash().getId();
            this.reservation = review.getReservation();
            this.comment = review.getComment();
            this.rate = review.getRate();
        }

    }
    @Getter
    @Setter
    public static class ReviewByCarwashIdDTO {
        private double rate;
        private String username;
        private LocalDateTime created_at;
        private String comment;
        private List<Long> keywordIdList;

        public ReviewByCarwashIdDTO(Review review, User user, List<ReviewKeyword> reviewKeyword) {
            this.rate = review.getRate();
            this.username = user.getUsername();
            this.created_at = review.getCreatedAt();
            this.comment = review.getComment();
            this.keywordIdList = reviewKeyword.stream()
                            .map(rk -> rk.getKeyword().getId())
                            .collect(Collectors.toList());
        }
    }
    @Getter
    @Setter
    public static class ReviewResponseDTO {
        private List<ReviewByCarwashIdDTO> reviews;

        public ReviewResponseDTO(List<ReviewByCarwashIdDTO> reviews) {
            this.reviews = reviews;
        }
    }

}


