package org.acme.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Weather data
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
public class Weather {
    private String temperature;
    private Integer zip;

    public Weather() {
    }
    public Weather(Integer zip) {
        this.zip = zip;
    }
    public Weather(Integer zip, String temperature) {
        this.zip = zip;
        this.temperature = temperature;
    }

    @JsonProperty("temperature")
    public String getTemperature() {
        return temperature;
    }

    @JsonProperty("temperature")
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @JsonProperty("zip")
    public Integer getZip() {
        return zip;
    }

    @JsonProperty("zip")
    public void setZip(Integer zip) {
        this.zip = zip;
    }
}