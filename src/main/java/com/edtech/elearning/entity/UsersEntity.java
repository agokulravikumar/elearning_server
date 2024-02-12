package com.edtech.elearning.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ep_users")
@EntityListeners(AuditingEntityListener.class)
public class UsersEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status")
    private Integer status;

    @Setter(AccessLevel.NONE)
    @Column(name = "ts", nullable = false, insertable = false, updatable = false)
    private LocalDateTime ts;

    @Setter(AccessLevel.NONE)
    @CreatedDate
    @Column(name = "crt_at", nullable = false, updatable = false)
    private LocalDateTime crtAt;

    @Setter(AccessLevel.NONE)
    @CreatedBy
    @Column(name = "crt_by", nullable = false, updatable = false)
    private String crtBy;

    @Setter(AccessLevel.NONE)
    @LastModifiedDate
    @Column(name = "upd_at", nullable = false)
    private LocalDateTime updAt;

    @Setter(AccessLevel.NONE)
    @LastModifiedBy
    @Column(name = "upd_by", nullable = false)
    private String updBy;

}
