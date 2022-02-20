<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.mappers.${className}Mapper">

    <resultMap id="${className}Dto" type="${packageName}.dto.${moduleName}.${bizName}.${className}Dto">
        <#list entity.columns as column>
            <#if column.isKey>
        <id column="${column.columnName}" property="${column.name}"/>
            <#else>
        <result column="${column.columnName}" property="${column.name}"/>
            </#if>
        </#list>
    </resultMap>

    <sql id="${className}Dto_sql">
        SELECT
        <#list entity.columns as column>
            tb.${column.columnName}<#if column_index != entity.columns?size - 1>,</#if>
        </#list>
        FROM ${entity.tableName} AS tb
    </sql>

    <#if queryParams??>
    <select id="query" resultMap="${className}Dto">
        <include refid="${className}Dto_sql"/>
        <where>
            <#list queryParams.columns as column>
                <#if column.viewType == 6>
            <if test="vo.${column.name}Start != null">
            AND tb.${column.columnName} >= ${r"#{"}vo.${column.name}${r"Start}"}
            </if>
            <if test="vo.${column.name}End != null">
            <![CDATA[
            AND tb.${column.columnName} <= ${r"#{"}vo.${column.name}${r"End}"}
            ]]>
            </if>
                <#else>
            <if test="vo.${column.name} != null<#if column.type == 'String'> and vo.${column.name} != ''</#if>">
            <@format><#include "query-type-sql.ftl"/></@format>
            </if>
                </#if>
            </#list>
        </where>
        <#if orderColumns?? && (orderColumns?size > 0) >
        ORDER BY <#list orderColumns as orderColumn>tb.${orderColumn.columnName} ${orderColumn.orderType}<#if orderColumn_index != orderColumns?size - 1>, </#if></#list>
        </#if>
    </select>
    </#if>

    <select id="getById" resultMap="${className}Dto">
        <include refid="${className}Dto_sql"/>
        <where>
            <#list keys as key>
            AND tb.${key.columnName} = ${r"#{"}${key.name}${r"}"}
            </#list>
        </where>
    </select>
</mapper>
