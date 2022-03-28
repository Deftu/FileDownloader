package xyz.deftu.fd;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.function.Consumer;

public class OkHttpDownloadCallback implements DownloadCallback {
    private final OkHttpClient client;
    private final Consumer<Long> transferCallback;

    public OkHttpDownloadCallback() {
        this(new OkHttpClient());
    }

    public OkHttpDownloadCallback(OkHttpClient client) {
        this(client, progress -> {
        });
    }

    public OkHttpDownloadCallback(OkHttpClient client, Consumer<Long> transferCallback) {
        this.client = client;
        this.transferCallback = transferCallback;
    }

    public boolean download(FileDownloader downloader, String url) {
        FileDownloaderImpl impl = (FileDownloaderImpl) downloader;
        Request.Builder request = new Request.Builder()
                .url(url)
                .get();
        if (!downloader.getUserAgent().isEmpty())
            request.header("User-Agent", downloader.getUserAgent());
        try (Response response = client.newCall(request.build()).execute()) {
            if (response.code() != HttpURLConnection.HTTP_OK)
                return false;
            try (ResponseBody body = response.body()) {
                if (body == null)
                    return false;
                File tempFile = FileHelper.createTemporaryFile(impl.getTempDir());
                while (tempFile.exists()) tempFile = FileHelper.createTemporaryFile(impl.getTempDir());
                if (!tempFile.exists()) tempFile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
                ReadableByteChannel streamChannel = Channels.newChannel(body.byteStream());
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