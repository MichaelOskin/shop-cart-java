package org.penzgtu.Application.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Phone {
    private double price;
    private String name;
    private String model;
    private String nameCPU;
    private int cores;
    private double freq;
    private int hDisplay;
    private double sDisplay;
    private int wDisplay;
    private int ram;
    private int rom;
    private String type;
    private String ver;
    private String date;
    @JsonCreator
    public Phone(
            @JsonProperty("cores") int cores,
            @JsonProperty("freq") int freq,
            @JsonProperty("nameCPU") String nameCPU,
            @JsonProperty("h") int hDisplay,
            @JsonProperty("s") double sDisplay,
            @JsonProperty("w") int wDisplay,
            @JsonProperty("ram") int ram,
            @JsonProperty("rom") int rom,
            @JsonProperty("type") String type,
            @JsonProperty("ver") String ver,
            @JsonProperty("id") String model,
            @JsonProperty("date") String date,
            @JsonProperty("name") String name
    ) {
        this.cores = cores;
        this.freq = freq;
        this.nameCPU = nameCPU;
        this.hDisplay = hDisplay;
        this.sDisplay = sDisplay;
        this.wDisplay = wDisplay;
        this.ram = ram;
        this.rom = rom;
        this.type = type;
        this.ver = ver;
        this.model = model;
        this.date = date;
        this.name = name;
    }
}

