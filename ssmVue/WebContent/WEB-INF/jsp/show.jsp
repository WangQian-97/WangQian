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
<style type="text/css">
		
		.aaa{
			display:black;
		}
		
		.bbb{
			display: none;
		}

</style>
<center>	
			
				
				<div id = "did">
				<a href="${pageContext.request.contextPath }/stu/toAdd.do">添加</a>
				姓名：<input name="sname" v-model="student.sname"> 
				班级：<select v-model="student.bid">
						<option value="0">--请选择--</option>
				  		<option v-for="banji in blist" :value="banji.bid" v-text="banji.bname"></option>
				  	</select>
				生日：<input type="date" v-model="student.start">-<input type="date" v-model="student.end">
				<input type="button" @click="sousuo()" value="搜索">
				<table border="1" id="tid">
						<tr>
							<td>选择</td>
							<td>学生ID</td>
							<td>学生姓名</td>
							<td>学生班级</td>
							<td>学生生日</td>
							<td>学生照片</td>
							<td>操作</td>
						</tr>
						<tr v-for="(stu,index) in slist">
							<td><input type="checkbox" v-model="shanchu" :value="stu.sid"></td>
							<td v-text="stu.sid"></td>
							<td v-text="stu.sname"></td>
							<td v-text="stu.bname"></td>
							<td v-text="format(stu.birthday)"></td>
							<td v-text=""></td>
							<td><input type="button" @click="toUpdate(index)" value="修改"></td>
						</tr>
				</table>
				<input type="button" @click="del()" value="删除">
				
				<form id ="fid" action="" :class="yincang">
				  名字：<input type="text" v-model="student.sname"><br>
				  班级：<select v-model="student.bid">
				  		<option v-for="banji in blist" :value="banji.bid" v-text="banji.bname"></option>
				  	</select><br>
				  生日：<input type="date"  v-model="student.birthday"><br>
				  照片：<input type="file"><br>
					<input type="button" @click="update()" value="修改">
				</form>
				总页数：{{pageinfo.pages}} &nbsp;&nbsp;&nbsp; 当前页:{{pageinfo.pageNum}}
				<input type="button" @click="jump(pageinfo.firstPage)" value="首页"> 
				<input type="button" @click="jump(pageinfo.prePage)" value="上一页"> 
				<input type="button" @click="jump(pageinfo.nextPage)" value="下一页"> 
				<input type="button" @click="jump(pageinfo.lastPage)" value="尾页"> 
				</div>
</center>
</body>
<script type="text/javascript">
	
		var table = new Vue({
			el:"#did",
			data:{
				blist:[],
				slist:[],
				student:{
					bid:0
				},
				shanchu:[],
				yincang:"bbb",
				pageinfo:{}
			},
			created(){
				axios.post("${pageContext.request.contextPath}/stu/findAll.do",this.student).then(function(res){
					table.slist = res.data.list;
					table.pageinfo = res.data;
				}),
				axios.post("${pageContext.request.contextPath}/stu/findBanji.do").then(function(res){
					table.blist = res.data;
				})
			},
			methods:{
				format(datetime){
					var year = new Date(datetime).getFullYear();
					var month1= new Date(datetime).getMonth()+1;/* +1之后不能比较 ,当成一个整体比较*/
					var month = month1<10?"0"+month1:month1;
					var date = new Date(datetime).getDate()<10?"0"+new Date(datetime).getDate():new Date(datetime).getDate();
					return  year+"-"+month+"-"+date
				},
				toUpdate(i){
					table.yincang="aaa";
					table.student = table.slist[i];
					table.student.birthday=table.format(table.student.birthday);
				},
				update(i){
					axios.post("${pageContext.request.contextPath}/stu/update.do",table.student).then(function(res){
						location.href="${pageContext.request.contextPath}/stu/toShow.do";
					})
				},
				del(){
					axios.post("${pageContext.request.contextPath}/stu/delAll.do",table.shanchu).then(function(res){
						location.href="${pageContext.request.contextPath}/stu/toShow.do";
					})
				},
				jump(pageNum){
					this.student.pageNum=pageNum;
					if(pageNum>=1 && pageNum<=table.pageinfo.pages){
						axios.post("${pageContext.request.contextPath}/stu/findAll.do",this.student).then(function(res){
							table.slist = res.data.list;
							table.pageinfo = res.data;
							
						})
					}
					
				}, 
				sousuo(){
					axios.post("${pageContext.request.contextPath}/stu/findAll.do",this.student).then(function(res){
						table.slist = res.data.list;
						table.pageinfo = res.data;
					})
				}
			}
		});



</script>
</html>