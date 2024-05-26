package com.loading.postAPI.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.loading.postAPI.models.Post;
import com.loading.postAPI.models.dto.PostCreateDTO;
import com.loading.postAPI.models.dto.PostUpdateDTO;
import com.loading.postAPI.repositories.PostRepository;
import com.loading.postAPI.security.UserSpringSecurity;
import com.loading.postAPI.services.exceptions.AuthorizationException;
import com.loading.postAPI.services.exceptions.DataBindingViolationException;
import com.loading.postAPI.services.exceptions.ObjectNotFoundException;

@Service
public class PostService {
     
    @Autowired
    private PostRepository PostRepository;

    @Autowired
    private UserService userService;

    
    public Post finfById(Long id){

        UserSpringSecurity userSpringSecurity = userService.authenticated();
        if (Objects.isNull(userSpringSecurity)) {
            throw new AuthorizationException("Acesso negado");
        }

        Optional<Post> userc = this.PostRepository.findById(id);
        return userc.orElseThrow(()-> new ObjectNotFoundException(
            "Usuario nao encontrado id:" + id + ", Tipo "+Post.class.getName()
        ));
    }
   
    public List<Post> findAll(){
        return PostRepository.findAll();
    }

    public Page<Post> findAllPag(int pag, int itens){
        return PostRepository.findAll(PageRequest.of(pag, itens));
    }


    @Transactional
    public Post create(Post obj){
        UserSpringSecurity userSpringSecurity = userService.authenticated();
        if (Objects.isNull(userSpringSecurity)) {
            throw new AuthorizationException("Acesso negado");
        }

        obj.setId(null);
        obj = this.PostRepository.save(obj);
        return obj;
    }

    @Transactional
    public Post update(Post obj){
        Post newObj = finfById(obj.getId());
        newObj.setDescription(obj.getDescription());
        newObj.setTitle(obj.getTitle());
        newObj.setType(obj.getType());
        newObj.setUserUpd(userService.authenticated().getUsername());
        newObj.setDateupdate(LocalDate.now());
        return this.PostRepository.save(newObj);
    }

    public void delete(Long id){
        finfById(id);
        try {
            this.PostRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não foi possivel excluir a entidade");
        }
    }


    public Post fromDTO(@Valid PostCreateDTO obj){
        Post newObj = new Post();
        newObj.setUserCad(userService.authenticated().getUsername());
        newObj.setUserUpd("null");
        newObj.setDescription(obj.getDescription());
        newObj.setTitle(obj.getTitle());
        newObj.setType(obj.getType());
        newObj.setDatecreate(LocalDate.now());
        newObj.setDateupdate(LocalDate.now());

        return newObj;
    }

    public Post fromDTO(@Valid PostUpdateDTO obj){
        Post newObj = new Post();
        newObj.setId(obj.getId());
        newObj.setDescription(obj.getDescription());
        newObj.setTitle(obj.getTitle());
        newObj.setType(obj.getType());

        return newObj;
    }
}
