package com.dandan2611.downloadup.threads;

import com.dandan2611.downloadup.downloaders.AbstractDownloader;

import java.util.ArrayList;

public class ThreadsManager {

    private static ArrayList<Thread> createdThreads;

    static {
        createdThreads = new ArrayList<>();
    }

    /**
     * Create and store thread for downloader
     * @param downloader
     * @param runnable
     * @return
     */
    public static Thread createThread(AbstractDownloader downloader, Runnable runnable) {
        final String downloaderClassName = downloader.getClass().getName();
        final String downloaderId = String.valueOf(downloader.getDownloaderId());
        final String threadName = "downloader-" + downloaderClassName + "-" + downloaderId;

        final Thread thread = new Thread(runnable, threadName);
        createdThreads.add(thread);

        return thread;
    }

}
