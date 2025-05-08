package io.github.MulatramAwA.makei18ngreatagain.translator;

import io.github.MulatramAwA.makei18ngreatagain.Makei18ngreatagain;
import org.json.JSONArray;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import static io.github.MulatramAwA.makei18ngreatagain.Makei18ngreatagain.LOGGER;

public class BingTranslator {
    private String ig,iid,key,token;
    public BingTranslator(){
        try{
            URL url= new URL("https://cn.bing.com/translator?ref=TThis&text=&from=en&to=zh-Hans");
            HttpsURLConnection connection=(HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/135.0.0.0 Safari/537.36 Edg/135.0.0.0");
            connection.setConnectTimeout(5000);
            try{
                String s=conn2str(connection);
                Pattern pattern=Pattern.compile("IG:\"(\\w+)\"");
                LOGGER.info(s);
                this.ig=pattern.matcher(s).group(0);
                pattern=Pattern.compile("<div id=\"tta_outGDCont\" data-iid=\"([\\w.]+)\">");
                this.iid=pattern.matcher(s).group(0);
                pattern=Pattern.compile("var params_AbusePreventionHelper = \\[(\\d+),\"([^\"]+)\",\\d+\\];");
                this.key=pattern.matcher(s).group(1);
                this.token=pattern.matcher(s).group(2);
            }finally {
                connection.disconnect();
            }
        }catch (Exception e){
            LOGGER.error("Failed while initializing: {}", (Object) e.getStackTrace());
        }
    }
    public String getTranslate(String s){
        try {
            URL url=new URL("https://cn.bing.com/ttranslatev3?isVertical=1&&IG="+this.ig+"&IID="+this.iid);
            String data="fromLang=en&to=zh-Hans&text="+s+"&tryFetchingGenderDebiasedTranslations=true&token="+token+"&key="+key;
            HttpsURLConnection connection=(HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/135.0.0.0 Safari/537.36 Edg/135.0.0.0");
            connection.setRequestProperty("Content-Type","application/json; charset=utf-8");
            connection.setRequestProperty("Content-Length", String.valueOf(data.getBytes(StandardCharsets.UTF_8).length));
            connection.setConnectTimeout(5000);
            try{
                OutputStream outputStream=connection.getOutputStream();
                outputStream.write(data.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
                String str=conn2str(connection);
                JSONArray jsonArray=new JSONArray(str);
                return jsonArray.getJSONObject(0).getJSONArray("translations").getJSONObject(0).getString("text");
            }finally {
                connection.disconnect();
            }
        }catch (Exception e){
            LOGGER.error("Failed while getting translate of {}: {}",s,e.getMessage());
            return null;
        }
    }
    private String conn2str(HttpsURLConnection connection) throws IOException {
        InputStream inputStream=connection.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
        String s;
        StringBuilder buffer=new StringBuilder();
        while ((s=reader.readLine())!=null){
            buffer.append(s);
        }
        return buffer.toString();
    }
}
