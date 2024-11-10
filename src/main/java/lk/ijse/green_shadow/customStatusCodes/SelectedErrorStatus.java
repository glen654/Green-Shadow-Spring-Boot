package lk.ijse.green_shadow.customStatusCodes;

import lk.ijse.green_shadow.dto.CropStatus;
import lk.ijse.green_shadow.dto.EquipmentStatus;
import lk.ijse.green_shadow.dto.FieldStatus;
import lk.ijse.green_shadow.dto.MonitoringLogStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedErrorStatus implements CropStatus, FieldStatus, EquipmentStatus, MonitoringLogStatus {
    private int statusCode;
    private String statusMessage;
}
