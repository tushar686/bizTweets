package com.biztweets.formatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexResponse;
import org.jongo.Jongo;

import com.biztweets.model.Metadata;
import com.biztweets.model.Tweets;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;


public class CSVToJSON {
	private static final String DELIMITER = ",";
	private Jongo jongo;
	ElasticseachClient elasticsearchClient;
	ObjectMapper objMapper;
	
	public CSVToJSON() {
		elasticsearchClient = new ElasticseachClient();	
		elasticsearchClient.startElasticsearchConnection();
		objMapper = new ObjectMapper();
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//jongo = new Jongo(new MongoClient().getDB("bizTweets"));
	}
	
	public static void main(String[] args) throws UnknownHostException {
		CSVToJSON convertor = new CSVToJSON();
		for(File file : new File(args[0]).listFiles()) {
			if(file.getName().endsWith(".csv")) {
				convertor.readCsvToTweets(file);
			}
		}
		convertor.elasticsearchClient.closeElasticsearchConnection();
	}
	
	public void readCsvToTweets(File csvFile) throws UnknownHostException {
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			String[] header;
			header = br.readLine().split(DELIMITER);
			while ((line = br.readLine()) != null) {
				Tweets tweets = new Tweets(csvFile.getName().substring(0, csvFile.getName().indexOf('.')), createMetadata(csvFile, line, header), generateRandomTimeStamp());
				writeToElasticsearch(tweets);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	private void writeToElasticsearch(Tweets tweets) throws ElasticsearchException, JsonProcessingException {
		long start = System.currentTimeMillis();
		IndexResponse response = elasticsearchClient.client.prepareIndex("biztweets", "tweets")
		        .setSource(objMapper.writeValueAsString(tweets))
		        .execute()
		        .actionGet();
		System.out.println(System.currentTimeMillis() - start);
	}
	
	private void writeToMongo(Tweets tweets) {
		//jongo.getCollection("tweets").save(tweets);
		//jongo.getCollection("tweetsW").save(tweets);
	}


	
	private List<Metadata> createMetadata(File csvFile, String line, String[] header) {
		List<Metadata> metadata = new ArrayList<>();
		    String[] values = line.split(DELIMITER);
		    for(int i=0; i<values.length; i++) {
		    	metadata.add(new Metadata(generateFieldNameFromHeaderLine(header, i), values[i]));
		    }
		return metadata;
	}

	private String generateFieldNameFromHeaderLine(String[] header, int i) {
		if(i<header.length) {
			return header[i];
		} 
		return "field" + i;
	}
	
	private Timestamp generateRandomTimeStamp() {
		long offset = Timestamp.valueOf("2010-01-01 00:00:00").getTime();
		long end = Timestamp.valueOf("2014-03-01 00:00:00").getTime();
		long diff = end - offset + 1;
		return new Timestamp(offset + (long)(Math.random() * diff));
	}
}

