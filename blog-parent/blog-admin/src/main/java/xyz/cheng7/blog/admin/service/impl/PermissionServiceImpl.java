package xyz.cheng7.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.cheng7.blog.admin.comdel.params.PageParam;
import xyz.cheng7.blog.admin.mapper.PermissionMapper;
import xyz.cheng7.blog.admin.pojo.Permission;
import xyz.cheng7.blog.admin.service.PermissionService;
import xyz.cheng7.blog.admin.vo.ErrorCode;
import xyz.cheng7.blog.admin.vo.PageResult;
import xyz.cheng7.blog.admin.vo.Result;

import java.util.List;

/**
 * @author c
 * @description 针对表【ms_permission】的数据库操作Service实现
 * @createDate 2022-08-14 10:57:46
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
        implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Result listPermission(PageParam pageParam) {

        Page<Permission> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(pageParam.getQueryString())) {
            lambdaQueryWrapper.eq(Permission::getName, pageParam.getQueryString());
        }
        Page<Permission> permissionPage = permissionMapper.selectPage(page, lambdaQueryWrapper);
        PageResult<Permission> pageResult = new PageResult<>();
        pageResult.setList(permissionPage.getRecords());
        pageResult.setTotal(permissionPage.getTotal());
        return Result.success(pageResult);
    }

    @Override
    public Result addPermission(Permission permission) {
        if (StringUtils.isBlank(permission.getName())
                || StringUtils.isBlank(permission.getPath())
                || StringUtils.isBlank(permission.getDescription())
                || permissionMapper.insert(permission) != 1) {
            return Result.failure(ErrorCode.CREATE_PERMISSION_FAILURE.getCode(), ErrorCode.CREATE_PERMISSION_FAILURE.getMsg());
        }
        return Result.success(null);
    }

    @Override
    public Result updatePermission(Permission permission) {
        if (StringUtils.isBlank(permission.getName())
                || StringUtils.isBlank(permission.getPath())
                || StringUtils.isBlank(permission.getDescription())
                || null == permission.getId()
                || permissionMapper.updateById(permission) != 1) {
            return Result.failure(ErrorCode.UPDATE_PERMISSION_FAILURE.getCode(), ErrorCode.UPDATE_PERMISSION_FAILURE.getMsg());
        }
        return Result.success(null);
    }

    @Override
    public Result deletePermission(Long id) {
        if (permissionMapper.deleteById(id) != 1) {
            return Result.failure(ErrorCode.DELETE_PERMISSION_FAILURE.getCode(), ErrorCode.DELETE_PERMISSION_FAILURE.getMsg());
        }
        return Result.success(null);
    }

    @Override
    public List<Permission> findPermissionsByUserId(Long userId) {
        return permissionMapper.selectByUserId(userId);
    }
}




