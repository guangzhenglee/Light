package com.light.system.service.impl;

import com.light.common.base.Ztree;
import com.light.system.domain.SysMenu;
import com.light.system.domain.SysRole;
import com.light.system.domain.SysUser;
import com.light.system.mapper.SysMenuMapper;
import com.light.system.mapper.SysRoleMenuMapper;
import com.light.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Override
    public List<SysMenu> selectMenusByUser(SysUser user) {
        return null;
    }

    @Override
    public List<SysMenu> selectMenuList(SysMenu menu) {
        return null;
    }

    @Override
    public List<SysMenu> selectMenuAll() {
        return null;
    }

    @Override
    public Set<String> selectPermsByUserId(Long userId) {
        return null;
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
