package xyz.deftu.fd;

import java.util.function.Consumer;

class Constants {
    final static DownloadCallback downloadCallback = new JvmDownloadCallback();
    final static boolean caches = true;
    final static Consumer<Long> transferCallback = (progress) -> {};
    final static int timeout = 30 * 1000;
}