package com.jiyun.mapper;

import java.util.List;

import com.jiyun.bean.Banji;
import com.jiyun.bean.Student;

public interface StudentMapper {

	List<Student> findAll(Student stu);

	List<Banji> findBanji();

	int add(Student stu);

	int update(Student stu);

	void delAll(Integer[] shanchu);

}
