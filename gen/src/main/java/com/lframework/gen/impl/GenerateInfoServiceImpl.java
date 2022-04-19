package com.lframework.gen.impl;

import com.lframework.common.utils.StringUtil;
import com.lframework.gen.dto.dataobj.GenGenerateInfoDto;
import com.lframework.gen.entity.GenGenerateInfo;
import com.lframework.gen.enums.GenKeyType;
import com.lframework.gen.enums.GenTemplateType;
import com.lframework.gen.mappers.GenGenerateInfoMapper;
import com.lframework.gen.service.IGenerateInfoService;
import com.lframework.gen.vo.dataobj.UpdateGenerateInfoVo;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.EnumUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenerateInfoServiceImpl extends
    BaseMpServiceImpl<GenGenerateInfoMapper, GenGenerateInfo> implements IGenerateInfoService {

  @Override
  public GenGenerateInfoDto getByDataObjId(String dataObjId) {

    return getBaseMapper().getByDataObjId(dataObjId);
  }

  @Transactional
  @Override
  public void updateGenerate(String dataObjId, UpdateGenerateInfoVo vo) {

    GenGenerateInfo data = getBaseMapper().selectById(dataObjId);
    if (data != null) {
      getBaseMapper().deleteById(data.getId());
    } else {
      data = new GenGenerateInfo();
    }
    data.setId(dataObjId);
    data.setTemplateType(EnumUtil.getByCode(GenTemplateType.class, vo.getTemplateType()));
    data.setPackageName(vo.getPackageName());
    data.setModuleName(vo.getModuleName());
    data.setBizName(vo.getBizName());
    data.setClassName(vo.getClassName());
    data.setClassDescription(vo.getClassDescription());
    if (!StringUtil.isBlank(vo.getParentMenuId())) {
      data.setParentMenuId(vo.getParentMenuId());
    }
    data.setKeyType(EnumUtil.getByCode(GenKeyType.class, vo.getKeyType()));

    if (!StringUtil.isBlank(vo.getAuthor())) {
      data.setAuthor(vo.getAuthor());
    }
    data.setMenuCode(vo.getMenuCode());
    data.setMenuName(vo.getMenuName());
    data.setDetailSpan(vo.getDetailSpan());
    data.setIsCache(vo.getIsCache());
    data.setHasDelete(vo.getHasDelete());

    getBaseMapper().insert(data);
  }

  @Transactional
  @Override
  public void deleteByDataObjId(String dataObjId) {

    getBaseMapper().deleteById(dataObjId);
  }
}
