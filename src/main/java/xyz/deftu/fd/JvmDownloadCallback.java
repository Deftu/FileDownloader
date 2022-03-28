package xyz.deftu.fd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.function.Consumer;

public class JvmDownloadCallback implements DownloadCallback {
    private final Consumer<Long> transferCallback;

    public JvmDownloadCallback() {
        this(progress -> {
        });
    }

    public JvmDownloadCallback(Consumer<Long> transferCallback) {
        this.transferCallback = transferCallback;
    }

    public boolean download(FileDownloader downloader, String url) {
        FileDownloaderImpl impl = (FileDownloaderImpl) downloader;
        HttpURLConnection connection = ConnectionHelper.createConnection(url, downloader.isCached(), downloader.getUserAgent(), downloader.getTimeout());
        try {
            if (connection == null)
                return false;
            if (connection.getResponseCode() != 200)
                return false;
            try (InputStream stream = connection.getInputStream()) {
                File tempFile = FileHelper.createTemporaryFile(impl.getTempDir());
                while (tempFile.exists()) tempFile = FileHelper.createTemporaryFile(impl.getTempDir());
                if (!tempFile.exists()) tempFile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
                ReadableByteChannel streamChannel = Channels.newChannel(stream);
                long progress;
                while ((progress = fileOutputStream.getChannel().transferFrom(streamChannel, 0, Long.MAX_VALUE)) > 0)
                    if (transferCallback != null)
                        transferCallback.accept(progress);
                impl.setTempFile(tempFile);
                fileOutputStream.flush();
                fileOutputStream.close();
                streamChannel.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}