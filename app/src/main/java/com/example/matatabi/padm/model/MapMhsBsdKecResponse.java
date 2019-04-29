package com.example.matatabi.padm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MapMhsBsdKecResponse {
    @SerializedName("value")
    private String value;

    @SerializedName("hasil")
    private List<MapMhsBsdKec> mapMhsBsdKecList;

    public MapMhsBsdKecResponse(String value, List<MapMhsBsdKec> mapMhsBsdKecList) {
        this.value = value;
        this.mapMhsBsdKecList = mapMhsBsdKecList;
    }

    public String getValue() {
        return value;
    }

    public List<MapMhsBsdKec> getMapMhsBsdKecList() {
        return mapMhsBsdKecList;
    }
}
