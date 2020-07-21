import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        MinioClient minioClient = MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials("minioadmin", "minioadmin")
                .build();
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket("test").build());
        for (Result<Item> result : results) {
            try {
                String objectName = result.get().objectName();
                System.out.println(result.get().objectName());
                int nuOfRecords = countRecord(minioClient,objectName);
                System.out.println(nuOfRecords);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
//            try {
//                System.out.println(result.get());
//            }
//            catch (Exception e ) {
//                System.out.println(e.toString()
//                );
//            }
        }
    }

    public static int countRecord(MinioClient minioClient, String objectName){
        int record_counter = 0;
        try (InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                .bucket("test")
                .object(objectName)
                .build());
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        ) {
            while(reader.readLine() != null) {
                record_counter += 1;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return record_counter;
    }
}
