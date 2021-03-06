
package mobi.victorchandler.view;
import mobi.victorchandler.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
/**
 * Class that extends BaseAdapter
 * @author riveram
 *
 */
public class ImageAdapter extends BaseAdapter {

    int mGalleryItemBackground;

    public ImageAdapter(Context c) {
        mContext = c;
        // See res/values/attrs.xml for the <declare-styleable> that defines
        // Gallery1.
        TypedArray a = c.obtainStyledAttributes(R.styleable.Slider);
        mGalleryItemBackground = a.getResourceId(
                R.styleable.Slider_android_galleryItemBackground, 0);
        a.recycle();
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView i = new ImageView(mContext);

        i.setImageResource(mImageIds[position]);
        i.setScaleType(ImageView.ScaleType.FIT_XY);
        i.setLayoutParams(new Gallery.LayoutParams(200, 120));

        // The preferred Gallery item background
        i.setBackgroundResource(mGalleryItemBackground);

        return i;
    }

    private Context mContext;

    private Integer[] mImageIds = {
            R.drawable.gallery_photo_1,
            R.drawable.gallery_photo_2,
            R.drawable.gallery_photo_3,
            R.drawable.gallery_photo_4
    };

}
