package study.fisco.demo.intercepted;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import study.fisco.demo.handleException.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class Interceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String res = (String) redisTemplate.opsForValue().get(token);
        if(res == null){
            throw new ServiceException("请登录");
        }
        request.setAttribute("userId",Integer.parseInt(res));
        return true;
    }

}
