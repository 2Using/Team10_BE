package bdbe.bdbd.controller.user;

import bdbe.bdbd._core.security.CustomUserDetails;
import bdbe.bdbd._core.utils.ApiUtils;
import bdbe.bdbd.dto.reservation.ReservationRequest;
import bdbe.bdbd.dto.reservation.ReservationResponse;
import bdbe.bdbd.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 사용자 예약 관련 요청을 처리하는 사용자 API
 * 예약 생성, 수정, 삭제 및 현재 및 최근 예약 정보 조회 기능 제공
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserReservationController {

    private final ReservationService reservationService;

    @PostMapping("/carwashes/{bay-id}/payment")
    public ResponseEntity<?> findPayAmount(
            @PathVariable("bay-id") Long bayId,
            @Valid @RequestBody ReservationRequest.ReservationTimeDTO dto
    ) {
        ReservationResponse.PayAmountDTO responseDTO = reservationService.findPayAmount(dto, bayId);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PutMapping("/reservations/{reservation-id}")
    public ResponseEntity<?> updateReservation(
            @PathVariable("reservation-id") Long reservationId,
            @Valid @RequestBody ReservationRequest.UpdateDTO dto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        reservationService.update(dto, reservationId, userDetails.getMember());

        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @DeleteMapping("/reservations/{reservation-id}")
    public ResponseEntity<?> deleteReservation(
            @PathVariable("reservation-id") Long reservationId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        reservationService.delete(reservationId, userDetails.getMember());

        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @GetMapping("/reservations/current-status")
    public ResponseEntity<?> findCurrentStatusReservation(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        ReservationResponse.fetchCurrentStatusReservationDTO dto = reservationService.findCurrentStatusReservation(userDetails.getMember());

        return ResponseEntity.ok(ApiUtils.success(dto));
    }

    @GetMapping("/reservations/recent")
    public ResponseEntity<?> updateReservation(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        ReservationResponse.fetchRecentReservationDTO dto = reservationService.fetchRecentReservation(userDetails.getMember());

        return ResponseEntity.ok(ApiUtils.success(dto));
    }
}