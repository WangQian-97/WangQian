package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "p_person")
public class Person implements Serializable {
    @Id
    private Integer id;

    @Column(name = "p_name")
    private String pName;

    private String gender;

    @Transient
    private Company company;
    public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Integer cid;
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date jointime;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return p_name
     */
    public String getpName() {
        return pName;
    }

    /**
     * @param pName
     */
    public void setpName(String pName) {
        this.pName = pName == null ? null : pName.trim();
    }

    /**
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    /**
     * @return cid
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * @param cid
     */
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    /**
     * @return jointime
     */
    public Date getJointime() {
        return jointime;
    }

    /**
     * @param jointime
     */
    public void setJointime(Date jointime) {
        this.jointime = jointime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pName=").append(pName);
        sb.append(", gender=").append(gender);
        sb.append(", cid=").append(cid);
        sb.append(", jointime=").append(jointime);
        sb.append("]");
        return sb.toString();
    }
}