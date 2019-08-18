package springstudy.restquery.domain;

import springstudy.restquery.service.dto.SearchCriteria;

import java.util.List;

public interface IUserDao {

    List<User> searchUser(List<SearchCriteria> params);
    void save(User entity);
}
