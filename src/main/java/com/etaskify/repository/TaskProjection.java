package com.etaskify.repository;

import java.time.LocalDateTime;


public interface TaskProjection {

    Long getId();

    String getTitle();

    void setTitle(String title);

    String getDescription();

    String getStatus();

    LocalDateTime getCreated();

    String getDeadLine();

    LocalDateTime getEndDate();
}
