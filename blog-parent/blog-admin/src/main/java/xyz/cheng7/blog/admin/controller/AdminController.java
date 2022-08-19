package xyz.cheng7.blog.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.cheng7.blog.admin.comdel.params.PageParam;
import xyz.cheng7.blog.admin.pojo.Permission;
import xyz.cheng7.blog.admin.service.PermissionService;
import xyz.cheng7.blog.admin.vo.Result;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private PermissionService permissionService;


    @PostMapping("permission/permissionList")
    public Result getPermissionList(@RequestBody PageParam pageParam) {
        return permissionService.listPermission(pageParam);
    }

    @PostMapping("permission/add")
    public Result addPermission(@RequestBody Permission permission) {
        return permissionService.addPermission(permission);
    }

    @PostMapping("permission/update")
    public Result updatePermission(@RequestBody Permission permission) {
        return permissionService.updatePermission(permission);
    }

    @GetMapping("permission/delete/{id}")
    public Result updatePermission(@PathVariable("id") Long id) {
        return permissionService.deletePermission(id);
    }

}
