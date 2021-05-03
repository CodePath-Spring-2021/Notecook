
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
public class Recipes$$Parcelable
    implements Parcelable, ParcelWrapper<com.example.notecook.Models.Recipes>
{

    private com.example.notecook.Models.Recipes recipes$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<Recipes$$Parcelable>CREATOR = new Creator<Recipes$$Parcelable>() {


        @Override
        public Recipes$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new Recipes$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public Recipes$$Parcelable[] newArray(int size) {
            return new Recipes$$Parcelable[size] ;
        }

    }
    ;

    public Recipes$$Parcelable(com.example.notecook.Models.Recipes recipes$$2) {
        recipes$$0 = recipes$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(recipes$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.example.notecook.Models.Recipes recipes$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(recipes$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(recipes$$1));
            parcel$$1 .writeString(recipes$$1 .sourceUrl);
            parcel$$1 .writeInt(recipes$$1 .readyInMinutes);
            parcel$$1 .writeString(recipes$$1 .image);
            if (recipes$$1 .instructions == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(recipes$$1 .instructions.size());
                for (java.lang.String string$$0 : recipes$$1 .instructions) {
                    parcel$$1 .writeString(string$$0);
                }
            }
            if (recipes$$1 .ingredientName == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(recipes$$1 .ingredientName.size());
                for (java.lang.String string$$1 : recipes$$1 .ingredientName) {
                    parcel$$1 .writeString(string$$1);
                }
            }
            parcel$$1 .writeString(recipes$$1 .author);
            parcel$$1 .writeString(recipes$$1 .title);
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.example.notecook.Models.Recipes getParcel() {
        return recipes$$0;
    }

    public static com.example.notecook.Models.Recipes read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.example.notecook.Models.Recipes recipes$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            recipes$$4 = new com.example.notecook.Models.Recipes();
            identityMap$$1 .put(reservation$$0, recipes$$4);
            recipes$$4 .sourceUrl = parcel$$3 .readString();
            recipes$$4 .readyInMinutes = parcel$$3 .readInt();
            recipes$$4 .image = parcel$$3 .readString();
            int int$$0 = parcel$$3 .readInt();
            java.util.ArrayList<java.lang.String> list$$0;
            if (int$$0 < 0) {
                list$$0 = null;
            } else {
                list$$0 = new java.util.ArrayList<java.lang.String>(int$$0);
                for (int int$$1 = 0; (int$$1 <int$$0); int$$1 ++) {
                    list$$0 .add(parcel$$3 .readString());
                }
            }
            recipes$$4 .instructions = list$$0;
            int int$$2 = parcel$$3 .readInt();
            java.util.ArrayList<java.lang.String> list$$1;
            if (int$$2 < 0) {
                list$$1 = null;
            } else {
                list$$1 = new java.util.ArrayList<java.lang.String>(int$$2);
                for (int int$$3 = 0; (int$$3 <int$$2); int$$3 ++) {
                    list$$1 .add(parcel$$3 .readString());
                }
            }
            recipes$$4 .ingredientName = list$$1;
            recipes$$4 .author = parcel$$3 .readString();
            recipes$$4 .title = parcel$$3 .readString();
            com.example.notecook.Models.Recipes recipes$$3 = recipes$$4;
            identityMap$$1 .put(identity$$1, recipes$$3);
            return recipes$$3;
        }
    }

}
