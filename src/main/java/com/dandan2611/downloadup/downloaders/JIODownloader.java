package com.dandan2611.downloadup.downloaders;

import com.dandan2611.downloadup.threads.ThreadsManager;

import java.io.*;
import java.net.URL;

/**
 * File downloader using Java IO
 */
public class JIODownloader extends AbstractDownloader {

    private InputStream urlInputStream = null;
    private BufferedInputStream urlBufferedInputStream = null;

    private FileOutputStream outputStream = null;

    // ------------------

    private byte[] data = new byte[1024];

    // ------------------

    /**
     * Download states
     *  0  - paused
     * -1  - stopped (manual stop)
     *  1  - downloading
     */
    protected int state = 0;

    public JIODownloader(String url, File outputFile, boolean async) {
        super(url, outputFile, async);
    }

    @Override
    public boolean isDownloading() {
        return this.state == 1;
    }

    @Override
    public void prepare() throws IOException {

        // Converting string url into URL object
        final URL convertedUrl = new URL(getDownloadUrl());

        // Opening InputStream from url
        this.urlInputStream = convertedUrl.openStream();

        // Creating BufferedInputStream from url InputStream
        this.urlBufferedInputStream = new BufferedInputStream(this.urlInputStream);

        // Creating FileOutputStream
        this.outputStream = new FileOutputStream(getOutputFile());

        getHook().onDownloadPrepared(this);

    }

    @Override
    public void dispose() throws IOException {

        if(isDownloading())
            stopDownload();

        // Closing input streams
        this.urlBufferedInputStream.close();
        this.urlInputStream.close();

        // Cleaning memory
        this.urlBufferedInputStream = null;
        this.urlInputStream = null;

        getHook().onDownloadDisposed(this);

    }

    @Override
    public void download() throws IOException {

        this.state = 1;

        final Runnable runnable = new JIODownloadRunnable(this);

        if(isAsync())
            ThreadsManager.createThread(this, runnable).start();
        else
            runnable.run();

    }

    @Override
    public void setDownloading(boolean paused) {
        this.state = paused ? 0 : 1;
        if(state == 0) getHook().onDownloadPaused(this);
        else getHook().onDownloadResumed(this);
    }

    @Override
    public void stopDownload() {
        this.state = -1;
        getHook().onDownloadStopped(this);
    }

    protected boolean isCancelled() {
        return this.state == -1;
    }

    private class JIODownloadRunnable implements Runnable {

        private final JIODownloader downloader;

        public JIODownloadRunnable(JIODownloader downloader) {
            this.downloader = downloader;
        }

        @Override
        public void run() {

            downloader.getHook().onDownloadStarted(downloader);

            try {

                int byteContent;

                // Pause & Resume downloading loop
                while(true) {

                    // Downloading loop
                    while(downloader.isDownloading()) {

                        byteContent = downloader.urlBufferedInputStream.read(downloader.data, 0, downloader.data.length);

                        if(byteContent == -1) {
                            downloader.stopDownload();
                            downloader.getHook().onDownloadFinished(downloader);
                            downloader.outputStream.close();
                            return;
                        }
                        else {
                            downloader.outputStream.write(downloader.data, 0, byteContent);
                        }

                    }

                    if(downloader.isCancelled()) return;

                    Thread.sleep(250);

                }

            }

            // Error thrown while downloading file
            catch (Exception e) {
                System.err.println("Encountered an error while downloading '" + downloader.getDownloadUrl() + "'");
                e.printStackTrace();
            }

        }

    }

}
