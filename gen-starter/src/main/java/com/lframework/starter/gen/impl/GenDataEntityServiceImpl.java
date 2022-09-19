package com.lframework.starter.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.gen.converters.GenStringConverter;
import com.lframework.starter.gen.converters.GenViewTypeConverter;
import com.lframework.starter.gen.dto.data.entity.DataEntityGenerateDto;
import com.lframework.starter.gen.dto.gen.GenCreateColumnConfigDto;
import com.lframework.starter.gen.dto.gen.GenDetailColumnConfigDto;
import com.lframework.starter.gen.dto.gen.GenGenerateInfoDto;
import com.lframework.starter.gen.dto.gen.GenQueryColumnConfigDto;
import com.lframework.starter.gen.dto.gen.GenQueryParamsColumnConfigDto;
import com.lframework.starter.gen.dto.gen.GenUpdateColumnConfigDto;
import com.lframework.starter.gen.dto.simpledb.OriSimpleTableDto;
import com.lframework.starter.gen.entity.GenDataEntity;
import com.lframework.starter.gen.entity.GenDataEntityDetail;
import com.lframework.starter.gen.entity.GenSimpleTable;
import com.lframework.starter.gen.entity.GenSimpleTableColumn;
import com.lframework.starter.gen.enums.GenConvertType;
import com.lframework.starter.gen.enums.GenDataType;
import com.lframework.starter.gen.enums.GenKeyType;
import com.lframework.starter.gen.enums.GenOrderType;
import com.lframework.starter.gen.enums.GenStatus;
import com.lframework.starter.gen.enums.GenTemplateType;
import com.lframework.starter.gen.enums.GenViewType;
import com.lframework.starter.gen.events.DataEntityDeleteEvent;
import com.lframework.starter.gen.mappers.GenDataEntityMapper;
import com.lframework.starter.gen.service.IGenCreateColumnConfigService;
import com.lframework.starter.gen.service.IGenDataEntityDetailService;
import com.lframework.starter.gen.service.IGenDataEntityService;
import com.lframework.starter.gen.service.IGenDetailColumnConfigService;
import com.lframework.starter.gen.service.IGenQueryColumnConfigService;
import com.lframework.starter.gen.service.IGenQueryParamsColumnConfigService;
import com.lframework.starter.gen.service.IGenUpdateColumnConfigService;
import com.lframework.starter.gen.service.IGenerateInfoService;
import com.lframework.starter.gen.service.ISimpleDBService;
import com.lframework.starter.gen.service.ISimpleTableColumnService;
import com.lframework.starter.gen.service.ISimpleTableService;
import com.lframework.starter.gen.vo.data.entity.CreateDataEntityVo;
import com.lframework.starter.gen.vo.data.entity.GenDataEntityDetailVo;
import com.lframework.starter.gen.vo.data.entity.QueryDataEntityVo;
import com.lframework.starter.gen.vo.data.entity.UpdateDataEntityGenerateVo;
import com.lframework.starter.gen.vo.data.entity.UpdateDataEntityVo;
import com.lframework.starter.gen.vo.gen.UpdateGenerateInfoVo;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenDataEntityServiceImpl extends
    BaseMpServiceImpl<GenDataEntityMapper, GenDataEntity> implements IGenDataEntityService {

  @Autowired
  private ISimpleTableService simpleTableService;

  @Autowired
  private ISimpleTableColumnService simpleTableColumnService;

  @Autowired
  private IGenerateInfoService generateInfoService;

  @Autowired
  private IGenDataEntityDetailService genDataEntityDetailService;

  @Autowired
  private ISimpleDBService simpleDBService;

  @Autowired
  private IGenCreateColumnConfigService genCreateColumnConfigService;

  @Autowired
  private IGenUpdateColumnConfigService genUpdateColumnConfigService;

  @Autowired
  private IGenQueryColumnConfigService genQueryColumnConfigService;

  @Autowired
  private IGenQueryParamsColumnConfigService genQueryParamsColumnConfigService;

  @Autowired
  private IGenDetailColumnConfigService genDetailColumnConfigService;

  @Autowired
  private GenViewTypeConverter genViewTypeConverter;

  @Override
  public PageResult<GenDataEntity> query(Integer pageIndex, Integer pageSize,
      QueryDataEntityVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<GenDataEntity> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<GenDataEntity> query(QueryDataEntityVo vo) {
    return getBaseMapper().query(vo);
  }

  @Override
  public GenDataEntity findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional
  @Override
  public String create(CreateDataEntityVo data) {
    GenDataEntity record = new GenDataEntity();
    record.setId(IdUtil.getId());
    record.setName(data.getName());
    if (!StringUtil.isBlank(data.getCategoryId())) {
      record.setCategoryId(data.getCategoryId());
    }
    record.setAvailable(Boolean.TRUE);
    record.setDescription(
        StringUtil.isBlank(data.getDescription()) ? StringPool.EMPTY_STR : data.getDescription());

    this.save(record);

    OriSimpleTableDto table = simpleDBService.getByTableName(data.getTableName());
    if (ObjectUtil.isNull(table)) {
      throw new DefaultClientException("数据表【" + data.getTableName() + "】不存在！");
    }

    table.setId(record.getId());

    GenSimpleTable simpleTable = new GenSimpleTable();
    simpleTable.setId(table.getId());
    simpleTable.setTableSchema(table.getTableSchema());
    simpleTable.setTableName(table.getTableName());
    simpleTable.setEngine(table.getEngine());
    simpleTable.setTableCollation(table.getTableCollation());
    simpleTable.setTableComment(table.getTableComment());
    simpleTable.setConvertType(GenConvertType.UNDERLINE_TO_CAMEL);

    simpleTableService.save(simpleTable);

    int orderNo = 1;
    for (GenDataEntityDetailVo column : data.getColumns()) {
      GenSimpleTableColumn columnDto = table.getColumns().stream()
          .filter(t -> t.getColumnName().equals(column.getId())).findFirst().orElse(null);
      if (columnDto == null) {
        throw new DefaultClientException("字段【" + column.getId() + "】不存在！");
      }

      GenDataEntityDetail detail = new GenDataEntityDetail();
      detail.setId(IdUtil.getId());
      detail.setEntityId(record.getId());
      detail.setName(column.getName());
      detail.setColumnName(GenStringConverter.convertToCamelCase(GenConvertType.UNDERLINE_TO_CAMEL,
          columnDto.getColumnName()));
      detail.setIsKey(columnDto.getIsKey());
      detail.setDataType(EnumUtil.getByCode(GenDataType.class, column.getDataType()));
      detail.setColumnOrder(orderNo);
      detail.setDescription(column.getDescription());
      detail.setViewType(EnumUtil.getByCode(GenViewType.class, column.getViewType()));
      detail.setFixEnum(column.getFixEnum());
      detail.setEnumBack(column.getEnumBack());
      detail.setEnumFront(column.getEnumFront());
      detail.setRegularExpression(column.getRegularExpression());
      detail.setIsOrder(column.getIsOrder());
      detail.setOrderType(EnumUtil.getByCode(GenOrderType.class, column.getOrderType()));

      if (!genViewTypeConverter.canConvert(detail.getViewType(), detail.getDataType())) {
        List<GenViewType> viewTypes = genViewTypeConverter.convert(detail.getDataType());
        throw new DefaultClientException(
            "字段【" + detail.getName() + "】数据类型和显示类型不匹配，当前数据类型为【" + detail.getDataType().getDesc()
                + "】，" + (!CollectionUtil.isEmpty(viewTypes) ? "显示类型只能为【" + CollectionUtil.join(
                genViewTypeConverter.convert(detail.getDataType()).stream()
                    .map(GenViewType::getDesc).collect(
                        Collectors.toList()), StringPool.STR_SPLIT_CN) + "】" : "暂不支持显示此数据类型"));
      }

      genDataEntityDetailService.save(detail);

      // 真实列信息
      GenSimpleTableColumn simpleTableColumn = new GenSimpleTableColumn();
      simpleTableColumn.setId(detail.getId());
      simpleTableColumn.setTableId(table.getId());
      simpleTableColumn.setColumnName(columnDto.getColumnName());
      simpleTableColumn.setDataType(columnDto.getDataType());
      simpleTableColumn.setIsNullable(columnDto.getIsNullable());
      simpleTableColumn.setIsKey(columnDto.getIsKey());
      simpleTableColumn.setColumnDefault(columnDto.getColumnDefault());
      simpleTableColumn.setOrdinalPosition(columnDto.getOrdinalPosition());
      simpleTableColumn.setColumnComment(columnDto.getColumnComment());

      simpleTableColumnService.save(simpleTableColumn);

      orderNo++;
    }

    // 设置默认的基础设置
    UpdateGenerateInfoVo updateGenerateInfoVo = new UpdateGenerateInfoVo();
    updateGenerateInfoVo.setTemplateType(GenTemplateType.LIST.getCode());
    updateGenerateInfoVo.setPackageName("com.lframework");
    updateGenerateInfoVo.setModuleName(StringPool.EMPTY_STR);
    updateGenerateInfoVo.setBizName(
        GenStringConverter.convertToNormalLowerCase(GenConvertType.UNDERLINE_TO_CAMEL,
            simpleTable.getTableName()));
    // 强制转驼峰并且首字母大写
    String className = GenStringConverter.convertToCamelCase(GenConvertType.UNDERLINE_TO_CAMEL,
        simpleTable.getTableName());
    updateGenerateInfoVo.setClassName(
        className.substring(0, 1).toUpperCase() + className.substring(1));
    updateGenerateInfoVo.setClassDescription(
        StringUtil.isEmpty(simpleTable.getTableComment()) ? StringPool.EMPTY_STR
            : simpleTable.getTableComment());
    updateGenerateInfoVo.setKeyType(GenKeyType.SNOW_FLAKE.getCode());
    updateGenerateInfoVo.setMenuCode(StringPool.EMPTY_STR);
    updateGenerateInfoVo.setMenuName(StringPool.EMPTY_STR);
    updateGenerateInfoVo.setDetailSpan(4);
    updateGenerateInfoVo.setIsCache(true);
    updateGenerateInfoVo.setHasDelete(false);

    generateInfoService.updateGenerate(record.getId(), updateGenerateInfoVo);

    return record.getId();
  }

  @Transactional
  @Override
  public void update(UpdateDataEntityVo vo) {
    GenDataEntity record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("数据实体不存在！");
    }
    Wrapper<GenDataEntity> updateWrapper = Wrappers.lambdaUpdate(GenDataEntity.class)
        .eq(GenDataEntity::getId, vo.getId()).set(GenDataEntity::getName, vo.getName())
        .set(GenDataEntity::getAvailable, vo.getAvailable()).set(GenDataEntity::getCategoryId,
            StringUtil.isBlank(vo.getCategoryId()) ? null : vo.getCategoryId())
        .set(GenDataEntity::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    this.update(updateWrapper);

    Wrapper<GenDataEntityDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
        GenDataEntityDetail.class).eq(GenDataEntityDetail::getEntityId, vo.getId());
    genDataEntityDetailService.remove(deleteDetailWrapper);

    List<GenSimpleTableColumn> columnDtos = simpleTableColumnService.getByTableId(record.getId());

    int orderNo = 1;
    for (GenDataEntityDetailVo column : vo.getColumns()) {
      GenSimpleTableColumn columnDto = columnDtos.stream()
          .filter(t -> t.getId().equals(column.getId())).findFirst().orElse(null);

      GenDataEntityDetail detail = new GenDataEntityDetail();
      detail.setId(column.getId());
      detail.setEntityId(record.getId());
      detail.setName(column.getName());
      detail.setColumnName(GenStringConverter.convertToCamelCase(GenConvertType.UNDERLINE_TO_CAMEL,
          columnDto.getColumnName()));
      detail.setIsKey(columnDto.getIsKey());
      detail.setDataType(EnumUtil.getByCode(GenDataType.class, column.getDataType()));
      detail.setColumnOrder(orderNo);
      detail.setDescription(column.getDescription());
      detail.setViewType(EnumUtil.getByCode(GenViewType.class, column.getViewType()));
      detail.setFixEnum(column.getFixEnum());
      detail.setEnumBack(column.getEnumBack());
      detail.setEnumFront(column.getEnumFront());
      detail.setRegularExpression(column.getRegularExpression());
      detail.setIsOrder(column.getIsOrder());
      detail.setOrderType(EnumUtil.getByCode(GenOrderType.class, column.getOrderType()));

      if (!genViewTypeConverter.canConvert(detail.getViewType(), detail.getDataType())) {
        List<GenViewType> viewTypes = genViewTypeConverter.convert(detail.getDataType());
        throw new DefaultClientException(
            "字段【" + detail.getName() + "】数据类型和显示类型不匹配，当前数据类型为【" + detail.getDataType().getDesc()
                + "】，" + (!CollectionUtil.isEmpty(viewTypes) ? "显示类型只能为【" + CollectionUtil.join(
                genViewTypeConverter.convert(detail.getDataType()).stream()
                    .map(GenViewType::getDesc).collect(
                        Collectors.toList()), StringPool.STR_SPLIT_CN) + "】" : "暂不支持显示此数据类型"));
      }

      genDataEntityDetailService.save(detail);

      orderNo++;
    }
  }

  @Transactional
  @Override
  public void delete(@NonNull String id) {

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(id);
    List<String> columnIds = columns.stream().map(GenDataEntityDetail::getId)
        .collect(Collectors.toList());

    getBaseMapper().deleteById(id);

    genDataEntityDetailService.deleteByEntityId(id);

    DataEntityDeleteEvent event = new DataEntityDeleteEvent(this);
    event.setId(id);
    event.setColumnIds(columnIds);

    ApplicationUtil.publishEvent(event);
  }

  @Transactional
  @Override
  public void batchDelete(@NonNull List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    for (String id : ids) {
      this.delete(id);
    }
  }

  @Transactional
  @Override
  public void batchEnable(List<String> ids) {
    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<GenDataEntity> wrapper = Wrappers.lambdaUpdate(GenDataEntity.class)
        .set(GenDataEntity::getAvailable, Boolean.TRUE).in(GenDataEntity::getId, ids);
    getBaseMapper().update(wrapper);
  }

  @Transactional
  @Override
  public void batchUnable(List<String> ids) {
    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<GenDataEntity> wrapper = Wrappers.lambdaUpdate(GenDataEntity.class)
        .set(GenDataEntity::getAvailable, Boolean.FALSE).in(GenDataEntity::getId, ids);
    getBaseMapper().update(wrapper);
  }

  @Override
  public DataEntityGenerateDto getGenerateById(String id) {
    DataEntityGenerateDto result = new DataEntityGenerateDto();

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(id);
    result.setColumns(columns);

    GenGenerateInfoDto generateInfo = generateInfoService.getByDataObjId(id);
    result.setGenerateInfo(generateInfo);

    List<GenCreateColumnConfigDto> createColumnConfigDtos = genCreateColumnConfigService.getByDataEntityId(
        id);
    result.setCreateConfigs(createColumnConfigDtos);

    List<GenUpdateColumnConfigDto> updateColumnConfigDtos = genUpdateColumnConfigService.getByDataEntityId(
        id);
    result.setUpdateConfigs(updateColumnConfigDtos);

    List<GenQueryColumnConfigDto> queryColumnConfigDtos = genQueryColumnConfigService.getByDataEntityId(
        id);
    result.setQueryConfigs(queryColumnConfigDtos);

    List<GenQueryParamsColumnConfigDto> queryParamsColumnConfigDtos = genQueryParamsColumnConfigService.getByDataEntityId(
        id);
    result.setQueryParamsConfigs(queryParamsColumnConfigDtos);

    List<GenDetailColumnConfigDto> detailColumnConfigDtos = genDetailColumnConfigService.getByDataEntityId(
        id);
    result.setDetailConfigs(detailColumnConfigDtos);

    return result;
  }

  @Transactional
  @Override
  public void updateGenerate(UpdateDataEntityGenerateVo vo) {

    generateInfoService.updateGenerate(vo.getId(), vo.getGenerateInfo());

    genCreateColumnConfigService.updateGenerate(vo.getId(), vo.getCreateConfigs());

    genUpdateColumnConfigService.updateGenerate(vo.getId(), vo.getUpdateConfigs());

    genQueryColumnConfigService.updateGenerate(vo.getId(), vo.getQueryConfigs());

    genQueryParamsColumnConfigService.updateGenerate(vo.getId(), vo.getQueryParamsConfigs());

    genDetailColumnConfigService.updateGenerate(vo.getId(), vo.getDetailConfigs());

    Wrapper<GenDataEntity> updateWrapper = Wrappers.lambdaUpdate(GenDataEntity.class)
        .set(GenDataEntity::getGenStatus, GenStatus.SET_GEN).eq(GenDataEntity::getId, vo.getId());
    getBaseMapper().update(updateWrapper);
  }
}
