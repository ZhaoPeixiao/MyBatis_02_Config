package com.peixiao.mybatis.dao;

import com.peixiao.mybatis.bean.Employee;

/**
 * @author Peixiao Zhao
 * @date 2020/9/18 23:25
 */
public interface EmployeeMapper {

    Employee getEmployee(Integer id);

}
