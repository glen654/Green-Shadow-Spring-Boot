package lk.ijse.green_shadow.entity.impl;

import jakarta.persistence.*;
import lk.ijse.green_shadow.entity.Designation;
import lk.ijse.green_shadow.entity.Gender;
import lk.ijse.green_shadow.entity.Role;
import lk.ijse.green_shadow.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "staff")
public class StaffEntity implements SuperEntity {
    @Id
    private String id;
    private String first_name;
    private String last_name;
    @Enumerated(EnumType.STRING)
    private Designation designation;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String joined_date;
    private String dob;
    private String address;
    private String contact_no;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToMany
    @JoinTable(name = "Field_Staff_assignment",joinColumns = @JoinColumn(name = "staff_id"),
                                                             inverseJoinColumns = @JoinColumn(name = "field_code"))
    private List<FieldEntity> fields;
    @OneToMany(mappedBy = "assigned_staff",cascade = CascadeType.ALL)
    private List<VehicleEntity> vehicles;
}