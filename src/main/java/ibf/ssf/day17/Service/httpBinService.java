package ibf.ssf.day17.Service;

import java.io.StringReader;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class httpBinService {

    public void postAsJson(String name, String email) {
        // create JSON object
        JsonObject json = Json.createObjectBuilder()
                .add("name", name)
                .add("email", email)
                .build();

        RequestEntity<String> req = RequestEntity
                .post("http://httpbin.org/post")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(json.toString(), String.class);

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req, String.class);

        // check the status code
        System.out.printf("Status code: %s\n".formatted(resp.getStatusCode()));

        String payload = resp.getBody();

        System.out.printf("Payload: %s\n", payload);

    }

    public void post(String name, String email) {

        // create a form
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();

        form.set("name", name);
        form.set("email", email);

        // POST /post
        // Content type is applciation/x-www-form-urlencoded
        // Accept: application/json
        RequestEntity<MultiValueMap<String, String>> req = RequestEntity
                .post("http://httpbin.org/post")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(form, MultiValueMap.class);

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req, String.class);

        // check the status code
        System.out.printf("Status code: %s\n".formatted(resp.getStatusCode()));

        String payload = resp.getBody();

        System.out.printf("Payload: %s\n", payload);

    }

    public void get(String name, String email) {

        // create the url with the properly encoded query string
        // GET /get?name=<name>&email=<email>
        String url = UriComponentsBuilder.fromUriString("http://httpbin.org/get")
                .queryParam("name", name)
                .queryParam("email", email)
                .toUriString();

        RequestEntity<Void> req = RequestEntity
                .get(url)
                .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req, String.class);

        // check the status code
        System.out.printf("Status code: %s\n".formatted(resp.getStatusCode()));

        String payload = resp.getBody();

        System.out.printf("Payload: %s\n", payload);
    }

    public void get() {

        // creating a get request, it is always void
        RequestEntity<Void> req = RequestEntity.get("http://httpbin.org/get")
                .build();

        // creating a REST template
        RestTemplate template = new RestTemplate();

        // make the request, the payload of the response will be a string
        ResponseEntity<String> resp = template.exchange(req, String.class);

        // check the status code
        System.out.printf("Status code: %s\n".formatted(resp.getStatusCode()));

        // Get the payload
        String payload = resp.getBody();

        System.out.printf("Payload: %s\n", payload);

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject json = reader.readObject();

        JsonObject headers = json.getJsonObject("headers");
        String traceId = headers.getString("X-Amzn-Trace-Id");
        System.out.printf("Traceid is: %s\n", traceId);

    }
}
