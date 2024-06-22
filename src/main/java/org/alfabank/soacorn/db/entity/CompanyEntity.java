package org.alfabank.soacorn.db.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "company")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_id_gen")
    @SequenceGenerator(name = "company_id_gen", sequenceName = "company_id_seq", allocationSize = 1)
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

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "deleted_at")
    private Instant deletedAt;

}