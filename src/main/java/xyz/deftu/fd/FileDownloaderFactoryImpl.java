package xyz.deftu.fd;

import java.io.File;

class FileDownloaderFactoryImpl implements FileDownloaderFactory {
    private DownloadCallback downloadCallback = Constants.downloadCallback;
    private boolean caches = Constants.caches;
    private String userAgent;
    private int timeout = Constants.timeout;

    public FileDownloader create(File tempDir, File existingFile) {
        return FileDownloader.create(tempDir, existingFile)
                .withDownloadCallback(downloadCallback)
                .withCaches(caches)
                .withUserAgent(userAgent)
                .withTimeout(timeout);
    }

    public FileDownloader create(File tempDir) {
        return FileDownloader.create(tempDir)
                .withDownloadCallback(downloadCallback)
                .withCaches(caches)
                .withUserAgent(userAgent)
                .withTimeout(timeout);
    }

    public FileDownloaderFactory withDownloadCallback(DownloadCallback callback) {
        this.downloadCallback = callback;
        return this;
    }

    public FileDownloaderFactory withCaches(boolean caches) {
        this.caches = caches;
        return this;
    }

    public FileDownloaderFactory withUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public FileDownloaderFactory withTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }
}