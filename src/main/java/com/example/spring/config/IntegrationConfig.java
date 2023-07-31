package com.example.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.mongodb.store.MongoDbChannelMessageStore;
import org.springframework.integration.store.MessageGroupQueue;
import org.springframework.messaging.MessageChannel;


@Configuration
public class IntegrationConfig
{

	@Bean
	public MongoDbChannelMessageStore mongoDbChannelMessageStore(MongoDatabaseFactory databaseFactory)
	{
		return new MongoDbChannelMessageStore(databaseFactory);
	}

	@Bean
	public MessageChannel myExampleChannel(MongoDbChannelMessageStore mongoDbChannelMessageStore)
	{
		MessageGroupQueue messageGroupQueue = new MessageGroupQueue(mongoDbChannelMessageStore, "myExample");
		return new QueueChannel(messageGroupQueue);
	}
}
