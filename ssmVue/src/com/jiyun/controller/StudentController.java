package com.jiyun.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiyun.bean.Banji;
import com.jiyun.bean.Student;
import com.jiyun.service.StudentService;

@Controller
@RequestMapping("stu")
public class StudentController {
	
		@Autowired
		private StudentService studentService;
		
		@RequestMapping("findAll")
		@ResponseBody
		public PageInfo<Student> findAll(@RequestBody Student stu){
			if(stu.getPageNum()==null){
				PageHelper.startPage(1, 2);
				List<Student> list  = studentService.findAll(stu);
				PageInfo<Student> pageinfo= new PageInfo<>(list);
				return pageinfo;
			}else{
				PageHelper.startPage(stu.getPageNum(), 2);
				List<Student> list  = studentService.findAll(stu);
				PageInfo<Student> pageinfo= new PageInfo<>(list);
				System.out.println(pageinfo.getList());
				return pageinfo;
				
			}
			
		}
		
		@RequestMapping("toShow")
		public String toShow(){
			return "show";
		}
		
		@RequestMapping("toAdd")
		public String toAdd(){
			return "add";
		}
		
		@RequestMapping("findBanji")
		public @ResponseBody List<Banji> findBanji(){
			List<Banji> blist = studentService.findBanji();
			return blist;
		}
		
		@RequestMapping("add")
		@ResponseBody
		public int add(@RequestBody Student stu){
			int i = studentService.add(stu);
			return i;
		}
		
		@RequestMapping("update")
		@ResponseBody
		public int update(@RequestBody Student stu){
			int  i = studentService.update(stu);
			return i;
		}
		
		@RequestMapping("delAll")
		@ResponseBody
		public void update(@RequestBody Integer [] shanchu){
			 studentService.delAll(shanchu);
		}
}
