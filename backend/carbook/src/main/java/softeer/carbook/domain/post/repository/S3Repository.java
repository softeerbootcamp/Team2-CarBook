package softeer.carbook.domain.post.repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Repository
public class S3Repository {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    public S3Repository(AmazonS3 amazonS3Client) {
        this.amazonS3Client = (AmazonS3Client) amazonS3Client;
    }

    public String upload(MultipartFile multipartFile, String dirName, int postId) throws IOException {
        File uploadImage = makeLocalFile(multipartFile).orElseThrow(() -> new IllegalArgumentException("Local File Creation Failed"));
        return upload(uploadImage, dirName, postId);
    }

    private Optional<File> makeLocalFile(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)){
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    private String upload(File uploadImage, String dirName, int postId){
        String fileName = dirName + "/" + Integer.toString(postId)+"_"+uploadImage.getName();
        String uploadImageUrl = uploadS3(uploadImage, fileName);
        uploadImage.delete();
        return uploadImageUrl;
    }

    private String uploadS3(File image, String fileName){
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, image).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

}
