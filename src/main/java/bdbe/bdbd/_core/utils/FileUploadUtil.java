package bdbe.bdbd._core.utils;

import bdbe.bdbd._core.exception.BadRequestError;
import bdbe.bdbd._core.exception.InternalServerError;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.IOException;
import java.util.*;


@Service
@Slf4j
public class FileUploadUtil {

    private final AmazonS3 s3Client;
    private final String bucketName;
    static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png");

    public FileUploadUtil(
            @Value("${cloud.aws.credentials.accessKey}") String accessKey,
            @Value("${cloud.aws.credentials.secretKey}") String secretKey,
            @Value("${cloud.aws.region.static}") String region,
            @Value("${cloud.aws.proxy.host}") String proxyHost,
            @Value("${cloud.aws.proxy.port}") int proxyPort,
            @Value("${cloud.aws.s3.bucket}") String bucketName) {

        log.info("accessKey:" + accessKey);
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(region);

        if ("prod".equals(System.getProperty("spring.profiles.active")) &&
                proxyHost != null && !proxyHost.isEmpty() && proxyPort > 0) {
            ClientConfiguration clientConfiguration = new ClientConfiguration();
            clientConfiguration.setProxyHost(proxyHost);
            clientConfiguration.setProxyPort(proxyPort);
            builder.withClientConfiguration(clientConfiguration);
        }

        this.s3Client = builder.build();
        this.bucketName = bucketName;
    }

    public void validateFiles(MultipartFile[] files) throws BadRequestError {
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String extension = "";

            if (originalFilename != null) {
                int i = originalFilename.lastIndexOf('.');
                if (i > 0) {
                    extension = originalFilename.substring(i).toLowerCase();
                }
            }

            if (!FileUploadUtil.ALLOWED_EXTENSIONS.contains(extension)) {
                throw new BadRequestError(
                        BadRequestError.ErrorCode.VALIDATION_FAILED,
                        Collections.singletonMap("Invalid file extension for file: " + originalFilename, "Only JPG, JPEG, and PNG are allowed.")
                );
            }
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        validateFiles(new MultipartFile[]{file}); // This will throw BadRequestError if validation fails

        String originalFilename = file.getOriginalFilename();
        String mimeType = file.getContentType();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.')).toLowerCase();

        String uniqueFilename = UUID.randomUUID().toString() + extension;
        String keyName = "uploads/" + uniqueFilename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(mimeType);
        metadata.setContentLength(file.getSize());
        metadata.setContentDisposition("inline");

        s3Client.putObject(bucketName, keyName, file.getInputStream(), metadata);
        return s3Client.getUrl(bucketName, keyName).toExternalForm();
    }

    public List<String> uploadFiles(List<MultipartFile> files) {
        List<String> uploadResults = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String result = uploadFile(file);
                uploadResults.add(result);
            } catch (Exception e) {
                log.error("File upload failed: " + e.getMessage(), e);
                throw new InternalServerError(
                        InternalServerError.ErrorCode.INTERNAL_SERVER_ERROR,
                        Collections.singletonMap("File", "File upload failed: " + e.getMessage()));
            }
        }
        return uploadResults;
    }
}