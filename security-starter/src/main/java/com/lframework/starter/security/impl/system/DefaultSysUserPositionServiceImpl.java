package com.lframework.starter.security.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.security.dto.system.position.DefaultSysUserPositionDto;
import com.lframework.starter.security.entity.DefaultSysUserPosition;
import com.lframework.starter.security.mappers.system.DefaultSysUserPositionMapper;
import com.lframework.starter.security.service.system.ISysUserPositionService;
import com.lframework.starter.security.vo.system.position.SysUserPositionSettingVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

public class DefaultSysUserPositionServiceImpl implements ISysUserPositionService {

  @Autowired
  private DefaultSysUserPositionMapper defaultSysUserPositionMapper;

  @OpLog(type = OpLogType.OTHER, name = "用户设置岗位，用户ID：{}，岗位ID：{}", params = {"#vo.userId",
      "#vo.positionId"})
  @Transactional
  @Override
  public void setting(SysUserPositionSettingVo vo) {

    this.doSetting(vo);

    ISysUserPositionService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(vo.getUserId());
  }

  @Cacheable(value = DefaultSysUserPositionDto.CACHE_NAME, key = "#userId")
  @Override
  public List<DefaultSysUserPositionDto> getByUserId(String userId) {

    return doGetByUserId(userId);
  }

  protected void doSetting(SysUserPositionSettingVo vo) {

    Wrapper<DefaultSysUserPosition> deleteWrapper = Wrappers
        .lambdaQuery(DefaultSysUserPosition.class)
        .eq(DefaultSysUserPosition::getUserId, vo.getUserId());
    defaultSysUserPositionMapper.delete(deleteWrapper);

    if (!CollectionUtil.isEmpty(vo.getPositionIds())) {
      for (String positionId : vo.getPositionIds()) {
        DefaultSysUserPosition record = new DefaultSysUserPosition();
        record.setId(IdUtil.getId());
        record.setUserId(vo.getUserId());
        record.setPositionId(positionId);

        defaultSysUserPositionMapper.insert(record);
      }
    }
  }

  protected List<DefaultSysUserPositionDto> doGetByUserId(String userId) {

    return defaultSysUserPositionMapper.getByUserId(userId);
  }

  @CacheEvict(value = DefaultSysUserPositionDto.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(String key) {

  }
}
