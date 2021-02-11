package org.idsp.smartengine.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hswebframework.ezorm.rdb.mapping.annotation.ColumnType;

import javax.persistence.Column;
import javax.persistence.Table;
import java.sql.JDBCType;

@Data

@Table(name = "se_task_assignee_instance")
public class TaskAssigneeEntity extends BaseProcessEntity {

    @Column(name="process_instance_id",length = 20, nullable = false)
    @Schema(description = "processInstanceId")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.BIGINT)
    private Long processInstanceId;
    
    @Column(name="task_instance_id",length = 20, nullable = false)
    @Schema(description = "taskInstanceId")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.BIGINT)
    private Long taskInstanceId;

    @Column(name="assignee_id",length = 255, nullable = false)
    @Schema(description = "assigneeId")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String assigneeId;

    @Column(name="assignee_type",length = 128, nullable = false)
    @Schema(description = "assigneeType")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String assigneeType;

    //private Integer priority;


}