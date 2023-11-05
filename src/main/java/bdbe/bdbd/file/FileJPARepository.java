package bdbe.bdbd.file;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileJPARepository extends JpaRepository<File, Long> {
    List<File> findByCarwash_IdAndIsDeletedFalse(Long carwashId);

    Optional<File> findFirstByCarwashIdAndIsDeletedFalseOrderByUploadedAtAsc(Long carwashId);

}
