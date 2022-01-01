package dev.appkr.emexample;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlbumRepository {

  private final EntityManager em;

  public List<AlbumView> findAll(int page, int size) {
    final String sql = "SELECT new dev.appkr.emexample.AlbumView(a, s) " +
        "FROM Album a JOIN a.singer s ";
    
    TypedQuery<AlbumView> query = em.createQuery(sql, AlbumView.class);
    query.setFirstResult(page * size);
    query.setMaxResults(size);

    return query.getResultList();
  }
}
