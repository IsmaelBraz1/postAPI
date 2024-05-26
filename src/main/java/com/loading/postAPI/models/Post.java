package com.loading.postAPI.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.loading.postAPI.models.enums.ProfileEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Post.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Post {
    public static final String TABLE_NAME = "post";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "UserCad")
    private String userCad;

    @Column(name = "userUpd")
    private String userUpd;

    @Column(name = "description", length = 500, nullable = false)
    @NotBlank
    @Size(min=1, max = 500)
    private String description;

    @Column(name = "title", length = 120, nullable = false)
    @NotBlank
    @Size(min=1, max = 120)
    private String title;

    @Column(name = "type", length = 50, nullable = false)
    @NotBlank
    @Size(min=1, max = 50)
    private String type;

    @Column(name = "datecreate", nullable = false)
    private LocalDate datecreate;

    @Column(name = "dateupdate", nullable = false)
    private LocalDate dateupdate; 

}
