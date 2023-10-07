package bdbe.bdbd.reservation;

import bdbe.bdbd._core.errors.security.CustomUserDetails;
import bdbe.bdbd._core.errors.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class ReservationRestController {

    private final ReservationService reservationService;

    // 세차장 예약하기
    @PostMapping("/carwashes/{carwash_id}/bays/{bay_id}/reservations")
    public ResponseEntity<?> save(
            @PathVariable("carwash_id") Long carwashId,
            @PathVariable("bay_id") Long bayId,
            @RequestBody ReservationRequest.SaveDTO dto,
            @AuthenticationPrincipal CustomUserDetails userDetails
            )
    {
        reservationService.save(dto, carwashId, bayId, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(null));

    }

    // 세차장별 예약 내역 조회
    @GetMapping("/carwashes/{carwash_id}/bays")
    public ResponseEntity<?> findAllByCarwash(
            @PathVariable("carwash_id") Long carwashId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    )
    {
        ReservationResponse.findAllResponseDTO dto = reservationService.findAllByCarwash(carwashId, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(dto));

    }

    // 결제 후 예약 내역 조회
    @GetMapping("/reservations")
    public ResponseEntity<?> fetchLatestReservation(
            @AuthenticationPrincipal CustomUserDetails userDetails
    )
    {
        ReservationResponse.findLatestOneResponseDTO dto = reservationService.fetchLatestReservation(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(dto));
    }

    // 현재 시간 기준 예약 내역 조회
    @GetMapping("/reservations/current-status")
    public ResponseEntity<?> fetchCurrentStatusReservation(
            @AuthenticationPrincipal CustomUserDetails userDetails
    )
    {
        ReservationResponse.fetchCurrentStatusReservationDTO dto = reservationService.fetchCurrentStatusReservation(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(dto));
    }

}