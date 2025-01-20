package com.example.tcit.demo.controller;


import com.example.tcit.demo.dto.PostDto;
import com.example.tcit.demo.entity.Post;
import com.example.tcit.demo.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

        @GetMapping("/listar")
        public ResponseEntity<?> listar() {
            List<Post> posts = postService.getAllPosts();
            Map<String, Object> response = new HashMap<>();

            if (posts.isEmpty()) {
                response.put("message", "No hay resultados en estos momentos");
                response.put("data", posts);
                return ResponseEntity.ok(response);
            }
            response.put("message", "Lista encontrada");
            response.put("data", posts);
            return ResponseEntity.ok(response);
        }


    @PostMapping("/crear")
    public ResponseEntity<?> crear(@Valid @RequestBody PostDto postRequestDto) {
        try {
            Post createdPost = postService.crear(postRequestDto);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Post creado exitosamente");
            response.put("datos", createdPost);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", "Ocurrió un error inesperado");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }


        @DeleteMapping("eliminar/{id}")
        public ResponseEntity<?> eliminar(@PathVariable String id) {
            try {
                postService.eliminar(id);
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Eliminado con éxito");
                return ResponseEntity.ok(response);
            } catch (IllegalArgumentException e) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("message", e.getMessage());
                return ResponseEntity.badRequest().body(errorResponse);
            } catch (Exception e) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("mensaje", "Ocurrió un error inesperado");
                errorResponse.put("error", e.getMessage());
                return ResponseEntity.status(500).body(errorResponse);
            }
        }
    @PostMapping("/filtro")
    public ResponseEntity<?> filtrarNombre(@RequestBody Map<String, String> requestBody) {
        try {
            String nombre = requestBody.get("nombre");
            if (nombre == null || nombre.isEmpty()) {
                throw new IllegalArgumentException("El nombre para filtrar no puede estar vacío");
            }
            List<PostDto> filteredPosts = postService.filtrarNombre(nombre);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Nombre encontrado");
            response.put("datos", filteredPosts);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", "Ocurrió un error inesperado");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }


}
