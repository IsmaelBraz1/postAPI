package com.loading.postAPI.models.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostCreateDTO {

    @NotBlank
    @Size(min = 2, max = 100)
    private String description;

    @NotBlank
    @Size(min=1, max = 120)
    private String title;

    @NotBlank
    @Size(min=1, max = 50)
    private String type;

    private String userCad;

    private String userUpd;

    private LocalDate datecreate;

    private LocalDate dateupdate; 

}
