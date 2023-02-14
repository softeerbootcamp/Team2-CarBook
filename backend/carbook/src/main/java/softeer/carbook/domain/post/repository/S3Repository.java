package softeer.carbook.domain.post.repository;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import softeer.carbook.domain.user.service.UserService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Repository
public class S3Repository {
    private static final Logger logger = LoggerFactory.getLogger(S3Repository.class);
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    public S3Repository(AmazonS3 amazonS3Client) {
        this.amazonS3Client = (AmazonS3Client) amazonS3Client;
    }

    public String upload(MultipartFile multipartFile, String dirName, int postId) {
        File uploadImage = makeLocalFile(multipartFile, postId).orElseThrow(() -> new IllegalArgumentException("Local File Creation Failed"));
        return upload(uploadImage, dirName);
    }

    private Optional<File> makeLocalFile(MultipartFile file, int postId){
        File convertFile = new File(postId+"_"+file.getOriginalFilename());
        try {
            if (convertFile.createNewFile()) {
                FileOutputStream fos = new FileOutputStream(convertFile);
                fos.write(file.getBytes());
                return Optional.of(convertFile);
            }
            convertFile.delete();
            return Optional.empty();
        } catch (IOException e) {
            convertFile.delete();
            return Optional.empty();
        }
    }

    private String upload(File uploadImage, String dirName){
        String fileName = dirName + "/" +uploadImage.getName();
        String uploadImageUrl = uploadS3(uploadImage, fileName);
        uploadImage.delete();
        return uploadImageUrl;
    }

    private String uploadS3(File image, String fileName){
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, image).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public void deleteS3(String key){
        try{
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
        } catch (SdkClientException e){
            logger.info("Image Not Exist In S3");
        }
    }

}
