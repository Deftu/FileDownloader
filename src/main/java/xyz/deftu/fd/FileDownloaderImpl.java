package xyz.deftu.fd;

import java.io.File;
import java.util.Objects;

class FileDownloaderImpl implements FileDownloader {
    private boolean caches = Constants.caches;
    private String userAgent;
    private int timeout = Constants.timeout;
    private int bufferSize = Constants.bufferSize;

    private FileDownloadState state;
    private File tempDir;
    private File existingFile;

    private boolean downloaded;
    private File tempFile;

    private boolean doingValidation;
    private boolean failedValidation;

    public void initialize(File tempDir, File existingFile) {
        if (state != null) throw new IllegalStateException("Cannot initialize file downloader after a state has been set.");
        this.tempDir = tempDir;
        this.existingFile = existingFile;
        state = FileDownloadState.INITIALIZED;
    }

    public void download(String url) {
        if (state != FileDownloadState.INITIALIZED) throw new IllegalStateException("Download must take place after initialization");
    }

    public void validate() {
        if (state != FileDownloadState.DOWNLOADED) throw new IllegalStateException("Validation must take place after download.");
        doingValidation = true;
        // TODO - failedValidation = existingFile != null && existingFile.exists() && !Objects.equals(fetchChecksum(file.toPath()), fetchChecksum(existingFile.toPath()));
        state = FileDownloadState.VALIDATED;
    }

    public void complete(File newFile) {
        if (state == FileDownloadState.DOWNLOADED || state == FileDownloadState.VALIDATED) {

        } else throw new IllegalStateException("Completion must take place after download or validation.");
    }

    public FileDownloader withCaches(boolean caches) {
        this.caches = caches;
        return this;
    }

    public FileDownloader withUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public FileDownloader withTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public FileDownloader withBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
        return this;
    }

    public FileDownloadState getState() {
        return state;
    }
}