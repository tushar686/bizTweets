package com.biztweets.formatter;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ElasticseachClient {
	Settings settings;
	Client client;
	
	public static void main(String[] args) {
		/*RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        rt.getMessageConverters().add(new StringHttpMessageConverter());

       String uri = "http://192.168.1.8:9300/twitter/tweet/1";

        String returns = rt.getForObject(uri, String.class);
        System.out.println(returns);*/
		
		ElasticseachClient elasticsearchClient = new ElasticseachClient();	
		elasticsearchClient.startElasticsearchConnection();
		
		long start = System.currentTimeMillis();
		GetResponse response = elasticsearchClient.client.prepareGet("twitter", "tweet", "2")
		        .execute()
		        .actionGet();
		long end = System.currentTimeMillis();
		System.out.println(response.getSourceAsString());
		System.out.println(end-start);
		elasticsearchClient.closeElasticsearchConnection();
		

	}
	
	public void startElasticsearchConnection() {
		settings = ImmutableSettings.settingsBuilder()
		        .put("cluster.name", "tusharcluster").build();
		
		client = new TransportClient(settings)
        .addTransportAddress(new InetSocketTransportAddress("192.168.1.8", 9300));
	}
	
	public void closeElasticsearchConnection() {
		client.close();
	}

}
