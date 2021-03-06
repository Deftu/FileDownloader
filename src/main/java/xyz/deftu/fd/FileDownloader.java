package xyz.deftu.fd;

import java.io.File;
import java.util.function.Consumer;

public interface FileDownloader {
    // Internals.

    /**
     * Initializes this {@link FileDownloader} so it can be used.
     *
     * <b>Do not call this unless you know what you're doing!</b>
     *
     * @param tempDir The temporary directory for downloads.
     * @param existingFile The already existing file, may be null if there is none.
     */
    void initialize(File tempDir, File existingFile);

    // Usage.

    /**
     * Downloads the file from the URL.
     *
     * @param url The URL of the file to download, may be a redirect.
     */
    void download(String url);

    /**
     * Validates that the file downloaded and the existing file are different MD5 hash checksums.
     */
    void validate();

    /**
     * Moves and renames the temporary file to replace the existing file.
     *
     * @param newFile The new file that the temporary file will be converted into.
     */
    void complete(File newFile);

    // Additional properties.

    /**
     * @return This file downloader's downloader callback, this is what downloads the file initially.
     */
    DownloadCallback getDownloadCallback();

    /**
     * Applies a <b>callback</b> value to this {@link FileDownloader}.
     *
     * @param callback The value for <b>callback</b>.
     * @return The {@link FileDownloader} itself.
     */
    FileDownloader withDownloadCallback(DownloadCallback callback);

    /**
     * @return Whether this download is cached.
     */
    boolean isCached();

    /**
     * Applies a <b>caches</b> value to this {@link FileDownloader}.
     *
     * @param caches The value for <b>caches</b>.
     * @return The {@link FileDownloader} itself.
     */
    FileDownloader withCaches(boolean caches);

    /**
     * @return This file downloader's user agent.
     */
    String getUserAgent();

    /**
     * Applies a <b>userAgent</b> values to this {@link FileDownloader}.
     *
     * @param userAgent The value for <b>userAgent</b>.
     * @return The {@link FileDownloader} itself.
     */
    FileDownloader withUserAgent(String userAgent);

    /**
     * @return This file downloader's download timeout.
     */
    int getTimeout();

    /**
     * Applies a <b>timeout</b> values to this {@link FileDownloader}.
     *
     * @param timeout The value for <b>timeout</b>.
     * @return The {@link FileDownloader} itself.
     */
    FileDownloader withTimeout(int timeout);

    // Management.

    /**
     * @return The current state of this {@link FileDownloader}.
     */
    FileDownloadState getState();

    /**
     * Creates a new instance of a {@link FileDownloader}.
     *
     * @return A new instance of {@link FileDownloader}.
     */
    static FileDownloader create(File tempDir, File existingFile) {
        FileDownloader instance = new FileDownloaderImpl();
        instance.initialize(tempDir, existingFile);
        return instance;
    }

    /**
     * Creates a new instance of a {@link FileDownloader}.
     *
     * @return A new instance of {@link FileDownloader}.
     */
    static FileDownloader create(File tempDir) {
        return create(tempDir, null);
    }
}