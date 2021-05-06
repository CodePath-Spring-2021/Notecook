
package com.example.notecook.Models;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.parceler.Generated;
import org.parceler.IdentityCollection;
import org.parceler.ParcelWrapper;
import org.parceler.ParcelerRuntimeException;

@Generated("org.parceler.ParcelAnnotationProcessor")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class Fav$$Parcelable
    implements Parcelable, ParcelWrapper<com.example.notecook.Models.Fav>
{

    private com.example.notecook.Models.Fav fav$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<Fav$$Parcelable>CREATOR = new Creator<Fav$$Parcelable>() {


        @Override
        public Fav$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new Fav$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public Fav$$Parcelable[] newArray(int size) {
            return new Fav$$Parcelable[size] ;
        }

    }
    ;

    public Fav$$Parcelable(com.example.notecook.Models.Fav fav$$2) {
        fav$$0 = fav$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(fav$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.example.notecook.Models.Fav fav$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(fav$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(fav$$1));
            parcel$$1 .writeString(fav$$1 .instructions);
            parcel$$1 .writeString(fav$$1 .key_id);
            parcel$$1 .writeString(fav$$1 .item_author);
            parcel$$1 .writeString(fav$$1 .image_url);
            parcel$$1 .writeString(fav$$1 .item_type);
            parcel$$1 .writeByteArray(fav$$1 .item_image);
            parcel$$1 .writeString(fav$$1 .item_time);
            parcel$$1 .writeString(fav$$1 .ingredientsList);
            parcel$$1 .writeString(fav$$1 .item_title);
            parcel$$1 .writeString(fav$$1 .fav_status);
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.example.notecook.Models.Fav getParcel() {
        return fav$$0;
    }

    public static com.example.notecook.Models.Fav read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.example.notecook.Models.Fav fav$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            fav$$4 = new com.example.notecook.Models.Fav();
            identityMap$$1 .put(reservation$$0, fav$$4);
            fav$$4 .instructions = parcel$$3 .readString();
            fav$$4 .key_id = parcel$$3 .readString();
            fav$$4 .item_author = parcel$$3 .readString();
            fav$$4 .image_url = parcel$$3 .readString();
            fav$$4 .item_type = parcel$$3 .readString();
            fav$$4 .item_image = parcel$$3 .createByteArray();
            fav$$4 .item_time = parcel$$3 .readString();
            fav$$4 .ingredientsList = parcel$$3 .readString();
            fav$$4 .item_title = parcel$$3 .readString();
            fav$$4 .fav_status = parcel$$3 .readString();
            com.example.notecook.Models.Fav fav$$3 = fav$$4;
            identityMap$$1 .put(identity$$1, fav$$3);
            return fav$$3;
        }
    }

}
