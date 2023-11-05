package bdbe.bdbd.file;

import bdbe.bdbd._core.errors.exception.ForbiddenError;
import bdbe.bdbd._core.errors.utils.FileUploadUtil;
import bdbe.bdbd.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Transactional
@Service
public class FileService {

    private final FileUploadUtil fileUploadUtil;

    @Autowired
    private FileJPARepository fileJPARepository;

    public FileService(FileUploadUtil fileUploadUtil) {
        this.fileUploadUtil = fileUploadUtil;
    }

    public FileResponse.SimpleFileResponseDTO uploadFile(MultipartFile multipartFile, Long carwashId) throws Exception {
        return fileUploadUtil.uploadFile(multipartFile, carwashId);
    }

    public List<FileResponse.SimpleFileResponseDTO> uploadFiles(MultipartFile[] multipartFile, Long carwashId) throws Exception {
        return fileUploadUtil.uploadFiles(multipartFile, carwashId);
    }

    public void deleteFile(Long fileId, Member member) {

        File file = fileJPARepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("file id :" + fileId + " not found"));
        if (file.getCarwash().getMember().getId() != member.getId()){
            throw new ForbiddenError("User is not the owner of the Carwash related to file.");
        }
        file.changeDeletedFlag(true); //삭제에 대한 플래그
        fileJPARepository.save(file);
    }


}
