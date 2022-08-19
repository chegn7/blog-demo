package xyz.cheng7.blog.admin.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import xyz.cheng7.blog.admin.service.AdminService;
import xyz.cheng7.blog.admin.service.PermissionService;
@Slf4j
@Component
public class UsernamePasswordAuthProvider implements AuthenticationProvider {

    @Autowired
    AdminService adminService;

    @Autowired
    PermissionService permissionService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserDetails userDetails = adminService.loadUserByUsername(username);
        if (null == userDetails) return null;
        if (!passwordEncoder.matches(password, userDetails.getPassword())) return null;
        log.info(String.format("通过认证，账户名: %s", username));
        log.info(String.format("用户权限: %s", userDetails.getAuthorities()));
        /**
         * Object principal 认证的主要信息
         * Object credentials 证书
         * Collection<\? extends GrantedAuthority> authorities 权限
         */
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    /**
     * 地区的authentication provider支持哪种类型的认证
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
