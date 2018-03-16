package ru.ic218.wallpapermanager.data.remote.client;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okio.Buffer;

/**
 * @author Nikolay Vlaskin on 15.03.2018.
 */


public final class CustomTrust {
    private static OkHttpClient InsecureHttpClient;

    public static OkHttpClient client() {
        if (InsecureHttpClient == null) {
            try {
                InsecureHttpClient = insecureOkHttpClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return InsecureHttpClient;
    }

    private static OkHttpClient insecureOkHttpClient(){
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] { trustManager }, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        return new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .build();
    }

    /**
     * Returns an input stream containing one or more certificate PEM files. This implementation just
     * embeds the PEM files in Java strings; most applications will instead read this from a resource
     * file that gets bundled with the application.
     */
    private static InputStream trustedCertificatesInputStream() {
        // PEM files for root certificates of Comodo and Entrust. These two CAs are sufficient to view
        // https://publicobject.com (Comodo) and https://squareup.com (Entrust). But they aren't
        // sufficient to connect to most HTTPS sites including https://godaddy.com and https://visa.com.
        // Typically developers will need to get a PEM file from their organization's TLS administrator.
        String comodoRsaCertificationAuthority = ""
                + "-----BEGIN CERTIFICATE-----\n" +
                "MIIHBzCCBe+gAwIBAgISA4SpRqY8pg+Bw+jjrcVmasL4MA0GCSqGSIb3DQEBCwUA\n" +
                "MEoxCzAJBgNVBAYTAlVTMRYwFAYDVQQKEw1MZXQncyBFbmNyeXB0MSMwIQYDVQQD\n" +
                "ExpMZXQncyBFbmNyeXB0IEF1dGhvcml0eSBYMzAeFw0xODAxMjUyMTQ1MzFaFw0x\n" +
                "ODA0MjUyMTQ1MzFaMBYxFDASBgNVBAMTC3BpeGFiYXkuY29tMIIBIjANBgkqhkiG\n" +
                "9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw8SZ++VLkzgBG98zgAjS9rLm6LJh9Wr+hYIo\n" +
                "LlVGjc8JQwh4O3mfk+AJGYnfFb1QvJREBRdXQpRAKQYslLMxkHDgwSA5Ioq6d2Lx\n" +
                "9xXJnl5TP5YBllch59gaj1YjVDyDPrQ4cWIhCbh8mZe38W66CP+a0JpAcCHBYLqo\n" +
                "oT996StHdGypCrhBp31wzOKhlA3K9CBV2QaHDnxlUvbsUPUDutoi1cT2uyU8nK18\n" +
                "5KG6HUKdOxqxUBU3hDkiO3Fx3fHFF5VfWFK6qNzLQrLuLO4P/xIrkeKjFQtF7s3Y\n" +
                "7gJfG23Ba/FwPlhcXYAf6c1XGFWNlnUjzUTeRShYh8KD7iWNVwIDAQABo4IEGTCC\n" +
                "BBUwDgYDVR0PAQH/BAQDAgWgMB0GA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcD\n" +
                "AjAMBgNVHRMBAf8EAjAAMB0GA1UdDgQWBBQz0dIe1++etOVopeDmFBvj05/kADAf\n" +
                "BgNVHSMEGDAWgBSoSmpjBH3duubRObemRWXv86jsoTBvBggrBgEFBQcBAQRjMGEw\n" +
                "LgYIKwYBBQUHMAGGImh0dHA6Ly9vY3NwLmludC14My5sZXRzZW5jcnlwdC5vcmcw\n" +
                "LwYIKwYBBQUHMAKGI2h0dHA6Ly9jZXJ0LmludC14My5sZXRzZW5jcnlwdC5vcmcv\n" +
                "MIICIgYDVR0RBIICGTCCAhWCEmFkdmVudG15ZnJpZW5kLmNvbYIPY2RuLnBpeGFi\n" +
                "YXkuY29tgg9kYi5wYWdld2l6ei5jb22CDmRiLnBpeGFiYXkuY29tghNnb29kaWVz\n" +
                "LnBpeGFiYXkuY29tghFtYXBzLWZvci1mcmVlLmNvbYIPbW4ucGFnZXdpenouY29t\n" +
                "ghBtbjAxLnBpeGFiYXkuY29tghBtbjAyLnBpeGFiYXkuY29tghBtbjAzLnBpeGFi\n" +
                "YXkuY29tghBtbjA0LnBpeGFiYXkuY29tghBtbjA1LnBpeGFiYXkuY29tghBtbjA2\n" +
                "LnBpeGFiYXkuY29tggxwYWdld2l6ei5jb22CC3BhZ2V3aXp6LmRlggtwaXhhYmF5\n" +
                "LmNvbYIKcGl4YWJheS5kZYINcGl4YWNhcmRzLmNvbYILcGl4YWpvZS5jb22CC3Bs\n" +
                "YWluanMuY29tghZzYWZlc2VhcmNoLnBpeGFiYXkuY29tggt3aXp6bGV5LmNvbYIW\n" +
                "d3d3LmFkdmVudG15ZnJpZW5kLmNvbYIVd3d3Lm1hcHMtZm9yLWZyZWUuY29tghB3\n" +
                "d3cucGFnZXdpenouY29tgg93d3cucGFnZXdpenouZGWCD3d3dy5waXhhYmF5LmNv\n" +
                "bYIOd3d3LnBpeGFiYXkuZGWCEXd3dy5waXhhY2FyZHMuY29tgg93d3cucGxhaW5q\n" +
                "cy5jb22CD3d3dy53aXp6bGV5LmNvbTCB/gYDVR0gBIH2MIHzMAgGBmeBDAECATCB\n" +
                "5gYLKwYBBAGC3xMBAQEwgdYwJgYIKwYBBQUHAgEWGmh0dHA6Ly9jcHMubGV0c2Vu\n" +
                "Y3J5cHQub3JnMIGrBggrBgEFBQcCAjCBngyBm1RoaXMgQ2VydGlmaWNhdGUgbWF5\n" +
                "IG9ubHkgYmUgcmVsaWVkIHVwb24gYnkgUmVseWluZyBQYXJ0aWVzIGFuZCBvbmx5\n" +
                "IGluIGFjY29yZGFuY2Ugd2l0aCB0aGUgQ2VydGlmaWNhdGUgUG9saWN5IGZvdW5k\n" +
                "IGF0IGh0dHBzOi8vbGV0c2VuY3J5cHQub3JnL3JlcG9zaXRvcnkvMA0GCSqGSIb3\n" +
                "DQEBCwUAA4IBAQAAhXwBACJAvbqSCEHfjV8pVgxZZnoJTO1OwRpZXq6ihxg4BKsV\n" +
                "+PMGqeoJDxb5WwJP/F9F/9dc4phpbCBGOcq21RbiOqfzssTK6qKr0N6S9ywRz+Gb\n" +
                "sgh8Ccs8qEyf68fBYEKJS3g7zV7WE2fPV7PJgkvUTQ+k106F3qrrbOuHVLJxaCkY\n" +
                "lGhlr+zGRZnThop1y2l4CWDodkQMUthBM4OSXPrLlwku1nbQPcn4FVZxKB6Eckrw\n" +
                "bdGC2mpuRVvs/mbnOZCUS9bAcitdKG5yb2dZfCZ8tIBXqEJS5q13WjQQ6AImXU50\n" +
                "ia6Ub8lFQhmn+QH8Iu0pUxjJ3yORYpxkH3C9\n" +
                "-----END CERTIFICATE-----\n";
        String entrustRootCertificateAuthority = ""
                + "-----BEGIN CERTIFICATE-----\n" +
                "MIIEkjCCA3qgAwIBAgIQCgFBQgAAAVOFc2oLheynCDANBgkqhkiG9w0BAQsFADA/\n" +
                "MSQwIgYDVQQKExtEaWdpdGFsIFNpZ25hdHVyZSBUcnVzdCBDby4xFzAVBgNVBAMT\n" +
                "DkRTVCBSb290IENBIFgzMB4XDTE2MDMxNzE2NDA0NloXDTIxMDMxNzE2NDA0Nlow\n" +
                "SjELMAkGA1UEBhMCVVMxFjAUBgNVBAoTDUxldCdzIEVuY3J5cHQxIzAhBgNVBAMT\n" +
                "GkxldCdzIEVuY3J5cHQgQXV0aG9yaXR5IFgzMIIBIjANBgkqhkiG9w0BAQEFAAOC\n" +
                "AQ8AMIIBCgKCAQEAnNMM8FrlLke3cl03g7NoYzDq1zUmGSXhvb418XCSL7e4S0EF\n" +
                "q6meNQhY7LEqxGiHC6PjdeTm86dicbp5gWAf15Gan/PQeGdxyGkOlZHP/uaZ6WA8\n" +
                "SMx+yk13EiSdRxta67nsHjcAHJyse6cF6s5K671B5TaYucv9bTyWaN8jKkKQDIZ0\n" +
                "Z8h/pZq4UmEUEz9l6YKHy9v6Dlb2honzhT+Xhq+w3Brvaw2VFn3EK6BlspkENnWA\n" +
                "a6xK8xuQSXgvopZPKiAlKQTGdMDQMc2PMTiVFrqoM7hD8bEfwzB/onkxEz0tNvjj\n" +
                "/PIzark5McWvxI0NHWQWM6r6hCm21AvA2H3DkwIDAQABo4IBfTCCAXkwEgYDVR0T\n" +
                "AQH/BAgwBgEB/wIBADAOBgNVHQ8BAf8EBAMCAYYwfwYIKwYBBQUHAQEEczBxMDIG\n" +
                "CCsGAQUFBzABhiZodHRwOi8vaXNyZy50cnVzdGlkLm9jc3AuaWRlbnRydXN0LmNv\n" +
                "bTA7BggrBgEFBQcwAoYvaHR0cDovL2FwcHMuaWRlbnRydXN0LmNvbS9yb290cy9k\n" +
                "c3Ryb290Y2F4My5wN2MwHwYDVR0jBBgwFoAUxKexpHsscfrb4UuQdf/EFWCFiRAw\n" +
                "VAYDVR0gBE0wSzAIBgZngQwBAgEwPwYLKwYBBAGC3xMBAQEwMDAuBggrBgEFBQcC\n" +
                "ARYiaHR0cDovL2Nwcy5yb290LXgxLmxldHNlbmNyeXB0Lm9yZzA8BgNVHR8ENTAz\n" +
                "MDGgL6AthitodHRwOi8vY3JsLmlkZW50cnVzdC5jb20vRFNUUk9PVENBWDNDUkwu\n" +
                "Y3JsMB0GA1UdDgQWBBSoSmpjBH3duubRObemRWXv86jsoTANBgkqhkiG9w0BAQsF\n" +
                "AAOCAQEA3TPXEfNjWDjdGBX7CVW+dla5cEilaUcne8IkCJLxWh9KEik3JHRRHGJo\n" +
                "uM2VcGfl96S8TihRzZvoroed6ti6WqEBmtzw3Wodatg+VyOeph4EYpr/1wXKtx8/\n" +
                "wApIvJSwtmVi4MFU5aMqrSDE6ea73Mj2tcMyo5jMd6jmeWUHK8so/joWUoHOUgwu\n" +
                "X4Po1QYz+3dszkDqMp4fklxBwXRsW10KXzPMTZ+sOPAveyxindmjkW8lGy+QsRlG\n" +
                "PfZ+G6Z6h7mjem0Y+iWlkYcV4PIWL1iwBi8saCbGS5jN2p8M+X+Q7UNKEkROb3N6\n" +
                "KOqkqm57TH2H3eDJAkSnh6/DNFu0Qg==\n" +
                "-----END CERTIFICATE-----\n";
        return new Buffer()
                .writeUtf8(comodoRsaCertificationAuthority)
                .writeUtf8(entrustRootCertificateAuthority)
                .inputStream();
    }

    /**
     * Returns a trust manager that trusts {@code certificates} and none other. HTTPS services whose
     * certificates have not been signed by these certificates will fail with a {@code
     * SSLHandshakeException}.
     *
     * <p>This can be used to replace the host platform's built-in trusted certificates with a custom
     * set. This is useful in development where certificate authority-trusted certificates aren't
     * available. Or in production, to avoid reliance on third-party certificate authorities.
     *
     * <p>See also {@link CertificatePinner}, which can limit trusted certificates while still using
     * the host platform's built-in trust store.
     *
     * <h3>Warning: Customizing Trusted Certificates is Dangerous!</h3>
     *
     * <p>Relying on your own trusted certificates limits your server team's ability to update their
     * TLS certificates. By installing a specific set of trusted certificates, you take on additional
     * operational complexity and limit your ability to migrate between certificate authorities. Do
     * not use custom trusted certificates in production without the blessing of your server's TLS
     * administrator.
     */
    private static X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private static KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
