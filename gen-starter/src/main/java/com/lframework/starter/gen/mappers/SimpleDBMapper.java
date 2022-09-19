package com.lframework.starter.gen.mappers;

import com.lframework.starter.gen.dto.simpledb.OriSimpleTableDto;
import com.lframework.starter.gen.dto.simpledb.SimpleDBDto;
import com.lframework.starter.gen.vo.simpledb.SimpleTableSelectorVo;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SimpleDBMapper extends BaseMapper {

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<SimpleDBDto> selector(SimpleTableSelectorVo vo);

  /**
   * 根据TableName查询
   *
   * @param tableName
   * @return
   */
  OriSimpleTableDto getByTableName(@Param("tableName") String tableName);
}
