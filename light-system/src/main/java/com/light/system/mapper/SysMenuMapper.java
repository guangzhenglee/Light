package com.light.system.mapper;

import com.light.system.domain.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单
 * author:ligz
 */
public interface SysMenuMapper {
    /**
     * 查询系统所有菜单（含按钮）
     * @return 菜单列表
     */
    List<SysMenu> selectMenuAll();

    /**
     * 查询系统正常显示菜单（不含按钮）
     * @return 菜单列表
     */
    List<SysMenu> selectMenuNormalAll();

    /**
     * 根据用户ID查询菜单
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenusByUserId(Long userId);

    /**
     * 根据用户ID查询权限
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> selectPermsByUserId(Long userId);

    /**
     * 根据角色ID查询菜单
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<String> selectMenuByRoleId(Long roleId);

    /**
     * 查询系统菜单列表
     * @param sysMenu 菜单信息
     * @return 菜单列表
     */
    List<SysMenu> selectMenuList(SysMenu sysMenu);

    /**
     * 根据菜单ID查询信息
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    SysMenu selectMenuById(Long menuId);

    /**
     * 查询菜单下面的数量
     * @param parentId 菜单父ID
     * @return
     */
    int selectCountMenuByParentId(Long parentId);

    /**
     * 校验菜单名称是否唯一
     * @param menuName 菜单名称
     * @param parentId 父菜单ID
     * @return
     */
    SysMenu checkMenuNameUnique(@Param("menuName") String menuName, @Param("parentId") Long parentId);

    /**
     * 删除菜单管理信息
     * @param menuId 菜单ID
     * @return
     */
    int deleteMenuById(Long menuId);

    /**
     * 新增菜单信息
     * @param sysMenu 菜单信息
     * @return 结果
     */
    int insertMenu(SysMenu sysMenu);

    /**
     * 修改菜单信息
     * @param sysMenu 菜单信息
     * @return 结果
     */
    int updateMenu(SysMenu sysMenu);



}
