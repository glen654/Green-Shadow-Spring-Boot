package lk.ijse.green_shadow.util;

import java.util.Base64;
import java.util.UUID;

public class AppUtil {
    public static String generateCropId(){
        return "CROP-" + UUID.randomUUID();
    }

    public static String generateEquipmentId(){

        return "EQUIP-" + UUID.randomUUID();
    }

    public static String generateFieldId(){
        return "FIELD-" + UUID.randomUUID();
    }

    public static String generateLogId(){
        return "LOG-" + UUID.randomUUID();
    }

    public static String generateStaffId(){
        return "STAFF-" + UUID.randomUUID();
    }

    public static String generateUserId(){
        return "USER-" + UUID.randomUUID();
    }

    public static String generateVehicleId(){
        return "VEHICLE-" + UUID.randomUUID();
    }

    public static String cropImageToBase64(byte [] cropImage){
        return Base64.getEncoder().encodeToString(cropImage);
    }

    public static String fieldImageOneToBase64(byte [] fieldImage){
        return Base64.getEncoder().encodeToString(fieldImage);
    }

    public static String observedImageOneToBase64(byte [] observedImage){
        return Base64.getEncoder().encodeToString(observedImage);
    }
}

