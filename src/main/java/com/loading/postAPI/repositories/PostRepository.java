package com.loading.postAPI.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loading.postAPI.models.Post;



@Repository
public interface PostRepository extends JpaRepository <Post, Long>{
   Page<Post> findAll(Pageable pageable);
} 