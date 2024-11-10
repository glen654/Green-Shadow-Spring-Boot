package lk.ijse.green_shadow.customStatusCodes;

import lk.ijse.green_shadow.dto.CropStatus;
import lk.ijse.green_shadow.dto.EquipmentStatus;
import lk.ijse.green_shadow.dto.FieldStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedErrorStatus implements CropStatus, FieldStatus, EquipmentStatus {
    private int statusCode;
    private String statusMessage;
}
