package ibf.ssf.day17.Service;

import java.io.StringReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf.ssf.day17.Model.City;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class WeatherService {

    @Value("${weathermap.key}")
    private String apiKey;

    public Optional<City> getWeatherData(String city) {

        // String APIKEY = "b476655eb4d9f491696b723d41efead7";

        String Url = UriComponentsBuilder.fromUriString("https://api.openweathermap.org/data/2.5/weather")
                .queryParam("q", city)
                .queryParam("appid", apiKey)
                .toUriString();

        RequestEntity<Void> req = RequestEntity
                .get(Url)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        RestTemplate template = new RestTemplate();
        String payload;
        ResponseEntity<String> resp = null;


        try {
            resp = template.exchange(req, String.class);
            payload = resp.getBody();
            System.out.printf("Status code: %s\n".formatted(resp.getStatusCode()));

        } catch (HttpClientErrorException ex) {
            return Optional.empty();

        } finally{
            System.out.printf("Status code: %s\n".formatted(resp.getStatusCode()));
        }

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject json = reader.readObject();
        JsonArray arr = json.getJsonArray("weather");
        json = arr.getJsonObject(0);

        // JsonObject response = Json.createObjectBuilder()
        // .add("main", json.getString("main"))
        // .add("description", json.getString("description"))
        // .build();

        City currCity = new City();
        currCity.setName(city);
        currCity.setDescription(json.getString("description"));
        currCity.setMain(json.getString("main"));

        return Optional.of(currCity);

        // System.out.printf("Weather data: %s\n", weatherData.toString());

    }
}
