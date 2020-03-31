package com.dandan2611.downloadup.hook;

import com.dandan2611.downloadup.downloaders.AbstractDownloader;

public abstract class DownloaderHook {

    public void onDownloadPrepared(AbstractDownloader downloader) {}
    public void onDownloadDisposed(AbstractDownloader downloader) {}
    public void onDownloadStarted(AbstractDownloader downloader) {}
    public void onDownloadStopped(AbstractDownloader downloader) {}
    public void onDownloadPaused(AbstractDownloader downloader) {}
    public void onDownloadResumed(AbstractDownloader downloader) {}
    public void onDownloadFinished(AbstractDownloader downloader) {}

}
