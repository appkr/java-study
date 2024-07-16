package dev.appkr.adjacencyList

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1L,

    var name: String = "",

    @ManyToOne
    var parent: Category? = null,

    @OneToMany(mappedBy = "parent", cascade = [(CascadeType.ALL)])
    var children: MutableList<Category> = mutableListOf()
) {
    fun add(child: Category) {
        children.add(child)
        child.parent = this
    }

    fun findBy(categoryId: Long): Category? {
        if (this.id == categoryId) {
            return this
        }
        for (child in children) {
            val found = child.findBy(categoryId)
            if (found != null) {
                return found
            }
        }
        return null
    }

    fun findBy(categoryName: String): Category? {
        if (this.name == categoryName) {
            return this
        }
        for (child in children) {
            val found = child.findBy(categoryName)
            if (found != null) {
                return found
            }
        }
        return null
    }
}
