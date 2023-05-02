package com.greenThumb.repository;

import com.greenThumb.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


}
