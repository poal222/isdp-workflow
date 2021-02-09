package org.idsp.smartengine.constant;

public interface IsdpBpmAssigneeTypeConstant {

	/**
	 * 扩展原有的指派类型，原有指派类型只有user和group
	 * 考虑业务场景，指派类型扩展为，用户，员工，组织，角色，岗位
	 */

	String USER = "User";
	String EMPLOYEE = "Employee";
	String ORGANZATION = "Organization";
	String ROLE = "Role";
	String POSITION = "Postion";


}
