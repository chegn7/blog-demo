package xyz.cheng7.blog.admin.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName ms_admin_permission
 */
@Data
public class AdminPermission implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private Long adminId;

    /**
     * 
     */
    private Long permissionId;

    private static final long serialVersionUID = 1L;
}