package com.light.system.mapper;

import com.light.system.domain.SysDictData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典
 * author:ligz
 */
public interface SysDictDataMapper {
    /**
     * 根据条件查询字典
     * @param sysDictData
     * @return
     */
    List<SysDictData> selectDictDataList(SysDictData sysDictData);

    /**
     * 根据类型查询所有的字典
     * @param dictType
     * @return
     */
    List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 根据键值查询字典名称
     * @param dictType
     * @param dictValue
     * @return
     */
    String selectDictLabel(@Param("dictType") String dictType, @Param("dictValue") String dictValue);

    /**
     * 根据ID查询
     * @param dictCode
     * @return
     */
    SysDictData selectDictDataById(Long dictCode);

    /**
     * 统计某个字典子类型的个数
     * @param dictType
     * @return
     */
    int countDictDataByType(String dictType);

    /**
     * 根据ID删除字典
     * @param dictCode
     * @return
     */
    int deleteDictDataById(Long dictCode);

    /**
     * 根据ID批量删除
     * @param ids
     * @return
     */
    int deleteDictDataByIds(String[] ids);

    /**
     * 插入数据
     * @param sysDictData
     * @return
     */
    int insertDictData(SysDictData sysDictData);

    /**
     * 更新数据
     * @param sysDictData
     * @return
     */
    int updateDictData(SysDictData sysDictData);

    /**
     * 更新字典类型
     * @param oldDictType
     * @param newDictType
     * @return
     */
    int updateDictDataType(@Param("oldDictType") String oldDictType, @Param("newDictType") String newDictType);
}
