package com.lframework.starter.mybatis.mappers.system;

import com.lframework.starter.mybatis.dto.system.position.DefaultSysPositionDto;
import com.lframework.starter.mybatis.entity.DefaultSysPosition;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.mybatis.vo.system.position.QuerySysPositionVo;
import com.lframework.starter.mybatis.vo.system.position.SysPositionSelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统岗位 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-06-30
 */
public interface DefaultSysPositionMapper extends BaseMapper<DefaultSysPosition> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<DefaultSysPositionDto> query(@Param("vo") QuerySysPositionVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DefaultSysPositionDto findById(String id);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<DefaultSysPositionDto> selector(@Param("vo") SysPositionSelectorVo vo);

}
