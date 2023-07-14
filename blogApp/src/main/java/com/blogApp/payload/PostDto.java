package com.blogApp.payload;

import lombok.Data;

@Data
public class PostDto {

    private Long id;
    private String name;
    private String content;
    private String description;
}
