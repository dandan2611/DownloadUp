package com.dandan2611.downloadup.test;

import com.dandan2611.downloadup.downloaders.AbstractDownloader;
import com.dandan2611.downloadup.downloaders.JIODownloader;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class DownloadUpTester {

    public void testDownloaders() {

        final long startMillis = System.currentTimeMillis();

        final File testfilesDirectory = new File("testfiles/");
        testfilesDirectory.mkdirs();

        advert("Downloading test file with Java IO");

        try {
            final JIODownloader jioDownloader = new JIODownloader("http://ovh.net/files/10Mio.dat", new File(testfilesDirectory, "10mio-jio"), false);
            testDownloader(jioDownloader);
        }
        catch (Exception e) {
            System.err.println("Error occured while downloading test file");
            e.printStackTrace();
        }

        advert("Cleaning up...");

        try {
            FileUtils.cleanDirectory(testfilesDirectory);
            FileUtils.deleteDirectory(testfilesDirectory);
        }
        catch (Exception e) {
            System.err.println("Error occured while cleaning up test files");
            e.printStackTrace();
        }

        final long endMillis = System.currentTimeMillis();
        final long millisTaken = endMillis - startMillis;

        advert("Test complete in " + millisTaken + "ms");

    }

    private void testDownloader(AbstractDownloader downloader) throws Exception {
        System.out.println("Preparing downloader...");
        downloader.prepare();
        System.out.println("Launching download...");
        downloader.download();
    }

    private void advert(String title) {
        System.out.println("---------------------------------------------");
        System.out.println(title);
        System.out.println("---------------------------------------------");
    }

}
