package com.mugisius.DailyNoteApi.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weather", url = "https://api.openweathermap.org")
public interface WeatherProxy {

    @GetMapping("/data/2.5/weather")
    String getWeather(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam String appid
    );

    @GetMapping("/geo/1.0/direct")
    String getCoordinates(
            @RequestParam String q,
            @RequestParam int limit,
            @RequestParam String appid
    );
}
