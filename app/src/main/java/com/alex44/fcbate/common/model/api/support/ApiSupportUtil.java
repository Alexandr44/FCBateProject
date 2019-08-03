package com.alex44.fcbate.common.model.api.support;

import com.alex44.fcbate.common.model.ISystemInfo;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import timber.log.Timber;

/**
 * Fixes error like:
 * javax.net.ssl.SSLHandshakeException: javax.net.ssl.SSLProtocolException: SSL handshake aborted: ssl=0x58ff16d8: Failure in SSL library, usually a protocol error
 * error:1407742E:SSL routines:SSL23_GET_SERVER_HELLO:tlsv1 alert protocol version (external/openssl/ssl/s23_clnt.c:744 0x5d576c58:0x00000000)
 */
public class ApiSupportUtil {

    public static OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder client, ISystemInfo systemInfo) {
        if (systemInfo.needToConfigSSL()) {
            try {
                SSLContext sc = SSLContext.getInstance("TLSv1.2");
                sc.init(null, null, null);

                X509TrustManager trustManager = getTrustManager();
                client.sslSocketFactory(new Tls12SocketFactory(sc.getSocketFactory()), trustManager);

                ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2).build();

                List<ConnectionSpec> specs = new ArrayList<>();
                specs.add(cs);
                specs.add(ConnectionSpec.COMPATIBLE_TLS);
                specs.add(ConnectionSpec.CLEARTEXT);

                client.connectionSpecs(specs);
            } catch (Exception exc) {
                Timber.e(exc, "Error while setting TLS 1.2");
            }
        }

        return client;
    }

    private static X509TrustManager getTrustManager() {
        TrustManager[] trustManagers = null;
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            trustManagers = trustManagerFactory.getTrustManagers();
        }
        catch (NoSuchAlgorithmException e) {
            Timber.e(e, "TrustManagerFactory getInstance Exception");
        }
        catch (KeyStoreException e) {
            Timber.e(e, "TrustManagerFactory init Exception");
        }
        if (trustManagers == null || trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

}
