package com.longbridge.greendemo.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.util.Date;

/**
 * POJO model for codes.
 * @apiNote Model for codes
 * @author idisimagha dublin-green
 */
@Entity
@Table(name = "codes")
@EntityListeners(AuditingEntityListener.class)
public class Code {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(required = false, hidden = true)
	private long id;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "code", nullable = false)
	private Long code;

	@Column(name = "status", nullable = false)
	@ApiModelProperty(required = false, hidden = true)
	private boolean status = false;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(required = false, hidden = true)
	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	@Column(name = "created_by", nullable = false)
	@CreatedBy
	@ApiModelProperty(required = false, hidden = true)
	private String createdBy;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(required = false, hidden = true)
	@Column(name = "updated_at", nullable = false)
	private Date updatedAt;

	@Column(name = "updated_by", nullable = false)
	@LastModifiedBy
	@ApiModelProperty(required = false, hidden = true)
	private String updatedBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public long getCode() {
		return code;
	}
	
	public void setCode(long code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
