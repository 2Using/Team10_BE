package bdbe.bdbd._core.errors.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;

@Configuration
@Slf4j
public class AWSConfig {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${proxy.host:}")
    private String proxyHost;

    @Value("${proxy.port:0}")
    private int proxyPort;


    @Bean
    @Profile("!prod")
    public AmazonS3 amazonS3Client() {
        log.info("Initializing AmazonS3 client for not 'prod' profile.");
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    @Bean
    @Profile("prod")
    public AmazonS3 amazonS3ClientProd() {
        log.info("Initializing AmazonS3 client for 'prod' profile.");

        ClientConfiguration clientConfiguration = new ClientConfiguration();
        // 연결 타임아웃 시간을 60초로 설정
        clientConfiguration.setConnectionTimeout(60000);  // 10,000 ms = 10 s

        // 소켓 타임아웃 시간을 60초로 설정
        clientConfiguration.setSocketTimeout(60000);  // 10,000 ms = 10 s
        if (!StringUtils.isEmpty(proxyHost) && proxyPort > 0) {
            log.info("Setting up proxy: Host = {}, Port = {}", proxyHost, proxyPort);
            clientConfiguration.setProxyHost(proxyHost);
            clientConfiguration.setProxyPort(proxyPort);
        } else {
            log.warn("Proxy settings are empty or incorrect. Proxy will not be used.");
        }

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        log.info("AWS Credentials: AccessKey = {}, SecretKey is {} characters long.",
                accessKey, secretKey.length());

        AmazonS3 amazonS3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withClientConfiguration(clientConfiguration)
                .withRegion(region)
                .build();

        if (amazonS3Client != null) {
            log.info("Successfully initialized AmazonS3 client.");
        } else {
            log.error("Failed to initialize AmazonS3 client.");
        }

        return amazonS3Client;
    }
}
