package com.light.system.service.impl;

import com.light.common.base.Ztree;
import com.light.common.utils.StringUtil;
import com.light.system.domain.SysMenu;
import com.light.system.domain.SysRole;
import com.light.system.domain.SysUser;
import com.light.system.mapper.SysMenuMapper;
import com.light.system.mapper.SysRoleMenuMapper;
import com.light.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 菜单 业务层处理
 * author:ligz
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {
    private static final String PREMISSION_STRING = "perms[\"{0}\"]" ;

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    /**
     * 根据用户查询菜单
     * @param user 用户信息
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenusByUser(SysUser user) {
        List<SysMenu> menus;
        if (user.isAdmin()) {
            menus = menuMapper.selectMenuAll();
        } else {
            menus = menuMapper.selectMenusByUserId(user.getUserId());
        }
        return menus;
    }

    /**
     * 查询菜单集合
     * @return 所有菜单信息
     */
    @Override
    public List<SysMenu> selectMenuList(SysMenu menu) {
        return menuMapper.selectMenuList(menu);
    }

    /**
     * 查询菜单集合
     * @return 所有菜单信息
     */
    @Override
    public List<SysMenu> selectMenuAll() {
        return menuMapper.selectMenuAll();
    }

    /**
     * 根据用户的ID查询权限
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectPermsByUserId(Long userId) {
        List<String> perms = menuMapper.selectPermsByUserId(userId);
        Set<String> permSet = new HashSet<>();
        perms.stream().filter(StringUtil::isNotEmpty).forEach(perm -> permSet.addAll(Arrays.asList(perm.trim().split(","))));
        return permSet;
    }

    @Override
    public List<Ztree> roleMenuTreeData(SysRole role) {
        return null;
    }

    @Override
    public List<Ztree> menuTreeData() {
        return null;
    }

    @Override
    public Map<String, String> selectPermsAll() {
        return null;
    }

    @Override
    public int deleteMenuById(Long menuId) {
        return 0;
    }

    @Override
    public SysMenu selectMenuById(Long menuId) {
        return null;
    }

    @Override
    public int selectCountMenuByParentId(Long parentId) {
        return 0;
    }

    @Override
    public int selectCountRoleMenuByMenuId(Long menuId) {
        return 0;
    }

    @Override
    public int insertMenu(SysMenu menu) {
        return 0;
    }

    @Override
    public int updateMenu(SysMenu menu) {
        return 0;
    }

    @Override
    public String checkMenuNameUnique(SysMenu menu) {
        return null;
    }
}
