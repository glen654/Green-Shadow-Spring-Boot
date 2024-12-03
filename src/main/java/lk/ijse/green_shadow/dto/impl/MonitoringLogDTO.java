package lk.ijse.green_shadow.dto.impl;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lk.ijse.green_shadow.dto.MonitoringLogStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonitoringLogDTO implements MonitoringLogStatus {
    private String log_code;
    private String log_date;
    private String log_details;
    private String observed_image;
    private List<FieldDTO> fields;
    private List<CropDTO> crops;
    private List<StaffDTO> staff;
}
