package xyz.cheng7.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xyz.cheng7.blog.dao.pojo.SysLog;
import xyz.cheng7.blog.service.SysLogService;
import xyz.cheng7.blog.dao.mapper.SysLogMapper;
import org.springframework.stereotype.Service;

/**
* @author c
* @description 针对表【ms_sys_log】的数据库操作Service实现
* @createDate 2022-08-08 15:02:19
*/
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog>
    implements SysLogService{

}




