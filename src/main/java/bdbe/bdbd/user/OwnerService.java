package bdbe.bdbd.user;

import bdbe.bdbd._core.errors.exception.BadRequestError400;
import bdbe.bdbd._core.errors.exception.InternalServerError500;
import bdbe.bdbd._core.errors.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OwnerService {
         private final PasswordEncoder passwordEncoder;
         private final UserJPARepository userJPARepository;

    public void join(UserRequest.JoinDTO requestDTO) {
             sameCheckEmail(requestDTO.getEmail());

             requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
             try {
                 userJPARepository.save(requestDTO.toEntity());
             } catch (Exception e) {
                 throw new InternalServerError500("unknown server error");
             }
         }

    public String login(UserRequest.LoginDTO requestDTO) {
        User userPS = userJPARepository.findByEmail(requestDTO.getEmail()).orElseThrow(
                () -> new BadRequestError400("이메일을 찾을 수 없습니다 : "+requestDTO.getEmail())
        );

        if(!passwordEncoder.matches(requestDTO.getPassword(), userPS.getPassword())){
            throw new BadRequestError400("패스워드가 잘못입력되었습니다 ");
        }
        return JWTProvider.create(userPS);
    }


    public void sameCheckEmail(String email) {
        Optional<User> userOP = userJPARepository.findByEmail(email);
        if (userOP.isPresent()) {
            throw new BadRequestError400("동일한 이메일이 존재합니다 : " + email);
        }
    }
}
