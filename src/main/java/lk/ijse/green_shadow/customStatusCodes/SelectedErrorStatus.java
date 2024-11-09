package lk.ijse.green_shadow.customStatusCodes;

import lk.ijse.green_shadow.dto.CropStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedErrorStatus implements CropStatus {
    private int statusCode;
    private String statusMessage;
}
