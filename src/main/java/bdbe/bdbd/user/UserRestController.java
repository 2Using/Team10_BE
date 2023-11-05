package bdbe.bdbd.user;


import bdbe.bdbd._core.errors.exception.BadRequestError;
import bdbe.bdbd._core.errors.security.CustomUserDetails;
import bdbe.bdbd._core.errors.security.JWTProvider;
import bdbe.bdbd._core.errors.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;
    // (기능3) 이메일 중복체크
    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody @Valid UserRequest.EmailCheckDTO emailCheckDTO, Errors errors) {
        userService.sameCheckEmail(emailCheckDTO.getEmail());
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    //(기능4) 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> joinUser(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Errors errors) {
        requestDTO.setRole(UserRole.ROLE_USER);
        userService.join(requestDTO);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }



    // (기능5) 로그인
//    @PostMapping("/login")
////    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO, Errors errors) {
////        String jwt = userService.login(requestDTO);
////        return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body(ApiUtils.success(null));
////    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO, Errors errors) {
//        if (errors.hasErrors()) {
//            String errorMessage = errors.getAllErrors().get(0).getDefaultMessage();
//            throw new Exception400(errorMessage);
//        }
//        String jwt = userService.login(requestDTO);
//        return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body(ApiUtils.success(null));
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO, Errors errors) {
        if (errors.hasErrors()) {
            String errorMessage = errors.getAllErrors().get(0).getDefaultMessage();
            throw new BadRequestError(errorMessage);
        }
        UserResponse.LoginResponse response = userService.login(requestDTO);
        return ResponseEntity.ok().header(JWTProvider.HEADER, response.getJwtToken()).body(ApiUtils.success(response));
    }

    @PostMapping("/info")
    public ResponseEntity<?> findUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        OwnerResponse.UserInfoDTO dto = memberService.findUserInfo(userDetails.getMember());
        return ResponseEntity.ok(ApiUtils.success(dto));
    }

    // 로그아웃 사용안함 - 프론트에서 JWT 토큰을 브라우저의 localstorage에서 삭제하면 됨.
}

