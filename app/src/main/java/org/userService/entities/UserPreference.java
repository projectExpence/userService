package org.userService.entities;

import jakarta.persistence.*;
import lombok.*;
import org.userService.Enums.*;

@Entity
@Table(name = "user_preference")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userID")
    private UserInfo user;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Occupation occupation;

    private String field;

    @Enumerated(EnumType.STRING)
    private IncomeLevel incomeLevel;

    @Enumerated(EnumType.STRING)
    private Goal goal;

    @Enumerated(EnumType.STRING)
    private RiskTolerance riskTolerance;

    private String hobbies;

}
