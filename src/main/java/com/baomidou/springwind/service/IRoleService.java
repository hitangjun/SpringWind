package com.baomidou.springwind.service;

import com.baomidou.framework.service.ISuperService;
import com.baomidou.springwind.entity.Role;

/**
 *
 * Role 表数据服务层接口
 *
 */
public interface IRoleService extends ISuperService<Role> {

    void deleteByUserId(Long userId);
}