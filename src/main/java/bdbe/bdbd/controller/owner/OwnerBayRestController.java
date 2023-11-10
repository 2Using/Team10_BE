package bdbe.bdbd.controller.owner;

import bdbe.bdbd._core.security.CustomUserDetails;
import bdbe.bdbd._core.utils.ApiUtils;
import bdbe.bdbd.dto.bay.BayRequest;
import bdbe.bdbd.service.bay.BayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 세차장의 '베이'(세차 작업 공간) 관련 기능을 처리하는 사장님 API
 * - 베이 생성: 특정 세차장에 새로운 베이를 생성합니다.
 * - 베이 상태 업데이트: 특정 베이의 상태(예: 사용 가능, 사용 불가 등)를 업데이트합니다.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/owner")
public class OwnerBayRestController {

    private final BayService bayService;

    @PostMapping("/carwashes/{carwash-id}/bays")
    public ResponseEntity<?> createBay(
            @PathVariable("carwash-id") Long carwashId,
            @Valid @RequestBody BayRequest.SaveDTO saveDTO,
            Errors errors,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        bayService.createBay(saveDTO, carwashId, userDetails.getMember());

        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @PutMapping("/bays/{bay-id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable("bay-id") Long bayId,
            @RequestParam int status,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        bayService.changeStatus(bayId, status, userDetails.getMember());

        return ResponseEntity.ok(ApiUtils.success(null));
    }
}