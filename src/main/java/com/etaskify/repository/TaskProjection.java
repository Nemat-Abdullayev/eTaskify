package com.etaskify.repository;

import java.time.LocalDateTime;

public interface TaskProjection {

    Long getId();

    String getTitle();

    String getDescription();

    String getStatus();

    LocalDateTime getCreated();

    String getDeadLine();

    LocalDateTime getEndDate();
}
