package com.loading.postAPI.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.loading.postAPI.models.Post;
import com.loading.postAPI.models.dto.PostCreateDTO;
import com.loading.postAPI.models.dto.PostUpdateDTO;
import com.loading.postAPI.services.PostService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/post") 
@Validated
public class PostController {
    
    @Autowired
    private PostService postService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable Long id){
        Post obj = this.postService.finfById(id);
        return ResponseEntity.ok().body(obj);
    }

    
    @GetMapping("/listar")
    public ResponseEntity<List<Post>> findAll(){
        List<Post> objs = this.postService.findAll();
        return ResponseEntity.ok().body(objs);
    }

    @GetMapping("/listarPag")
    public ResponseEntity<Page<Post>> findAllPag(@RequestParam int pag, @RequestParam int itens){
        Page<Post> objs = this.postService.findAllPag(pag, itens);
        return ResponseEntity.ok().body(objs);
    }


    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody PostCreateDTO obj){
        Post userc = this.postService.fromDTO(obj);
        Post newUser = this.postService.create(userc);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody PostUpdateDTO obj, @PathVariable Long id){
        obj.setId(id);
        Post userc = this.postService.fromDTO(obj);
        this.postService.update(userc);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
