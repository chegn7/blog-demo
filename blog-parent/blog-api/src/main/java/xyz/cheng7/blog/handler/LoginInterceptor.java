package xyz.cheng7.blog.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import xyz.cheng7.blog.service.TokenService;
import xyz.cheng7.blog.util.JSONUtil;
import xyz.cheng7.blog.util.UserThreadLocal;
import xyz.cheng7.blog.vo.ErrorCode;
import xyz.cheng7.blog.vo.Result;
import xyz.cheng7.blog.vo.SysUserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenService tokenService;

    /**
     * 登录拦截器，preHandle，在执行controller的方法(handler)之前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 根据路径放行，判断访问的路径是否是需要登录的
        // 访问静态资源的直接放行，会访问class path下的static目录
        if (handler instanceof ResourceHttpRequestHandler) return true;
        // 2. 需要登陆的
        // 2.1 token是否为空
        // 2.2 token是否正确
        if (handler instanceof HandlerMethod) {
            log.info("request start");
            String token = request.getHeader("Authorization");
            log.info("request uri:{}", request.getRequestURI());
            log.info("request method:{}", request.getMethod());
            log.info("token:{}", token);
            log.info("request end");
            // 2.1 & 2.2 在check里已经实现了，这里不重复判空
            SysUserVo userVo = tokenService.check(token);
            if (userVo == null) {
                Result result = Result.failure(ErrorCode.NOT_LOGIN_ERROR.getCode(), ErrorCode.NOT_LOGIN_ERROR.getMsg());
                String resJSON = JSONUtil.getInstance().toJSON(result);
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().print(resJSON);
                return false;
            }
            UserThreadLocal.put(userVo);
            return true;
        }
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // pre 里放了，用完了要删，不删有内存泄漏的风险
        // 为什么有内存泄漏的风险？
        UserThreadLocal.remove();
    }
}
