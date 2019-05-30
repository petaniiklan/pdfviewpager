
package id.pi.pdfviewpager.library.asset;

import android.content.Context;

import id.pi.pdfviewpager.library.service.CopyAssetService;

public class CopyAssetServiceImpl implements CopyAsset {
    private Context context;

    public CopyAssetServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public void copy(String assetName, String destinationPath) {
        CopyAssetService.startCopyAction(context, assetName, destinationPath);
    }
}
