package test.channeltest;

import com.projeto.watchflix.controller.ChannelController;
import com.projeto.watchflix.service.UserService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
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
public class ChannelTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private ChannelController channelController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(channelController).build();
    }

    private String url = "http://localhost:8080/";



    @Test
    public void testRegisterChannel() throws IOException {
        File imageFile = new File("C:\\Users\\jabub\\OneDrive\\Área de Trabalho\\ProjetoPi\\projeto\\backend\\src\\main\\java\\images\\imagem.jpg");
        assertTrue(imageFile.exists(), "Image file does not exist");

        // Prepare multipart form data
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.addBinaryBody("file", imageFile);
        entityBuilder.addTextBody("firstName", "John");
        entityBuilder.addTextBody("lastName", "Doe");
        entityBuilder.addTextBody("fullName", "John Doe");
        entityBuilder.addTextBody("email", "john@example.com");
        entityBuilder.addTextBody("password", "password");

        HttpPost postRequest = new HttpPost(url + "api/channel");
        postRequest.setEntity(entityBuilder.build());

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
                int statusCode = response.getCode();
                assertEquals(201, statusCode, "Expected status code 201 for successful creation");

            } catch (HttpResponseException e) {
                fail("HTTP response exception occurred: " + e.getMessage());
            }
        }
    }

    @Test
    public void findAllChannels() {
        HttpUriRequest request = new HttpGet(url + "api/channel");

        try (CloseableHttpClient httpClient = HttpClients.custom().build()) {
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                int statusCode = response.getCode();
                assertEquals(200, statusCode, "Expected status code 200");

                String content = EntityUtils.toString(response.getEntity());

                JSONArray jsonArray = (JSONArray) JSONValue.parse(content);
                int numberOfItems = jsonArray.size();

                assertTrue(numberOfItems >= 1, "Number of items is at least 1 or more");

            } catch (HttpResponseException e) {
                fail("HTTP response exception occurred: " + e.getMessage());
            }
        } catch (IOException | ParseException e) {
            fail("IO exception occurred: " + e.getMessage());
        }
    }


    @Test
    public void findChannelById() {
        HttpUriRequest request = new HttpGet(url + "api/channel/id?id=1654");

        try (CloseableHttpClient httpClient = HttpClients.custom().build()) {
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                int statusCode = response.getCode();
                assertEquals(200, statusCode, "Expected status code 200");

                String content = EntityUtils.toString(response.getEntity());

                JSONObject jsonObject = (JSONObject) JSONValue.parse(content);

                assertNotNull(jsonObject.get("id"), "Field 'id' should not be null and should exist");

            } catch (HttpResponseException e) {
                fail("HTTP response exception occurred: " + e.getMessage());
            }
        } catch (IOException | ParseException e) {
            fail("IO exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteChannelById() throws Exception {
        long channelIdToDelete = 312;


        HttpUriRequest deleteRequest = new HttpDelete(url + "api/channel/id/?id=" + channelIdToDelete);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpClient.execute(deleteRequest)) {
                int statusCode = response.getCode();

                assertEquals(HttpStatus.SC_OK, statusCode, "Expected status code 200 for successful deletion");

            } catch (HttpResponseException e) {
                fail("HTTP response exception occurred: " + e.getMessage());
            }
        }
    }

}