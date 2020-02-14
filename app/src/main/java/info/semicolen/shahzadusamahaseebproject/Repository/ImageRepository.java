package info.semicolen.shahzadusamahaseebproject.Repository;

import android.graphics.Bitmap;

public class ImageRepository {


    Bitmap OriginalCropImage;
    Bitmap EditedCropImage;
    Bitmap OriginalGreyScaleImage;
    Bitmap EditedGreyScaleImage;
    Bitmap EditedCroppedGreyScaleImage;



    Boolean isImageGreyScale = false;
    Boolean isImageCropped = false;


    private static ImageRepository instance;

    public static ImageRepository getInstance() {
        if(instance == null) {
            instance = new ImageRepository();
        }
        return instance;
    }


    public Bitmap getEditedCroppedGreyScaleImage() {
        return EditedCroppedGreyScaleImage;
    }

    public void setEditedCroppedGreyScaleImage(Bitmap editedCroppedGreyScaleImage) {
        EditedCroppedGreyScaleImage = editedCroppedGreyScaleImage;
    }

    public Boolean getImageGreyScale() {
        return isImageGreyScale;
    }

    public void setImageGreyScale(Boolean imageGreyScale) {
        isImageGreyScale = imageGreyScale;
    }

    public Boolean getImageCropped() {
        return isImageCropped;
    }

    public void setImageCropped(Boolean imageCropped) {
        isImageCropped = imageCropped;
    }

    public Bitmap getOriginalCropImage() {
        return OriginalCropImage;
    }

    public void setOriginalCropImage(Bitmap originalCropImage) {
        OriginalCropImage = originalCropImage;
    }

    public Bitmap getEditedCropImage() {
        return EditedCropImage;
    }

    public void setEditedCropImage(Bitmap editedCropImage) {
        EditedCropImage = editedCropImage;
    }

    public Bitmap getOriginalGreyScaleImage() {
        return OriginalGreyScaleImage;
    }

    public void setOriginalGreyScaleImage(Bitmap originalGreyScaleImage) {
        OriginalGreyScaleImage = originalGreyScaleImage;
    }

    public Bitmap getEditedGreyScaleImage() {
        return EditedGreyScaleImage;
    }

    public void setEditedGreyScaleImage(Bitmap editedGreyScaleImage) {
        EditedGreyScaleImage = editedGreyScaleImage;
    }
}
