package xyz.cheng7.blog.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.cheng7.blog.dao.pojo.SysUser;
import xyz.cheng7.blog.service.LoginService;
import xyz.cheng7.blog.service.SysUserService;
import xyz.cheng7.blog.service.TokenService;
import xyz.cheng7.blog.util.IDUtil;
import xyz.cheng7.blog.util.SaltUtil;
import xyz.cheng7.blog.vo.ErrorCode;
import xyz.cheng7.blog.vo.Result;
import xyz.cheng7.blog.vo.SysUserVo;
import xyz.cheng7.blog.vo.params.LoginParams;
import xyz.cheng7.blog.vo.params.RegisterParams;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IDUtil idUtil;

    @Override
    public Result login(LoginParams loginParams) {
        /**
         * 1. 校验参数合法性
         * 2. 查询用户表，验证账号密码
         * 3. 如果验证失败，登陆失败
         * 4. 如果验证成功，生成jwt，返回给前端
         * 5. token放入redis中， jwt : user json
         */
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        // 1.
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.failure(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        // 2.
        SysUserVo userVo = sysUserService.findSysUser(account, password);
        // 3.
        if (null == userVo) return Result.failure(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        // 4.
        String jwt = tokenService.generateToken(userVo);
        return Result.success(jwt);
    }

    @Override
    public Result logout(String jwt) {
        tokenService.deleteToken(jwt);
        return Result.success(null);
    }

    @Override
    @Transactional
    public Result register(RegisterParams registerParams) {
        String account = registerParams.getAccount();
        String password = registerParams.getPassword();
        String nickname = registerParams.getNickname();
        // 1. 参数校验
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(nickname)) {
            return Result.failure(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        // 2. 判断account是否已存在
        SysUserVo userVo = sysUserService.findSysUserByAccount(account);
        if (userVo != null) {
            return Result.failure(ErrorCode.ACCOUNT_ALREADY_EXIST.getCode(), ErrorCode.ACCOUNT_ALREADY_EXIST.getMsg());
        }
        // 3. 事务插入
        // 创建用户实体，填充值
        SysUser user = new SysUser();
        user.setId(idUtil.generateID());
        user.setAccount(account);
        user.setSalt(SaltUtil.getSalt(32));
        user.setPassword(SaltUtil.getPasswordWithSalt(password, user.getSalt()));
        user.setNickname(nickname);
        user.setCreateDate(System.currentTimeMillis());
        user.setLastLogin(System.currentTimeMillis());
        user.setAvatar("/static/img/logo.b3a48c0.png");
        user.setAdmin(false);
        user.setDeleted(false);
        user.setStatus("");
        user.setEmail("");
        /**
         * mybatis plus 默认使用了雪花算法，分布式id
         * 如果使用mybatis 手写 xml
         * <insert id="addUser" parameterType="com.xxxx.qcby.entity.User" keyProperty="id" useGeneratedKeys="true">
         *         insert into t_user(user_name, password, sex)
         *         values (#{user.userName}, #{user.password}, #{user.sex});
         * </insert>
         * keyProperty 的意思是：keyProperty 取id的key值
         *              主要是在主键是自增的情况下，添加成功后可以直接使用主键值
         *              其中keyProperty的值是对象的属性值不是数据库表中的字段名。
         * useGeneratedKeys 的意思是：useGeneratedKeys 参数只针对 insert 语句生效，默认为 false。
         *                  当设置为 true 时，表示如果插入的表以自增列为主键，
         *                  则允许 JDBC 支持自动生成主键，并可将自动生成的主键返回。
         */
        sysUserService.save(user);
        userVo = new SysUserVo();// userVo此前为null
        BeanUtils.copyProperties(user, userVo);
        String token = tokenService.generateToken(userVo);
        return Result.success(token);
    }
}
