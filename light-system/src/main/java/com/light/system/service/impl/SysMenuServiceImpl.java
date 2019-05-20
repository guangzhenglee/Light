package com.light.system.service.impl;

import com.light.common.base.Ztree;
import com.light.common.constant.UserConstants;
import com.light.common.utils.StringUtil;
import com.light.system.domain.SysMenu;
import com.light.system.domain.SysRole;
import com.light.system.domain.SysUser;
import com.light.system.mapper.SysMenuMapper;
import com.light.system.mapper.SysRoleMenuMapper;
import com.light.system.service.ISysMenuService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
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
        return getChildPerms(menus, 0);
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    private List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
        List<SysMenu> returnList = new ArrayList<>();
        list.stream().filter(t -> t.getParentId() == parentId).forEach(t -> {
            recursionFn(list, t);
            returnList.add(t);
        });
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list 菜单列表
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                for (SysMenu n : childList) {
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<>();
        list.stream().filter(sysMenu -> sysMenu.getParentId().equals(t.getMenuId())).forEach(tlist::add);
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return !org.springframework.util.CollectionUtils.isEmpty(getChildList(list, t));
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
        //去除两端的空格，目的是去除无效的p，空的perms，
        perms.stream().filter(StringUtil::isNotEmpty).forEach(perm -> permSet.addAll(Arrays.asList(perm.trim().split(","))));
        return permSet;
    }

    /**
     * 根据角色ID查询菜单
     * @param role 角色对象
     * @return 菜单列表
     */
    @Override
    public List<Ztree> roleMenuTreeData(SysRole role) {
        Long roleId = role.getRoleId();
        List<Ztree> ztrees;
        List<SysMenu> menuList = menuMapper.selectMenuAll();
        if (ObjectUtils.allNotNull(roleId)) {
            List<String> roleMenuList = menuMapper.selectMenuByRoleId(roleId);
            ztrees = initZtree(menuList, roleMenuList, true);
        } else {
            ztrees = initZtree(menuList, null, true);
        }
        return ztrees;
    }

    /**
     * 对象转菜单树
     *
     * @param menuList 菜单列表
     * @return 树结构列表
     */
    private List<Ztree> initZtree(List<SysMenu> menuList) {
        return initZtree(menuList, null, false);
    }

    /**
     * 对象转菜单树
     * @param menuList     菜单列表
     * @param roleMenuList 角色已存在菜单列表
     * @param permsFlag    是否需要显示权限标识
     * @return 菜单树
     */
    private List<Ztree> initZtree(List<SysMenu> menuList, List<String> roleMenuList, boolean permsFlag) {
        List<Ztree> ztrees = new ArrayList<>();
        boolean isCheck = CollectionUtils.isNotEmpty(roleMenuList);
        if (CollectionUtils.isNotEmpty(menuList)) {
            menuList.forEach(menu -> {
                Ztree ztree = new Ztree();
                ztree.setPId(menu.getMenuId());
                ztree.setPId(menu.getParentId());
                ztree.setName(transMenuName(menu, permsFlag));
                ztree.setTitle(menu.getMenuName());
                if (isCheck) {
                    ztree.setChecked(roleMenuList.contains(menu.getMenuId() + menu.getPerms()));
                }
                ztrees.add(ztree);
            });
        }
        return ztrees;
    }

    /**
     * 给菜单名称加上权限
     * @param menu
     * @param permsFlag
     * @return
     */
    private String transMenuName(SysMenu menu, boolean permsFlag) {
        StringBuilder sb = new StringBuilder();
        sb.append(menu.getMenuName());
        if (permsFlag) {
            sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;").append(menu.getPerms()).append("</font>");
        }
        return sb.toString();
    }


    /**
     * 查询所有菜单
     * @return 菜单列表
     */
    @Override
    public List<Ztree> menuTreeData() {
        List<SysMenu> menuList = menuMapper.selectMenuAll();
        return initZtree(menuList);
    }

    /**
     * 查询系统所有权限
     * @return 权限列表
     */
    @Override
    public Map<String, String> selectPermsAll() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        List<SysMenu> permissions = menuMapper.selectMenuAll();
        if (!org.springframework.util.CollectionUtils.isEmpty(permissions)) {
            permissions.forEach(menu -> map.put(menu.getUrl(), MessageFormat.format(PREMISSION_STRING, menu.getPerms())));
        }
        return map;
    }

    /**
     * 删除菜单管理信息
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int deleteMenuById(Long menuId) {
        return menuMapper.deleteMenuById(menuId);
    }

    /**
     * 根据菜单ID查询信息
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public SysMenu selectMenuById(Long menuId) {
        return menuMapper.selectMenuById(menuId);
    }

    /**
     * 查询子菜单数量
     * @param parentId 父级菜单ID
     * @return 结果
     */
    @Override
    public int selectCountMenuByParentId(Long parentId) {
        return menuMapper.selectCountMenuByParentId(parentId);
    }

    /**
     * 查询菜单使用数量
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int selectCountRoleMenuByMenuId(Long menuId) {
        return roleMenuMapper.selectCountRoleMenuByMenuId(menuId);
    }

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int insertMenu(SysMenu menu) {
        return menuMapper.insertMenu(menu);
    }

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int updateMenu(SysMenu menu) {
        return menuMapper.updateMenu(menu);
    }

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public String checkMenuNameUnique(SysMenu menu) {
        SysMenu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (ObjectUtils.allNotNull(info) && !info.getMenuId().equals(menu.getMenuId())) {
            return UserConstants.MENU_NAME_NOT_UNIQUE;
        }
        return UserConstants.MENU_NAME_UNIQUE;
    }
}
