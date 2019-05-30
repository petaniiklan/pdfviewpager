
package id.pi.pdfviewpager.library.adapter;

import android.graphics.Bitmap;

public interface BitmapContainer {
    Bitmap get(int position);

    void remove(int position);

    void clear();
}