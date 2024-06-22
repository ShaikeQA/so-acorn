package org.alfabank.soacorn.db.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "service")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_id_gen")
    @SequenceGenerator(name = "service_id_gen", sequenceName = "service_id_seq", allocationSize = 1)
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

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(name = "middle_name", length = 20)
    private String middleName;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @Column(name = "email", length = 256)
    private String email;

}