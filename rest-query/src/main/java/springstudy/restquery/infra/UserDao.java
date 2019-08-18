package springstudy.restquery.infra;

import springstudy.restquery.domain.IUserDao;
import springstudy.restquery.domain.User;
import springstudy.restquery.service.dto.SearchCriteria;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.Consumer;

public class UserDao implements IUserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> searchUser(List<SearchCriteria> params) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> r = query.from(User.class);

        Predicate predicate = builder.conjunction();

        UserSearchQueryCriteriaConsumer searchConsumer = new UserSearchQueryCriteriaConsumer(predicate, builder, r);
        params.stream().forEach(searchConsumer);;
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        List<User> result = em.createQuery(query).getResultList();

        return result;
    }

    @Override
    public void save(User entity) {
        em.persist(entity);
    }

    static class UserSearchQueryCriteriaConsumer implements Consumer<SearchCriteria> {

        private Predicate predicate;
        private CriteriaBuilder builder;
        private Root r;

        public UserSearchQueryCriteriaConsumer(Predicate predicate, CriteriaBuilder builder, Root r) {
            this.predicate = predicate;
            this.builder = builder;
            this.r = r;
        }

        @Override
        public void accept(SearchCriteria param) {
            if (param.getOperation().equalsIgnoreCase(">")) {
                predicate = builder.and(this.predicate,
                    builder.greaterThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
            } else if (param.getOperation().equalsIgnoreCase("<")) {
                predicate = builder.and(predicate,
                    builder.lessThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
            } else if (param.getOperation().equalsIgnoreCase(":")) {
                if (r.get(param.getKey()).getJavaType() == String.class) {
                    predicate = builder.and(predicate,
                        builder.like(r.get(param.getKey()), "%" + param.getValue() + "%"));
                } else {
                    predicate = builder.and(predicate,
                        builder.equal(r.get(param.getKey()), param.getValue()));
                }
            }
        }

        public Predicate getPredicate() {
            return predicate;
        }
    }
}
