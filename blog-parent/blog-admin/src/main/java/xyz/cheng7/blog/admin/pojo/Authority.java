package xyz.cheng7.blog.admin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
@Data
@AllArgsConstructor
public class Authority implements GrantedAuthority {
    Long id;
    String name;
    @Override
    public String getAuthority() {
        return id + " " + name;
    }
}
