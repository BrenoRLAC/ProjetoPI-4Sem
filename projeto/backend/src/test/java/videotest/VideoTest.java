package videotest;

import com.projeto.watchflix.controller.VideoController;
import com.projeto.watchflix.service.VideoService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class VideoTest {

    private MockMvc mockMvc;

    @Mock
    private VideoService videoService;

    @InjectMocks
    private VideoController videoController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(videoController).build();
    }

    private String url = "http://localhost:8080/";



    @Test
    public void testRegisterVideo()  {
        File imageFile = new File("C:\\Users\\jabub\\OneDrive\\Área de Trabalho\\ProjetoPi\\projeto\\backend\\src\\main\\java\\images\\imagemvideo.jpg");
        assertTrue(imageFile.exists(), "Image file does not exist");


        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.addBinaryBody("thumbnailFile", imageFile);
        entityBuilder.addTextBody("videoUrl", "dQw4w9WgXcQ");
        entityBuilder.addTextBody("title", "Doe");
        entityBuilder.addTextBody("description", "Fager");

        int videoStatus = 3;
        entityBuilder.addTextBody("videoStatus", String.valueOf(videoStatus));

        HttpPost postRequest = new HttpPost(url + "api/videos");
        postRequest.setEntity(entityBuilder.build());

        try (CloseableHttpResponse response = HttpClients.createDefault().execute(postRequest)) {
            int statusCode = response.getCode();
            assertTrue(statusCode == 201,"Sucesso na inserção do video. " + statusCode);
        } catch (IOException e) {
            fail("Exception occurred: " + e.getMessage());
        }

    }



    @Test
    public void testRegisterVideoFailed()  {
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.addTextBody("thumbnailFile", "imagemInvalida");
        entityBuilder.addTextBody("videoUrl", "dQw4w9WgXcQ");
        entityBuilder.addTextBody("title", "Doe");
        entityBuilder.addTextBody("description", "Fager");

        int videoStatus = 3;
        entityBuilder.addTextBody("videoStatus", String.valueOf(videoStatus));

        HttpPost postRequest = new HttpPost(url + "api/videos");
        postRequest.setEntity(entityBuilder.build());

        try (CloseableHttpResponse response = HttpClients.createDefault().execute(postRequest)) {
            int statusCode = response.getCode();
            System.out.println("Falha na inserção do video: " + statusCode);
            assertTrue(statusCode == 400, "Falha na inserção do video. Arquivo de imagem inválido" + statusCode);
        } catch (IOException e) {
            fail("Exception occurred: " + e.getMessage());
        }

    }

    @Test
    public void testRegisterComment() {

        long videoId = 5;


        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String commentJson = "{\"commentText\":\"Test comment\",\"like\":0,\"dislike\":0}";

            HttpPost postRequest = new HttpPost(url + "api/videos/" + videoId + "/comment");
            postRequest.setHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
            postRequest.setEntity(new StringEntity(commentJson, ContentType.APPLICATION_JSON));

            try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
                int statusCode = response.getCode();

                assertTrue(statusCode == 201 || statusCode == 404,
                        "Expected status code 201 or 404, got: " + statusCode);
            } catch (IOException e) {
                fail("HTTP request exception occurred: " + e.getMessage());
            }
        } catch (IOException e) {
            fail("Failed to create HTTP client: " + e.getMessage());
        }
    }

    @Test
    public void findAllVideos() {
        HttpUriRequest request = new HttpGet(url + "api/videos");

        try (CloseableHttpClient httpClient = HttpClients.custom().build()) {
            CloseableHttpResponse response = httpClient.execute(request);

            int statusCode = response.getCode();
            assertEquals(200, statusCode, "Expected status code 200");

            JSONArray jsonArray = (JSONArray) JSONValue.parse(EntityUtils.toString(response.getEntity()));
            assertTrue(jsonArray.size() >= 1, "Number of items is at least 1 or more");

        } catch (IOException | ParseException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }


    @Test
    public void findAllVideosById() {
        HttpUriRequest request = new HttpGet(url + "api/videos/id?id=1");

        try (CloseableHttpClient httpClient = HttpClients.custom().build()) {
            CloseableHttpResponse response = httpClient.execute(request);

            int statusCode = response.getCode();
            assertEquals(200, statusCode, "Expected status code 200");

            JSONArray jsonArray = (JSONArray) JSONValue.parse(EntityUtils.toString(response.getEntity()));
            assertTrue(jsonArray.size() >= 1, "Number of items is at least 1 or more");

        } catch (IOException | ParseException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void findVideosByViews() {
        HttpUriRequest request = new HttpGet(url + "api/videos/videobyview?groupViewCount=5");

        try (CloseableHttpClient httpClient = HttpClients.custom().build()) {
            CloseableHttpResponse response = httpClient.execute(request);


            int statusCode = response.getCode();
            assertTrue(statusCode == 200 || statusCode == 404, "Expected status code 200 or 404, got: " + statusCode);

        } catch (IOException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteVideo() {
        long videoId = 11;

        HttpUriRequest deleteRequest = new HttpDelete(url + "api/videos/" + videoId + "/deleteVideo");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(deleteRequest)) {

            int statusCode = response.getCode();
            assertEquals(HttpStatus.SC_OK, statusCode, "Expected status code 200 for successful deletion");

        } catch (IOException e) {
            fail("Exception occurred: " + e.getMessage());

        }
    }

    @Test
    public void testDeleteComment() {
        long commentId = 11;

        HttpUriRequest deleteRequest = new HttpDelete(url + "api/videos/" + commentId + "/deletecomment");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(deleteRequest)) {

            int statusCode = response.getCode();
            assertTrue(statusCode == 200 || statusCode == 404, "Expected status code 200 or 404, got: " + statusCode);

        } catch (IOException e) {
            fail("Exception occurred: " + e.getMessage());

        }
    }


    @Test
    void testLikeOrDislikeVideo() {

        HttpUriRequest likeRequest = new HttpPost(url + "api/videos/" + 12 + "/likeOrDislikeVideo?likeOrDislike=1");


        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(likeRequest)) {

            int statusCode = response.getCode();
            assertTrue(statusCode == 200 || statusCode == 404, "Expected status code 200 or 404, got: " + statusCode);

        } catch (IOException e) {
            fail("Exception occurred: " + e.getMessage());

        }

    }

    @Test
    void testLikeOrDislikeComment() {

        HttpUriRequest likeRequest = new HttpPost(url + "api/videos/" + 12 + "/likeOrDislikeComment?likeOrDislike=0");


        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(likeRequest)) {

            int statusCode = response.getCode();
            assertTrue(statusCode == 200 || statusCode == 404, "Expected status code 200 or 404, got: " + statusCode);

        } catch (IOException e) {
            fail("Exception occurred: " + e.getMessage());

        }

    }

}