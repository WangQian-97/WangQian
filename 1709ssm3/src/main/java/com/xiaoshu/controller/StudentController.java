package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Attachment;
import com.xiaoshu.entity.Banji;
import com.xiaoshu.entity.Log;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("student")
public class StudentController extends LogController{
	static Logger logger = Logger.getLogger(StudentController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private StudentService studentService;
	
	//导入
		@RequestMapping("inStudent")
		public void inStudent(MultipartFile file,HttpServletResponse response){
			JSONObject result=new JSONObject();
			
			try {
				 InputStream is = file.getInputStream();
				 HSSFWorkbook workbook = new HSSFWorkbook(is);
				 HSSFSheet sheet = workbook.getSheetAt(0);
				 int lastRowNum = sheet.getLastRowNum();
				 ArrayList<Student> list = new ArrayList<Student>();
				 for (int i = 1; i <= lastRowNum; i++) {
					HSSFRow row = sheet.getRow(i);
					Student student = new Student();
					/*student.setSid((int)row.getCell(0).getNumericCellValue());*/
					student.setSname(row.getCell(1).getStringCellValue());
					student.setSex(row.getCell(2).getStringCellValue());
					student.setHobby(row.getCell(3).getStringCellValue());
					student.setPic(row.getCell(4).getStringCellValue());
					String bir = row.getCell(5).getStringCellValue();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					//parse方法是转成datel类型 format是转成字符串
					student.setBirthday(dateFormat.parse(bir));
					if("H1909A".equals(row.getCell(6).getNumericCellValue())){
						student.setBid(1);
					}else if ("H1909B".equals(row.getCell(6).getNumericCellValue())) {
						student.setBid(2);
					}else{
						student.setBid(3);
					}
					studentService.addStudent(student);
				}
				 result.put("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("导入信息错误",e);
				result.put("success", true);
				result.put("errorMsg", "对不起，操作失败");
			}
			WriterUtil.write(response, result.toString());
		}
		
	
	
	@RequestMapping("outStudent")
	public void backup(HttpServletRequest request,HttpServletResponse response){
		JSONObject result = new JSONObject();
		try {
			String time = TimeUtil.formatTime(new Date(), "yyyyMMddHHmmss");
		    String excelName = "学生信息"+time;
		    List<Student> list = studentService.findAllStudent();
			String[] handers = {"编号","姓名","性别","爱好","照片","生日","班级"};
			// 1导入硬盘
			ExportExcelToDisk(request,handers,list, excelName);
			 
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("", "对不起，备份失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	
	// 导出到硬盘
	@SuppressWarnings("resource")
	private void ExportExcelToDisk(HttpServletRequest request,
			String[] handers, List<Student> list, String excleName) throws Exception {
		
		try {
			HSSFWorkbook wb = new HSSFWorkbook();//创建工作簿
			HSSFSheet sheet = wb.createSheet("操作记录备份");//第一个sheet
			HSSFRow rowFirst = sheet.createRow(0);//第一个sheet第一行为标题
			rowFirst.setHeight((short) 500);
			for (int i = 0; i < handers.length; i++) {
				sheet.setColumnWidth((short) i, (short) 4000);// 设置列宽
			}
			//写标题了
			for (int i = 0; i < handers.length; i++) {
			    //获取第一行的每一个单元格
			    HSSFCell cell = rowFirst.createCell(i);
			    //往单元格里面写入值
			    cell.setCellValue(handers[i]);
			}
			for (int i = 0;i < list.size(); i++) {
			    //获取list里面存在是数据集对象
			    Student student = list.get(i);
			    //创建数据行
			    HSSFRow row = sheet.createRow(i+1);
			    //设置对应单元格的值
			    row.setHeight((short)400);   // 设置每行的高度
			    //"序号","操作人","IP地址","操作时间","操作模块","操作类型","详情"
			    row.createCell(0).setCellValue(list.get(i).getSid());
				row.createCell(1).setCellValue(list.get(i).getSname());
				row.createCell(2).setCellValue(list.get(i).getSex());
				row.createCell(3).setCellValue(list.get(i).getHobby());
				row.createCell(4).setCellValue(list.get(i).getPic());
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				row.createCell(5).setCellValue(dateFormat.format(list.get(i).getBirthday()));
				row.createCell(6).setCellValue(list.get(i).getBid());
			}
			//写出文件（path为文件路径含文件名）
				OutputStream os;
				File file = new File("d:/"+excleName+".xls");
				
				if (!file.exists()){//若此目录不存在，则创建之  
					file.createNewFile();  
					logger.debug("创建文件夹路径为："+ file.getPath());  
	            } 
				os = new FileOutputStream(file);
				wb.write(os);
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
	}

	



	
	@RequestMapping("studentIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Role> roleList = roleService.findRole(new Role());
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("roleList", roleList);
		List<Banji> blist = studentService.findAllBanji();
		request.setAttribute("blist", blist);
		return "student";
	}
	
	
	@RequestMapping(value="studentList",method=RequestMethod.POST)
	public void studentList(Student student,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			/*User user = new User();
			String username = request.getParameter("username");
			String roleid = request.getParameter("roleid");
			String usertype = request.getParameter("usertype");*/
			String order = request.getParameter("order");
			String ordername = request.getParameter("ordername");
			/*if (StringUtil.isNotEmpty(username)) {
				user.setUsername(username);
			}
			if (StringUtil.isNotEmpty(roleid) && !"0".equals(roleid)) {
				user.setRoleid(Integer.parseInt(roleid));
			}
			if (StringUtil.isNotEmpty(usertype)) {
				user.setUsertype(usertype.getBytes()[0]);
			}*/
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<Student> studentList= studentService.findUserPage(student,pageNum,pageSize,ordername,order);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",studentList.getTotal() );
			jsonObj.put("rows", studentList.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
			throw e;
		}
	}
	
	
	// 新增或修改
	@RequestMapping("reserveStudent")
	public void reserveStudent(MultipartFile picFile,String [] hobbys,HttpServletRequest request,Student student,HttpServletResponse response){
		Integer sid = student.getSid();
		JSONObject result=new JSONObject();
		
		String hobby = "";
		hobby = StringUtils.join(hobbys,",");
		student.setHobby(hobby);
		
		
		String filename =null;
		if(picFile!=null){
			filename = picFile.getOriginalFilename();
		}
		
		try {
			if (sid != null) {   // userId不为空 说明是修改
				Student sname = studentService.existUserWithUserName(student.getSname());
				if((sname != null && sname.getSid().compareTo(sid)==0) ||student!=null){
					if(filename!=null){
						String suffix = filename.substring(filename.lastIndexOf("."));
						String newFileName = System.currentTimeMillis()+suffix;
						File file = new File("f:/photo/"+newFileName);
						picFile.transferTo(file);
						student.setPic("/photo/"+newFileName);
				}
					student.setSid(sid);
					studentService.updateStudent(student);
					result.put("success", true);
				} 
				
			}else {   // 添加
				if(studentService.existUserWithUserName(student.getSname())==null){  // 没有重复可以添加
					if(filename!=null){
							String suffix = filename.substring(filename.lastIndexOf("."));
							String newFileName = System.currentTimeMillis()+suffix;
							File file = new File("f:/photo/"+newFileName);
							picFile.transferTo(file);
							student.setPic("/photo/"+newFileName);
					}
					System.out.println(student);
					studentService.addStudent(student);
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	@RequestMapping("deleteStudent")
	public void deleteStudent(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				studentService.deleteStudent(Integer.parseInt(id));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	@RequestMapping("editPassword")
	public void editPassword(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		String oldpassword = request.getParameter("oldpassword");
		String newpassword = request.getParameter("newpassword");
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("currentUser");
		if(currentUser.getPassword().equals(oldpassword)){
			User user = new User();
			user.setUserid(currentUser.getUserid());
			user.setPassword(newpassword);
			try {
				userService.updateUser(user);
				currentUser.setPassword(newpassword);
				session.removeAttribute("currentUser"); 
				session.setAttribute("currentUser", currentUser);
				result.put("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("修改密码错误",e);
				result.put("errorMsg", "对不起，修改密码失败");
			}
		}else{
			logger.error(currentUser.getUsername()+"修改密码时原密码输入错误！");
			result.put("errorMsg", "对不起，原密码输入错误！");
		}
		WriterUtil.write(response, result.toString());
	}
}
