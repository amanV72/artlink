package com.artlink.post.repo;

import com.artlink.post.models.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends MongoRepository<Post,String> {
    Optional<List<Post>> findByUserIdOrderByCreatedAtDesc(String userId);
    Optional<List<Post>> findByTagsOrderByCreatedAtDesc(String tag);
    Optional<List<Post>> findAllByOrderByCreatedAtDesc(PageRequest request);
}
