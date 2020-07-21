package com.jiyun.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


public class Student {

		private Integer sid;
		private String sname;
		private Integer bid;
		private Date birthday;
		private String pic;
		private String bname;
		private Integer pageNum;
		@DateTimeFormat(pattern="yyyy-MM-dd")
		private Date start;
		@DateTimeFormat(pattern="yyyy-MM-dd")
		private Date end;
		public Integer getSid() {
			return sid;
		}
		public void setSid(Integer sid) {
			this.sid = sid;
		}
		public String getSname() {
			return sname;
		}
		public void setSname(String sname) {
			this.sname = sname;
		}
		public Integer getBid() {
			return bid;
		}
		public void setBid(Integer bid) {
			this.bid = bid;
		}
		public Date getBirthday() {
			return birthday;
		}
		public void setBirthday(Date birthday) {
			this.birthday = birthday;
		}
		public String getPic() {
			return pic;
		}
		public void setPic(String pic) {
			this.pic = pic;
		}
		public String getBname() {
			return bname;
		}
		public void setBname(String bname) {
			this.bname = bname;
		}
		public Integer getPageNum() {
			return pageNum;
		}
		public void setPageNum(Integer pageNum) {
			this.pageNum = pageNum;
		}
		public Date getStart() {
			return start;
		}
		public void setStart(Date start) {
			this.start = start;
		}
		public Date getEnd() {
			return end;
		}
		public void setEnd(Date end) {
			this.end = end;
		}
		@Override
		public String toString() {
			return "Student [sid=" + sid + ", sname=" + sname + ", bid=" + bid + ", birthday=" + birthday + ", pic="
					+ pic + ", bname=" + bname + ", pageNum=" + pageNum + ", start=" + start + ", end=" + end + "]";
		}
		public Student(Integer sid, String sname, Integer bid, Date birthday, String pic, String bname, Integer pageNum,
				Date start, Date end) {
			super();
			this.sid = sid;
			this.sname = sname;
			this.bid = bid;
			this.birthday = birthday;
			this.pic = pic;
			this.bname = bname;
			this.pageNum = pageNum;
			this.start = start;
			this.end = end;
		}
		public Student() {
			super();
			// TODO Auto-generated constructor stub
		}
		

		
		
}
