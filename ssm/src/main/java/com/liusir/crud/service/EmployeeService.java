package com.liusir.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liusir.crud.bean.Employee;
import com.liusir.crud.bean.EmployeeExample;
import com.liusir.crud.bean.EmployeeExample.Criteria;
import com.liusir.crud.dao.EmployeeMapper;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeMapper employeeMapper;

	
	
	
	
	/**
	 * 查询所有员工
	 * @return
	 */
	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		return employeeMapper.selectByExampleWithDept(null);
	}

	public void saveEmp(Employee employee) {

		employeeMapper.insertSelective(employee);
		
	}

	
	public boolean checkUser(String empName) {

		EmployeeExample example= new EmployeeExample();
		
		Criteria criteria = example.createCriteria();
		
		criteria.andEmpNameEqualTo(empName);
		
		long count= employeeMapper.countByExample(example);
		
		return count==0;
	}

	public Employee getEmp(Integer id) {

		Employee employee = employeeMapper.selectByPrimaryKey(id);
		
		return employee;
	}

	public void updateEmp(Employee employee) {
		//修改数据
		employeeMapper.updateByPrimaryKeySelective(employee);
		
	}

	public void deleteEmp(Integer id)
	{
		employeeMapper.deleteByPrimaryKey(id);
	}

	public void deletEmps(List<Integer> lis) {
		
		
		EmployeeExample example=new EmployeeExample();
		
		Criteria criteria = example.createCriteria();
		
		criteria.andEmpIdIn(lis);
		
		employeeMapper.deleteByExample(example);
		
		
		
	}
	
}
