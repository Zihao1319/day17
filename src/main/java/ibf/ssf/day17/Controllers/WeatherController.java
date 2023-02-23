package ibf.ssf.day17.Controllers;

import java.io.StringReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ibf.ssf.day17.Model.City;
import ibf.ssf.day17.Service.WeatherService;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Controller
@RequestMapping(path = "/")
public class WeatherController {

    @Autowired
    private WeatherService weatherSvc;

    // @GetMapping
    // public String displayForm(Model model) {
    // return "index";
    // }

    @PostMapping("/weather")
    public String postData(@RequestParam MultiValueMap<String, String> form, Model model) {
        String cityName = form.getFirst("city");
        // System.out.printf("result back: %s\n", cityName);

        Optional<City> opt = weatherSvc.getWeatherData(cityName);
        City cityWeather = opt.get();

        // JsonReader reader = Json.createReader(new StringReader(result));
        // JsonObject json = reader.readObject();

        model.addAttribute("city", cityWeather);
        // System.out.printf("result back: %s\n", json.toString());
        return "index";

    }

}
