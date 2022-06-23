package com.etaskify.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public class Parser {

    public LocalDateTime calculateEnDateOfTask(LocalDateTime created, String deadLine) {
        log.info("parse deadLine and calculate end date of task {}", deadLine);
        if (Objects.isNull(deadLine)) {
            return created;
        }
        String[] split = deadLine.split(" ");
        String value = split[0];
        String suffix = split[1].toLowerCase();
        if (suffix.startsWith("m")) {
            return created.plusMonths(Long.parseLong(value));
        } else if (suffix.startsWith("d")) {
            return created.plusDays(Long.parseLong(value));
        } else if (suffix.startsWith("h")) {
            return created.plusHours(Long.parseLong(value));
        } else {
            return created;
        }
    }
}
