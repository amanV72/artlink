package com.ecommerce.user.repositories;

import com.ecommerce.user.models.ArtCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtCategoryRepo extends JpaRepository<ArtCategory,Long> {
}
