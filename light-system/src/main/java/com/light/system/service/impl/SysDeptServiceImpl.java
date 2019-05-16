package com.light.system.service.impl;

import com.light.common.annotation.DataScope;
import com.light.common.base.Ztree;
import com.light.common.constant.UserConstants;
import com.light.common.exception.BusinessException;
import com.light.system.domain.SysDept;
import com.light.system.domain.SysRole;
import com.light.system.mapper.SysDeptMapper;
import com.light.system.service.ISysDeptService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门管理 服务实现
 * author:ligz
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;

    /**
     * 查询部门管理数据
     * @param dept 部门信息
     * @return
     */
    @Override
    @DataScope(tableAlias = "d")
    public List<SysDept> selectDeptList(SysDept dept) {
        return sysDeptMapper.selectDeptList(dept);
    }

    /**
     * 查询部门管理树
     * @param dept 部门信息
     * @return 所有部门信息
     */
    @Override
    @DataScope(tableAlias = "d")
    public List<Ztree> selectDeptTree(SysDept dept) {
        List<SysDept> deptList = sysDeptMapper.selectDeptList(dept);
        return initZtree(deptList);
    }

    /**
     * 部门对象转部门树
     * @param deptList     部门列表
     * @return 部门树
     */
    private List<Ztree> initZtree(List<SysDept> deptList) {
        return initZtree(deptList, null);
    }

    /**
     * 部门对象转部门树
     * @param deptList     部门列表
     * @param roleDeptList 角色已存在菜单列表
     * @return 部门树
     */
    private List<Ztree> initZtree(List<SysDept> deptList, List<String> roleDeptList) {
        //TODO:需要根据权限加载树，是否可选还要再看一下
        List<Ztree> ztrees = new ArrayList<>();
        boolean isCheck = CollectionUtils.isNotEmpty(roleDeptList);
        if (CollectionUtils.isNotEmpty(deptList)) {
            deptList.stream()
                    .filter(dept -> UserConstants.DEPT_NORMAL.equals(dept.getStatus()))
                    .forEach(dept ->{
                        Ztree ztree = new Ztree();
                        ztree.setId(dept.getDeptId());
                        ztree.setPId(dept.getParentId());
                        ztree.setName(dept.getDeptName());
                        ztree.setTitle(dept.getDeptName());
                        if (isCheck) {
                            ztree.setChecked(roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
                        }
                        ztrees.add(ztree);
                    });
        }
        return ztrees;
    }

    /**
     * 根据角色ID查询部门（数据权限）
     * @param role 角色对象
     * @return 部门列表（数据权限）
     */
    @Override
    public List<Ztree> roleDeptTreeData(SysRole role) {
        Long roleId = role.getRoleId();
        List<Ztree> ztrees;
        List<SysDept> deptList = selectDeptList(new SysDept());
        if (ObjectUtils.allNotNull(roleId)) {
            List<String> roleDeptList = sysDeptMapper.selectRoleDeptTree(roleId);
            ztrees = initZtree(deptList, roleDeptList);
        } else {
            ztrees = initZtree(deptList);
        }
        return ztrees;
    }

    /**
     * 查询部门数
     * @param parentId 部门ID
     * @return 结果
     */
    @Override
    public int selectDeptCount(Long parentId) {
        SysDept dept = new SysDept();
        dept.setParentId(parentId);
        return sysDeptMapper.selectDeptCount(dept);
    }

    /**
     * 查询部门是否存在用户
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result = sysDeptMapper.checkDeptExistUser(deptId);
        return result > 0;
    }

    /**
     * 删除部门管理信息
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId) {
        return sysDeptMapper.deleteDeptById(deptId);
    }

    /**
     * 新增保存部门信息
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(SysDept dept) {
        //先查询上级部门
        SysDept info = sysDeptMapper.selectDeptById(dept.getParentId());
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
            throw new BusinessException("上级部门已停用，新增失败");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        return sysDeptMapper.insertDept(dept);
    }

    /**
     * 修改部门的信息
     * @param dept 部门信息
     * @return
     */
    @Override
    public int updateDept(SysDept dept) {
        SysDept parentDept = sysDeptMapper.selectDeptById(dept.getParentId());
        if (ObjectUtils.allNotNull(parentDept)) {
            //修改了父部门，需要将祖先修改
            String ancestors = parentDept.getAncestors() + "," + parentDept.getDeptId();
            dept.setAncestors(ancestors);
            updateDeptChildren(dept, ancestors);
        }
        int result = sysDeptMapper.updateDept(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
            //如果该部门变为可用，所有的上级部门都变为可用
            updatParentDeptStatus(dept);
        }
        return result;
    }

    /**
     * 修改子元素关系
     * @param sysDept   部门
     * @param ancestors 元素列表
     */
    private void updateDeptChildren(SysDept sysDept, String ancestors) {
        SysDept dept = new SysDept();
        dept.setParentId(sysDept.getDeptId());
        //寻找同一个父部门的部门,需要修改所有子部门的祖先
        List<SysDept> childrens = sysDeptMapper.selectDeptList(dept);
        if (CollectionUtils.isNotEmpty(childrens)) {
            childrens.forEach(children -> {
                children.setAncestors(ancestors + "," + dept.getParentId());
                if (!UserConstants.DEPT_NORMAL.equals(sysDept.getStatus())) {
                    children.setStatus(sysDept.getStatus());
                }
            });
            sysDeptMapper.updateDeptChildren(childrens);
            childrens.stream().filter(children -> !UserConstants.DEPT_NORMAL.equals(children.getStatus()))
                    .forEach(children -> updateDeptChildren(children, children.getAncestors()));
        }
    }

    /**
     * 修改该部门的父级部门状态
     * @param dept 当前部门
     */
    private void updatParentDeptStatus(SysDept dept) {
        String updateBy = dept.getUpdateBy();
        dept = sysDeptMapper.selectDeptById(dept.getDeptId());
        dept.setUpdateBy(updateBy);
        sysDeptMapper.updateDeptStatus(dept);
    }

    /**
     * 根据部门ID查询信息
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptById(Long deptId) {
        return sysDeptMapper.selectDeptById(deptId);
    }

    /**
     * 校验部门名称是否唯一
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(SysDept dept) {
        SysDept info = sysDeptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (ObjectUtils.allNotNull(info) && !info.getDeptId().equals(dept.getDeptId())) {
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }
        return UserConstants.DEPT_NAME_UNIQUE;
    }
}
