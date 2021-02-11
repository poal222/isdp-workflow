package org.idsp.smartengine.dao;

import org.hswebframework.ezorm.rdb.executor.SqlRequests;
import org.hswebframework.ezorm.rdb.executor.SyncSqlExecutor;
import org.idsp.smartengine.entity.VariableInstanceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VariableInstanceDAO {

    private ZoneId zoneId = ZoneId.systemDefault();



    @Autowired
    private SyncSqlExecutor defaultJdbcExecutor;

    private String getSelectSql="select id, gmt_create, gmt_modified,process_instance_id,execution_instance_id,field_key,field_type,field_long_value,field_double_value,field_string_value from se_variable_instance";


    public VariableInstanceEntity findOne(@Param("id") Long id) {

        List<VariableInstanceEntity> list = new ArrayList<>();
        defaultJdbcExecutor.select(new StringBuilder(getSelectSql).append("  where id=?  ").toString(), id)
                .forEach(stringObjectMap -> {
                    VariableInstanceEntity variableInstanceEntity = new VariableInstanceEntity();

                    LocalDate localDate = (LocalDate) stringObjectMap.get("gmt_create");

                    ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
                    variableInstanceEntity.setGmtCreate(Date.from(zdt.toInstant()));

                    LocalDate localDate1 = (LocalDate) stringObjectMap.get("gmt_modified");

                    ZonedDateTime zdt1 = localDate1.atStartOfDay(zoneId);
                    variableInstanceEntity.setGmtCreate(Date.from(zdt1.toInstant()));


                    variableInstanceEntity.setId((Long) stringObjectMap.get("id"));

                    variableInstanceEntity.setProcessInstanceId((Long) stringObjectMap.get("process_instance_id"));

                    variableInstanceEntity.setExecutionInstanceId((Long) stringObjectMap.get("execution_instance_id"));
                    variableInstanceEntity.setFieldKey((String) stringObjectMap.get("field_key"));

                    variableInstanceEntity.setFieldType((String) stringObjectMap.get("field_type"));
                    variableInstanceEntity.setFieldLongValue((Long) stringObjectMap.get("field_long_value"));
                    variableInstanceEntity.setFieldDoubleValue((Double) stringObjectMap.get("field_double_value"));
                    variableInstanceEntity.setFieldStringValue((String) stringObjectMap.get("field_string_value"));

                    list.add(variableInstanceEntity);

                });
        return list.get(0);
    }

    public List<VariableInstanceEntity> findList(@Param("processInstanceId") Long processInstanceId, @Param("executionInstanceId") Long executionInstanceId){

        List<VariableInstanceEntity> list=new ArrayList<>();
        StringBuilder finlistSql =new StringBuilder(getSelectSql);
        finlistSql.append(" where 1=1 ");
        if(processInstanceId !=null ){
            finlistSql.append(" and process_instance_id="+processInstanceId);
        }
        if(executionInstanceId !=null ){
            finlistSql.append(" and execution_instance_id="+executionInstanceId);
        }
        defaultJdbcExecutor.select(finlistSql.toString())
                .forEach(stringObjectMap -> {
                    VariableInstanceEntity variableInstanceEntity= new VariableInstanceEntity();

                    LocalDate localDate =  (LocalDate)stringObjectMap.get("gmt_create");

                    ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
                    variableInstanceEntity.setGmtCreate(Date.from(zdt.toInstant()));

                    LocalDate localDate1 =  (LocalDate)stringObjectMap.get("gmt_modified");

                    ZonedDateTime zdt1 = localDate1.atStartOfDay(zoneId);
                    variableInstanceEntity.setGmtCreate(Date.from(zdt1.toInstant()));


                    variableInstanceEntity.setId((Long) stringObjectMap.get("id"));

                    variableInstanceEntity.setProcessInstanceId((Long) stringObjectMap.get("process_instance_id"));

                    variableInstanceEntity.setExecutionInstanceId((Long) stringObjectMap.get("execution_instance_id"));
                    variableInstanceEntity.setFieldKey((String) stringObjectMap.get("field_key"));

                    variableInstanceEntity.setFieldType((String) stringObjectMap.get("field_type"));
                    variableInstanceEntity.setFieldLongValue((Long) stringObjectMap.get("field_long_value"));
                    variableInstanceEntity.setFieldDoubleValue((Double) stringObjectMap.get("field_double_value"));
                    variableInstanceEntity.setFieldStringValue((String) stringObjectMap.get("field_string_value"));

                    list.add(variableInstanceEntity);

                });
        return list;
    }


    public void insert(  VariableInstanceEntity variableInstanceEntity ){
        String insertsql ="insert into se_variable_instance (id, gmt_create, gmt_modified, " +
                "process_instance_id,execution_instance_id,field_key,field_type,field_long_value,field_double_value,field_string_value)"
                +"values (?, now(), now(), ?::bigint,?::bigint,?,?, ?::bigint,?::numeric,?)";
        defaultJdbcExecutor.update(SqlRequests.of(insertsql,
                variableInstanceEntity.getId(),
                variableInstanceEntity.getProcessInstanceId(),
                variableInstanceEntity.getExecutionInstanceId(),
                variableInstanceEntity.getFieldKey(),
                variableInstanceEntity.getFieldType(),
                variableInstanceEntity.getFieldLongValue(),
                variableInstanceEntity.getFieldDoubleValue(),
                variableInstanceEntity.getFieldStringValue())

        );
    }

    public int update(VariableInstanceEntity variableInstanceEntity){
        StringBuilder insertsql =new StringBuilder("update se_variable_instance set " +
                "gmt_modified=now() ");
        if(variableInstanceEntity.getFieldLongValue() !=null ){
            insertsql.append(" ,field_long_value="+variableInstanceEntity.getFieldLongValue());
        }
        if(variableInstanceEntity.getFieldDoubleValue() !=null ){
            insertsql.append(" ,field_double_value="+variableInstanceEntity.getFieldDoubleValue()+"");
        }
        if(variableInstanceEntity.getFieldStringValue() !=null ){
            insertsql.append(" ,field_string_value='"+variableInstanceEntity.getFieldStringValue()+"'");
        }


        insertsql.append(" where id=? ");
        return defaultJdbcExecutor.update(SqlRequests.of(insertsql.toString(),variableInstanceEntity.getId()));
    }

    public void delete(@Param("id") Long id){
        defaultJdbcExecutor.execute(SqlRequests.of(" delete from se_variable_instance where id=? ",id));
    }
}
//
//    <?xml version="1.0" encoding="UTF-8" ?>
//<!DOCTYPE mapper
//        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
//<mapper namespace="com.alibaba.smart.framework.engine.persister.database.dao.VariableInstanceDAO">
//
//<sql id="baseColumn">
//        id, gmt_create, gmt_modified,process_instance_id,
//        execution_instance_id,field_key,field_type,field_long_value,field_double_value,field_string_value
//</sql>
//
//<insert id="insert" parameterType="com.alibaba.smart.framework.engine.persister.database.entity.VariableInstanceEntity"   keyProperty="id">
//        insert into se_variable_instance (<include refid="baseColumn"/>)
//        values (#{id}, now(), now(), #{processInstanceId}, #{executionInstanceId},
//        #{fieldKey},#{fieldType},#{fieldLongValue},#{fieldDoubleValue},#{fieldStringValue} )
//</insert>
//
//<delete id="delete" parameterType="long">
//        delete from se_variable_instance where id=#{id}
//</delete>
//
//<update id="update" parameterType="com.alibaba.smart.framework.engine.persister.database.entity.VariableInstanceEntity">
//        update se_variable_instance
//<set>
//            gmt_modified=now()
//<if test="fieldLongValue != null">,field_long_value = #{fieldLongValue}</if>
//<if test="fieldDoubleValue != null">,field_double_value = #{fieldDoubleValue}</if>
//<if test="fieldStringValue != null">,field_string_value = #{fieldStringValue}</if>
//</set>
//        where id=#{id}
//</update>
//
//<select id="findOne" resultType="com.alibaba.smart.framework.engine.persister.database.entity.VariableInstanceEntity">
//        select
//<include refid="baseColumn"/>
//        from se_variable_instance where id=#{id}
//</select>
//
//<select id="findList" resultType="com.alibaba.smart.framework.engine.persister.database.entity.VariableInstanceEntity">
//        select
//<include refid="baseColumn"/>
//        from  se_variable_instance
//<include refid="condition"/>
//</select>
//
//<sql id="condition">
//<where>
//<if test="processInstanceId != null">and process_instance_id = #{processInstanceId}</if>
//<if test="executionInstanceId != null">and execution_instance_id = #{executionInstanceId}</if>
//</where>
//</sql>
//
//</mapper>
