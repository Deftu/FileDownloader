package xyz.deftu.fd;

public interface DownloadCallback {
    boolean download(FileDownloader downloader, String url);
}