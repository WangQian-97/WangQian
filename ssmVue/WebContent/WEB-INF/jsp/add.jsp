<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/vue.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/axios-0.18.0.js"></script>
</head>
<body>
		
			<form id ="fid" action="${pageContext.request.contextPath}/stu/add.do" method="post">
				  名字：<input type="text" v-model="student.sname"><br>
				  班级：<select v-model="student.bid">
				  		<option v-for="banji in blist" :value="banji.bid" v-text="banji.bname"></option>
				  	</select><br>
				  生日：<input type="date" v-model="student.birthday"><br>
				  照片：<input type="file" ><br>
					<input type="button" @click="add" value="添加">
			</form>

</body>
<script type="text/javascript">

		var form = new Vue({
			el:"#fid",
			data:{
				blist:[],
				student:{}
			},
			created(){
				axios.post("${pageContext.request.contextPath}/stu/findBanji.do").then(function(res){
					form.blist = res.data;
				})
			},
			methods:{
				add(){
					axios.post("${pageContext.request.contextPath}/stu/add.do",form.student).then(function(res){
						  if(res.data>0){
							 location.href="${pageContext.request.contextPath}/stu/toShow.do"
						 }  
					})
				}
			}
		});



</script>
</html>