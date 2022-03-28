package xyz.deftu.fd;

import java.io.File;
import java.util.function.Consumer;

/**
 * A factory for instantiation of {@link FileDownloader}s,
 * this can be used to create multiple downloaders with
 * properties predefined.
 */
public interface FileDownloaderFactory {
    // Creating.

    /**
     * Creates a {@link FileDownloader} with predefined properties
     * from the factory.
     * 
     * @param tempDir The directory temporary downloads are stored in.
     * @param existingFile The existing file, may be null if there is none.
     * @return A new {@link FileDownloader} with predefined properties based on the factory.
     */
    FileDownloader create(File tempDir, File existingFile);

    /**
     * Creates a {@link FileDownloader} with predefined properties
     * from the factory.
     *
     * @param tempDir The directory temporary downloads are stored in.
     * @return A new {@link FileDownloader} with predefined properties based on the factory.
     */
    FileDownloader create(File tempDir);

    // Properties.

    /**
     * Applies predefined <b>callback</b> values to this {@link FileDownloaderFactory}.
     *
     * @param callback The predefined value for <b>callback</b>.
     * @return The {@link FileDownloaderFactory} itself.
     */
    FileDownloaderFactory withDownloadCallback(DownloadCallback callback);

    /**
     * Applies predefined <b>caches</b> values to this {@link FileDownloaderFactory}.
     * 
     * @param caches The predefined value for <b>caches</b>.
     * @return The {@link FileDownloaderFactory} itself.
     */
    FileDownloaderFactory withCaches(boolean caches);

    /**
     * Applies predefined <b>userAgent</b> values to this {@link FileDownloaderFactory}.
     *
     * @param userAgent The predefined value for <b>userAgent</b>.
     * @return The {@link FileDownloaderFactory} itself.
     */
    FileDownloaderFactory withUserAgent(String userAgent);

    /**
     * Applies predefined <b>timeout</b> values to this {@link FileDownloaderFactory}.
     *
     * @param timeout The predefined value for <b>timeout</b>.
     * @return The {@link FileDownloaderFactory} itself.
     */
    FileDownloaderFactory withTimeout(int timeout);

    /**
     * Creates a new instance of a {@link FileDownloaderFactory}.
     * 
     * @return A new instance of {@link FileDownloaderFactory}.
     */
    static FileDownloaderFactory create() {
        return new FileDownloaderFactoryImpl();
    }
}