package com.dandan2611.downloadup.downloaders;

import java.io.File;

public abstract class AbstractDownloader {

    /**
     * Check if the downloader is currently running
     * @return if the downloader is currently downloading
     */
    public abstract boolean isDownloading();

    /**
     * Preparing all tools to download the file
     */
    public abstract void prepare() throws Exception;

    /**
     * Cleaning memory after the download
     */
    public abstract void dispose() throws Exception;

    /**
     * Download the file
     */
    public abstract void download() throws Exception;

    /**
     * Toggle the download process
     */
    public abstract void setDownloading(boolean paused);

    /**
     * Stopping the download process
     */
    public abstract void stopDownload();

    /**
     * Next downloader id
     */
    public static int DOWNLOADER_NEXT_ID = 0;

    /**
     * Downloader ID
     * Used to create threads names
     */
    private final int downloaderId;

    /**
     * URL of the file who will be downloaded
     */
    private final String downloadUrl;

    /**
     * File where the url's file will be stored
     */
    private File outputFile;

    /**
     * Is the downloader in a thread;
     */
    private boolean async;

    /**
     * DownloaderThread
     */
    private Thread downloaderThread;

    /**
     * Create a downloader
     * @param url File URL
     * @param async Should we run the downloader in a Thread ?
     */
    public AbstractDownloader(final String url, final File outputFile, final boolean async) {
        this.downloaderId = AbstractDownloader.DOWNLOADER_NEXT_ID++;
        this.downloadUrl = url;
        this.outputFile = outputFile;
        this.async = async;
    }

    protected Thread getDownloaderThread() {
        return this.downloaderThread;
    }

    public int getDownloaderId() {
        return downloaderId;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public boolean isAsync() {
        return this.async;
    }

}
