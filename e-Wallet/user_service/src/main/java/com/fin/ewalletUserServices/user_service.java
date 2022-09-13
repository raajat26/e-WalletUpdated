package com.fin.ewalletUserServices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class user_service{
    private static Logger logger = LoggerFactory.getLogger(user_service.class);
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    RedisTemplate<String, Object> redisTemplate; //defining redis template

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate; //defining kafka template
    private static final String WALLET_CREATE_TOPIC = "walletCreate"; // string for generic data

    @Autowired
    user_Repository user_repo;
    private static String REDIS_USER_KEY_PREFIX = "user::";
    @Value("${user.account.create.default.Balance}")
    int defaultBalanceForUser;
    public void createUser(user_impl user) throws JsonProcessingException {
        // 1. create an entry in the DB
        // 2. enable/create wallet for this user
        user_repo.save(user); // save user details in DB, SQL
        logger.info("Data Successfully saved into DB, now saving into Redis");
        redisTemplate.opsForValue().set(REDIS_USER_KEY_PREFIX + user.getUserId(), user); // store the keys ad values to the Redis server
        logger.info("Data Saved Into Redis, proceeding further to kafka messaging to Wallet Service");

        /*
            Now constructing the data so that we can send it via kafkatemplate easily.

         */
        JSONObject json = new JSONObject();
        json.put("userId", user.getUserId());
        json.put("balance",defaultBalanceForUser);

        kafkaTemplate.send(WALLET_CREATE_TOPIC, user.getUserId(), objectMapper.writeValueAsString(json)); //publish the kafka message
        // for now keeping the data as null because yet we dont know what wallet service will be expecting being consumer
        //++ if we want to send the above data to a particular topic then we can do this by removing the user.getUserId() also however it is recommended to use partitions and embedd key in that

    }

    public user_impl getUserId(String user_id){
        // search in the DB and then return the function
        user_impl user = (user_impl) redisTemplate.opsForValue().get(REDIS_USER_KEY_PREFIX+user_id); // fetching the data from redis on the basis of user_id and then typecasting to the user object
        if(user == null)
        { // if user is null in the redis then search in the database
            user = user_repo.findByUserId(user_id);


            if(user!=null)
            { // now if user is found in the database then store it in the redis for a particular tineperiod so that caching is used effectively
                redisTemplate.opsForValue().set(REDIS_USER_KEY_PREFIX + user.getUserId(), user);
            }
        }


        return user;

        /*      1. User Services
                after this start writing the
                2. Wallet Service
         */

//



    }

}