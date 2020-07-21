package com.jiyun.bean;

public class Banji {

		private Integer bid;
		private String bname;
		public Integer getBid() {
			return bid;
		}
		public void setBid(Integer bid) {
			this.bid = bid;
		}
		public String getBname() {
			return bname;
		}
		public void setBname(String bname) {
			this.bname = bname;
		}
		@Override
		public String toString() {
			return "Banji [bid=" + bid + ", bname=" + bname + "]";
		}
		public Banji(Integer bid, String bname) {
			super();
			this.bid = bid;
			this.bname = bname;
		}
		public Banji() {
			super();
			// TODO Auto-generated constructor stub
		}
		
}
