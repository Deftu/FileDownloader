import org.junit.jupiter.api.Test;
import xyz.deftu.fd.FileDownloader;
import xyz.deftu.fd.FileDownloaderFactory;

import java.io.File;

public class FileDownloadTest {
    @Test public void test() {
        FileDownloaderFactory factory = FileDownloaderFactory.create()
                .withUserAgent("Mozilla/5.0 (File Downloader Test)");
        FileDownloader downloader = factory.create(new File("Temporary"));
    }
}