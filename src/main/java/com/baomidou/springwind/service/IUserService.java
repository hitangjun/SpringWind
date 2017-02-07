package com.baomidou.springwind.service;

import com.baomidou.framework.service.ISuperService;
import com.baomidou.springwind.entity.User;

/**
 *
 * User 表数据服务层接口
 *
 */
public interface IUserService extends ISuperService<User> {

	User selectByLoginName(String loginName);

	void deleteUser(Long userId);
}