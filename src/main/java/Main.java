import io.minio.GetObjectArgs;
import io.minio.MinioClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@lombok.extern.slf4j.Slf4j
public class Main {
    public static void main(String[] args) {
        int record_counter = 0;
        MinioClient minioClient = MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials("minioadmin", "minioadmin")
                .build();
        try (InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                .bucket("my-bucketname")
                .object("my-objectname")
                .build());
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        ) {
            while(reader.ready()) {
                reader.readLine();
                record_counter += 1;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        System.out.println(record_counter);
    }
}
