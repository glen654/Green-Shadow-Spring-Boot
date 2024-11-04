package lk.ijse.green_shadow.entity.impl;

import jakarta.persistence.*;
import lk.ijse.green_shadow.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "monitoring_log")
public class MonitoringLogEntity implements SuperEntity {
    @Id
    private String log_code;
    private String log_date;
    private String log_details;
    @Column(columnDefinition = "LONGTEXT")
    private String observed_image;
    @ManyToMany
    @JoinTable(name = "Log_Field",joinColumns = @JoinColumn(name = "log_code"),
                                  inverseJoinColumns = @JoinColumn(name = "field_code"))
    private List<FieldEntity> fields;
    @OneToMany(mappedBy = "")
    private List<CropEntity> crops;
    private List<StaffEntity> staff;

}
