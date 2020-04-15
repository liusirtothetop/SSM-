package com.liusir.crud.test;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.liusir.crud.bean.Department;
import com.liusir.crud.bean.Employee;
import com.liusir.crud.bean.EmployeeExample;
import com.liusir.crud.bean.EmployeeExample.Criteria;
import com.liusir.crud.dao.DepartmentMapper;
import com.liusir.crud.dao.EmployeeMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class DaoTest {

		@Autowired
		DepartmentMapper departmentMapper;
		@Autowired
		EmployeeMapper EmployeeMapper;
		
		@Autowired
		SqlSession sqlSession;
	
	@Test
	public void testCRUD() {
		
		
		//departmentMapper.insertSelective(new Department(null,"开发部"));
		//departmentMapper.insertSelective(new Department(null,"测试部"));

			
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		
		for(int i=0;i<1000;i++)
		{
			String uid=UUID.randomUUID().toString().substring(0, 5)+i;
			
			mapper.insert(new Employee(null, uid,"M", uid+"@aliyun.com", 1,null));
			
		}
		System.out.println("添加完成");
		
		
	}
	
	@Test
	public void test() {
		EmployeeExample employee=new EmployeeExample();
		
		Criteria createCriteria = employee.createCriteria();
		createCriteria.andDIdBetween(1, 5);
		
		
		List<Employee> selectByExampleWithDept = EmployeeMapper.selectByExampleWithDept(employee);
		
		System.out.println(selectByExampleWithDept);
	}
	
}
