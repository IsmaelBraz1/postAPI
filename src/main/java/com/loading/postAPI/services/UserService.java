package com.loading.postAPI.services;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.loading.postAPI.models.User;
import com.loading.postAPI.models.dto.UserCreateDTO;
import com.loading.postAPI.models.dto.UserUpdateDTO;
import com.loading.postAPI.models.enums.ProfileEnum;
import com.loading.postAPI.repositories.UserRepository;
import com.loading.postAPI.security.UserSpringSecurity;
import com.loading.postAPI.services.exceptions.AuthorizationException;
import com.loading.postAPI.services.exceptions.DataBindingViolationException;
import com.loading.postAPI.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {
     
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    
    public User finfById(Long id){
        UserSpringSecurity userSpringSecurity = authenticated();
        if(!Objects.nonNull(userSpringSecurity)){
            throw new AuthorizationException("Acesso negado!");
        }

        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(()-> new ObjectNotFoundException(
            "Usuario nao encontrado id:" + id + ", Tipo "+User.class.getName()
        ));
    }
   

    @Transactional
    public User create(User obj){
        obj.setId(null);
       obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User update(User obj){
        User newObj = finfById(obj.getId());
        newObj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        return this.userRepository.save(newObj);
    }

    public void delete(Long id){
        finfById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não excluido! a entidade esta relacionada");
        }
    }

    public static UserSpringSecurity authenticated(){
        try {
            return(UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    public User fromDTO(@Valid UserCreateDTO obj){
        User user = new User();
        user.setUsername(obj.getUsername());
        user.setPassword(obj.getPassword());

        return user;
    }

    public User fromDTO(@Valid UserUpdateDTO obj){
        User user = new User();
        user.setId(obj.getId());
        user.setPassword(obj.getPassword());

        return user;
    }
}
