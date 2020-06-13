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
 * The type Transaction.
 *
 * @author idisimagha dublin-green
 */
@Entity
@Table(name = "transactions")
@EntityListeners(AuditingEntityListener.class)
public class Transaction {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(required = false, hidden = true)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "approve", nullable = false)
    private boolean approve = true;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "created_by", nullable = false)
    @CreatedBy
    private String createdBy;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approved_at", nullable = false)
    private Date approvedAt;

    @Column(name = "approved_by", nullable = false)
    @LastModifiedBy
    private String approvedBy;

  /**
   * Gets id.
   *
   * @return the id
   */
  public long getId() {
        return id;
    }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(long id) {
        this.id = id;
    }

  /**
   * Gets name
   *
   * @return the name
   */
  public String getName() {
        return name;
    }

  /**
   * Sets name
   *
   * @param name the transaction name
   */
  public void setName(String name) {
        this.name = name;
    }

  /**
   * Gets approval status
   *
   * @return the approve
   */
  public boolean getApprove() {
        return approve;
    }

  /**
   * Sets approve
   *
   * @param approve
   * Status for approve. 1 for true and 0 for false
   */
  public void setApprove(boolean approve) {
        this.approve = approve;
    }


  /**
   * Gets created at.
   *
   * @return the created at
   */
  public Date getCreatedAt() {
        return createdAt;
    }

  /**
   * Sets created at.
   *
   * @param createdAt the created at
   */
  public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

  /**
   * Gets created by.
   *
   * @return the created by
   */
  public String getCreatedBy() {
        return createdBy;
    }

  /**
   * Sets created by.
   *
   * @param createdBy the created by
   */
  public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

  /**
   * Gets approvedAt.
   *
   * @return the updated at
   */
  public Date getApprovedAt() {
        return approvedAt;
    }

  /**
   * Sets approvedAt .
   *
   * @param approvedAt the updated at
   */
  public void setApprovedAt(Date approvedAt) {
        this.approvedAt = approvedAt;
    }

  /**
   * Gets approved by.
   *
   * @return the approvedBy
   */
  public String getApprovedBy() {
        return approvedBy;
    }

  /**
   * Sets approved by.
   *
   * @param approvedBy
   */
  public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", approve='" + approve + '\'' +
                ", createdAt=" + createdAt +
                ", createdBy='" + createdBy + '\'' +
                ", approvedBy=" + approvedBy +
                ", approvedBy='" + approvedBy + '\'' +
                '}';
    }


}
