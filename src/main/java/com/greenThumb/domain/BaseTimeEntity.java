package com.greenThumb.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @CreatedDate
    private String created;

    @LastModifiedDate
    private String modified;

    @PrePersist
        //해당 엔티티 저장하기 전
    void onPrePersist(){
        this.created = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        this.modified = created;
    }

    @PreUpdate
        //해당 엔티티 수정 하기 전
    void onPreUpdate(){
        this.modified = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
