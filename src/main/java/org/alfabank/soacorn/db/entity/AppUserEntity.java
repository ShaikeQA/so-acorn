package org.alfabank.soacorn.db.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.alfabank.soacorn.db.entity.dataTypes.Role;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "app_users")
public class AppUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_users_id_gen")
    @SequenceGenerator(name = "app_users_id_gen", sequenceName = "app_users_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ColumnDefault("true")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @ColumnDefault("now()")
    @Column(name = "create_timestamp", nullable = false)
    private OffsetDateTime createTimestamp;

    @ColumnDefault("now()")
    @Column(name = "change_timestamp", nullable = false)
    private OffsetDateTime changeTimestamp;

    @Column(name = "login", nullable = false, length = 20)
    private String login;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "display_name", nullable = false, length = 40)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "app_users_role_enum not null", nullable = false)
    private Role role;

}

