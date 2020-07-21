package com.xiaoshu.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "p_company")
public class Company implements Serializable {
    @Id
    private Integer cid;

    @Column(name = "company_name")
    private String companyName;

    private static final long serialVersionUID = 1L;

    /**
     * @return company_id
     */
   

    /**
     * @param companyId
     */
  

    /**
     * @return company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Company [cid=" + cid + ", companyName=" + companyName + "]";
	}

    
}