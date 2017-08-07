package com.newman.moviedatabase.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Stack;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ImageReader;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;


/**
 * Class handles asynchronous lazy loadi
 */
public class ImageManager
{
    private static final String TAG = "ImageManager";
    private HashMap<String, SoftReference<Bitmap>> mImageMap = new HashMap<String, SoftReference<Bitmap>>();
    private File mCacheDirectory;
    private ImageQueue mImageQueue = new ImageQueue();
    private Thread mImageLoaderThread = new Thread(new ImageQueueManager());

    /**
     *
     * @param context
     */
    public ImageManager (Context context)
    {
        //Make background thread lower priority to mitigate UI performance interference
        mImageLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);

        //Find the
        findCacheDirectory(context);
    }

    /**
     *
     *
     * @param context
     */
    private void findCacheDirectory(Context context)
    {
        String sdState = android.os.Environment.getExternalStorageState();
        if (sdState.equals(Environment.MEDIA_MOUNTED))
        {
            File sdDir = android.os.Environment.getExternalStorageDirectory();
            mCacheDirectory = new File(sdDir, "data/newmanmovies");
        }
        else
        {
            mCacheDirectory = context.getCacheDir();
        }

        if (!mCacheDirectory.exists())
        {
            mCacheDirectory.mkdirs();
        }
    }

    /**
     *
     *
     * @param url
     * @param imageView
     * @param defaultDrawableID
     */
    public void displayImage(String url, ImageView imageView, int defaultDrawableID)
    {
        if (mImageMap.containsKey(url))
        {
            imageView.setImageBitmap(mImageMap.get(url).get());
        }
        else
        {
            queueImage(url, imageView, defaultDrawableID);
            imageView.setImageResource(defaultDrawableID);
        }
    }


    /**
     *
     *
     * @param url
     * @param imageView
     * @param defaultDrawableID
     */
    private void queueImage(String url, ImageView imageView, int defaultDrawableID)
    {
        //Clear queue in case it has been used for other images
        mImageQueue.clean(imageView);
        ImageRef p = new ImageRef(url, imageView, defaultDrawableID);

        synchronized (mImageQueue.imageRefs)
        {
            mImageQueue.imageRefs.push(p);
            mImageQueue.imageRefs.notifyAll();
        }

        //Start thread if not started yet
        if (mImageLoaderThread.getState() == Thread.State.NEW)
        {
            mImageLoaderThread.start();
        }
    }

    /**
     * Method to load a bitmap image, either by loading it from local cache or by downloading it over a connection.
     *
     * @param url - The url for the image.
     * @return - The bitmap image at the requested URL.
     */
    private Bitmap getBitmap(String url)
    {
        String fileName = String.valueOf(url.hashCode());

        File bitmapFile = new File(mCacheDirectory, fileName);
        Bitmap bitmap = BitmapFactory.decodeFile(bitmapFile.getPath());

        //Check if bitmap is in cache
        if (bitmap != null)
        {
            return bitmap;
        }

        //Bitmap isn't in cache, it has to be downloaded
        try
        {
            URLConnection openConnection = new URL(url).openConnection();
            bitmap = BitmapFactory.decodeStream(openConnection.getInputStream());
            writeFile(bitmap, bitmapFile);

            return bitmap;
        }
        catch (Exception e)
        {
            Log.e(TAG, "getBitmap: Problem decoding bitmap over network connection.", e);
            return null;
        }
    }

    /**
     * Method to write bitmap data to a file.
     *
     * @param bitmap - The bitmap to write.
     * @param f - The file to write to.
     */
    private void writeFile(Bitmap bitmap, File f)
    {
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, out);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null)
                {
                    out.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


//region Classes

    private class ImageRef
    {
        public String url;
        public ImageView imageView;
        public int defDrawableID;

        public ImageRef(String u, ImageView i, int defaultDrawableID)
        {
            url = u;
            imageView = i;
            defDrawableID = defaultDrawableID;
        }
    }

    private class ImageQueue
    {
        private Stack<ImageRef> imageRefs = new Stack<ImageRef>();

        /**
         * Method to remove all items from the
         * @param view
         */
        public void clean(ImageView view)
        {
            for (int x = 0; x < imageRefs.size();)
            {
                if (imageRefs.get(x).imageView == view)
                {
                    imageRefs.remove(x);
                }
                else
                {
                    ++x;
                }
            }
        }
    }

    private class ImageQueueManager implements Runnable
    {
        @Override
        public void run()
        {
            try {

                while (true)
                {
                    //Thread waits until there are images in the queue to be retrieved
                    if (mImageQueue.imageRefs.size() == 0)
                    {
                        synchronized (mImageQueue.imageRefs)
                        {
                            mImageQueue.imageRefs.wait();
                        }
                    }

                    //There are images to be loaded
                    if (mImageQueue.imageRefs.size() != 0)
                    {
                        ImageRef imageToLoad;

                        synchronized (mImageQueue.imageRefs)
                        {
                            imageToLoad = mImageQueue.imageRefs.pop();
                        }

                        Bitmap bmp = getBitmap(imageToLoad.url);
                        mImageMap.put(imageToLoad.url, new SoftReference<Bitmap>(bmp));
                        Object tag = imageToLoad.imageView.getTag();

                        if (tag != null && ((String)tag).equals(imageToLoad.url))
                        {
                            BitmapDisplayer bmpDisplayer = new BitmapDisplayer(bmp, imageToLoad.imageView, imageToLoad.defDrawableID);
                            Activity a = (Activity)imageToLoad.imageView.getContext();
                            a.runOnUiThread(bmpDisplayer);
                        }
                    }

                    if (Thread.interrupted())
                    {
                        break;
                    }
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Class used to display bitmap images on the UI thread.
     */
    private class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        ImageView imageView;
        int defDrawableID;

        public BitmapDisplayer(Bitmap b, ImageView i, int defaultDrawableID)
        {
            this.bitmap = b;
            this.imageView = i;
            this.defDrawableID = defaultDrawableID;
        }

        @Override
        public void run()
        {
            if (bitmap != null)
            {
                imageView.setImageBitmap(bitmap);
            }
            else
            {
                imageView.setImageResource(defDrawableID);
            }
        }
    }
//endregion
}
