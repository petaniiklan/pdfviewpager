
package id.pi.pdfviewpager.library.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import id.pi.pdfviewpager.library.R;
import id.pi.pdfviewpager.library.util.EmptyClickListener;


public class PDFPagerAdapter extends BasePDFPagerAdapter {

    private static final float DEFAULT_SCALE = 1f;

    PdfScale scale = new PdfScale();
    View.OnClickListener pageClickListener = new EmptyClickListener();

    public PDFPagerAdapter(Context context, String pdfPath) {
        super(context, pdfPath);
    }

    @Override
    @SuppressWarnings("NewApi")
    public Object instantiateItem(ViewGroup container, int position) {
        View v = inflater.inflate(R.layout.view_pdf_page, container, false);
        SubsamplingScaleImageView ssiv = v.findViewById(R.id.subsamplingImageView);

        if (renderer == null || getCount() < position) {
            return v;
        }

        PdfRenderer.Page page = getPDFPage(renderer, position);

        Bitmap bitmap = bitmapContainer.get(position);
        ssiv.setImage(ImageSource.bitmap(bitmap));
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        page.close();

        ((ViewPager) container).addView(v, 0);

        return v;
    }

    @Override
    public void close() {
        super.close();
    }

    public static class Builder {
        Context context;
        String pdfPath = "";
        float scale = DEFAULT_SCALE;
        float centerX = 0f, centerY = 0f;
        int offScreenSize = DEFAULT_OFFSCREENSIZE;
        float renderQuality = DEFAULT_QUALITY;
        View.OnClickListener pageClickListener = new EmptyClickListener();

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setScale(float scale) {
            this.scale = scale;
            return this;
        }

        public Builder setScale(PdfScale scale) {
            this.scale = scale.getScale();
            this.centerX = scale.getCenterX();
            this.centerY = scale.getCenterY();
            return this;
        }

        public Builder setCenterX(float centerX) {
            this.centerX = centerX;
            return this;
        }

        public Builder setCenterY(float centerY) {
            this.centerY = centerY;
            return this;
        }

        public Builder setRenderQuality(float renderQuality) {
            this.renderQuality = renderQuality;
            return this;
        }

        public Builder setOffScreenSize(int offScreenSize) {
            this.offScreenSize = offScreenSize;
            return this;
        }

        public Builder setPdfPath(String path) {
            this.pdfPath = path;
            return this;
        }

        public Builder setOnPageClickListener(View.OnClickListener listener) {
            if (listener != null) {
                pageClickListener = listener;
            }
            return this;
        }

        public PDFPagerAdapter create() {
            PDFPagerAdapter adapter = new PDFPagerAdapter(context, pdfPath);
            adapter.scale.setScale(scale);
            adapter.scale.setCenterX(centerX);
            adapter.scale.setCenterY(centerY);
            adapter.offScreenSize = offScreenSize;
            adapter.renderQuality = renderQuality;
            adapter.pageClickListener = pageClickListener;
            return adapter;
        }
    }
}
