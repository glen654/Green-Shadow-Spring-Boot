package lk.ijse.green_shadow.entity.impl;

import jakarta.persistence.*;
import lk.ijse.green_shadow.entity.EquipmentType;
import lk.ijse.green_shadow.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "equipment")
public class EquipmentEntity implements SuperEntity {
    @Id
    private String equipment_id;
    private String name;
    @Enumerated(EnumType.STRING)
    private EquipmentType type;
    private String status;
    @ManyToOne
    @JoinColumn(name = "id")
    private StaffEntity assigned_staff;
    @ManyToOne
    private FieldEntity assigned_field;
}
