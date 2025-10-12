package com.example.financialapp.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;


import java.util.Set;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor//(access = PROTECTED)
@EqualsAndHashCode(of = "id")
public class UserEntity {
    @Id @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String passwordHash;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles;
}
