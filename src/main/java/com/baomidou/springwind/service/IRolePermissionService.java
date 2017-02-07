package com.baomidou.springwind.service;

import java.util.List;

import com.baomidou.springwind.entity.RolePermission;
import com.baomidou.framework.service.ISuperService;

/**
 *
 * RolePermission 表数据服务层接口
 *
 */
public interface IRolePermissionService extends ISuperService<RolePermission> {

	/**
	 * <p>
	 * 判断是否存在角色对应的权限
	 * </p>
	 * 
	 * @param permissionId
	 *            权限ID
	 * @return
	 */
	boolean existRolePermission(Long permissionId);

	/**
	 * 根据角色ID获取对应的所有关联权限的ID
	 * @param id
	 * @return
	 */
	List<Long> selecPermissionIdsByRoleId(Long id);

}