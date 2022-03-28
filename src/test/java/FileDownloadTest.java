import org.junit.jupiter.api.Test;
import xyz.deftu.fd.FileDownloader;
import xyz.deftu.fd.FileDownloaderFactory;
import xyz.deftu.fd.OkHttpDownloadCallback;

import java.io.File;

public class FileDownloadTest {
    @Test public void test() {
        FileDownloaderFactory factory = FileDownloaderFactory.create()
                .withDownloadCallback(new OkHttpDownloadCallback())
                .withUserAgent("Mozilla/5.0 (File Downloader Test)");
        File tempDir = new File("Temporary");
        File sampleFile = new File("sample.json");
        if (!tempDir.exists()) tempDir.mkdirs();
        FileDownloader downloader = factory.create(tempDir, sampleFile);
        String url = "https://filesamples.com/samples/code/json/sample2.json";
        downloader.download(url);
        downloader.validate();
        downloader.complete(sampleFile);
    }
}