package com.example.tcit.demo.service;

import com.example.tcit.demo.dto.PostDto;
import com.example.tcit.demo.mapper.PostMapper;
import com.example.tcit.demo.repository.PostRepository;
import com.example.tcit.demo.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll(); // Devuelve todos los posts almacenados
    }


    public Post crear(PostDto postDto) {
        if (postDto.getNombre() == null || postDto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del post no puede estar vacío");
        }

        if (postDto.getDescripcion() == null || postDto.getDescripcion().isEmpty()) {
            throw new IllegalArgumentException("La descripción del post no puede estar vacía");
        }
        Post post = PostMapper.toEntity(postDto);
        return postRepository.save(post);
    }
    
    public void eliminar(String id) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isEmpty()) {
            throw new IllegalArgumentException("El post con ID " + id + " no existe");
        }

        postRepository.deleteById(id);
    }

    public List<PostDto> filtrarNombre(PostDto postDto) {
        if (postDto.getNombre() == null || postDto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre para filtrar no puede estar vacío");
        }

        List<Post> posts = postRepository.findAll()
                .stream()
                .filter(post -> post.getNombre().toLowerCase().contains(postDto.getNombre().toLowerCase()))
                .collect(Collectors.toList());

        if (posts.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron posts que coincidan con el nombre proporcionado");
        }

        return posts.stream()
                .map(PostMapper::toDto)
                .collect(Collectors.toList());
    }

}