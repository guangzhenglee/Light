package com.light.system.mapper;

import com.light.system.domain.SysDictType;

import java.util.List;

/**
 * 字典类型
 * author:ligz
 */
public interface SysDictTypeMapper {
    /**
     * 按条件查询字典类型
     * @param sysDictType
     * @return
     */
    List<SysDictType> selectDictTypeList(SysDictType sysDictType);

    /**
     * 查询所有字典类型
     * @return
     */
    List<SysDictType> selectDictTypeAll();

    /**
     * 根据ID查询字典类型
     * @param dictId
     * @return
     */
    SysDictType selectDictTypeById(Long dictId);

    /**
     * 插入字典类型
     * @param sysDictType
     * @return
     */
    int insertDictType(SysDictType sysDictType);

    /**
     * 更新字典类型
     * @param sysDictType
     * @return
     */
    int updateDictType(SysDictType sysDictType);

    /**
     * 根据类型查询字典类型
     * @param dictType
     * @return
     */
    SysDictType checkDictTypeUnique(String dictType);

    /**
     * 根据ID删除字典类型
     * @param dictId
     * @return
     */
    int deleteDictTypeById(Long dictId);

    /**
     * 批量删除字典类型
     * @param ids
     * @return
     */
    int deleteDictTypeByIds(Long[] ids);
}
