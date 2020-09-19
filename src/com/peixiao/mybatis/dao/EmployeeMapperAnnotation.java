package com.peixiao.mybatis.dao;

import com.peixiao.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Select;

/**
 * @author Peixiao Zhao
 * @date 2020/9/19 18:10
 */
public interface EmployeeMapperAnnotation {

    @Select("select * from tbl_employee where id = #{id}")
    Employee getEmployee(Integer id);

}
