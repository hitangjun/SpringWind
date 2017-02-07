package com.baomidou.springwind.mapper;

import java.util.List;

import com.baomidou.springwind.entity.RolePermission;
import com.baomidou.mybatisplus.mapper.AutoMapper;

/**
 *
 * RolePermission 表数据库控制层接口
 *
 */
public interface RolePermissionMapper extends AutoMapper<RolePermission> {

	
	/**
	 * 根据角色ID获取对应的所有关联权限的ID
	 * @param id 角色Id
	 * @return
	 */
	List<Long> selecPermissionIdsByRoleId(Long id);


}