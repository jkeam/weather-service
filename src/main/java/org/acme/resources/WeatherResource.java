package org.acme.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;

import org.acme.models.Weather;
import org.acme.services.WeatherService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/weather")
@Produces(MediaType.APPLICATION_JSON)
public class WeatherResource {
    @ConfigProperty(name = "weather.api.token")
    String weatherApiToken;

    @GET
    public Weather getWeather(@QueryParam("zip") String zip) {
        if (zip == null || zip.isEmpty()) {
            return null;
        }
        return WeatherService.findWeather(weatherApiToken, zip);
    }
}