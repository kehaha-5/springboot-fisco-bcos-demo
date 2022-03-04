package study.fisco.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import study.fisco.demo.dao.UserDao;
import study.fisco.demo.entety.User;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class JpaService {
    @Autowired
    private UserDao userDao;

    public void Save(User user){
        userDao.save(user);
    }

    public List<User> selectAll(){
        return userDao.findAll();
    }

    public List<User> select(int page, int limit, int age, String name){
        Page<User> userPage = userDao.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> where = new ArrayList<>();
                if (age != 0) {
                    where.add(criteriaBuilder.equal(root.get("age").as(Integer.class), age));
                }
                if (!name.equals("")) {
                    where.add(criteriaBuilder.like(root.get("name").as(String.class), "%".concat(name).concat("%")));
                }
                Order order = criteriaBuilder.desc(root.get("id").as(Long.class));
                Predicate[] wheres = new Predicate[where.size()];
                return query.orderBy(order).where(wheres).getRestriction();
            }
        }, PageRequest.of(page, limit));
        return userPage.getContent();
    }

    public void batchDeleteById(List<Integer> ids){
        List<User> userList = new ArrayList<>();
        ids.forEach(i->{
            User userEntety = new User();
            userEntety.setId(new Long(i));
            userList.add(userEntety);
        });
        userDao.deleteInBatch(userList);
    }
}
