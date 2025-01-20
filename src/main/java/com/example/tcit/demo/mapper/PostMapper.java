package com.example.tcit.demo.mapper;

import com.example.tcit.demo.dto.PostDto;
import com.example.tcit.demo.entity.Post;

public class PostMapper {
    public static Post toEntity(PostDto postDto) {
        Post post = new Post();
        post.setNombre(postDto.getNombre());
        post.setDescripcion(postDto.getDescripcion());
        return post;
    }

    public static PostDto toDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setNombre(post.getNombre());
        postDto.setDescripcion(post.getDescripcion());
        return postDto;
    }
}
