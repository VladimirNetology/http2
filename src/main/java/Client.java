import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Client {
    public static final String HTTP_SYTE = "https://api.nasa.gov/planetary/apod?api_key=MsovgeAFtpqzlxbiniUIeh8hbi1CVeaYcgmpqW8Y";
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        final CloseableHttpClient httpClient = HttpClients.createDefault();

        final HttpUriRequest httpGet = new HttpGet(HTTP_SYTE);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            Nasa nasa = mapper.readValue(
                    response.getEntity().getContent(), new TypeReference<>() {
                    }
            );

            System.out.println(nasa.getUrl());
            URL website = new URL(nasa.getUrl());

            String[] temp = nasa.getUrl().split("/");
            String fileName = temp[temp.length - 1];
            System.out.println(fileName);

            ReadableByteChannel readableByteChannel = Channels.newChannel(website.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        }
    }
}

