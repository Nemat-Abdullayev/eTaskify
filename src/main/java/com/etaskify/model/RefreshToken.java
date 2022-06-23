package com.etaskify.model;
import com.etaskify.model.base.AbstractBaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken extends AbstractBaseEntity {

    private Long userId;
    private String refreshToken;
    @Column(name = "access_token",length = 4000)
    private String accessToken;
    private LocalDateTime expiredDateTime;
    private boolean active;
}
