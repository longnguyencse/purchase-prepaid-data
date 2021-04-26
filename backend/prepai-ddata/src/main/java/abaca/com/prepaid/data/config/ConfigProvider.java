package abaca.com.prepaid.data.config;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
public class ConfigProvider {

    @Value("${app.path-voucher.server}")
    private String voucherServerEndpoint;

    @Bean
    @Qualifier(value = "voucherServer")
    public Retrofit retrofit() {
        Dispatcher dispatcher = new Dispatcher(Executors.newFixedThreadPool(20));
        dispatcher.setMaxRequests(20);
        dispatcher.setMaxRequestsPerHost(1);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient =
                new OkHttpClient.Builder()
                        .dispatcher(dispatcher)
                        .addInterceptor(logging)
                        .connectionPool(new ConnectionPool(100, 30, TimeUnit.SECONDS))
                        .build();

        return new Retrofit.Builder()
                .baseUrl(HttpUrl.get(voucherServerEndpoint))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
