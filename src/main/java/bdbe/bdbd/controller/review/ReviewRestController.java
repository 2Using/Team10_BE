package bdbe.bdbd.controller.review;

import bdbe.bdbd._core.security.CustomUserDetails;
import bdbe.bdbd._core.utils.ApiUtils;
import bdbe.bdbd.dto.review.ReviewRequest;
import bdbe.bdbd.dto.review.ReviewResponse;
import bdbe.bdbd.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReviewRestController {

    private final ReviewService reviewService;

    // 리뷰 등록 기능
    @PostMapping("/user/reviews")
    public ResponseEntity<?> createReview (@RequestBody @Valid ReviewRequest.SaveDTO saveDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        reviewService.createReview(saveDTO, userDetails.getMember());
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // 리뷰 조회 기능
    @GetMapping("/open/carwashes/{carwashId}/reviews")
    public ResponseEntity<?> getReviewsByCarwashId(@PathVariable("carwashId") Long carwashId) {
        ReviewResponse.ReviewResponseDTO dto = reviewService.getReviewsByCarwashId(carwashId);

        return ResponseEntity.ok(ApiUtils.success(dto));
    }

    // 리뷰 키워드 불러오기
    @GetMapping("/open/reviews")
    public ResponseEntity<?> getReviewKeyword() {
        ReviewResponse.ReviewKeywordResponseDTO dto = reviewService.getReviewKeyword();

        return ResponseEntity.ok(ApiUtils.success(dto));
    }

}
