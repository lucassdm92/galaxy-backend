package com.galaxybck.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DistanceResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("rows")
    private List<Row> rows;

    public String getStatus() { return status; }
    public List<Row> getRows() { return rows; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Row {
        @JsonProperty("elements")
        private List<Element> elements;

        public List<Element> getElements() { return elements; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Element {
        @JsonProperty("status")
        private String status;

        @JsonProperty("distance")
        private DistanceValue distance;

        public String getStatus() { return status; }
        public DistanceValue getDistance() { return distance; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DistanceValue {
        @JsonProperty("value")
        private long value; // metros

        @JsonProperty("text")
        private String text;

        public long getValue() { return value; }
        public String getText() { return text; }

        public double toKilometers() {
            return value / 1000.0;
        }
    }
}
