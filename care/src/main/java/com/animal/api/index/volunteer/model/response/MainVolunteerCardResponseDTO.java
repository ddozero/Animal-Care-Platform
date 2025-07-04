package com.animal.api.index.volunteer.model.response;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainVolunteerCardResponseDTO {

    private int idx;
    private String title;
    private String location;
    private String formattedDate;
    private String thumbnailPath;
}
