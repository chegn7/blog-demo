package xyz.cheng7.blog.admin.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import xyz.cheng7.blog.admin.service.AuthService;

import javax.servlet.http.HttpServletRequest;
@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public boolean auth(HttpServletRequest request, Authentication authentication) {

        return false;
    }
}
