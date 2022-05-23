package ${packageName}.impl.${moduleName};

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.github.pagehelper.PageInfo;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.EnumUtil;
import ${packageName}.entity.${className};
<#if importPackages??>
    <#list importPackages as p>
import ${p};
    </#list>
</#if>
import ${packageName}.mappers.${className}Mapper;
import ${packageName}.service.${moduleName}.I${className}Service;
<#if create??>
import ${packageName}.vo.${moduleName}.${bizName}.Create${className}Vo;
</#if>
<#if queryParams??>
import ${packageName}.vo.${moduleName}.${bizName}.Query${className}Vo;
</#if>
<#if update??>
import ${packageName}.vo.${moduleName}.${bizName}.Update${className}Vo;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class ${className}ServiceImpl extends BaseMpServiceImpl${r"<"}${className}Mapper, ${className}${r">"} implements I${className}Service {
<#if queryParams??>

    @Override
    public PageResult${r"<"}${className}${r">"} query(Integer pageIndex, Integer pageSize, Query${className}Vo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List${r"<"}${className}${r">"} datas = this.query(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }
</#if>
<#if queryParams??>

    @Override
    public List${r"<"}${className}${r">"} query(Query${className}Vo vo) {

        return getBaseMapper().query(vo);
    }
</#if>

<#if isCache>
    @Cacheable(value = ${className}.CACHE_NAME, key = "#${keys[0].name}", unless = "#result == null")
</#if>
    @Override
    public ${className} findById(<#list keys as key>${key.type} ${key.name}<#if key_index != keys?size - 1>, </#if></#list>) {

        return getBaseMapper().selectById(${keys[0].name});
    }
<#if create??>

    @OpLog(type = OpLogType.OTHER, name = "新增${classDescription}，ID：{}", params = ${r'{"#'}${create.keys[0].name}${r'"}'})
    @Transactional
    @Override
    public ${create.keys[0].type} create(Create${className}Vo vo) {

        ${className} data = new ${className}();
        <#if create.appointId>
        data.set${create.keys[0].nameProperty}(${create.idCode});
        </#if>
        <#list create.columns as column>
            <#if column.required>
                <#if column.fixEnum>
        data.set${column.nameProperty}(EnumUtil.getByCode(${column.type}.class, vo.get${column.nameProperty}()));
                <#else>
        data.set${column.nameProperty}(vo.get${column.nameProperty}());
                </#if>
            <#else>
                <#if column.type == 'String'>
        if (!StringUtil.isBlank(vo.get${column.nameProperty}())) {
            data.set${column.nameProperty}(vo.get${column.nameProperty}());
        }
                <#else>
        if (vo.get${column.nameProperty}() != null) {
                    <#if column.fixEnum>
            data.set${column.nameProperty}(EnumUtil.getByCode(${column.type}.class, vo.get${column.nameProperty}()));
                    <#else>
            data.set${column.nameProperty}(vo.get${column.nameProperty}());
                    </#if>
        }
                </#if>
            </#if>
        </#list>

        getBaseMapper().insert(data);

        OpLogUtil.setVariable("${create.keys[0].name}", <#if create.keys[0].type == 'String'>data.get${create.keys[0].nameProperty}()<#else>String.valueOf(data.get${create.keys[0].nameProperty}())</#if>);
        OpLogUtil.setExtra(vo);

        return data.get${create.keys[0].nameProperty}();
    }
</#if>
<#if update??>

    @OpLog(type = OpLogType.OTHER, name = "修改${classDescription}，ID：{}", params = ${r'{"#'}${update.keys[0].name}${r'"}'})
    @Transactional
    @Override
    public void update(Update${className}Vo vo) {

        ${className} data = getBaseMapper().selectById(vo.get${update.keys[0].nameProperty}());
        if (ObjectUtil.isNull(data)) {
            throw new DefaultClientException("${classDescription}不存在！");
        }

        LambdaUpdateWrapper${r"<"}${className}${r">"} updateWrapper = Wrappers.lambdaUpdate(${className}.class)
    <#list update.columns as column>
        <#if column.required>
            <#if column.fixEnum>
                .set(${className}::get${column.nameProperty}, EnumUtil.getByCode(${column.type}.class, vo.get${column.nameProperty}()))
            <#else>
                .set(${className}::get${column.nameProperty}, vo.get${column.nameProperty}())
            </#if>
        <#else>
            <#if column.type == 'String'>
                .set(${className}::get${column.nameProperty}, StringUtil.isBlank(vo.get${column.nameProperty}()) ? null : vo.get${column.nameProperty}())
            <#else>
                <#if column.fixEnum>
                .set(${className}::get${column.nameProperty}, vo.get${column.nameProperty}() == null ? null : EnumUtil.getByCode(${column.type}.class, vo.get${column.nameProperty}()))
                <#else>
                .set(${className}::get${column.nameProperty}, vo.get${column.nameProperty}() == null ? null : vo.get${column.nameProperty}())
                </#if>
            </#if>
        </#if>
    </#list>
                .eq(${className}::get${update.keys[0].nameProperty}, vo.get${update.keys[0].nameProperty}());

        getBaseMapper().update(updateWrapper);

        OpLogUtil.setVariable("${update.keys[0].name}", <#if update.keys[0].type == 'String'>data.get${update.keys[0].nameProperty}()<#else>String.valueOf(data.get${update.keys[0].nameProperty}())</#if>);
        OpLogUtil.setExtra(vo);
    }
</#if>
    <#if hasDelete>

    @OpLog(type = OpLogType.OTHER, name = "删除${classDescription}，ID：{}", params = ${r'{"#'}${keys[0].name}${r'"}'})
    @Transactional
    @Override
    public void deleteById(<#list keys as key>${key.type} ${key.name}<#if key_index != keys?size - 1>, </#if></#list>) {

        getBaseMapper().deleteById(<#list keys as key>${key.name}<#if key_index != keys?size - 1>, </#if></#list>);
    }
    </#if>

    <#if isCache>
    @CacheEvict(value = ${className}.CACHE_NAME, key = "#key")
    </#if>
    @Override
    public void cleanCacheByKey(Serializable key) {

    }
}
