package lk.ijse.green_shadow.customStatusCodes;

import lk.ijse.green_shadow.dto.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedErrorStatus implements CropStatus, FieldStatus, EquipmentStatus,
             MonitoringLogStatus, VehicleStatus,StaffStatus {
    private int statusCode;
    private String statusMessage;
}
