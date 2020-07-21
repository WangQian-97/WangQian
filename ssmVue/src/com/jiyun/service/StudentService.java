package com.jiyun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.jiyun.bean.Banji;
import com.jiyun.bean.Student;
import com.jiyun.mapper.StudentMapper;

@Service
public class StudentService {
		
		@Autowired
		private StudentMapper studentMapper;

		public List<Student> findAll(Student stu) {
			return studentMapper.findAll(stu);
		}

		public List<Banji> findBanji() {
			return studentMapper.findBanji();
		}

		public int add(Student stu) {
			int i = studentMapper.add(stu);
			return i;
		}

		public int update(Student stu) {
			int i = studentMapper.update(stu);
			return 0;
		}

		public void delAll(Integer[] shanchu) {
			studentMapper.delAll(shanchu);
		}
		
}
