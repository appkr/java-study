package dev.appkr.adjacencyList

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CategoryRepository : JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL")
    fun findAllRootCategory(): Collection<Category>
}
