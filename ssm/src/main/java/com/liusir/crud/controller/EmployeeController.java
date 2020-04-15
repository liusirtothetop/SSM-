package com.liusir.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liusir.crud.bean.Employee;
import com.liusir.crud.bean.Msg;
import com.liusir.crud.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	// ɾ������

	@ResponseBody
	@RequestMapping(value = "/emp/{ids}", method = RequestMethod.DELETE)
	public Msg deleteEmp(@PathVariable("ids") String ids) {

		List<Integer> lis = new ArrayList<Integer>();
		if (ids.contains("-")) {
			
			String[] split = ids.split("-");
			for (String sp : split) {
				int id = Integer.parseInt(sp);
				lis.add(id);
			}
			employeeService.deletEmps(lis);

		} else {

			int id = Integer.parseInt(ids);

			employeeService.deleteEmp(id);

		}
		return Msg.success();
	}

	// �����޸ĵ�����
	@ResponseBody
	@RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
	public Msg updateEmp(Employee employee) {
		employeeService.updateEmp(employee);
		return Msg.success();
	}

	// �õ�����Ա������

	@ResponseBody
	@RequestMapping(value = "/emp{id}", method = RequestMethod.GET)
	public Msg getEmp(@PathVariable Integer id) {

		Employee employee = employeeService.getEmp(id);

		return Msg.success().add("emp", employee);
	}

	@ResponseBody
	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	public Msg saveEmp(@Valid Employee employee, BindingResult result) {

		if (result.hasErrors()) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError error : errors) {
				map.put(error.getField(), error.getDefaultMessage());
			}
			return Msg.fail().add("error", map);
		} else {
			employeeService.saveEmp(employee);

			return Msg.success();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/checkuser")
	public Msg checkuser(@RequestParam("empName") String empName) {

		// ���ж��û����Ƿ��ǺϷ��ı��ʽ;
		String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";

		if (!empName.matches(regx)) {
			return Msg.fail().add("va_msg", "�û���������6-16λ���ֺ���ĸ����ϻ���2-5λ����");
		}
		boolean b = employeeService.checkUser(empName);
		if (b) {
			return Msg.success();
		} else {
			return Msg.fail().add("va_msg", "�û���������");
		}
	}

	/**
	 * ����jackson����
	 * 
	 * @param pn
	 * @return
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		// �ⲻ��һ����ҳ��ѯ
		// ����PageHelper��ҳ���
		// �ڲ�ѯ֮ǰֻ��Ҫ���ã�����ҳ�룬�Լ�ÿҳ�Ĵ�С
		PageHelper.startPage(pn, 5);
		// startPage��������������ѯ����һ����ҳ��ѯ
		List<Employee> emps = employeeService.getAll();
		// ʹ��pageInfo��װ��ѯ��Ľ����ֻ��Ҫ��pageInfo����ҳ������ˡ�
		// ��װ����ϸ�ķ�ҳ��Ϣ,���������ǲ�ѯ���������ݣ�����������ʾ��ҳ��
		PageInfo page = new PageInfo(emps, 5);
		return Msg.success().add("pageInfo", page);
	}

	/**
	 * ��ѯԱ�����ݣ���ҳ��ѯ��
	 * 
	 * @return
	 */
	// @RequestMapping("/emps")
	public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
		// �ⲻ��һ����ҳ��ѯ��
		// ����PageHelper��ҳ���
		// �ڲ�ѯ֮ǰֻ��Ҫ���ã�����ҳ�룬�Լ�ÿҳ�Ĵ�С
		PageHelper.startPage(pn, 5);
		// startPage��������������ѯ����һ����ҳ��ѯ
		List<Employee> emps = employeeService.getAll();

		// ʹ��pageInfo��װ��ѯ��Ľ����ֻ��Ҫ��pageInfo����ҳ������ˡ�
		// ��װ����ϸ�ķ�ҳ��Ϣ,���������ǲ�ѯ���������ݣ�����������ʾ��ҳ��
		PageInfo page = new PageInfo(emps, 5);

		model.addAttribute("pageInfo", page);

		return "list";
	}

}
