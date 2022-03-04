package study.fisco.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import study.fisco.demo.entety.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class JdbcService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addUser(User user){
        jdbcTemplate.update("insert into user(name,age,email) values(?,?,?)",user.getName(),user.getAge(),user.getEmail());
    }

    public List<User> select(){
        return jdbcTemplate.query("select * from user",new BeanPropertyRowMapper<>(User.class));
    }


    public List<User> select(int page,int limit,int id,String name){
        int offsetLimit = page==1?0:limit * (page-1);
        if(id!=0 && !name.equals("")){
            return jdbcTemplate.query("select * from user where id=? AND name LIKE concat('%',?,'%') limit ?,?",new Object[]{id,name,offsetLimit,limit},new BeanPropertyRowMapper<>(User.class));
        }else if(id!=0 && name.equals("")){
            return jdbcTemplate.query("select * from user where id=? limit ?,?",new Object[]{id,offsetLimit,limit},new BeanPropertyRowMapper<>(User.class));
        }else if(id==0 && !name.equals("")){
            return jdbcTemplate.query("select * from user where name LIKE concat('%',?,'%') limit ?,?",new Object[]{name,offsetLimit,limit},new BeanPropertyRowMapper<>(User.class));
        }
        return jdbcTemplate.query("select * from user limit ?,?",new Object[]{offsetLimit,limit},new BeanPropertyRowMapper<>(User.class));
    }

    public void updateById(int id,String name,int age ,String email ){
        jdbcTemplate.update("update user set name=?,age=?,email=? where id=?",name,age,email,id);
    }

    public void batchDeleteById(List<Integer> ids){
        jdbcTemplate.batchUpdate("delete from user where id = ?", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, ids.get(i));
            }

            @Override
            public int getBatchSize() {
                return ids.size();
            }
        });
    }

}
