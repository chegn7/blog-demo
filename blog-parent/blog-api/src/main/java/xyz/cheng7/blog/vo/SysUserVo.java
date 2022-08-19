package xyz.cheng7.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserVo {
    private String id;
    private String account;

    private Boolean admin;

    private String avatar;
    private String createDate;

    private String email;
    private String lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String status;

}
