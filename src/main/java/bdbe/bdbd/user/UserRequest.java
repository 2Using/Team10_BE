package bdbe.bdbd.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class UserRequest {
    @Getter
    @Setter
    public static class JoinDTO {

        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;

        @NotEmpty
        @Size(min = 8, max = 20, message = "8에서 20자 이내여야 합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.")
        private String password;

        @Size(min = 8, max = 45, message = "8에서 45자 이내여야 합니다.")
        @NotEmpty
        private String username;

//        @NotNull
//        private Long regionId;

        @Enumerated(EnumType.STRING)
        @NotNull
        private UserRole role;

//        //notNUll 설정 불가 by int
//        private int credit = 0;

        @Size(min = 9, max = 14)
        @NotEmpty
        private String tel;

        //        public User toEntity(RegionJPARepository regionRepository) {
//            Region region = regionRepository.findById(regionId)
//                    .orElseThrow(() -> new IllegalArgumentException("해당 ID의 Region이 존재하지 않습니다."));
//
//            return User.builder()
//                    .email(email)
//                    .password(password)
//                    .username(username)
//                    .role(String.valueOf(UserRole.ROLE_USER))
//                    .build();
//        }
        public User toEntity(String encodedPassword) {
            return User.builder()
                    .email(email)
                    .password(encodedPassword)
                    .username(username)
                    .role(String.valueOf(role))
//                    .credit(0)
                    .tel(tel)
                    .build();
        }

    }


    @Getter
    @Setter
    public static class LoginDTO {
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;

        @NotEmpty
        @Size(min = 8, max = 20, message = "8에서 20자 이내여야 합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.")
        private String password;
    }

    @Getter
    @Setter
    public static class EmailCheckDTO {
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;
    }

    @Getter
    @Setter
    public static class UpdatePasswordDTO {
        @NotEmpty
        @Size(min = 8, max = 20, message = "8에서 20자 이내여야 합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.")
        private String password;
    }

}



