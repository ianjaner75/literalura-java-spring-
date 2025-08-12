package com.alura.literatura.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexBook(
        int id,
        String title,
        List<GutendexAuthor> authors,
        List<String> languages,
        @JsonProperty("download_count") Integer downloadCount
) {}
