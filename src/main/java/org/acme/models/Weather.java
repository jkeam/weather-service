package org.acme.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Weather data
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
public class Weather {
    private float temperature;
    private Integer zip;
    private float feelsLike;

    public Weather() {
    }
    public Weather(Integer zip) {
        this.zip = zip;
    }
    public Weather(Integer zip, float temperature, float feelsLike) {
        this.zip = zip;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
    }

    @JsonProperty("temperature")
    public float getTemperature() {
        return temperature;
    }

    @JsonProperty("temperature")
    public void setTemperature(float temperature) {
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

    @JsonProperty("feelsLike")
    public float getFeelsLike() {
        return feelsLike;
    }

    @JsonProperty("feelsLike")
    public void setFeelsLike(float feelsLike) {
        this.feelsLike = feelsLike;
    }
}