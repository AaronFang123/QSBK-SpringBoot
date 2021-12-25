package fun.aaronfang.qsbk.demo.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import fun.aaronfang.qsbk.demo.common.ApiValidationException;
import fun.aaronfang.qsbk.demo.constants.ApiUserAuth;
import fun.aaronfang.qsbk.demo.constants.ApiUserBindPhone;
import fun.aaronfang.qsbk.demo.constants.ApiUserState;
import fun.aaronfang.qsbk.demo.constants.QsbkCommonConstants;
import fun.aaronfang.qsbk.demo.model.UserEntity;
import fun.aaronfang.qsbk.demo.repo.UserRepo;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Optional;

public class AuthenticationInterceptor implements HandlerInterceptor {

    UserRepo userRepo;

    public AuthenticationInterceptor(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(ApiUserAuth.class)) {
            return checkUserToken(request, response ,method);
        }

        if (method.isAnnotationPresent(ApiUserBindPhone.class)) {
            return checkPhoneBind(request);
        }

        if (method.isAnnotationPresent(ApiUserState.class)) {
            return checkUserState(request);
        }
        return true;
    }

    /**
     * 如果 `@UserLoginToken` 注释存在，进行token提取和验证
     *
     * @param request HttpServletRequest
     * @param method Method
     * @return token验证是否通过
     */
    private boolean checkUserToken(HttpServletRequest request, HttpServletResponse response, Method method) {
        String token = request.getHeader("token");

//        if (method.isAnnotationPresent(PassToken.class)) {
//            PassToken passToken = method.getAnnotation(PassToken.class);
//            if (passToken.required()) {
//                return true;
//            }
//        }
        ApiUserAuth apiUserAuth = method.getAnnotation(ApiUserAuth.class);
        if (apiUserAuth.required()) {
            if (token == null) {
                throw new ApiValidationException("非法token，禁止操作",20003);
            }
        }
        String uid;
        try{
            uid = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException ex) {
            throw new RuntimeException("401");
        }
        Optional<UserEntity> userEntity = userRepo.findById(Integer.valueOf(uid));
        if (!userEntity.isPresent()) {
            throw new ApiValidationException("非法token，请重新登录，未登录或已过期", 20003);
        }
        UserEntity entity = userEntity.get();
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(entity.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("401");
        }

        HttpSession session = request.getSession();
        // 已登录信息保存在Session里
        session.setAttribute(QsbkCommonConstants.TAG_LOGIN_SESSION, entity);
        return true;
    }


    /**
     * 如果 `@ApiUserBindPhone` 注解存在，验证是否绑定手机
     *
     * @param request HttpServerRequest
     * @return Boolean 是否绑定手机
     */
    private boolean checkPhoneBind(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (Integer.parseInt(userId) < 1) {
            throw new ApiValidationException("请先绑定手机", 20008);
        }
        return true;
    }


    /**
     * （该注解需要配合TOKEN验证使用）
     * 如果 `@ApiUserState` 注解存在，验证是否绑定手机
     *
     * @param request HttpServletRequest
     * @return Boolean 该用户是否被禁用
     */
    private boolean checkUserState(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object o = session.getAttribute(QsbkCommonConstants.TAG_LOGIN_SESSION);
        if (o != null) {
            UserEntity user = (UserEntity) o;
            if (user.getStatus() == 0) {
                throw new ApiValidationException("该用户已被禁用", 20001);
            }
        }
        return true;
    }
}
