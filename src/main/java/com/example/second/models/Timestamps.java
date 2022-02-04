package com.example.second.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@MappedSuperclass
public class Timestamps {

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    // EntityListener before an entity is created
    @PrePersist
    private void onCreate(){
        createTime = updateTime = new Date();
    }

    // EntityListener before an entity is updated
    @PreUpdate
    private void onUpdate(){
        updateTime = new Date();
    }
}
