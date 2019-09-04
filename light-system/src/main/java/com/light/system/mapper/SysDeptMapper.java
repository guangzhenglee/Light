package com.light.system.mapper;

import com.light.system.domain.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:ligz
 */
public interface SysDeptMapper {
    /**
     * 插入部门
     * @param sysDept
     * @return
     */
    int insertDept(SysDept sysDept);

    /**
     * 删除部门，修改标志位
     * @param deptId
     * @return
     */
    int deleteDeptById(Long deptId);

    /**
     * 更新
     * @param sysDept
     * @return
     */
    int updateDept(SysDept sysDept);

    /**
     * 修改部门状态
     * @param sysDept
     */
    void updateDeptStatus(SysDept sysDept);


    /**
     * 更新部门的子部门
     * @param depts 子元素
     * @return
     */
    int updateDeptChildren(@Param("depts") List<SysDept> depts);

    /**
     * 获取子部门的个数
     * @param sysDept
     * @return
     */
    int selectDeptCount(SysDept sysDept);

    /**
     * 部门的人数
     * @param deptId
     * @return
     */
    int checkDeptExistUser(Long deptId);

    /**
     * 查询部门集合
     * @param sysDept
     * @return
     */
    List<SysDept> selectDeptList(SysDept sysDept);

    /**
     * 根据ID查询部门
     * @param deptId
     * @return
     */
    SysDept selectDeptById(Long deptId);

    /**
     * 校验部门名称是否唯一
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return
     */
    SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 根据角色ID查询部门
     * @param roleId 角色ID
     * @return 部门列表
     */
    List<String> selectRoleDeptTree(Long roleId);
}
