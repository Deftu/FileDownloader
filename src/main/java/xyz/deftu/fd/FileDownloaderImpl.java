package xyz.deftu.fd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Objects;
import java.util.function.Consumer;

class FileDownloaderImpl implements FileDownloader {
    private boolean caches = Constants.caches;
    private Consumer<Long> transferCallback = Constants.transferCallback;
    private String userAgent;
    private int timeout = Constants.timeout;

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
        HttpURLConnection connection = ConnectionHelper.createConnection(url, caches, userAgent, timeout);
        try (InputStream stream = connection.getInputStream()) {
            File tempFile = FileHelper.createTemporaryFile(tempDir);
            while (tempFile.exists()) tempFile = FileHelper.createTemporaryFile(tempDir);
            if (!tempFile.exists()) tempFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            ReadableByteChannel streamChannel = Channels.newChannel(stream);
            long progress;
            while ((progress = fileOutputStream.getChannel().transferFrom(streamChannel, 0, Long.MAX_VALUE)) > 0)
                if (transferCallback != null)
                    transferCallback.accept(progress);
            this.tempFile = tempFile;
            fileOutputStream.flush();
            fileOutputStream.close();
            streamChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            downloaded = true;
            state = FileDownloadState.DOWNLOADED;
        }
    }

    public void validate() {
        if (state != FileDownloadState.DOWNLOADED) throw new IllegalStateException("Validation must take place after download.");
        doingValidation = true;
        failedValidation = Objects.equals(HashingHelper.fetchChecksum(tempFile), HashingHelper.fetchChecksum(existingFile));
        state = FileDownloadState.VALIDATED;
    }

    public void complete(File newFile) {
        if (state == FileDownloadState.DOWNLOADED || state == FileDownloadState.VALIDATED) {
            if (downloaded && (!doingValidation || !failedValidation)) {
                if (existingFile != null && existingFile.exists()) existingFile.delete();
                if (!tempFile.renameTo(newFile)) throw new IllegalStateException("Failed to move downloaded file.");
                state = FileDownloadState.COMPLETED;
            } else if (tempFile != null) tempFile.delete();
        } else throw new IllegalStateException("Completion must take place after download or validation.");
    }

    public FileDownloader withCaches(boolean caches) {
        this.caches = caches;
        return this;
    }

    public FileDownloader withTransferCallback(Consumer<Long> transferCallback) {
        this.transferCallback = transferCallback;
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

    public FileDownloadState getState() {
        return state;
    }
}