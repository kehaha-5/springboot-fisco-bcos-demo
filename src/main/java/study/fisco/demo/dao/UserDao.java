package study.fisco.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import study.fisco.demo.entety.User;

@Repository
public interface UserDao extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
}
