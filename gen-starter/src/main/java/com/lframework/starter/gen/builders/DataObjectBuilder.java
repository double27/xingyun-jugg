package com.lframework.starter.gen.builders;

import com.lframework.starter.gen.components.DataObject;
import com.lframework.starter.gen.components.DataObjectColumn;
import com.lframework.starter.gen.dto.gen.GenGenerateInfoDto;
import com.lframework.starter.gen.entity.GenDataEntity;
import com.lframework.starter.gen.entity.GenDataEntityDetail;
import com.lframework.starter.gen.enums.GenType;
import com.lframework.starter.gen.service.IGenCreateColumnConfigService;
import com.lframework.starter.gen.service.IGenDataEntityDetailService;
import com.lframework.starter.gen.service.IGenDataEntityService;
import com.lframework.starter.gen.service.IGenDetailColumnConfigService;
import com.lframework.starter.gen.service.IGenQueryColumnConfigService;
import com.lframework.starter.gen.service.IGenQueryParamsColumnConfigService;
import com.lframework.starter.gen.service.IGenUpdateColumnConfigService;
import com.lframework.starter.gen.service.IGenerateInfoService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 数据对象Builder
 */
@Component
public class DataObjectBuilder {

  @Autowired
  private IGenDataEntityService genDataEntityService;

  @Autowired
  private IGenerateInfoService generateInfoService;

  @Autowired
  private IGenDataEntityDetailService genDataEntityDetailService;

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

  /**
   * 根据数据对象ID构建
   *
   * @param id
   * @return
   */
  public DataObject build(String id) {

    // 根据ID查询数据对象
    GenDataEntity dataObject = genDataEntityService.findById(id);
    DataObject result = new DataObject();
    result.setId(dataObject.getId());
    result.setName(dataObject.getName());
    result.setDescription(dataObject.getDescription());

    TableBuilder tableBuilder = TableBuilderFactory.getBuilder(GenType.SIMPLE_DB);

    result.setTable(tableBuilder.buildTable(dataObject.getId()));
    result.setColumns(this.buildColumns(dataObject.getId(), tableBuilder));
    result.setGenerateInfo(this.buildGenerateInfo(dataObject.getId()));

    return result;
  }

  private GenGenerateInfoDto buildGenerateInfo(String dataObjId) {

    return generateInfoService.getByDataObjId(dataObjId);
  }

  private List<DataObjectColumn> buildColumns(String entityId, TableBuilder tableBuilder) {

    List<DataObjectColumn> results = new ArrayList<>();

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    for (GenDataEntityDetail column : columns) {
      DataObjectColumn result = new DataObjectColumn();
      result.setId(column.getId());
      result.setName(column.getName());
      result.setColumnName(column.getColumnName());
      result.setIsKey(column.getIsKey());
      result.setDataType(column.getDataType());
      result.setColumnOrder(column.getColumnOrder());
      result.setDescription(column.getDescription());
      result.setViewType(column.getViewType());
      result.setTableColumn(tableBuilder.buildTableColumn(column.getId()));
      result.setFixEnum(column.getFixEnum());
      result.setEnumBack(column.getEnumBack());
      result.setEnumFront(column.getEnumFront());
      result.setRegularExpression(column.getRegularExpression());
      result.setIsOrder(column.getIsOrder());
      if (result.getIsOrder()) {
        result.setOrderType(column.getOrderType());
      }

      result.setCreateConfig(genCreateColumnConfigService.findById(column.getId()));
      result.setUpdateConfig(genUpdateColumnConfigService.findById(column.getId()));
      result.setQueryConfig(genQueryColumnConfigService.findById(column.getId()));
      result.setQueryParamsConfig(genQueryParamsColumnConfigService.findById(column.getId()));
      result.setDetailConfig(genDetailColumnConfigService.findById(column.getId()));

      results.add(result);
    }

    return results;
  }
}
