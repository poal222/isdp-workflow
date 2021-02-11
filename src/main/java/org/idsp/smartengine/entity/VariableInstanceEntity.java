package org.idsp.smartengine.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hswebframework.ezorm.rdb.mapping.annotation.ColumnType;

import javax.persistence.Column;
import javax.persistence.Table;
import java.sql.JDBCType;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "se_variable_instance")
public class VariableInstanceEntity extends BaseProcessEntity {


    @Column(name="process_instance_id",length = 255, nullable = false)
    @Schema(description = "process_instance_id")
    @ColumnType(javaType = Long.class, jdbcType = JDBCType.BIGINT)
    private Long processInstanceId;

    @Column(name="execution_instance_id",length = 255, nullable = false)
    @Schema(description = "execution_instance_id")
    @ColumnType(javaType = Long.class, jdbcType = JDBCType.BIGINT)
    private Long executionInstanceId;

    @Column(name="field_key",length = 255, nullable = false)
    @Schema(description = "field_key")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String fieldKey;

    @Column(name="field_type",length = 255, nullable = false)
    @Schema(description = "field_type")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String fieldType;

    @Column(name="field_long_value",length = 255, nullable = false)
    @Schema(description = "field_long_value")
    @ColumnType(javaType = Long.class, jdbcType = JDBCType.BIGINT)
    private Long fieldLongValue;

    @Column(name="field_double_value",length = 255, nullable = false)
    @Schema(description = "field_double_value")
    @ColumnType(javaType = Double.class, jdbcType = JDBCType.DOUBLE)
    private Double fieldDoubleValue;

    @Column(name="field_string_value",length = 255, nullable = false)
    @Schema(description = "field_string_value")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String fieldStringValue;

}